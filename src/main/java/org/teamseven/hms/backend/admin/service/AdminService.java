package org.teamseven.hms.backend.admin.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teamseven.hms.backend.admin.dto.ModifyBookingRequest;
import org.teamseven.hms.backend.admin.dto.ModifyTestRequest;
import org.teamseven.hms.backend.booking.entity.Booking;
import org.teamseven.hms.backend.booking.entity.BookingRepository;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired private BookingRepository bookingRepository;

    @Transactional
    public boolean modifyBooking(ModifyBookingRequest modifyBookingRequest) {
        Optional<Booking> existingBooking = bookingRepository
                .findByServiceIdAndReservedDateAndSlot(
                        modifyBookingRequest.getServiceId(),
                        modifyBookingRequest.getNewReservedDate(),
                        modifyBookingRequest.getNewSlot()
                );
        if(existingBooking.isPresent()) {
            throw new IllegalStateException("Unable to modify booking, booking already exists!");
        }

        return bookingRepository
                .updateBooking(
                        modifyBookingRequest.getPatientId(),
                        modifyBookingRequest.getServiceId(),
                        modifyBookingRequest.getOldReservedDate(),
                        modifyBookingRequest.getOldSlot(),
                        modifyBookingRequest.getNewReservedDate(),
                        modifyBookingRequest.getNewSlot()
                ) == 1;
    }

    @Transactional
    public boolean modifyTest(ModifyTestRequest modifyTestRequest) {
        Optional<Booking> existingTest = bookingRepository
                .checkTestExists(
                        modifyTestRequest.getNewReservedDate(),
                        modifyTestRequest.getServiceId()
                );
        if(existingTest.isPresent()) {
            throw new IllegalStateException("Unable to modify test, test already exists!");
        }

        return bookingRepository
                .updateTest(
                        modifyTestRequest.getPatientId(),
                        modifyTestRequest.getServiceId(),
                        modifyTestRequest.getOldReservedDate(),
                        modifyTestRequest.getNewReservedDate()
                ) == 1;
    }
}
