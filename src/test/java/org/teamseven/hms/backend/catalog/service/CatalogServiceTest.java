package org.teamseven.hms.backend.catalog.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.teamseven.hms.backend.catalog.dto.ServiceCatalogPaginationResponse;
import org.teamseven.hms.backend.catalog.dto.ServiceOverview;
import org.teamseven.hms.backend.catalog.entity.Service;
import org.teamseven.hms.backend.catalog.entity.ServiceRepository;
import org.teamseven.hms.backend.catalog.entity.ServiceType;
import org.teamseven.hms.backend.doctor.dto.DoctorProfile;
import org.teamseven.hms.backend.doctor.service.DoctorService;

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

    @Mock
    private DoctorService doctorService;

    @InjectMocks
    private CatalogService catalogService;

    @Test
    public void testGetServiceOverview_serviceNotFound_assertThrowNoSuchElementException() {
        UUID uuid = UUID.randomUUID();

        assertThrows(
                NoSuchElementException.class,
                () -> catalogService.getServiceOverview(uuid)
        );

        verify(serviceRepository).findById(uuid.toString());
    }

    @Test
    public void testGetServiceOverview_serviceFound_assertReturnElement() {
        UUID uuid = UUID.randomUUID();
        Service mockServiceObject = Service.builder()
                .serviceid(uuid.toString())
                .staffid(UUID.randomUUID())
                .doctorid(UUID.randomUUID().toString())
                .type("TEST")
                .name("Lab test")
                .description("full blood scan test")
                .estimatedPrice(25.50)
                .build();

        when(serviceRepository.findById(any())).thenReturn(Optional.of(mockServiceObject));

        ServiceOverview overview = catalogService.getServiceOverview(uuid);

        verify(serviceRepository).findById(uuid.toString());

        assertEquals(uuid, overview.getServiceId());
        assertEquals(mockServiceObject.getStaffid(), overview.getStaffId());
        assertEquals(mockServiceObject.getDoctorid(), overview.getDoctorId());
        assertEquals(mockServiceObject.getType(), overview.getType());
        assertEquals(mockServiceObject.getName(), overview.getName());
        assertEquals(mockServiceObject.getDescription(), overview.getDescription());
    }

    @Test
    public void testGetAvailableServices_returnFormattedDataFromRepository() {
        List<Service> mockList = getMockTestServiceList();

        when(serviceRepository.findAvailableServices(any(), any()))
                .thenReturn(
                        new PageImpl(mockList, PageRequest.of(0, 10), 1L)
                );

        ServiceCatalogPaginationResponse response = catalogService.getServiceCatalog(ServiceType.TEST, 1, 10);
        verify(serviceRepository).findAvailableServices(eq("TEST"), any());

        assertEquals(1, response.getTotalElements());
        assertEquals(1, response.getCurrentPage());
    }

    @Test
    public void testGetDoctorsCatalog_returnFormattedDataFromRepository() {
        List<Service> mockList = getMockDoctorsServiceList();

        when(serviceRepository.findAvailableServices(any(), any()))
                .thenReturn(
                        new PageImpl(mockList, PageRequest.of(0, 10), 1L)
                );

        when(doctorService.getDoctorProfiles(any()))
                .thenReturn(
                        List.of(
                                DoctorProfile
                                        .builder()
                                        .doctorId(UUID.fromString("7586e389-dd38-486c-937b-5fb8eab2d792"))
                                        .specialty("test specialty")
                                        .yearOfExperience("2.5")
                                        .build()
                        )
                );

        ServiceCatalogPaginationResponse response = catalogService.getServiceCatalog(ServiceType.APPOINTMENT, 1, 10);
        verify(serviceRepository).findAvailableServices(
                eq("APPOINTMENT"),
                eq(Pageable.ofSize(10).withPage(0))
        );

        assertEquals(1, response.getTotalElements());
        assertEquals(1, response.getCurrentPage());
    }

    private List<Service> getMockTestServiceList() {
        return List.of(
                Service.builder()
                        .serviceid(UUID.randomUUID().toString())
                        .name("test name")
                        .description("test description")
                        .build()
        );
    }

    private List<Service> getMockDoctorsServiceList() {
        return List.of(
                Service.builder()
                        .doctorid("7586e389-dd38-486c-937b-5fb8eab2d792")
                        .serviceid(UUID.randomUUID().toString())
                        .name("test name")
                        .description("test description")
                        .build()
        );
    }
}
