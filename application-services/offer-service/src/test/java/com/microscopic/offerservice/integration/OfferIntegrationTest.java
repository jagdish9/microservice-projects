package com.microscopic.offerservice.integration;

import com.microscopic.offerservice.entity.Offer;
import com.microscopic.offerservice.repository.OfferRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OfferIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OfferRepository offerRepository;

    @Test
    void testCreateAndFetchOffer() throws Exception {
        String requestJson = """
                {
                    "offerName": "Mobile",
                    "quantity": 15
                }
                """;

        mockMvc.perform(post("/offer/create-only-offer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());

        //fetch from DB
        List<Offer> offers = offerRepository.findAll();
        assertFalse(offers.isEmpty());
    }
}
