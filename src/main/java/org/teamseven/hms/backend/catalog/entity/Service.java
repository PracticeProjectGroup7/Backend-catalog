package org.teamseven.hms.backend.catalog.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@Document("services")
public class Service {
    @Id
    @Builder.Default
    @Column(name="servicesid")
    private String serviceid = UUID.randomUUID().toString();

    @Column(name = "doctorid")
    private String doctorid;

    @Column(name = "staffid")
    private UUID staffid;

    private String type;

    private String name;

    private String description;

    private String duration;

    private Double estimatedPrice;

    @Builder.Default
    private Integer isActive = 1;

    @Column(name="created_at", insertable=false)
    @Builder.Default
    private Instant createdAt = Instant.now();

    private Instant modifiedAt;
}
