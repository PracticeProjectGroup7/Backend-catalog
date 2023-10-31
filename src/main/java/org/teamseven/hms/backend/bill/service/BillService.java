package org.teamseven.hms.backend.bill.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teamseven.hms.backend.bill.dto.BillQueryResponse;
import org.teamseven.hms.backend.bill.dto.UpdateBillRequest;
import org.teamseven.hms.backend.booking.entity.Booking;
import org.teamseven.hms.backend.booking.entity.BookingRepository;
import org.teamseven.hms.backend.shared.exception.ResourceNotFoundException;

@Service
public class BillService {
    @Autowired private BookingRepository bookingRepository;
    public BillQueryResponse getBillByBookingId(String booking_id) {
        Booking booking = bookingRepository.findByBookingId(booking_id).orElseThrow(() -> new ResourceNotFoundException("Bill not found!"));
        return BillQueryResponse
                .builder()
                .bookingId(booking.getBookingId())
                .billNumber(booking.getBillNumber())
                .billStatus(booking.getBillStatus())
                .amountPaid(booking.getAmountPaid())
                .paidAt(booking.getPaidAt())
                .fees(booking.getFee())
                .build();
    }

    @Transactional
    public boolean updateBill(UpdateBillRequest updateBillRequest, String booking_id) {
        return bookingRepository.updateBillStatus(
                updateBillRequest.getBillStatus(),
                updateBillRequest.getPaidAt(),
                booking_id
        ) == 1;
    }
}
