package com.microscopic.batchprocess.repository;

import com.microscopic.batchprocess.entity.ArtifactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtifactRepository extends JpaRepository<ArtifactEntity, Long> {
}
