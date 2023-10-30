package org.teamseven.hms.backend.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RetrieveStaffPaginationResponse {
    private long totalElements;
    private int currentPage;
    private List<RetrieveStaffItem> items;
}
