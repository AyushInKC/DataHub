package com.JavaJunkie.DataHub.controllers;

import com.JavaJunkie.DataHub.dto.VersionRequest;
import com.JavaJunkie.DataHub.dto.VersionResponse;
import com.JavaJunkie.DataHub.services.VersionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/versions")
@RequiredArgsConstructor
public class VersionController {

    private final VersionService versionService;

    @PostMapping
    public ResponseEntity<VersionResponse> createVersion(@RequestBody VersionRequest request) {
        return ResponseEntity.ok(versionService.createVersion(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VersionResponse> getVersion(@PathVariable String id) {
        return ResponseEntity.ok(versionService.getVersion(id));
    }

    @GetMapping("/dataset/{datasetId}")
    public ResponseEntity<List<VersionResponse>> getVersionsByDataset(@PathVariable String datasetId) {
        return ResponseEntity.ok(versionService.getAllVersionsForDataset(datasetId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVersion(@PathVariable String id) {
        versionService.deleteVersion(id);
        return ResponseEntity.noContent().build();
    }
}
