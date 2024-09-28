package com.nagarro.assessment.model.rules;

import com.nagarro.assessment.dto.BillRequestDTO;
import com.nagarro.assessment.enums.UserType;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDiscountRule implements PercentageBasedDiscountRule {

    @Override
    public boolean isApplicable(BillRequestDTO bill) {
        return UserType.EMPLOYEE.equals(bill.getUserType());
    }

    @Override
    public double apply(BillRequestDTO bill, double currentTotal) {
        return currentTotal * 0.70; // 30% discount
    }

    @Override
    public int priority() {
        return 30;
    }


}