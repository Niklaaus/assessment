package com.nagarro.assessment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class CurrencyResponseDTO {
    private String result;

    @JsonProperty("conversion_rates")
    private Map<String, Double> conversion_rates;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Map<String, Double> getConversionRates() {
        return conversion_rates;
    }

    public void setConversionRates(Map<String, Double> conversion_rates) {
        this.conversion_rates = conversion_rates;
    }
}