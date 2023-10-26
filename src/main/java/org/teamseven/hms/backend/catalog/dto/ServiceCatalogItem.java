package org.teamseven.hms.backend.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.teamseven.hms.backend.catalog.entity.ServiceType;

import java.util.UUID;

public sealed class ServiceCatalogItem {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static final class Test extends ServiceCatalogItem {
        private String name;
        private String description;
        private UUID serviceId;
        private ServiceType type = ServiceType.TEST;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static final class DoctorAppointment extends ServiceCatalogItem {
        private String name;
        private String description;
        private UUID serviceId;
        private ServiceType type = ServiceType.APPOINTMENT;
        private UUID doctorId;
        private String yearsOfExperience;
        private String specialty;
    }
}
