package com.JavaJunkie.DataHub.repos;

import com.JavaJunkie.DataHub.models.Datasets;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface DatasetRepo extends MongoRepository<Datasets,String> {
    List<Datasets> findByOwnerId(String ownerId);
    Optional<Datasets> findByOwnerIdAndName(String ownerId, String name);
}
