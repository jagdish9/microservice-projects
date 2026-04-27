package com.microscopic.batchprocess.service;

import com.microscopic.batchprocess.constant.ArtifactLanguage;
import com.microscopic.batchprocess.entity.ArtifactEntity;
import com.microscopic.batchprocess.repository.ArtifactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArtifactService {

    private static final Logger log = LoggerFactory.getLogger(ArtifactService.class);

    private final ArtifactRepository artifactRepository;

    private final List<ArtifactEntity> buffer = new ArrayList<>();

    public ArtifactService(ArtifactRepository artifactRepository) {
        this.artifactRepository = artifactRepository;
    }

    @KafkaListener(topics = "file-topic", groupId = "file-group")
    public void consume(List<String> gavList) {
        for(String gavId : gavList) {
            ArtifactEntity artifactEntity = ArtifactEntity.builder()
                    .gavId(gavId)
                    .language(ArtifactLanguage.JAVA.getLanguage())
                    .languageCode(ArtifactLanguage.JAVA.getCode())
                    .build();

            buffer.add(artifactEntity);

            if(buffer.size() >= 100) {
                log.info("Processing for batch size: {}", buffer.size());
                artifactRepository.saveAll(buffer);
                buffer.clear();
            }
        }
    }

    public Page<ArtifactEntity> getArtifactByPage(int page, int size) {
        return artifactRepository.findAll(PageRequest.of(page, size));
    }
}
