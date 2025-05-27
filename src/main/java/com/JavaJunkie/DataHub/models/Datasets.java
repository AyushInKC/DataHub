package com.JavaJunkie.DataHub.models;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@Document(collection = "datasets")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@CompoundIndex(name = "owner_name_unique", def = "{'ownerId': 1, 'name': 1}", unique = true)


public class Datasets {
    @Id
    private String id;
    @Indexed(unique = true, sparse = true)
    private String name;
    @Indexed
    private String ownerId;
    @Builder.Default
    private String engine = "mongo";
    private String description;
    private String defaultBranchId;
    private Long quotaBytes;
    @CreatedDate
    @Builder.Default
    private Instant createdAt = Instant.now();
    @Builder.Default
    private Visibility visibility = Visibility.PUBLIC;

    public enum Visibility { PUBLIC, PRIVATE }
    @Builder.Default
    private boolean deleted = false;
}
