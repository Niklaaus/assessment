package com.nagarro.assessment.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.nagarro.assessment.dto.CurrencyResponseDTO;
import com.nagarro.assessment.service.impl.ExchangeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

class ExchangeServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ExchangeService exchangeService=new ExchangeServiceImpl(restTemplate,null);

    private final String currencyApiUrl = "https://api.example.com/currency/";
    @BeforeEach
    void setUp() {
        exchangeService.setCurrencyApiUrl(currencyApiUrl);
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void testGetExchangeRate_InvalidOriginalCurrency() {
        // Given
        String originalCurrency = "US"; // Invalid format
        String targetCurrency = "EUR";

        // When & Then
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> exchangeService.getExchangeRate(originalCurrency, targetCurrency)
        );

        // Assert exception message
        assertEquals("Original currency is required and must be a 3 letter code", thrown.getMessage());
    }

    @Test
    void testGetExchangeRate_InvalidTargetCurrency() {
        // Given
        String originalCurrency = "USD";
        String targetCurrency = "EU"; // Invalid format

        // When & Then
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> exchangeService.getExchangeRate(originalCurrency, targetCurrency)
        );

        // Assert exception message
        assertEquals("Target currency is required and must be a 3 letter code", thrown.getMessage());
    }

    }



