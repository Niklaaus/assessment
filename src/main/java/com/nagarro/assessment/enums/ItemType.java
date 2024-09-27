package com.nagarro.assessment.enums;

import java.util.Set;

public enum ItemType {
    GROCERY, NON_GROCERY;

    public static Set<ItemType> percentageDiscountApplicable(){
        return Set.of(NON_GROCERY);
    }

    public static Set<ItemType> percentageDiscountNotApplicable(){
        return Set.of(GROCERY);
    }
}
