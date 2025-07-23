package com.crypto.sim.controller;

import com.crypto.sim.dto.PriceResponseDTO;
import com.crypto.sim.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/prices")
@CrossOrigin
public class PriceController {

    private final PriceService priceService;

    @Autowired
    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    /**
     * Returns all current prices.
     */
    @GetMapping
    public ResponseEntity<List<PriceResponseDTO>> getAllPrices() {
        Map<String, Double> prices = priceService.getAllPrices();

        List<PriceResponseDTO> response = prices.entrySet().stream()
                .map(entry -> new PriceResponseDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    /**
     * Returns the price for a specific symbol.
     */
    @GetMapping("/{symbol}")
    public ResponseEntity<PriceResponseDTO> getPriceBySymbol(@PathVariable String symbol) {
        double price = priceService.getPrice(symbol.toUpperCase());

        return ResponseEntity.ok(new PriceResponseDTO(symbol.toUpperCase(), price));
    }
}
