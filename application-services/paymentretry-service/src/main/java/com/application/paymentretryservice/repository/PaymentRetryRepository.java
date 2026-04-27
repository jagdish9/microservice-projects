package com.application.paymentretryservice.repository;

import com.application.paymentretryservice.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PaymentRetryRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByStatusAndRetryCountLessThan(String failed, int retryCount);

    @Query("select * from payment where id = :paymentId")
    Payment findByPaymentId(@Param("paymentId") String paymentId);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("Delete from payment where id = :id")
    void deletePaymentById(@Param("id") Long id);
}
