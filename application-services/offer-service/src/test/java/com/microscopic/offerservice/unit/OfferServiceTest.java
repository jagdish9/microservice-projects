package com.microscopic.offerservice.unit;

import com.microscopic.offerservice.entity.Offer;
import com.microscopic.offerservice.repository.OfferRepository;
import com.microscopic.offerservice.service.OfferService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OfferServiceTest {

    @Mock
    private OfferRepository offerRepository;

    @InjectMocks
    private OfferService offerService;

    @Test
    void testCreateOffer() {
        Offer offer = Offer.builder()
                .id(2L)
                .offerName("Mobile")
                .quantity(20)
                .orderDate(LocalDateTime.now())
                .build();

        //Mock behavior
        when(offerRepository.save(offer)).thenReturn(offer);

        //call service
        Offer savedOffer = offerService.createOnlyOffer(offer);

        //verify
        assertEquals("Mobile", savedOffer.getOfferName());

        verify(offerRepository, times(1)).save(offer);
    }

    @Test
    void testGetOffer() {
        Offer offer = Offer.builder()
                .id(2L)
                .offerName("Mobile")
                .quantity(20)
                .orderDate(LocalDateTime.now())
                .build();

        when(offerRepository.findById(2L)).thenReturn(Optional.of(offer));

        Offer result = offerService.getOffer(2L);

        assertEquals(20, result.getQuantity());
    }

    @Test
    void getOffer_shouldThrowException() {
        when(offerRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> offerService.getOffer(2L));
    }
}
