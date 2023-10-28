package org.teamseven.hms.backend.booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.teamseven.hms.backend.booking.dto.AppointmentBookingSlotItem;
import org.teamseven.hms.backend.booking.dto.AppointmentBookingSlotPaginationResponse;
import org.teamseven.hms.backend.booking.dto.UpdateAppointmentRequest;
import org.teamseven.hms.backend.booking.entity.AppointmentStatus;
import org.teamseven.hms.backend.booking.service.AppointmentService;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AppointmentControllerTest {
    @Mock
    private AppointmentService service;

    @InjectMocks
    private AppointmentController controller;

    private MockMvc mockMvc;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        this.mockMvc =  MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testUpdateAppointment_successfulUpdate_assertReturn204() throws Exception{
        UpdateAppointmentRequest request = new UpdateAppointmentRequest(
                "abc",
                AppointmentStatus.PENDING
        );

        when(service.updateAppointmentDetails(any(), any())).thenReturn(true);

        mockMvc.perform(
                patch("/api/v1/appointments/07fec0a8-7145-11ee-8684-0242ac130003")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isNoContent());
    }

    @Test
    public void testUpdateAppointment_unsuccessfulUpdate_assertReturn304() throws Exception{
        UpdateAppointmentRequest request = new UpdateAppointmentRequest(
                "abc",
                AppointmentStatus.PENDING
        );

        when(service.updateAppointmentDetails(any(), any())).thenReturn(false);

        mockMvc.perform(
                patch("/api/v1/appointments/07fec0a8-7145-11ee-8684-0242ac130003")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isNotModified());
    }

    @Test
    public void testGetAppointmentSlots_assertReturnList_andReturn200() throws Exception{
        AppointmentBookingSlotPaginationResponse mockResponse = AppointmentBookingSlotPaginationResponse
                .builder()
                .items(
                        List.of(
                                AppointmentBookingSlotItem.builder()
                                        .bookingId(UUID.randomUUID())
                                        .patientName("Jane Doe")
                                        .status(AppointmentStatus.COMPLETED)
                                        .reservedDate("2023-10-10")
                                        .build()
                        )
                )
                .totalElements(1)
                .currentPage(1)
                .build();

        when(service.getDoctorSlots(any(), anyInt(), anyInt())).thenReturn(mockResponse);

        mockMvc.perform(
                get("/api/v1/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockResponse))
        ).andExpect(status().isOk());
    }
}
