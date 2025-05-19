package com.JavaJunkie.DataHub.services;
import com.JavaJunkie.DataHub.models.Datasets;
import com.JavaJunkie.DataHub.repos.DatasetRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DatasetService {

    private final DatasetRepo repo;

    public DatasetService(DatasetRepo repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<Datasets> listMine(String ownerId) {
        return repo.findByOwnerId(ownerId);
    }

    @Transactional(readOnly = true)
    public Datasets get(String datasetId) {
        return repo.findById(datasetId)
                .orElseThrow(() -> new IllegalArgumentException("Dataset not found"));
    }

    @Transactional
    public Datasets create(String ownerId, String name, String description, Datasets.Visibility visibility) {
        if (repo.findByOwnerIdAndName(ownerId, name).isPresent())
            throw new IllegalArgumentException("Dataset name already exists for this owner");

        return repo.save(Datasets.builder()
                .ownerId(ownerId)
                .name(name)
                .description(description)
                .visibility(visibility)
                .build());
    }

    @Transactional
    public void delete(String datasetId, String requesterId) {
        Datasets d = get(datasetId);
        if (!d.getOwnerId().equals(requesterId))
            throw new SecurityException("Only owner can delete dataset");

        // TODO: cascade delete branches + checkpoints, delete S3 dumps
        repo.deleteById(datasetId);
    }


}
