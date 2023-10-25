package org.teamseven.hms.backend.booking.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.teamseven.hms.backend.booking.dto.*;
import org.teamseven.hms.backend.booking.entity.*;
import org.teamseven.hms.backend.catalog.dto.ServiceOverview;
import org.teamseven.hms.backend.catalog.entity.ServiceRepository;
import org.teamseven.hms.backend.catalog.service.CatalogService;
import org.teamseven.hms.backend.user.entity.Patient;
import org.teamseven.hms.backend.user.entity.PatientRepository;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

@Service
public class BookingService {

    private static final String SLOT_DELIMITER = ",";
    private static final String APPOINTMENT_DESCRIPTION_PREFIX = "Appointment with Dr. ";

    @Autowired private BookingRepository bookingRepository;

    // TODO sheila: decouple booking service from patient / user DAO in favour of convenience when
    // migrating to microservices. make sure that @service(s) either only depend on other controllers
    // or other services of different domains.
    @Autowired private PatientRepository patientRepository;

    @Autowired private CatalogService catalogService;

    @Autowired private AppointmentRepository appointmentRepository;

    @Autowired private TestRepository testRepository;

    @Autowired private ServiceRepository serviceRepository;

    public BookingPaginationResponse getBookingHistory(
            UUID patientId,
            int page,
            int pageSize
    ) {
        String patientName = getPatientName(patientId);

        int zeroBasedIndexPage = page - 1;
        Page<Booking> bookingPage = bookingRepository.findPatientBookingsWithPagination(
                patientId.toString(),
                Pageable.ofSize(pageSize).withPage(zeroBasedIndexPage)
        );

        List<BookingOverview> bookingList = bookingPage.map(getBookingOverview).toList();

        return BookingPaginationResponse
                .builder()
                .bookingList(bookingList)
                .totalElements(bookingPage.getTotalElements())
                .patientName(patientName)
                .build();
    }

    private String getPatientName(UUID patientId) {
        Optional<Patient> patient = patientRepository.findById(patientId);
        if (patient.isEmpty()) {
            throw new NoSuchElementException("Something went wrong when fetching patient records");
        }

        return patient.get().getUser().getName();
    }

    private final Function<Booking, BookingType> getBookingType = it ->
            it.getAppointment() != null ? BookingType.APPOINTMENT : BookingType.TEST;

    private final Function<Booking, String[]> getSlots = it ->
            it.getSlots().split(SLOT_DELIMITER);

    private final BiFunction<BookingType, String, String> getDescription = (
            bookingType,
            serviceName
    ) -> switch (bookingType) {
        case TEST -> serviceName;
        case APPOINTMENT -> APPOINTMENT_DESCRIPTION_PREFIX + serviceName;
    };


    private final Function<Booking, BookingOverview> getBookingOverview = it -> {
        ServiceOverview serviceOverview = catalogService.getServiceOverview(it.getServiceId());

        return BookingOverview
                .builder()
                .bookingDate(it.getReservedDate())
                .slots(getSlots.apply(it))
                .bookingId(it.getBookingId())
                .type(getBookingType.apply(it))
                .bookingDescription(
                        getDescription.apply(getBookingType.apply(it), serviceOverview.getName())
                )
                .build();
    };

    public Booking getBookingById(UUID id) {
        return bookingRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public BookingInfoResponse getBookingInfo(UUID id) {
        Booking booking = getBookingById(id);

        BookingType bookingType = getBookingType.apply(booking);

        return BookingInfoResponse
                .builder()
                .bookingType(bookingType)
                .bookingDate(booking.getReservedDate())
                .slots(getSlots.apply(booking))
                .details(getBookingDetails.apply(booking, bookingType))
                .build();
    }

    private final BiFunction<Booking, BookingType, BookingDetails> getBookingDetails = (booking, bookingType) -> {
        ServiceOverview serviceOverview = catalogService.getServiceOverview(booking.getServiceId());

        return switch (bookingType) {
            case TEST -> {
                Test test = booking.getTest();
                yield BookingDetails.Test.builder()
                        .testName(serviceOverview.getName())
                        .testResult(test.getTestReport())
                        .testId(test.getTestId().toString())
                        .build();
            }
            case APPOINTMENT -> {
                Appointment appointment = booking.getAppointment();
                yield BookingDetails.Appointment.builder()
                        .doctorName(serviceOverview.getName())
                        .department(serviceOverview.getDescription())
                        .comments(appointment.getDiagnosis())
                        .appointmentId(appointment.getAppointmentId().toString())
                        .build();
            }
        };
    };

    @Transactional
    public Booking reserveSlot(AddBookingRequest bookingRequest) {
        Booking booked = new Booking();
        if (Objects.equals(bookingRequest.getType(), BookingType.APPOINTMENT)) {
            if(bookingExists(bookingRequest.getAppointmentDate(), bookingRequest.getSelectedSlot())) {
                throw new IllegalStateException("Appointment already exists!");
            }
            var appointment = Appointment.builder()
                    .patientId(bookingRequest.getPatientId())
                    .isActive(true)
                    .build();
            appointmentRepository.save(appointment);
            booked = book(bookingRequest, appointment.getAppointmentId(), bookingRequest.getServiceId());
        } else if (Objects.equals(bookingRequest.getType(), BookingType.TEST)) {
            if(testExists(bookingRequest.getAppointmentDate(), bookingRequest.getPatientId())) {
                throw new IllegalStateException("Test already exists!");
            }
            var test = Test.builder()
                    .patientId(bookingRequest.getPatientId())
                    .testDate(bookingRequest.getAppointmentDate())
                    .status(TestStatus.PENDING)
                    .isActive(true)
                    .build();
            testRepository.save(test);
            booked = book(bookingRequest, test.getTestId(), bookingRequest.getServiceId());
        }
        return booked;
    }

    private boolean testExists(String date, UUID patientId) {
        Optional<Booking> booking = bookingRepository.checkTestExists(date, String.valueOf(patientId));
        return booking.isPresent();
    }

    private Booking book(AddBookingRequest bookingRequest, UUID id, UUID serviceId) {
        Appointment appointment = new Appointment();
        Test test = new Test();
        if(Objects.equals(bookingRequest.getType(), BookingType.APPOINTMENT)) {
            appointment.setAppointmentId(id);
        } else {
            test.setTestId(id);
        }
        var booking = Booking.builder()
                .patientId(bookingRequest.getPatientId())
                .appointment(Objects.equals(bookingRequest.getType(), BookingType.APPOINTMENT) ? appointment : null)
                .test(Objects.equals(bookingRequest.getType(), BookingType.TEST) ? test : null )
                .serviceId(serviceId)
                .billStatus(BillStatus.UNPAID)
                .slots(bookingRequest.getSelectedSlot())
                .gst(8L)
                .reservedDate(bookingRequest.getAppointmentDate())
                .isActive(true)
                .build();
        return bookingRepository.save(booking);
    }

    private boolean bookingExists(String date, String slot) {
        Optional<Booking> booking = bookingRepository.findByAppointmentDate(date, slot);
        return booking.isPresent();
    }
}
