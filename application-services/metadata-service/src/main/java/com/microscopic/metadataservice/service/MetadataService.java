package com.microscopic.metadataservice.service;

import com.microscopic.metadataservice.config.GitHubClient;
import com.microscopic.metadataservice.entity.GitTag;
import com.microscopic.metadataservice.exception.ResourceNotFoundException;
import com.microscopic.metadataservice.repository.MetadataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MetadataService {

    private static final Logger log = LoggerFactory.getLogger(MetadataService.class);

    private final MetadataRepository metadataRepository;
    private final GitHubClient gitHubClient;

    public MetadataService(MetadataRepository metadataRepository, GitHubClient gitHubClient) {
        this.metadataRepository = metadataRepository;
        this.gitHubClient = gitHubClient;
    }

    public void loadTags(String owner, String repo) {
        List<Map<String, Object>> response = (List<Map<String, Object>>) gitHubClient.fetchTags(owner, repo);

        List<GitTag> tags = new ArrayList<>();

        for(Map<String, Object> tag : response) {
            GitTag gitTag = new GitTag();

            String finalRepo = owner + "/" + repo;
            String tagName = (String) tag.get("name");
            gitTag.setRepoName(finalRepo);
            gitTag.setTagName(tagName);
            gitTag.setZipUrl((String) tag.get("zipball_url"));
            gitTag.setTarUrl((String) tag.get("tarball_url"));
            gitTag.setNodeId((String) tag.get("node_id"));

            Map<String, String> commit = (Map<String, String>) tag.get("commit");

            gitTag.setCommitSha(commit.get("sha"));

            if(metadataRepository.findByRepoAndTagName(finalRepo, tagName) == 0) {
                tags.add(gitTag);
                log.info("Collected data for tag - {}", gitTag.getTagName());
            }
        }

        metadataRepository.saveAll(tags);
    }

    public GitTag getById(Long id) {
        log.info("Passed id -{}", id);
        return metadataRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found in DB "+id));
    }

    public String getZipUrl(Long id) {
        log.info("Id for zip url - {}", id);
        return getById(id).getZipUrl();
    }
}
