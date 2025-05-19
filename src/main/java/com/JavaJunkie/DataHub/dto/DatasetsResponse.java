package com.JavaJunkie.DataHub.dto;

import com.JavaJunkie.DataHub.models.Datasets;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DatasetsResponse {
    String id;
    String name;
    Datasets.Visibility visibility;
    String engine;
    String description;
}
