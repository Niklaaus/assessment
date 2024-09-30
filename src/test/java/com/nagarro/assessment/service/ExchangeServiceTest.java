package com.nagarro.assessment.service;

import static org.junit.jupiter.api.Assertions.*;

import com.nagarro.assessment.constants.ErrorMessages;
import com.nagarro.assessment.model.service.ExchangeService;
import com.nagarro.assessment.model.service.impl.ExchangeServiceImpl;
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
        assertEquals(ErrorMessages.INVALID_ORIGINAL_CURRENCY, thrown.getMessage());
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
        assertEquals(ErrorMessages.INVALID_TARGET_CURRENCY, thrown.getMessage());
    }

    }



