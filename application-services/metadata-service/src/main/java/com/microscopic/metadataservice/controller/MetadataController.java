package com.microscopic.metadataservice.controller;

import com.microscopic.metadataservice.service.MetadataService;
import io.micrometer.tracing.Tracer;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/metadata")
public class MetadataController {

    private static final Logger log = LoggerFactory.getLogger(MetadataController.class);

    private final MetadataService metadataService;

    @Autowired(required = false)
    Tracer tracer;

    public MetadataController(MetadataService metadataService) {
        this.metadataService = metadataService;
    }

    @PostMapping("/load-tags")
    public String loadTags(@RequestParam String owner, @RequestParam String repo) {
        log.info("loadTags method");
        metadataService.loadTags(owner, repo);
        return "Tags loaded successfully";
    }

    @PostConstruct
    public void check() {
        log.info("Tracer bean: {}", tracer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTag(@PathVariable Long id) {
        return ResponseEntity.ok(metadataService.getById(id));
    }

    @GetMapping("/get-url/{id}")
    public String getZipUrl(@PathVariable Long id) {
        return metadataService.getZipUrl(id);
    }
}
