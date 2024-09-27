package com.nagarro.assessment.dto;

import com.nagarro.assessment.enums.ItemType;

import java.util.List;

public class BillRequestDTO {
    private List<Item> items;
    private String userType;
    private int customerTenure;
    private String originalCurrency;
    private String targetCurrency;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getCustomerTenure() {
        return customerTenure;
    }

    public void setCustomerTenure(int customerTenure) {
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
        private String name;
        private ItemType category;
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
