package com.JavaJunkie.DataHub.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class DataFileResponse {
    private String id;
    private String name;
    private String type;
    private long size;
    private String downloadUrl;
    private Instant uploadedAt;
    private String versionId;
}
