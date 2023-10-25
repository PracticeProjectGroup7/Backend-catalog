package org.teamseven.hms.backend.catalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.teamseven.hms.backend.catalog.dto.ServiceCatalogPaginationResponse;
import org.teamseven.hms.backend.catalog.entity.ServiceType;
import org.teamseven.hms.backend.catalog.service.CatalogService;
import org.teamseven.hms.backend.shared.ResponseWrapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}
