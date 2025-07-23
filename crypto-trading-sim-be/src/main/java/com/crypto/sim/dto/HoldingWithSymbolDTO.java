package com.crypto.sim.dto;

public class HoldingWithSymbolDTO {
    private int id;
    private int userId;
    private int cryptoId;
    private double quantity;
    private String symbol;

    public HoldingWithSymbolDTO(int id, int userId, int cryptoId, double quantity, String symbol) {
        this.id = id;
        this.userId = userId;
        this.cryptoId = cryptoId;
        this.quantity = quantity;
        this.symbol = symbol;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCryptoId() {
        return cryptoId;
    }

    public void setCryptoId(int cryptoId) {
        this.cryptoId = cryptoId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
