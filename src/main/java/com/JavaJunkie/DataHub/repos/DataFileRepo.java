package com.JavaJunkie.DataHub.repos;

import com.JavaJunkie.DataHub.models.DataFile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DataFileRepo extends MongoRepository<DataFile,String> {
    List<DataFile> findByVersionId(String versionID);
}
