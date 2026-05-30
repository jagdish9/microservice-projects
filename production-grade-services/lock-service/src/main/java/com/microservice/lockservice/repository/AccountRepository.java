package com.microservice.lockservice.repository;

import com.microservice.lockservice.entity.Account;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    //Pessimistic lock
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    //@Query(value = "select a from account a where a.id = :id", nativeQuery = true) //account is a table name
    @Query(value = "select a from Account a where a.id = :id") // Account is entity name
    Optional<Account> findByIdForUpdate(@Param("id") Long id);
}
