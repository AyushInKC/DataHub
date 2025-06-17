package com.JavaJunkie.DataHub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VersionResponse {
    private String id;
    private String message;
    private Instant createdAt;
    private String createdBy;
    private String datasetId;
    private String parentVersionId;
}
