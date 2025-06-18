package com.JavaJunkie.DataHub.models;

import jakarta.annotation.sql.DataSourceDefinitions;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.Instant;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataFile {
    @Id
    private String id;

    private String name;
    private String type; // csv, json, etc.
    private long size;

    private String storagePath; // filesystem or GridFS ID

    @DBRef
    private Version version;

    private Instant uploadedAt;
}
