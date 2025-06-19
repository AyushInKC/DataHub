package com.JavaJunkie.DataHub.dto;

import com.JavaJunkie.DataHub.Enums.Visibility;
import com.JavaJunkie.DataHub.models.Datasets;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter

public class DatasetsRequest {
    String name;
    String description;
    Visibility visibility;
}
