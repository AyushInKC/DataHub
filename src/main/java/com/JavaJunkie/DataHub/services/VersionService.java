package com.JavaJunkie.DataHub.services;

import com.JavaJunkie.DataHub.dto.VersionRequest;
import com.JavaJunkie.DataHub.dto.VersionResponse;
import com.JavaJunkie.DataHub.models.Datasets;
import com.JavaJunkie.DataHub.models.Version;
import com.JavaJunkie.DataHub.repos.DatasetRepo;
import com.JavaJunkie.DataHub.repos.VersionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VersionService {

    private final VersionRepo versionRepo;
    private final DatasetRepo datasetRepo;

    public VersionResponse createVersion(VersionRequest request) {
        Datasets dataset = datasetRepo.findById(request.getDatasetId())
                .orElseThrow(() -> new RuntimeException("Dataset not found"));

        Version parent = null;
        if (request.getParentVersionId() != null) {
            parent = versionRepo.findById(request.getParentVersionId())
                    .orElseThrow(() -> new RuntimeException("Parent version not found"));
        }

        Version version = Version.builder()
                .message(request.getMessage())
                .createdAt(Instant.now())
                .createdBy(request.getCreatedBy())
                .dataset(dataset)
                .parent(parent)
                .build();

        Version saved = versionRepo.save(version);
        return mapToResponse(saved);
    }

    public VersionResponse getVersion(String versionId) {
        Version version = versionRepo.findById(versionId)
                .orElseThrow(() -> new RuntimeException("Version not found"));
        return mapToResponse(version);
    }

    public List<VersionResponse> getAllVersionsForDataset(String datasetId) {
        return versionRepo.findAll().stream()
                .filter(v -> v.getDataset() != null && datasetId.equals(v.getDataset().getId()))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void deleteVersion(String versionId) {
        versionRepo.deleteById(versionId);
    }

    private VersionResponse mapToResponse(Version version) {
        return VersionResponse.builder()
                .id(version.getId())
                .message(version.getMessage())
                .createdAt(version.getCreatedAt())
                .createdBy(version.getCreatedBy())
                .datasetId(version.getDataset() != null ? version.getDataset().getId() : null)
                .parentVersionId(version.getParent() != null ? version.getParent().getId() : null)
                .build();
    }
}
