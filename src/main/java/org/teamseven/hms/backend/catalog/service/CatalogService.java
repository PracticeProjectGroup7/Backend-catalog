package org.teamseven.hms.backend.catalog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.teamseven.hms.backend.catalog.dto.ServiceCatalogItem;
import org.teamseven.hms.backend.catalog.dto.ServiceCatalogPaginationResponse;
import org.teamseven.hms.backend.catalog.dto.ServiceOverview;
import org.teamseven.hms.backend.catalog.entity.ServiceRepository;
import org.teamseven.hms.backend.catalog.entity.ServiceType;
import org.teamseven.hms.backend.doctor.dto.DoctorProfile;
import org.teamseven.hms.backend.doctor.service.DoctorService;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CatalogService {
    @Autowired
    private ServiceRepository repository;

    @Autowired private DoctorService doctorService;

    public ServiceOverview getServiceOverview(UUID serviceId) {
        org.teamseven.hms.backend.catalog.entity.Service service = repository.findById(serviceId)
                .orElseThrow(NoSuchElementException::new);

        return getOverview.apply(service);
    }

    Function<org.teamseven.hms.backend.catalog.entity.Service, ServiceOverview> getOverview = it ->
            ServiceOverview.builder()
                    .serviceId(it.getServiceId())
                    .staffId(it.getStaffid())
                    .doctorId(it.getDoctorId())
                    .type(it.getType())
                    .name(it.getName())
                    .description(it.getDescription())
                    .estimatedPrice(it.getEstimatedPrice())
                    .build();


    public ServiceCatalogPaginationResponse getServiceCatalog(
            ServiceType serviceType,
            int page,
            int pageSize
    ) {
        int zeroBasedIndexPage = page - 1;

        Page<org.teamseven.hms.backend.catalog.entity.Service> services
                = repository.findAvailableServices(
                        serviceType.toString(),
                Pageable.ofSize(pageSize).withPage(zeroBasedIndexPage)
        );

        List<ServiceCatalogItem> items =  switch (serviceType) {
            case TEST -> services.map(getTestCatalogItem).toList();
            case APPOINTMENT -> constructDoctorAppointmentsCatalog(services);
        };

        return ServiceCatalogPaginationResponse.builder()
                .items(items)
                .totalElements(services.getTotalElements())
                .currentPage(page)
                .build();
    }

    private final Function<
            org.teamseven.hms.backend.catalog.entity.Service,
            ServiceCatalogItem
    > getTestCatalogItem = it -> ServiceCatalogItem.Test.builder()
            .name(it.getName())
            .description(it.getDescription())
            .serviceId(it.getServiceId())
            .type(ServiceType.TEST)
            .build();

    private List<ServiceCatalogItem> constructDoctorAppointmentsCatalog(
            Page<org.teamseven.hms.backend.catalog.entity.Service> services
    ) {
        List<UUID> doctorIds = services.map(it -> it.getDoctorId()).toList();
        Map<UUID, DoctorProfile> doctorProfiles = doctorService.getDoctorProfiles(doctorIds)
                .stream().collect(Collectors.toMap(DoctorProfile::getDoctorId, item -> item));


        return services.map(
                it -> getAppointmentCatalogItem.apply(it, doctorProfiles.get(it.getDoctorId()))
        ).toList();
    }

    private final BiFunction<
            org.teamseven.hms.backend.catalog.entity.Service,
            DoctorProfile,
            ServiceCatalogItem
            > getAppointmentCatalogItem = (it, profile) -> ServiceCatalogItem.DoctorAppointment
            .builder()
            .doctorId(it.getDoctorId())
            .serviceId(it.getServiceId())
            .name(it.getName())
            .description(it.getDescription())
            .specialty(profile.getSpecialty())
            .yearsOfExperience(profile.getYearOfExperience())
            .type(ServiceType.APPOINTMENT).build();

    public ServiceOverview getServiceOverviewByDoctorId(UUID doctorId) {
        org.teamseven.hms.backend.catalog.entity.Service service = repository.findByDoctorId(doctorId)
                .orElseThrow(NoSuchElementException::new);

        return getOverview.apply(service);
    }
}
