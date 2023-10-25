package org.teamseven.hms.backend.catalog.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.teamseven.hms.backend.catalog.dto.ServiceCatalogPaginationResponse;
import org.teamseven.hms.backend.catalog.dto.ServiceOverview;
import org.teamseven.hms.backend.catalog.entity.Service;
import org.teamseven.hms.backend.catalog.entity.ServiceRepository;
import org.teamseven.hms.backend.catalog.entity.ServiceType;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CatalogServiceTest {
    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private CatalogService catalogService;

    @Test
    public void testGetServiceOverview_serviceNotFound_assertThrowNoSuchElementException() {
        UUID uuid = UUID.randomUUID();

        assertThrows(
                NoSuchElementException.class,
                () -> catalogService.getServiceOverview(uuid)
        );

        verify(serviceRepository).findById(uuid);
    }

    @Test
    public void testGetServiceOverview_serviceFound_assertReturnElement() {
        UUID uuid = UUID.randomUUID();
        Service mockServiceObject = Service.builder()
                .serviceId(uuid)
                .staffid(UUID.randomUUID())
                .doctorId(UUID.randomUUID())
                .type("TEST")
                .name("Lab test")
                .description("full blood scan test")
                .estimatedPrice(25.50)
                .build();

        when(serviceRepository.findById(any())).thenReturn(Optional.of(mockServiceObject));

        ServiceOverview overview = catalogService.getServiceOverview(uuid);

        verify(serviceRepository).findById(uuid);

        assertEquals(uuid, overview.getServiceId());
        assertEquals(mockServiceObject.getStaffid(), overview.getStaffId());
        assertEquals(mockServiceObject.getDoctorId(), overview.getDoctorId());
        assertEquals(mockServiceObject.getType(), overview.getType());
        assertEquals(mockServiceObject.getName(), overview.getName());
        assertEquals(mockServiceObject.getDescription(), overview.getDescription());
    }

    @Test
    public void testGetAvailableServices_returnFormattedDataFromRepository() {
        List<Service> mockList = getMockServiceList();

        when(serviceRepository.findAvailableServices(any(), any()))
                .thenReturn(
                        new PageImpl(mockList, PageRequest.of(0, 10), 1L)
                );

        ServiceCatalogPaginationResponse response = catalogService.getServiceCatalog(ServiceType.TEST, 1, 10);
        verify(serviceRepository).findAvailableServices(eq("TEST"), any());

        assertEquals(1, response.getTotalElements());
        assertEquals(1, response.getCurrentPage());
    }

    private List<Service> getMockServiceList() {
        return List.of(
                Service.builder()
                        .serviceId(UUID.randomUUID())
                        .name("test name")
                        .description("test description")
                        .build()
        );
    }
}
