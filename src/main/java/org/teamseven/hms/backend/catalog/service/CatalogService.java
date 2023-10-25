package org.teamseven.hms.backend.catalog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teamseven.hms.backend.catalog.dto.ServiceOverview;
import org.teamseven.hms.backend.catalog.entity.ServiceRepository;

import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.function.Function;

@Service
public class CatalogService {
    @Autowired
    ServiceRepository repository;

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
}
