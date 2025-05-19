package com.JavaJunkie.DataHub.dto;

import com.JavaJunkie.DataHub.models.Datasets;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatasetResponse {
    String id;
    String name;
    Datasets.Visibility visibility;
    String engine;
    String description;
}
