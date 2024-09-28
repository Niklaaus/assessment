package com.nagarro.assessment.utils;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class Util {

    public static double limitToThreeDecimalDigit(double d){
        DecimalFormat df = new DecimalFormat("#.###");
        return Double.parseDouble(df.format(d));
    }

    public static boolean isValidCurrencyCodeFormat(String currencyCode){
        return Pattern.matches("^[A-Za-z]{3}$", currencyCode);
    }
}
