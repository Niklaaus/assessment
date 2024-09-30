package com.nagarro.assessment.rules;

import com.nagarro.assessment.constants.CommonConstants;
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
        double discount = ((int)currentTotal/100)* CommonConstants.FLAT_DISCOUNT_BILL_FACTOR;
        return currentTotal - discount;
    }


}
