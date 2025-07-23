package com.crypto.sim.model;

import java.time.LocalDateTime;

public class Transaction {
    private int id;
    private int userId;
    private int cryptoId;
    private double quantity;
    private double price;
    private LocalDateTime timestamp;
    private String type; // "BUY" or "SELL"
    private Boolean isProfitable;

    public Transaction() {
    }

    public Transaction(int id, int userId, int cryptoId, double quantity, double price, LocalDateTime timestamp, String type, boolean profitable) {
        this.id = id;
        this.userId = userId;
        this.cryptoId = cryptoId;
        this.quantity = quantity;
        this.price = price;
        this.timestamp = timestamp;
        this.type = type;
        this.isProfitable = profitable;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getCryptoId() {
        return cryptoId;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCryptoId(int cryptoId) {
        this.cryptoId = cryptoId;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setType(String type) {
        this.type = type;
    }
    public Boolean getProfitable() {
        return  isProfitable;
    }
    public void setProfitable(Boolean profitable) {
        isProfitable = profitable;
    }
}

