package org.teamseven.hms.backend.catalog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.teamseven.hms.backend.booking.dto.BookingPaginationResponse;
import org.teamseven.hms.backend.catalog.dto.ServiceCatalogItem;
import org.teamseven.hms.backend.catalog.dto.ServiceCatalogPaginationResponse;
import org.teamseven.hms.backend.catalog.dto.ServiceOverview;
import org.teamseven.hms.backend.catalog.entity.ServiceRepository;
import org.teamseven.hms.backend.catalog.entity.ServiceType;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.function.Function;

@Service
public class CatalogService {
    @Autowired
    private ServiceRepository repository;

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
            default -> List.of();
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
}
