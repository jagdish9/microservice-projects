package com.microscopic.batchprocess.controller;

import com.microscopic.batchprocess.entity.ArtifactEntity;
import com.microscopic.batchprocess.service.ArtifactService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/artifacts")
public class ArtifactController {

    private final ArtifactService artifactService;

    public ArtifactController(ArtifactService artifactService) {
        this.artifactService = artifactService;
    }

    @GetMapping("/artifact-by-page")
    public Page<ArtifactEntity> getArtifacts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return artifactService.getArtifactByPage(page, size);
    }
}
