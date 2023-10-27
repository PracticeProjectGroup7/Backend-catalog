package org.teamseven.hms.backend.booking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teamseven.hms.backend.booking.dto.UpdateAppointmentRequest;
import org.teamseven.hms.backend.booking.entity.AppointmentRepository;

import java.util.UUID;

@Service
public class AppointmentService {
    @Autowired private AppointmentRepository repository;

    public boolean updateAppointmentDetails(
            UUID appointmentId,
            UpdateAppointmentRequest appointmentRequest
    ) {
        return repository.setStatusAndCommentsForAppointment(
                appointmentRequest.getStatus(),
                appointmentRequest.getComments(),
                appointmentId
        ) == 1;
    }
}
