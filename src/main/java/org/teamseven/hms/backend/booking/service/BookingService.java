package org.teamseven.hms.backend.booking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.teamseven.hms.backend.booking.dto.BookingOverview;
import org.teamseven.hms.backend.booking.dto.BookingPaginationResponse;
import org.teamseven.hms.backend.booking.dto.BookingType;
import org.teamseven.hms.backend.booking.entity.Booking;
import org.teamseven.hms.backend.booking.entity.BookingRepository;
import org.teamseven.hms.backend.user.entity.Patient;
import org.teamseven.hms.backend.user.entity.PatientRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
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
            it.getAppointmentId() != null ? BookingType.APPOINTMENT : BookingType.TEST;

    private final Function<Booking, String[]> getSlots = it ->
            it.getSlots().split(SLOT_DELIMITER);

    private final BiFunction<BookingType, String, String> getDescription = (
            bookingType,
            serviceName
    ) -> switch (bookingType) {
        case TEST -> serviceName;
        case APPOINTMENT -> APPOINTMENT_DESCRIPTION_PREFIX + serviceName;
    };


    private final Function<Booking, BookingOverview> getBookingOverview = it ->
            BookingOverview
                    .builder()
                    .bookingDate(it.getReservedDate())
                    .slots(getSlots.apply(it))
                    .bookingId(it.getBookingId())
                    .type(getBookingType.apply(it))
                    .bookingDescription(
                            getDescription.apply(getBookingType.apply(it), it.getService().getName())
                    )
                    .build();

    public Booking getBookingById(UUID id) {
        return bookingRepository.findById(id).orElse(null);
    }
}
