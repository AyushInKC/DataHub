package com.JavaJunkie.DataHub.controllers;

import com.JavaJunkie.DataHub.dto.DataFileResponse;
import com.JavaJunkie.DataHub.services.DataFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class DataFileController {
    private final DataFileService fileService;

    @PostMapping("/upload/{versionId}")
    public ResponseEntity<List<DataFileResponse>> uploadFiles(
            @PathVariable String versionId,
            @RequestParam("files") MultipartFile[] files
    ) {
        return ResponseEntity.ok(fileService.upload(versionId, files));
    }

    @GetMapping("/{fileId}/download")
    public ResponseEntity<Resource> download(@PathVariable String fileId) {
        Resource file = fileService.download(fileId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFilename())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);
    }

    @GetMapping("/version/{versionId}")
    public ResponseEntity<List<DataFileResponse>> listFilesByVersion(@PathVariable String versionId) {
        return ResponseEntity.ok(fileService.listByVersion(versionId));
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> delete(@PathVariable String fileId) {
        fileService.delete(fileId);
        return ResponseEntity.noContent().build();
    }

}