package com.crypto.sim.controller;

import com.crypto.sim.model.User;
import com.crypto.sim.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable int userId) {
        return userService.getUserById(userId);
    }

    @PutMapping("/{userId}/balance")
    public void updateBalance(@PathVariable int userId, @RequestBody double newBalance) {
        userService.updateBalance(userId, newBalance);
    }

    @PostMapping("/{userId}/reset")
    public void resetUser(@PathVariable int userId) {
        userService.resetUser(userId);
    }
    @GetMapping("/{userId}/balance")
    public ResponseEntity<Double> getAccountBalance(@PathVariable int userId) {
        double balance = userService.getAccountBalance(userId);
        return ResponseEntity.ok(balance);
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        boolean success = userService.registerUser(user.getUsername(), user.getPassword());
        if (success) return ResponseEntity.ok("User registered");
        return ResponseEntity.badRequest().body("Username already exists");
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        User existingUser = userService.loginUser(user.getUsername(), user.getPassword());
        if (existingUser != null) {
            return ResponseEntity.ok(existingUser);
        }
        return ResponseEntity.status(401).build();
    }

}

