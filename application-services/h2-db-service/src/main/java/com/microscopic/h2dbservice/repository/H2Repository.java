package com.microscopic.h2dbservice.repository;

import com.microscopic.h2dbservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface H2Repository extends JpaRepository<User, Long> {
}
