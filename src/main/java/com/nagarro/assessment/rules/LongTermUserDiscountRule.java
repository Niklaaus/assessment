package com.nagarro.assessment.rules;

import com.nagarro.assessment.constants.CommonConstants;
import com.nagarro.assessment.dto.BillRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class LongTermUserDiscountRule implements PercentageBasedDiscountRule{
    @Override
    public double apply(BillRequestDTO bill, double currentTotal) {
        return currentTotal * CommonConstants.LONGTERM_DISCOUNT_BILL_FACTOR;
    }

    @Override
    public boolean isApplicable(BillRequestDTO bill) {
        return bill.getCustomerTenure() > CommonConstants.LONGTERM_TENURE;
    }

    @Override
    public int priority() {
        return CommonConstants.LONGTERM_DISCOUNT_PRIORITY;
    }
}
