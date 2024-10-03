package com.nagarro.assessment.constants;

public class CommonConstants {
    public static final String API_BASE_URL="/api";
    public static final String CALCULATE_API="/calculate";
    public static final String CALCULATE_API_URL=API_BASE_URL+CALCULATE_API;

    //10% discount
    public static final Double AFFILIATE_DISCOUNTED_BILL_FACTOR = 0.90;
    public static final int AFFILIATE_DISCOUNT_PRIORITY = 10;

    //30%
    public static final Double EMPLOYEE_DISCOUNTED_BILL_FACTOR = 0.70;
    public static final int EMPLOYEE_DISCOUNT_PRIORITY = 30;

    //5 rs per 100
    public static final double FLAT_DISCOUNT_BILL_FACTOR = 5.0;


    //5%
    public static final double LONGTERM_DISCOUNT_BILL_FACTOR=0.95;
    public static final int LONGTERM_DISCOUNT_PRIORITY=5;
    public static final int LONGTERM_TENURE=2;


    public static final String SUCCESS = "success";
}
