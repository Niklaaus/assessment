package com.nagarro.assessment.dto;

import com.nagarro.assessment.constants.ErrorMessages;
import com.nagarro.assessment.model.enums.ItemType;
import com.nagarro.assessment.model.enums.UserType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;


import java.util.List;

public class BillRequestDTO {
    @Override
    public String toString() {
        return "BillRequestDTO{" +
                "items=" + items +
                ", userType=" + userType +
                ", customerTenure=" + customerTenure +
                ", originalCurrency='" + originalCurrency + '\'' +
                ", targetCurrency='" + targetCurrency + '\'' +
                '}';
    }

    @NotEmpty(message = ErrorMessages.ITEMS_EMPTY)
    private List<@Valid Item> items;

    @NotNull(message = ErrorMessages.REQUIRED_FIELD)
    private UserType userType;

    @Min(value = 0, message = ErrorMessages.NEGATIVE_NOT_ALLOWED)
    private double customerTenure;

    @NotNull(message = ErrorMessages.REQUIRED_FIELD)
  //  @Size(min = 3, max = 3, message = "must be a 3-letter code")
    @Pattern(regexp = "^[A-Z]{3}$", message = ErrorMessages.INVALID_ORIGINAL_CURRENCY)
    private String originalCurrency;

    @NotNull(message = ErrorMessages.REQUIRED_FIELD)
   // @Size(min = 3, max = 3, message = "must be a 3-letter code")
    @Pattern(regexp = "^[A-Z]{3}$", message = ErrorMessages.INVALID_TARGET_CURRENCY)
    private String targetCurrency;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public double getCustomerTenure() {
        return customerTenure;
    }

    public void setCustomerTenure(double customerTenure) {
        this.customerTenure = customerTenure;
    }

    public String getOriginalCurrency() {
        return originalCurrency;
    }

    public void setOriginalCurrency(String originalCurrency) {
        this.originalCurrency = originalCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public static class Item {
        public Item(String name, ItemType category, double amount) {
            this.name = name;
            this.category = category;
            this.amount = amount;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "name='" + name + '\'' +
                    ", category=" + category +
                    ", amount=" + amount +
                    '}';
        }

        private String name;

        @NotNull(message = ErrorMessages.REQUIRED_FIELD)
        private ItemType category;

        @Min(value = 0, message = ErrorMessages.NEGATIVE_NOT_ALLOWED)
        private double amount;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ItemType getCategory() {
            return category;
        }

        public void setCategory(ItemType category) {
            this.category = category;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }
    }
}
