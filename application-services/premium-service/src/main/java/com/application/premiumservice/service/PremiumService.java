package com.application.premiumservice.service;

import com.application.premiumservice.entity.Premium;
import com.application.premiumservice.exception.PremiumNotFoundException;
import com.application.premiumservice.repository.PremiumRepository;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PremiumService {

    private final PremiumRepository premiumRepository;

    public PremiumService(PremiumRepository premiumRepository) {
        this.premiumRepository = premiumRepository;
    }

    @Transactional
    public Premium createPremium(@Valid Premium premium, String idempotencyKey) {
        return premiumRepository.findByIdempotencyKey(idempotencyKey)
                .orElseGet(() -> {
                    Premium newPremium = Premium.builder()
                            .orderId(premium.getOrderId())
                            .amount(premium.getAmount())
                            .status("CREATED")
                            .createdAt(LocalDateTime.now())
                            .idempotencyKey(idempotencyKey)
                            .build();
                    return premiumRepository.save(newPremium);
                });
    }

    @Transactional(readOnly = true)
    public Premium getPremium(String id) {
        return premiumRepository.findById(id)
                .orElseThrow(() -> new PremiumNotFoundException("Premium Not found"));
    }

    @Transactional
    public Premium updatePremium(String id, Premium premium) {
        Premium existingPremium = getPremium(id);

        existingPremium.setOrderId(premium.getOrderId());
        existingPremium.setAmount(premium.getAmount());
        existingPremium.setStatus(premium.getStatus());

        return premiumRepository.save(existingPremium);
    }

    @Transactional
    public Premium updateStatus(String id, String status) {
        Premium existingPremium = getPremium(id);

        existingPremium.setStatus(status);

        return premiumRepository.save(existingPremium);
    }
}
