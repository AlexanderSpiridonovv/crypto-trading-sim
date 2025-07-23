package com.crypto.sim.service;

import com.crypto.sim.dto.TransactionDTO;
import com.crypto.sim.model.Transaction;
import com.crypto.sim.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public void deleteTransactionsByUserId(int userId) {
        transactionRepository.deleteByUserId(userId);
    }

    public List<TransactionDTO> getDetailedTransactionsByUserId(int userId) {
        return transactionRepository.findDetailedByUserId(userId);
    }

}
