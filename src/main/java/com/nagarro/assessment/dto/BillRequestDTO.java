package com.nagarro.assessment.dto;

import com.nagarro.assessment.enums.ItemType;
import com.nagarro.assessment.enums.UserType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;


import java.util.List;

public class BillRequestDTO {

    @NotEmpty(message = "At least one item is required")
    private List<@Valid Item> items;

    @NotNull(message = "required field")
    private UserType userType;

    @Min(value = 0, message = "can't be less than 0")
    private double customerTenure;

    @NotNull(message = "required field")
  //  @Size(min = 3, max = 3, message = "must be a 3-letter code")
    @Pattern(regexp = "^[A-Z]{3}$", message = "must be a valid 3-letter currency code")
    private String originalCurrency;

    @NotNull(message = "required field")
   // @Size(min = 3, max = 3, message = "must be a 3-letter code")
    @Pattern(regexp = "^[A-Z]{3}$", message = "must be a valid 3-letter currency code")
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

        private String name;

        @NotNull(message = "required field")
        private ItemType category;

        @Min(value = 0, message = "can't be less than 0")
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
