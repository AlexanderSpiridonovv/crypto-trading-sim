package com.crypto.sim.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BuyRequest {

    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotBlank(message = "Cryptocurrency symbol is required")
    private String symbol;

    @Min(value = 0, message = "Quantity must be greater than 0")
    private double quantity;

    // Getters and setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
