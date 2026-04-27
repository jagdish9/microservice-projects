package com.appservice.paymentservice.repository;

import com.appservice.paymentservice.entity.ProcessedEvents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<ProcessedEvents, String> {
}
