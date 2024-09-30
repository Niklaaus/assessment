package com.nagarro.assessment.model.service.impl;

import com.nagarro.assessment.constants.CommonConstants;
import com.nagarro.assessment.constants.ErrorMessages;
import com.nagarro.assessment.dto.BillRequestDTO;
import com.nagarro.assessment.dto.BillResponseDTO;
import com.nagarro.assessment.dto.CurrencyResponseDTO;
import com.nagarro.assessment.model.enums.ItemType;
import com.nagarro.assessment.model.rules.DiscountRule;
import com.nagarro.assessment.model.rules.DiscountRules;
import com.nagarro.assessment.model.service.ExchangeService;
import com.nagarro.assessment.utils.Util;
import io.micrometer.common.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ExchangeServiceImpl implements ExchangeService {

    private static final Logger logger = LogManager.getLogger(ExchangeServiceImpl.class);

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
        logger.info("amount for eligible percentage discount: {}", nonGroceryAmount);
        logger.info("amount without eligible percentage discount: {}", groceryAmount);

       double discountedAmount= applyPercentageBasedDiscount(nonGroceryAmount,billRequestDTO);
        discountedAmount=discountedAmount+groceryAmount;
       discountedAmount= applyUniversalDiscount(discountedAmount, billRequestDTO);

        logger.info("Final discounted amount : {} {}",discountedAmount, billRequestDTO.getOriginalCurrency());

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
            logger.error("Invalid original currency code :{}", originalCurrency);
            throw new IllegalArgumentException(ErrorMessages.INVALID_ORIGINAL_CURRENCY);
        }

        if (StringUtils.isBlank(targetCurrency) || !Util.isValidCurrencyCodeFormat(targetCurrency)) {
            logger.error("Invalid target currency code :{}", originalCurrency);
            throw new IllegalArgumentException(ErrorMessages.INVALID_TARGET_CURRENCY);
        }

        try {
            String url = currencyApiUrl + originalCurrency;
            CurrencyResponseDTO response = restTemplate.getForObject(url, CurrencyResponseDTO.class);

            if (response != null && CommonConstants.SUCCESS.equalsIgnoreCase(response.getResult())) {
                logger.info("Successfully received response from Currency Conversion API");
                Map<String, Double> conversionRates = response.getConversionRates();
                return Optional.ofNullable(conversionRates.get(targetCurrency))
                        .orElseThrow(() -> new IllegalArgumentException(ErrorMessages.TARGET_CURRENCY_NOTFOUND));
            } else {
                logger.error(ErrorMessages.API_RESPONSE_FAILED + " : original :{}, target: {}", originalCurrency, targetCurrency);
                throw new RuntimeException(ErrorMessages.API_RESPONSE_FAILED);
            }
        } catch (Exception ex) {
            logger.error("error in conversion from {} to {}",originalCurrency, targetCurrency);
            logger.error(ex);
            throw new RuntimeException(ErrorMessages.ERROR_EXCHANGE_API);
        }
    }

    @Override
    public void setCurrencyApiUrl(String currencyApiUrl) {
        this.currencyApiUrl=currencyApiUrl;
    }

}
