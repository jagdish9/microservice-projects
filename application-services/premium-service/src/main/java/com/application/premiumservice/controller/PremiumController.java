package com.application.premiumservice.controller;

import com.application.premiumservice.entity.Premium;
import com.application.premiumservice.service.PremiumService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/premium")
public class PremiumController {

    private final PremiumService premiumService;

    public PremiumController(PremiumService premiumService) {
        this.premiumService = premiumService;
    }

    @PostMapping("/create-premium")
    public ResponseEntity<Premium> createPremium(
            @Valid @RequestBody Premium premium,
            @RequestHeader("Idempotency-key") String idempotencyKey
    ) {
        Premium createdPremium = premiumService.createPremium(premium, idempotencyKey);
        return new ResponseEntity<>(createdPremium, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Premium> getPremium(@PathVariable String id) {
        return ResponseEntity.ok(premiumService.getPremium(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Premium> updatePremium(
            @PathVariable String id,
            @RequestBody Premium premium
    ) {
        return ResponseEntity.ok(premiumService.updatePremium(id, premium));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Premium> updatePremiumStatus(
            @PathVariable String id,
            @RequestParam String status
    ) {
       return ResponseEntity.ok(premiumService.updateStatus(id, status));
    }
}
