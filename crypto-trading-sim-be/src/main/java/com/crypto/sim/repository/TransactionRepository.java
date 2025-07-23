package com.crypto.sim.repository;

import com.crypto.sim.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.crypto.sim.dto.TransactionDTO;

@Repository
public class TransactionRepository {

    private final DataSource dataSource;

    @Autowired
    public TransactionRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Transaction transaction) {


        String sql = """
            INSERT INTO transactions (user_id, crypto_id, type, price_at_transaction, quantity, profitable) 
            VALUES (?, ?, ?, ?, ?, ?)
            """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, transaction.getUserId());
            ps.setInt(2, transaction.getCryptoId());
            ps.setString(3, transaction.getType());
            ps.setDouble(4, transaction.getPrice());
            ps.setDouble(5, transaction.getQuantity());

            Boolean profitable = transaction.getProfitable();
            if (profitable != null) {
                ps.setBoolean(6, profitable);
            } else {
                ps.setNull(6, Types.BOOLEAN);
            }

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save transaction", e);
        }
    }


    public List<TransactionDTO> findDetailedByUserId(int userId) {
        List<TransactionDTO> transactions = new ArrayList<>();
        String sql = """
            SELECT t.id, c.symbol, t.type, t.quantity, t.price_at_transaction, t.timestamp, t.profitable
            FROM transactions t
            JOIN cryptocurrencies c ON t.crypto_id = c.id
            WHERE t.user_id = ?
            ORDER BY t.timestamp DESC
        """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                transactions.add(new TransactionDTO(
                        rs.getInt("id"),
                        rs.getString("symbol"),
                        rs.getString("type"),
                        rs.getDouble("quantity"),
                        rs.getDouble("price_at_transaction"),
                        rs.getTimestamp("timestamp").toLocalDateTime(),
                        rs.getBoolean("profitable")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch detailed transactions", e);
        }
        return transactions;
    }
    public void deleteByUserId(int userId) {
        String sql = "DELETE FROM transactions WHERE user_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete transactions", e);
        }
    }


}
