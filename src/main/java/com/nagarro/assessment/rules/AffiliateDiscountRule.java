package com.nagarro.assessment.rules;

import com.nagarro.assessment.constants.CommonConstants;
import com.nagarro.assessment.dto.BillRequestDTO;
import com.nagarro.assessment.model.enums.UserType;
import org.springframework.stereotype.Component;

@Component
public class AffiliateDiscountRule implements PercentageBasedDiscountRule {

    @Override
    public boolean isApplicable(BillRequestDTO bill) {
        return UserType.AFFILIATE.equals(bill.getUserType());
    }

    @Override
    public double apply(BillRequestDTO bill, double currentTotal) {
        return currentTotal * CommonConstants.AFFILIATE_DISCOUNTED_BILL_FACTOR;
    }

    @Override
    public int priority() {
        return CommonConstants.AFFILIATE_DISCOUNT_PRIORITY;
    }

}