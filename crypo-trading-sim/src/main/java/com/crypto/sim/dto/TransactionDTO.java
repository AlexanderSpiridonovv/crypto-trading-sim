package com.crypto.sim.dto;

import java.time.LocalDateTime;

public class TransactionDTO {
    private int id;
    private String cryptoSymbol;
    private String type;
    private double quantity;
    private double price;
    private LocalDateTime timestamp;
    private Boolean isProfitable;

    public TransactionDTO(int id, String cryptoSymbol, String type, double quantity, double price, LocalDateTime timestamp,  boolean isProfitable) {
        this.id = id;
        this.cryptoSymbol = cryptoSymbol;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
        this.timestamp = timestamp;
        this.isProfitable = isProfitable;
    }

    // Getters (no setters needed if read-only)
    public int getId() { return id; }
    public String getCryptoSymbol() { return cryptoSymbol; }
    public String getType() { return type; }
    public double getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public Boolean getProfitable() { return isProfitable; }
}
