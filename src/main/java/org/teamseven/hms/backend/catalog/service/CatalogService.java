package org.teamseven.hms.backend.catalog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.teamseven.hms.backend.catalog.dto.CreateDoctorService;
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
        org.teamseven.hms.backend.catalog.entity.Service service = repository.findById(serviceId.toString())
                .orElseThrow(NoSuchElementException::new);

        return getOverview.apply(service);
    }

    Function<org.teamseven.hms.backend.catalog.entity.Service, ServiceOverview> getOverview = it ->
            ServiceOverview.builder()
                    .serviceId(UUID.fromString(it.getServiceid()))
                    .staffId(it.getStaffid())
                    .doctorId(UUID.fromString(it.getDoctorid()))
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
            .serviceId(UUID.fromString(it.getServiceid()))
            .type(ServiceType.TEST)
            .estimatedPrice(it.getEstimatedPrice())
            .build();

    private List<ServiceCatalogItem> constructDoctorAppointmentsCatalog(
            Page<org.teamseven.hms.backend.catalog.entity.Service> services
    ) {
        List<UUID> doctorIds = services.map(it -> UUID.fromString(it.getDoctorid())).toList();
        Map<UUID, DoctorProfile> doctorProfiles = doctorService.getDoctorProfiles(doctorIds)
                .stream().collect(Collectors.toMap(DoctorProfile::getDoctorId, item -> item));

        return services.map(
                it -> getAppointmentCatalogItem.apply(it, doctorProfiles.get(UUID.fromString(it.getDoctorid())))
        ).toList();
    }

    public UUID createNewService(CreateDoctorService newService) {
        org.teamseven.hms.backend.catalog.entity.Service service = org.teamseven.hms.backend.catalog.entity.Service.builder()
                .doctorid(newService.getDoctorId().toString())
                .type(ServiceType.APPOINTMENT.name())
                .name(newService.getName())
                .description(newService.getDescription())
                .estimatedPrice(newService.getEstimatedPrice())
                .build();
        return UUID.fromString(repository.save(service).getServiceid());
    }

    private final BiFunction<
            org.teamseven.hms.backend.catalog.entity.Service,
            DoctorProfile,
            ServiceCatalogItem
            > getAppointmentCatalogItem = (it, profile) -> ServiceCatalogItem.DoctorAppointment
            .builder()
            .doctorId(UUID.fromString(it.getDoctorid()))
            .serviceId(UUID.fromString(it.getServiceid()))
            .name(it.getName())
            .description(it.getDescription())
            .specialty(profile.getSpecialty())
            .yearsOfExperience(profile.getYearOfExperience())
            .estimatedPrice(it.getEstimatedPrice())
            .type(ServiceType.APPOINTMENT).build();

    public ServiceOverview getServiceOverviewByDoctorId(UUID doctorId) {
        org.teamseven.hms.backend.catalog.entity.Service service = repository.findByDoctorid(doctorId.toString())
                .orElseThrow(NoSuchElementException::new);

        return getOverview.apply(service);
    }
}
