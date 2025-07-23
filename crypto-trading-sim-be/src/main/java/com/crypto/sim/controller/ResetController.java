package com.crypto.sim.controller;

import com.crypto.sim.repository.TransactionRepository;
import com.crypto.sim.repository.HoldingRepository;
import com.crypto.sim.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reset")
@CrossOrigin
public class ResetController {

    private static final double INITIAL_BALANCE = 10000.0;

    private final TransactionRepository transactionRepository;
    private final HoldingRepository holdingRepository;
    private final UserRepository userRepository;

    @Autowired
    public ResetController(TransactionRepository transactionRepository,
                           HoldingRepository holdingRepository,
                           UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.holdingRepository = holdingRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/{userId}")
    public String resetUser(@PathVariable int userId) {
        transactionRepository.deleteByUserId(userId);
        holdingRepository.deleteByUserId(userId);
        userRepository.updateBalance(userId, INITIAL_BALANCE);
        return "Account reset successfully";
    }
}
