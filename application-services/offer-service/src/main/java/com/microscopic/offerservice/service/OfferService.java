package com.microscopic.offerservice.service;

import com.microscopic.offerservice.dto.OfferDto;
import com.microscopic.offerservice.entity.Offer;
import com.microscopic.offerservice.exception.ResourceNotFoundException;
import com.microscopic.offerservice.repository.OfferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OfferService {

    private static final Logger log = LoggerFactory.getLogger(OfferService.class);

    private final OfferRepository offerRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OfferService(OfferRepository offerRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.offerRepository = offerRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Offer createOffer(OfferDto offerDto) {
        Offer offer = Offer.builder()
                .offerName(offerDto.getOfferName())
                .quantity(offerDto.getQuantity())
                .orderDate(LocalDateTime.now())
                .build();

        log.info("Creating offer: {}", offer.getOfferName());
        Offer savedOffer = offerRepository.save(offer);

        if(savedOffer.getId() != null) {
            log.info("Created offerId: {}", savedOffer.getId());

            kafkaTemplate.send("offer-topic", "Offer created with id: "+savedOffer.getId());
            log.info("Published message on kafka topic for offerId: {}", savedOffer.getId());
        }

        return savedOffer;
    }

    public Offer getOffer(Long id) {
        log.info("Getting details for offerId: {}", id);
        return offerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OfferId not found: "+id));
    }

    public Offer createOnlyOffer(Offer offer) {
        log.info("Creating only offer: {}", offer.getOfferName());
        return offerRepository.save(offer);
    }
}
