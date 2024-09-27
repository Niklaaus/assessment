package com.nagarro.assessment.service.impl;

import com.nagarro.assessment.dto.BillRequestDTO;
import com.nagarro.assessment.dto.BillResponseDTO;
import com.nagarro.assessment.dto.CurrencyResponseDTO;
import com.nagarro.assessment.enums.ItemType;
import com.nagarro.assessment.service.ExchangeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class ExchangeServiceImpl implements ExchangeService {
    @Value("${currency.exchange.url}")
    private String currencyApiUrl;

    private final RestTemplate restTemplate;

    public ExchangeServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public BillResponseDTO calculateFinalBill(BillRequestDTO billRequestDTO){

        double nonGroceryAmount= calculatePercentageDiscApplicableAmount(billRequestDTO.getItems());
        double groceryAmount= calculatePercentageDiscNAAmount(billRequestDTO.getItems());

       double discountedAmount= nonGroceryAmount-calculatePercentageBasedDiscount(nonGroceryAmount,billRequestDTO);
        discountedAmount=discountedAmount+groceryAmount;
       discountedAmount=discountedAmount-calculateUniversalDiscount(discountedAmount);


        double exchangeRate = getExchangeRate(billRequestDTO.getOriginalCurrency(), billRequestDTO.getTargetCurrency());
        return new BillResponseDTO(discountedAmount * exchangeRate, billRequestDTO.getTargetCurrency());

    }

    private double calculateUniversalDiscount(double discountedAmount) {
        return ((int)discountedAmount/100)*5.0;
    }

    private double calculatePercentageBasedDiscount(double nonGroceryAmount, BillRequestDTO billRequestDTO) {
        double percentageDiscount = 0.0;
        if ("employee".equalsIgnoreCase(billRequestDTO.getUserType())) {
            percentageDiscount = nonGroceryAmount * 0.30; // 30% discount for employees
        } else if ("affiliate".equalsIgnoreCase(billRequestDTO.getUserType())) {
            percentageDiscount = nonGroceryAmount * 0.10; // 10% discount for affiliates
        } else if (billRequestDTO.getCustomerTenure() > 2) {
            percentageDiscount = nonGroceryAmount * 0.05; // 5% discount for customers over 2 years
        }

        return percentageDiscount;
    }

    private double calculatePercentageDiscNAAmount(List<BillRequestDTO.Item> items) {
        return items.stream()
                .filter(item -> ItemType.percentageDiscountNotApplicable().contains(item.getCategory()))
                .mapToDouble(BillRequestDTO.Item::getAmount)
                .sum();
    }

    private double calculatePercentageDiscApplicableAmount(List<BillRequestDTO.Item> items) {
        return items.stream()
                .filter(item -> ItemType.percentageDiscountApplicable().contains(item.getCategory()))
                .mapToDouble(BillRequestDTO.Item::getAmount)
                .sum();
    }

    private double getExchangeRate(String originalCurrency, String targetCurrency) {
        String url = currencyApiUrl + targetCurrency;
        CurrencyResponseDTO response = restTemplate.getForObject(url, CurrencyResponseDTO.class);

        if (response != null && response.getResult().equals("success")) {
            Map<String, Double> conversionRates = response.getConversionRates();
            return conversionRates.get(targetCurrency);
        }else{
            throw new RuntimeException();
        }
    }

}
