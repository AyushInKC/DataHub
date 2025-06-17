package com.JavaJunkie.DataHub.models;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "versions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Version {

    @Id
    private String id;

    private String message;

    @CreatedDate
    private Instant createdAt;

    private String createdBy;

    @DBRef
    private Datasets dataset;

    @DBRef
    private Version parent;
}
