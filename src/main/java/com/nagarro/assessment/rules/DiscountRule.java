package com.nagarro.assessment.rules;

import com.nagarro.assessment.dto.BillRequestDTO;

@FunctionalInterface
public interface DiscountRule {
    double apply(BillRequestDTO bill, double currentTotal);

    default boolean isApplicable(BillRequestDTO bill) {
        return true;
    }

    default int priority() {
        return 0;
    }
}