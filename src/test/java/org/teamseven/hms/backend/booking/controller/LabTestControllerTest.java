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
import org.teamseven.hms.backend.booking.dto.TestAppointmentSlotItem;
import org.teamseven.hms.backend.booking.dto.TestAppointmentSlotPaginationResponse;
import org.teamseven.hms.backend.booking.dto.UpdateLabTestRequest;
import org.teamseven.hms.backend.booking.entity.TestStatus;
import org.teamseven.hms.backend.booking.service.LabTestService;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class LabTestControllerTest {
    @Mock
    private LabTestService service;

    @InjectMocks
    private LabTestController controller;

    private MockMvc mockMvc;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        this.mockMvc =  MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testUpdateTestStatus_successfulUpdate_assertReturn204() throws Exception{
        UpdateLabTestRequest request = new UpdateLabTestRequest(TestStatus.PENDING, "test result");

        when(service.updateTestStatus(any(), any(), any())).thenReturn(true);

        mockMvc.perform(
                patch("/api/v1/tests/07fec0a8-7145-11ee-8684-0242ac130003")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isNoContent());

        verify(service).updateTestStatus(
                UUID.fromString("07fec0a8-7145-11ee-8684-0242ac130003"), "test result", TestStatus.PENDING
        );
    }

    @Test
    public void testUpdateAppointment_unsuccessfulUpdate_assertReturn304() throws Exception{
        UpdateLabTestRequest request = new UpdateLabTestRequest(TestStatus.PENDING, "test result");

        when(service.updateTestStatus(any(), any(), any())).thenReturn(false);

        mockMvc.perform(
                patch("/api/v1/tests/07fec0a8-7145-11ee-8684-0242ac130003")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isNotModified());

        verify(service).updateTestStatus(
                UUID.fromString("07fec0a8-7145-11ee-8684-0242ac130003"), "test result", TestStatus.PENDING
        );
    }

    @Test
    public void testGetTestSlots_assertReturnList_andReturn200() throws Exception{
        TestAppointmentSlotPaginationResponse mockResponse = TestAppointmentSlotPaginationResponse
                .builder()
                .items(
                        List.of(
                                TestAppointmentSlotItem.builder()
                                        .bookingId(UUID.randomUUID())
                                        .patientName("Jane Doe")
                                        .status(TestStatus.COMPLETED)
                                        .testName("test name")
                                        .reservedDate("2023-10-10")
                                        .build()
                        )
                )
                .totalElements(1)
                .currentPage(1)
                .build();

        when(service.getTestAppointments(anyInt(), anyInt())).thenReturn(mockResponse);

        mockMvc.perform(
                get("/api/v1/tests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockResponse))
        ).andExpect(status().isOk());

        verify(service).getTestAppointments(1, 10);
    }
}
