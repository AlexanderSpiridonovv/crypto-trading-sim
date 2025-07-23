package com.crypto.sim.service;

import com.crypto.sim.model.TickerUpdate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PriceService {
    private final Map<String, Double> prices = new ConcurrentHashMap<>();

    public void updatePrice(TickerUpdate update) {
        prices.put(update.getSymbol(), update.getPrice());
    }

    public Map<String, Double> getAllPrices() {
        return prices;
    }
    public double getPrice(String symbol) {
        return prices.get(symbol);
    }
}