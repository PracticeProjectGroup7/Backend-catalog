package org.teamseven.hms.backend.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceCatalogPaginationResponse {
    private long totalElements;
    private int currentPage;

    private List<ServiceCatalogItem> items;
}
