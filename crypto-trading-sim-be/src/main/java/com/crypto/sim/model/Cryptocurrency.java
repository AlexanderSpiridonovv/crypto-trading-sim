package com.crypto.sim.model;

public class Cryptocurrency {
    private int id;
    private String symbol;
    private String name;

    public Cryptocurrency() {
    }

    public Cryptocurrency(int id, String symbol, String name) {
        this.id = id;
        this.symbol = symbol;
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public String getSymbol() {
        return symbol;
    }
    public String getName() {
        return name;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public void setName(String name) {
        this.name = name;
    }
}