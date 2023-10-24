package org.teamseven.hms.backend.booking.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.teamseven.hms.backend.booking.dto.BookingOverview;
import org.teamseven.hms.backend.booking.dto.BookingPaginationResponse;
import org.teamseven.hms.backend.booking.dto.BookingType;
import org.teamseven.hms.backend.booking.entity.Booking;
import org.teamseven.hms.backend.booking.entity.BookingRepository;
import org.teamseven.hms.backend.catalog.entity.Service;
import org.teamseven.hms.backend.user.User;
import org.teamseven.hms.backend.user.entity.Patient;
import org.teamseven.hms.backend.user.entity.PatientRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {
    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private BookingService bookingService;


    @Test
    public void testGetBookingHistory_patientNotFound_assertThrowsException() {
        Mockito.when(patientRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            bookingService.getBookingHistory(UUID.randomUUID(), 1, 10);
        });
    }

    @Test
    public void testGetBookingHistory_patientFound_returnFormattedDataFromRepository() throws ParseException {
        Mockito.when(patientRepository.findById(any()))
                .thenReturn(
                        Optional.of(
                                Patient.builder().user(
                                        User.builder().firstName("John").lastName("Doe").build()
                                ).build()
                        )
                );

        Mockito.when(bookingRepository.findPatientBookingsWithPagination(any(), any()))
                        .thenReturn(
                                new PageImpl(
                                        getMockBookingList(),
                                        PageRequest.of(0, 10),
                                        1L
                                )
                        );

        BookingPaginationResponse response = bookingService.getBookingHistory(UUID.randomUUID(), 1, 10);

        assertEquals(1, response.getTotalElements());
        assertEquals("John Doe", response.getPatientName());
        assertEquals(1, response.getBookingList().size());

        BookingOverview overview = response.getBookingList().get(0);
        assertEquals(UUID.fromString("efb5f4bf-75e3-49a5-8785-bb82b70029ed"), overview.getBookingId());
        assertEquals(BookingType.APPOINTMENT, overview.getType());
        assertEquals("Appointment with Dr. Loki", overview.getBookingDescription());
    }

    private List<Booking> getMockBookingList() throws ParseException {
        return List.of(
                Booking.builder()
                        .appointmentId(UUID.randomUUID())
                        .slots("1,2")
                        .bookingId(UUID.fromString("efb5f4bf-75e3-49a5-8785-bb82b70029ed"))
                        .service(Service.builder().name("Loki").build())
                        .reservedDate(new SimpleDateFormat("yyyy-MM-dd").parse("2023-12-11"))
                        .build()
        );
    }
}
