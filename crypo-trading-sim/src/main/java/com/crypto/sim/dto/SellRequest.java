package com.crypto.sim.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SellRequest {

    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotNull(message = "Holding ID is required")
    private Integer holdingId;

    @NotBlank(message = "Cryptocurrency symbol is required")
    private String symbol;

    @Min(value = 0, message = "Quantity must be greater than 0")
    private double quantity;

    // Getters and setters
    public Integer getUserId() {
        return userId;
    }

    public Integer getHoldingId() {
        return holdingId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setHoldingId(Integer holdingId) {
        this.holdingId = holdingId;
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
