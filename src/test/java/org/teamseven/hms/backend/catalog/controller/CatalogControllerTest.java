package org.teamseven.hms.backend.catalog.controller;

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
import org.teamseven.hms.backend.catalog.dto.CreateDoctorService;
import org.teamseven.hms.backend.catalog.dto.ServiceCatalogPaginationResponse;
import org.teamseven.hms.backend.catalog.dto.ServiceOverview;
import org.teamseven.hms.backend.catalog.entity.ServiceType;
import org.teamseven.hms.backend.catalog.service.CatalogService;
import org.teamseven.hms.backend.shared.ResponseWrapper;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CatalogControllerTest {
    @Mock
    private CatalogService catalogService;

    @InjectMocks
    private CatalogController controller;

    private MockMvc mockMvc;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        this.mockMvc =  MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetTestsCatalog_paginationNotSpecified_assertPaginationUseDefault() throws Exception {
        ServiceCatalogPaginationResponse response = getMockResponse();
        ResponseWrapper.Success<ServiceCatalogPaginationResponse> expectedResponse = new ResponseWrapper.Success<>(
                response
        );

        when(catalogService.getServiceCatalog(any(), any(int.class), any(int.class)))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/available-services/lab-tests"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));

        verify(catalogService).getServiceCatalog(ServiceType.TEST, 1, 10);
    }

    @Test
    public void testGetTestsCatalog_paginationCustomised_assertPaginationAsRequested() throws Exception {
        ServiceCatalogPaginationResponse response = getMockResponse();
        ResponseWrapper.Success<ServiceCatalogPaginationResponse> expectedResponse = new ResponseWrapper.Success<>(
                response
        );

        when(catalogService.getServiceCatalog(any(), any(int.class), any(int.class)))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/available-services/lab-tests?page=2&pageSize=5"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));

        verify(catalogService).getServiceCatalog(ServiceType.TEST, 2, 5);
    }

    private ServiceCatalogPaginationResponse getMockResponse() {
        return ServiceCatalogPaginationResponse.builder()
                .currentPage(1)
                .totalElements(1)
                .items(List.of())
                .build();
    }

    @Test
    public void testGetAppointmentsCatalog_paginationNotSpecified_assertPaginationUseDefault() throws Exception {
        ServiceCatalogPaginationResponse response = getMockResponse();
        ResponseWrapper.Success<ServiceCatalogPaginationResponse> expectedResponse = new ResponseWrapper.Success<>(
                response
        );

        when(catalogService.getServiceCatalog(any(), any(int.class), any(int.class)))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/available-services/doctors"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));

        verify(catalogService).getServiceCatalog(ServiceType.APPOINTMENT, 1, 10);
    }

    @Test
    public void testGetAppointmentsCatalog_paginationCustomised_assertPaginationAsRequested() throws Exception {
        ServiceCatalogPaginationResponse response = getMockResponse();
        ResponseWrapper.Success<ServiceCatalogPaginationResponse> expectedResponse = new ResponseWrapper.Success<>(
                response
        );

        when(catalogService.getServiceCatalog(any(), any(int.class), any(int.class)))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/available-services/doctors?page=2&pageSize=5"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));

        verify(catalogService).getServiceCatalog(ServiceType.APPOINTMENT, 2, 5);
    }

    @Test
    public void testGetServiceOverviewByDoctorId_doctorFound_returnOverview() throws Exception {
        ServiceOverview mockOverview = ServiceOverview.builder()
                .serviceId(UUID.fromString("a24d4964-14fb-462b-9b7e-87ec1ddb15b5"))
                .doctorId(UUID.fromString("1a25e8c2-ba53-4f93-86be-8d4c93f8ab22"))
                .build();

        ResponseWrapper.Success<ServiceOverview> expectedResponse = new ResponseWrapper.Success<>(
                mockOverview
        );

        when(catalogService.getServiceOverviewByDoctorId(any()))
                .thenReturn(mockOverview);

        mockMvc.perform(get("/api/v1/available-services/doctors/1a25e8c2-ba53-4f93-86be-8d4c93f8ab22"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));

        verify(catalogService).getServiceOverviewByDoctorId(UUID.fromString("1a25e8c2-ba53-4f93-86be-8d4c93f8ab22"));
    }

    @Test
    public void testGetServiceOverview_serviceFound_returnOverview() throws Exception {
        ServiceOverview mockOverview = ServiceOverview.builder()
                .serviceId(UUID.fromString("a24d4964-14fb-462b-9b7e-87ec1ddb15b5"))
                .doctorId(UUID.fromString("1a25e8c2-ba53-4f93-86be-8d4c93f8ab22"))
                .build();

        ResponseWrapper.Success<ServiceOverview> expectedResponse = new ResponseWrapper.Success<>(
                mockOverview
        );

        when(catalogService.getServiceOverview(any()))
                .thenReturn(mockOverview);

        mockMvc.perform(get("/api/v1/available-services/a24d4964-14fb-462b-9b7e-87ec1ddb15b5"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));

        verify(catalogService).getServiceOverview(UUID.fromString("a24d4964-14fb-462b-9b7e-87ec1ddb15b5"));
    }

    @Test
    public void testCreateNewService_successfullyCreated_assertReturnUuid() throws Exception {
        CreateDoctorService mockService = CreateDoctorService
                .builder()
                .doctorId(UUID.randomUUID())
                .name("Jane Doe")
                .description("Dermatologist")
                .estimatedPrice(15.0)
                .build();
        ResponseWrapper.Success<UUID> expectedResponse = new ResponseWrapper.Success<>(
                UUID.fromString("6f99b143-62bc-44b8-ba09-0904223b2f8a")
        );

        when(catalogService.createNewService(any()))
                .thenReturn(UUID.fromString("6f99b143-62bc-44b8-ba09-0904223b2f8a"));

        mockMvc.perform(
                post("/api/v1/available-services/doctors")
                        .content(objectMapper.writeValueAsString(mockService))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));

        verify(catalogService).createNewService(mockService);
    }
}
