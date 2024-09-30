package com.nagarro.assessment.rules;

import com.nagarro.assessment.constants.CommonConstants;
import com.nagarro.assessment.dto.BillRequestDTO;
import com.nagarro.assessment.model.enums.UserType;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDiscountRule implements PercentageBasedDiscountRule {

    @Override
    public boolean isApplicable(BillRequestDTO bill) {
        return UserType.EMPLOYEE.equals(bill.getUserType());
    }

    @Override
    public double apply(BillRequestDTO bill, double currentTotal) {
        return currentTotal * CommonConstants.EMPLOYEE_DISCOUNTED_BILL_FACTOR;
    }

    @Override
    public int priority() {
        return CommonConstants.EMPLOYEE_DISCOUNT_PRIORITY;
    }


}