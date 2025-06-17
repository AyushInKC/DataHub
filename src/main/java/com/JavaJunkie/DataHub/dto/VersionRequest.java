package com.JavaJunkie.DataHub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VersionRequest {
    private String message;
    private String createdBy;
    private String datasetId;
    private String parentVersionId;

}
