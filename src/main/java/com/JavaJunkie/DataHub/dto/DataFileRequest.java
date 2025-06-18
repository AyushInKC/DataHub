package com.JavaJunkie.DataHub.dto;

import lombok.Data;

@Data
public class DataFileRequest {
    private String name;
    private String type;
    private String versionId;
}
