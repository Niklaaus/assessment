package com.nagarro.assessment.service.impl;

import com.nagarro.assessment.dto.BillRequestDTO;
import com.nagarro.assessment.dto.BillResponseDTO;
import com.nagarro.assessment.dto.CurrencyResponseDTO;
import com.nagarro.assessment.enums.ItemType;
import com.nagarro.assessment.model.rules.DiscountRule;
import com.nagarro.assessment.model.rules.DiscountRules;
import com.nagarro.assessment.service.ExchangeService;
import com.nagarro.assessment.utils.Util;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ExchangeServiceImpl implements ExchangeService {
    @Value("${currency.exchange.url}")
    private String currencyApiUrl;
    private final RestTemplate restTemplate;
    private final DiscountRules discountRules;

    public ExchangeServiceImpl(RestTemplate restTemplate, DiscountRules discountRules) {
        this.restTemplate = restTemplate;
        this.discountRules = discountRules;
    }

    @Override
    public BillResponseDTO calculateFinalBill(BillRequestDTO billRequestDTO){

        double nonGroceryAmount= calculatePercentageDiscApplicableAmount(billRequestDTO.getItems());
        double groceryAmount= calculatePercentageDiscNAAmount(billRequestDTO.getItems());

       double discountedAmount= applyPercentageBasedDiscount(nonGroceryAmount,billRequestDTO);
        discountedAmount=discountedAmount+groceryAmount;
       discountedAmount= applyUniversalDiscount(discountedAmount, billRequestDTO);


        double exchangeRate = getExchangeRate(billRequestDTO.getOriginalCurrency(), billRequestDTO.getTargetCurrency());
        return new BillResponseDTO(Util.limitToThreeDecimalDigit(discountedAmount * exchangeRate), billRequestDTO.getTargetCurrency());

    }

    private double applyUniversalDiscount(double discountedAmount, BillRequestDTO billRequestDTO) {

        for(DiscountRule rule: discountRules.getUniversalDiscountRules()){
            if(rule.isApplicable(billRequestDTO)){
                discountedAmount= rule.apply(billRequestDTO,discountedAmount);
                break;
            }
        }
        return discountedAmount;

    }

    private double applyPercentageBasedDiscount(double nonGroceryAmount, BillRequestDTO billRequestDTO) {
        for(DiscountRule rule: discountRules.getPercenBasedDiscountRules()){
            if(rule.isApplicable(billRequestDTO)){
                nonGroceryAmount= rule.apply(billRequestDTO,nonGroceryAmount);
                break;
            }
        }
        return nonGroceryAmount;
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

    public double getExchangeRate(String originalCurrency,
                                  String targetCurrency) {
        if (StringUtils.isBlank(originalCurrency) || !Util.isValidCurrencyCodeFormat(originalCurrency))  {
            throw new IllegalArgumentException("Original currency is required and must be a 3 letter code");
        }

        if (StringUtils.isBlank(targetCurrency) || !Util.isValidCurrencyCodeFormat(targetCurrency)) {
            throw new IllegalArgumentException("Target currency is required and must be a 3 letter code");
        }

        try {
            String url = currencyApiUrl + originalCurrency;
            System.out.println("URL :" +url);
            CurrencyResponseDTO response = restTemplate.getForObject(url, CurrencyResponseDTO.class);

            if (response != null && "success".equalsIgnoreCase(response.getResult())) {
                Map<String, Double> conversionRates = response.getConversionRates();
                return Optional.ofNullable(conversionRates.get(targetCurrency))
                        .orElseThrow(() -> new IllegalArgumentException("Target currency rate not found."));
            } else {
                throw new RuntimeException("Failed to retrieve exchange rate. API response was unsuccessful. Please verify the original currency code ");
            }
        } catch (Exception ex) {
            //log.error(ex.getMessage());
            throw new RuntimeException("Error in fetching the exchange conversion rates.");
        }
    }

    @Override
    public void setCurrencyApiUrl(String currencyApiUrl) {
        this.currencyApiUrl=currencyApiUrl;
    }

}
