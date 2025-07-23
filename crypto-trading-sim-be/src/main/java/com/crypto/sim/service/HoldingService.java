package com.crypto.sim.service;

import com.crypto.sim.dto.HoldingWithSymbolDTO;

import com.crypto.sim.repository.HoldingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoldingService {

    private final HoldingRepository holdingRepository;

    public HoldingService(HoldingRepository holdingRepository) {
        this.holdingRepository = holdingRepository;
    }

    public List<HoldingWithSymbolDTO> getHoldingsByUserId(int userId) {
        return holdingRepository.findByUserId(userId);
    }
}
