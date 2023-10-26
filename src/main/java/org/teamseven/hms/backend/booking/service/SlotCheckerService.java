package org.teamseven.hms.backend.booking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teamseven.hms.backend.booking.dto.ServiceSlotInfo;
import org.teamseven.hms.backend.booking.entity.Booking;
import org.teamseven.hms.backend.booking.entity.BookingRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class SlotCheckerService {
    @Autowired
    private BookingRepository repository;

    private static final int FIRST_SLOT_NUM = 1;
    private static final int LAST_SLOT_NUM = 16;

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public ServiceSlotInfo getServiceSlots(
            UUID serviceId,
            Date reservedDate
    ) {
        Logger.getAnonymousLogger().info(
                "beginning query. service id: "
                + serviceId + "reserved date: " + new SimpleDateFormat(DATE_FORMAT).format(reservedDate)
        );
        List<Booking> existingBookings = repository.findByServiceIdAndReservedDate(
                serviceId,
                new SimpleDateFormat(DATE_FORMAT).format(reservedDate)
        );

        Set<Integer> bookedSlots = existingBookings.stream()
                .map(it -> Integer.parseInt(it.getSlots()))
                .collect(Collectors.toSet());

        Set<Integer> availableSlots = IntStream.range(FIRST_SLOT_NUM, LAST_SLOT_NUM + 1)
                .boxed()
                .collect(Collectors.filtering(it -> !bookedSlots.contains(it), Collectors.toSet()));

        return ServiceSlotInfo
                .builder()
                .bookedSlots(bookedSlots)
                .availableSlots(availableSlots)
                .build();
    }
}
