package com.nagarro.assessment.model.rules;

import com.nagarro.assessment.dto.BillRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class FlatDiscountRule implements UniversalDiscountRule {

    @Override
    public boolean isApplicable(BillRequestDTO bill) {
        return true; // This rule applies to everyone
    }

    @Override
    public double apply(BillRequestDTO bill, double currentTotal) {
        double discount = ((int)currentTotal/100)*5.0;
        return currentTotal - discount;
    }


}
