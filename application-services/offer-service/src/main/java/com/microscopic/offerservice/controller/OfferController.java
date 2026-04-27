package com.microscopic.offerservice.controller;

import com.microscopic.offerservice.dto.OfferDto;
import com.microscopic.offerservice.entity.Offer;
import com.microscopic.offerservice.service.OfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/offer")
public class OfferController {

    private static final Logger log = LoggerFactory.getLogger(OfferController.class);

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping("/create-offer")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createOffer(@RequestBody OfferDto offerDto) {
        return ResponseEntity.ok(offerService.createOffer(offerDto));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getOffer(@PathVariable Long id) {
        return ResponseEntity.ok(offerService.getOffer(id));
    }

    @PostMapping("/create-only-offer")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createOnlyOffer(@RequestBody Offer offer) {
        return ResponseEntity.ok(offerService.createOnlyOffer(offer));
    }
}
