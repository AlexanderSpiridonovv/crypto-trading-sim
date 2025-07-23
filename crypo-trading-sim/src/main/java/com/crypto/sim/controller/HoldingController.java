package com.crypto.sim.controller;

import com.crypto.sim.dto.HoldingWithSymbolDTO;
import com.crypto.sim.service.HoldingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/holdings")
public class HoldingController {

    private final HoldingService holdingService;

    public HoldingController(HoldingService holdingService) {
        this.holdingService = holdingService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<HoldingWithSymbolDTO>> getUserHoldings(@PathVariable int userId) {
        List<HoldingWithSymbolDTO> holdings = holdingService.getHoldingsByUserId(userId);
        return ResponseEntity.ok(holdings);
    }
}
