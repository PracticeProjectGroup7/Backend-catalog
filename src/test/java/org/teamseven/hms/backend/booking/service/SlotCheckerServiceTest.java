package org.teamseven.hms.backend.booking.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.teamseven.hms.backend.booking.dto.ServiceSlotInfo;
import org.teamseven.hms.backend.booking.entity.Booking;
import org.teamseven.hms.backend.booking.entity.BookingRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SlotCheckerServiceTest {
    @Mock
    private BookingRepository repository;

    @InjectMocks
    private SlotCheckerService service;

    @Test
    public void testGetServiceSlots_givenSomeBookedSlots_deriveAvailableSlots() throws ParseException {
        when(repository.findByServiceIdAndReservedDate(any(), any()))
                .thenReturn(
                        List.of(
                                Booking.builder()
                                        .slots("1").build(),
                                Booking.builder()
                                        .slots("2").build(),
                                Booking.builder()
                                        .slots("8").build()
                        )
                );

        UUID mockUuid = UUID.randomUUID();
        Date reservedDate = new SimpleDateFormat("yyyy-MM-dd").parse("2023-10-11");

        ServiceSlotInfo slotInfo = service.getServiceSlots(mockUuid, reservedDate);

        verify(repository).findByServiceIdAndReservedDate(mockUuid, "2023-10-11");

        assertEquals(Set.of(1, 2, 8), slotInfo.getBookedSlots());
        assertEquals(Set.of(3, 4, 5, 6, 7, 9, 10, 11, 12, 13, 14, 15, 16), slotInfo.getAvailableSlots());
    }
}
