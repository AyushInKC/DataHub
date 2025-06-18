package com.JavaJunkie.DataHub.services;

import com.JavaJunkie.DataHub.dto.DataFileResponse;
import com.JavaJunkie.DataHub.models.DataFile;
import com.JavaJunkie.DataHub.models.Version;
import com.JavaJunkie.DataHub.repos.DataFileRepo;
import com.JavaJunkie.DataHub.repos.VersionRepo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DataFileService {
    private final DataFileRepo fileRepo;
    private final VersionRepo versionRepo;

    private Path storagePath;

    @PostConstruct
    public void init() {
        storagePath = Paths.get("uploaded_files");
        try {
            Files.createDirectories(storagePath);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory", e);
        }
    }

    public List<DataFileResponse> upload(String versionId, MultipartFile[] files) {
        Version version = versionRepo.findById(versionId)
                .orElseThrow(() -> new RuntimeException("Version not found"));

        return List.of(files).stream().map(file -> {
            String newName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = storagePath.resolve(newName);
            try {
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException("Could not store file", e);
            }

            DataFile dataFile = DataFile.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .size(file.getSize())
                    .uploadedAt(Instant.now())
                    .storagePath(filePath.toString())
                    .version(version)
                    .build();

            return mapToResponse(fileRepo.save(dataFile));
        }).collect(Collectors.toList());
    }

    public Resource download(String fileId) {
        DataFile dataFile = fileRepo.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));
        return new FileSystemResource(new File(dataFile.getStoragePath()));
    }

    public void delete(String fileId) {
        DataFile dataFile = fileRepo.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));
        try {
            Files.deleteIfExists(Paths.get(dataFile.getStoragePath()));
        } catch (IOException e) {
            throw new RuntimeException("Could not delete file from disk", e);
        }
        fileRepo.deleteById(fileId);
    }

    public List<DataFileResponse> listByVersion(String versionId) {
        return fileRepo.findByVersionId(versionId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private DataFileResponse mapToResponse(DataFile file) {
        return DataFileResponse.builder()
                .id(file.getId())
                .name(file.getName())
                .type(file.getType())
                .size(file.getSize())
                .uploadedAt(file.getUploadedAt())
                .versionId(file.getVersion().getId())
                .downloadUrl("/api/files/" + file.getId() + "/download")
                .build();
    }

}