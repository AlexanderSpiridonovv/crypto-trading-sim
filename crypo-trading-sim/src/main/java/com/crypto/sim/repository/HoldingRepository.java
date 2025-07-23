package com.crypto.sim.repository;

import com.crypto.sim.dto.HoldingWithSymbolDTO;
import com.crypto.sim.model.Holding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HoldingRepository {

    private final DataSource dataSource;

    @Autowired
    public HoldingRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Holding findById(int id) {
        String sql = "SELECT * FROM holdings WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRowToHolding(rs);
            } else {
                throw new RuntimeException("Holding with id " + id + " not found.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch holding by id", e);
        }
    }

    public List<Holding> findByUserIdAndCryptoId(int userId, int cryptoId) {
        List<Holding> holdings = new ArrayList<>();
        String sql = "SELECT * FROM holdings WHERE user_id = ? AND crypto_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, cryptoId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                holdings.add(mapRowToHolding(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch holdings", e);
        }
        return holdings;
    }

    public List<HoldingWithSymbolDTO> findByUserId(int userId) {
        List<HoldingWithSymbolDTO> holdings = new ArrayList<>();
        String sql = """
            SELECT h.id, h.user_id, h.crypto_id, h.quantity, c.symbol
            FROM holdings h
            JOIN cryptocurrencies c ON h.crypto_id = c.id
            WHERE h.user_id = ?
            """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoldingWithSymbolDTO dto = new HoldingWithSymbolDTO(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("crypto_id"),
                        rs.getDouble("quantity"),
                        rs.getString("symbol")
                );
                holdings.add(dto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch holdings by user", e);
        }
        return holdings;
    }

    public void upsertHolding(Holding holding) {
        String sql = """
                INSERT INTO holdings (user_id, crypto_id, quantity, price_at_transaction)
                VALUES (?, ?, ?, ?)
                """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, holding.getUserId());
            ps.setInt(2, holding.getCryptoId());
            ps.setDouble(3, holding.getQuantity());
            ps.setDouble(4, holding.getBuyPrice());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to upsert holding", e);
        }
    }

    public void updateQuantity(int holdingId, double newQuantity) {
        String sql = "UPDATE holdings SET quantity = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, newQuantity);
            ps.setInt(2, holdingId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update holding quantity", e);
        }
    }

    public void deleteByUserId(int id) {
        String sql = "DELETE FROM holdings WHERE user_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete holding", e);
        }
    }
    public void deleteById(int id) {
        String sql = "DELETE FROM holdings WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete holding", e);
        }
    }

    private Holding mapRowToHolding(ResultSet rs) throws SQLException {
        return new Holding(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getInt("crypto_id"),
                rs.getDouble("quantity"),
                rs.getDouble("price_at_transaction"),
                rs.getTimestamp("timestamp").toLocalDateTime()
        );
    }
}
