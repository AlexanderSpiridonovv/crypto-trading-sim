package com.crypto.sim.service;

import com.crypto.sim.model.User;
import com.crypto.sim.repository.UserRepository;
import com.crypto.sim.util.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private static final double INITIAL_BALANCE = 10000.0;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(int userId) {
        return userRepository.findById(userId);
    }

    public void updateBalance(int userId, double newBalance) {
        userRepository.updateBalance(userId, newBalance);
    }

    public void resetUser(int userId) {
        userRepository.updateBalance(userId, INITIAL_BALANCE);
    }

    public double getAccountBalance(int userId) {
        return userRepository.getBalanceById(userId);
    }
    public boolean registerUser(String username, String password) {
        if (userRepository.findByUsername(username) != null) {
            return false; // Username already exists
        }

        String hashed = HashUtil.sha256(password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(hashed);
        user.setBalance(10000.0); // Default starting balance
        userRepository.createUser(user);
        return true;
    }


    public User loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) return null;
        String hashed = HashUtil.sha256(password);
        if(user.getPassword().equals(hashed))
            return user;
        return null;
    }


}
