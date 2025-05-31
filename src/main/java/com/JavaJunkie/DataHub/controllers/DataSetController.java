package com.JavaJunkie.DataHub.controllers;
import com.JavaJunkie.DataHub.dto.DatasetsRequest;
import com.JavaJunkie.DataHub.dto.DatasetsResponse;
import com.JavaJunkie.DataHub.models.Datasets;
import com.JavaJunkie.DataHub.services.DatasetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/datasets")
public class DataSetController {


    private final DatasetService svc;

    public DataSetController(DatasetService svc) {
        this.svc = svc;
    }


    @PostMapping
    public ResponseEntity<DatasetsResponse> create(@RequestBody DatasetsRequest req, Principal principal) {
        Datasets d = svc.create(
                principal.getName(),
                req.getName(),
                req.getDescription(),
                req.getVisibility()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(toDto(d));
    }


    @GetMapping
    public List<DatasetsResponse> listMine(Principal principal) {
        return svc.listMine(principal.getName()).stream().map(this::toDto).toList();
    }

    @GetMapping("/{id}")
    public DatasetsResponse get(@PathVariable String id) {
        return toDto(svc.get(id));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id, Principal principal) {
        svc.delete(id, principal.getName());
    }

    @PutMapping("/{id}")
    public DatasetsResponse update(@PathVariable String id, @RequestBody DatasetsRequest req, Principal principal) {
        Datasets updated = svc.update(id, principal.getName(), req.getName(), req.getDescription(), req.getVisibility());
        return toDto(updated);
    }

    @PatchMapping("/{id}/restore")
    public DatasetsResponse restore(@PathVariable String id, Principal principal) {
        Datasets restored = svc.restore(id, principal.getName());
        return toDto(restored);
    }
    private DatasetsResponse toDto(Datasets d) {
        return new DatasetsResponse(
                d.getId(),
                d.getName(),
                d.getVisibility(),
                d.getEngine(),
                d.getDescription()
        );
    }
}
