package com.crypto.sim.model;

public class TickerUpdate {
    private String symbol;
    private double price;

    public TickerUpdate(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() { return symbol; }
    public double getPrice() { return price; }

    public void setSymbol(String symbol) { this.symbol = symbol; }
    public void setPrice(double price) { this.price = price; }
}