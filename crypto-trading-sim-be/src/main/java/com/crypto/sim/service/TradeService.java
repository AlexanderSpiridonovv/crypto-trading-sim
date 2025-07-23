package com.crypto.sim.service;

import com.crypto.sim.model.*;
import com.crypto.sim.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TradeService {
    private final UserRepository userRepo;
    private final CryptoRepository cryptoRepo;
    private final TransactionRepository txnRepo;
    private final HoldingRepository holdingRepo;
    private final PriceService priceService;

    public TradeService(UserRepository userRepo, CryptoRepository cryptoRepo,
                        TransactionRepository txnRepo, HoldingRepository holdingRepo,
                        PriceService priceService) {
        this.userRepo = userRepo;
        this.cryptoRepo = cryptoRepo;
        this.txnRepo = txnRepo;
        this.holdingRepo = holdingRepo;
        this.priceService = priceService;
    }

    public void buyCrypto(int userId, String symbol, double quantity) {
        double pricePerUnit = priceService.getPrice(symbol);
        double totalCost = quantity * pricePerUnit;

        User user = userRepo.findById(userId);
        if (user.getBalance() < totalCost) throw new RuntimeException("Insufficient funds");

        Cryptocurrency crypto = cryptoRepo.findBySymbol(symbol);

        user.setBalance(user.getBalance() - totalCost);
        userRepo.save(user);

        holdingRepo.upsertHolding(new Holding(0, userId, crypto.getId(), quantity, pricePerUnit, LocalDateTime.now()));
        txnRepo.save(new Transaction(0, userId, crypto.getId(), quantity, pricePerUnit, LocalDateTime.now(), "BUY",false));
    }

    public void sellCrypto(int userId, int holdingId, String symbol, double quantityToSell) {
        double pricePerUnit = priceService.getPrice(symbol);
        Cryptocurrency crypto = cryptoRepo.findBySymbol(symbol);
        Holding holding = holdingRepo.findById(holdingId);

        if (holding == null || holding.getUserId() != userId || holding.getCryptoId() != crypto.getId()) {
            throw new IllegalArgumentException("Invalid holding ID or mismatched user/crypto");
        }

        if (quantityToSell > holding.getQuantity()) {
            throw new IllegalArgumentException("Not enough quantity to sell");
        }

        // Update or delete holding
        if (quantityToSell == holding.getQuantity()) {
            holdingRepo.deleteById(holdingId);
        } else {
            double newQuantity = holding.getQuantity() - quantityToSell;
            holding.setQuantity(newQuantity);
            holdingRepo.updateQuantity(holdingId, newQuantity);
        }

        double totalEarned = quantityToSell * pricePerUnit;

        // Update user balance
        User user = userRepo.findById(userId);
        user.setBalance(user.getBalance() + totalEarned);
        userRepo.save(user);

        // Is the sell profitable
        boolean isProfitable = false;
        double priceBought = holding.getBuyPrice();
        if(priceBought <= pricePerUnit) {
            isProfitable = true;
        }
        // Record transaction
        Transaction transaction = new Transaction(
                0,
                userId,
                crypto.getId(),
                quantityToSell,
                pricePerUnit,
                LocalDateTime.now(),
                "SELL",
                isProfitable
        );
        txnRepo.save(transaction);
    }
}