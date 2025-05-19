package com.JavaJunkie.DataHub.controllers;

import com.JavaJunkie.DataHub.dto.DatasetRequest;
import com.JavaJunkie.DataHub.dto.DatasetResponse;
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
    public ResponseEntity<DatasetResponse> create(@RequestBody DatasetRequest req, Principal principal) {
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
    public List<DatasetResponse> listMine(Principal principal) {
        return svc.listMine(principal.getName()).stream().map(this::toDto).toList();
    }

    @GetMapping("/{id}")
    public DatasetResponse get(@PathVariable String id) {
        return toDto(svc.get(id));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id, Principal principal) {
        svc.delete(id, principal.getName());
    }


    private DatasetResponse toDto(Datasets d) {
        return new DatasetResponse(d.getId(), d.getName(),
                d.getVisibility(), d.getEngine(),
                d.getDescription());
    }

}
