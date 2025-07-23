package com.crypto.sim.repository;

import com.crypto.sim.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class UserRepository {
    @Autowired
    private DataSource dataSource;

    public User findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getDouble("balance")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching user with id: " + id, e);
        }
    }

    public void save(User user) {
        String sql = "UPDATE users SET username = ?, balance = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setDouble(2, user.getBalance());
            stmt.setInt(3, user.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving user: " + user.getId(), e);
        }
    }
    public void updateBalance(int userId, double balance) {
        String sql = "UPDATE users SET balance = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, balance);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update user balance", e);
        }
    }
    public double getBalanceById(int userId) {
        String sql = "SELECT balance FROM users WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement b = conn.prepareStatement(sql)) {

            b.setInt(1, userId); // Set userId parameter

            try (ResultSet rs = b.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("balance");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching user with id: " + userId, e);
        }
        return 0;
    }
    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setBalance(rs.getDouble("balance"));
                return user;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching user by username", e);
        }
    }

    public void createUser(User user) {
        String sql = "INSERT INTO users (username, password, balance) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword()); // hashed password
            stmt.setDouble(3, user.getBalance());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating user", e);
        }
    }


}
