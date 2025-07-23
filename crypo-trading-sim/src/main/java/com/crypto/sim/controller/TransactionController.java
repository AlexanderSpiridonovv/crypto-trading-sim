package com.crypto.sim.controller;

import com.crypto.sim.dto.HoldingWithSymbolDTO;
import com.crypto.sim.dto.TransactionDTO;
import com.crypto.sim.model.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.crypto.sim.service.TransactionService;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /*@GetMapping("/{userId}")
    public List<Transaction> getTransactionsByUserId(@PathVariable int userId) {
        return transactionService.getRawTransactionsByUserId(userId);
    }*/

    @GetMapping("/{userId}")
    public ResponseEntity<List<TransactionDTO>> getUserTransactions(@PathVariable int userId) {
        List<TransactionDTO> transactions = transactionService.getDetailedTransactionsByUserId(userId);
        return ResponseEntity.ok(transactions);
    }
}

