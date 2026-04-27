package com.microscopic.offerservice.controller;

import com.microscopic.offerservice.entity.Offer;
import com.microscopic.offerservice.service.OfferService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OfferController.class)
public class OfferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OfferService offerService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public OfferService offerService() {
            return Mockito.mock(OfferService.class);
        }
    }

    @Test
    void testGetOffer() throws Exception {
        Offer offer = Offer.builder()
                .id(2L)
                .offerName("Mobile")
                .quantity(20)
                .orderDate(LocalDateTime.now())
                .build();

        when(offerService.getOffer(2L)).thenReturn(offer);

        mockMvc.perform(get("/offer/2"))
                .andExpect(status().isOk());
    }
}
