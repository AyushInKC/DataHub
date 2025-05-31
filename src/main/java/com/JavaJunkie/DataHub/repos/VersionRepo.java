package com.JavaJunkie.DataHub.repos;

import com.JavaJunkie.DataHub.models.Version;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VersionRepo extends MongoRepository<Version, String> {
}
