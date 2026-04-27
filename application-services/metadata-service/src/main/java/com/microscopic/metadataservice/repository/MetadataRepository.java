package com.microscopic.metadataservice.repository;

import com.microscopic.metadataservice.entity.GitTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MetadataRepository extends JpaRepository<GitTag, Long> {

    @Query(value = "select count(*) from t_tags where repo_name = :finalRepo and tag_name = :tagName", nativeQuery = true)
    int findByRepoAndTagName(@Param("finalRepo") String finalRepo, @Param("tagName") String tagName);
}
