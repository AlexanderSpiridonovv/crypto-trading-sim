package com.crypto.sim.model;
import java.time.LocalDateTime;

public class Holding {
    private int id;
    private int userId;
    private int cryptoId;
    private double quantity;
    private double buyPrice;
    private LocalDateTime timestamp;

    public Holding(int id, int userId, int cryptoId, double quantity, double buyPrice, LocalDateTime timestamp) {
        this.id = id;
        this.userId = userId;
        this.cryptoId = cryptoId;
        this.quantity = quantity;
        this.buyPrice = buyPrice;
        this.timestamp = timestamp;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public int getCryptoId() { return cryptoId; }
    public double getQuantity() { return quantity; }
    public double getBuyPrice() { return buyPrice; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setQuantity(double quantity) { this.quantity = quantity; }
}
