package com.nagarro.assessment.enums;

import java.util.EnumSet;
import java.util.Set;

public enum ItemType {
    GROCERY, ELECTRONICS, APPARELS, HOME_DECOR, STATIONARY;

    public static Set<ItemType> percentageDiscountApplicable(){
        EnumSet<ItemType> allItems = EnumSet.allOf(ItemType.class);
        allItems.removeAll(percentageDiscountNotApplicable());
        return allItems;
    }

    public static Set<ItemType> percentageDiscountNotApplicable(){
        return Set.of(GROCERY);
    }
}
