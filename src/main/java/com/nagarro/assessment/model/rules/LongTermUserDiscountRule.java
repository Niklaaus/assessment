package com.nagarro.assessment.model.rules;

import com.nagarro.assessment.dto.BillRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class LongTermUserDiscountRule implements PercentageBasedDiscountRule{
    @Override
    public double apply(BillRequestDTO bill, double currentTotal) {
        return currentTotal * 0.95; // 5% discount
    }

    @Override
    public boolean isApplicable(BillRequestDTO bill) {
        return bill.getCustomerTenure() > 2;
    }

    @Override
    public int priority() {
        return 5;
    }
}
