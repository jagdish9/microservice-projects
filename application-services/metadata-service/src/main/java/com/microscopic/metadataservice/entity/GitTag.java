package com.microscopic.metadataservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "t_tags")
@Getter
@Setter
public class GitTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String repoName;
    private String tagName;
    private String zipUrl;
    private String tarUrl;
    private String commitSha;
    private String nodeId;
}
