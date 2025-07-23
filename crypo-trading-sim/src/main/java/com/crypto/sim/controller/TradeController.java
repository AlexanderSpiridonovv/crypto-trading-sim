package com.crypto.sim.controller;

import com.crypto.sim.dto.BuyRequest;
import com.crypto.sim.dto.SellRequest;
import com.crypto.sim.service.TradeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/trade")
@Validated
public class TradeController {

    private final TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @PostMapping("/buy")
    public ResponseEntity<String> buyCrypto(@Valid @RequestBody BuyRequest request) {
        try {
            tradeService.buyCrypto(request.getUserId(), request.getSymbol().toUpperCase(), request.getQuantity());
            return ResponseEntity.ok("Buy order executed successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Buy failed: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }
    }

    @PostMapping("/sell")
    public ResponseEntity<String> sellCrypto(@Valid @RequestBody SellRequest request) {
        try {
            tradeService.sellCrypto(request.getUserId(), request.getHoldingId(), request.getSymbol().toUpperCase(), request.getQuantity());
            return ResponseEntity.ok("Sell order executed successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sell failed: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }
    }
}
