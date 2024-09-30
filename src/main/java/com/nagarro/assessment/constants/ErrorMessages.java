package com.nagarro.assessment.constants;

public class ErrorMessages {
    public static final String ERROR ="error";
    public static final String INVALID_REQUEST_BODY="Invalid request body format or missing fields.";
    public static final String UNEXPECTED="An unexpected error occurred";
    public static final String INVALID_ORIGINAL_CURRENCY="Original currency is required and must be a 3 letter code";
    public static final String INVALID_TARGET_CURRENCY ="Target currency is required and must be a 3 letter code" ;
    public static final String TARGET_CURRENCY_NOTFOUND ="Target currency rate not found." ;
    public static final String API_RESPONSE_FAILED ="Failed to retrieve exchange rate. API response was unsuccessful. Please verify the original currency code" ;
    public static final String ERROR_EXCHANGE_API = "Error in fetching the exchange conversion rates.";
    public static final String ITEMS_EMPTY = "At least one item is required";
    public static final String REQUIRED_FIELD = "required field";
    public static final String NEGATIVE_NOT_ALLOWED = "can't be less than 0";
    public static final String USER_NOT_FOUND = "User not found";
}
