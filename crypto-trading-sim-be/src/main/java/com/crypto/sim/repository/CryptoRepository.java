package com.crypto.sim.repository;

import com.crypto.sim.model.Cryptocurrency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CryptoRepository {
    private final DataSource dataSource;

    @Autowired
    public CryptoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //for future improvements
    public boolean save(Cryptocurrency crypto) {
        String sql = "INSERT INTO cryptocurrencies (symbol, name) VALUES (?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, crypto.getSymbol());
            stmt.setString(2, crypto.getName());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error saving cryptocurrency: " + e.getMessage());
            return false;
        }
    }

    public Cryptocurrency findBySymbol(String symbol) {
        String sql = "SELECT * FROM cryptocurrencies WHERE symbol = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, symbol);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding cryptocurrency: " + e.getMessage());
        }
        return null;
    }

    //for future use
    public List<Cryptocurrency> findAll() {
        List<Cryptocurrency> list = new ArrayList<>();
        String sql = "SELECT * FROM cryptocurrencies";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving cryptocurrencies: " + e.getMessage());
        }
        return list;
    }

    private Cryptocurrency mapRow(ResultSet rs) throws SQLException {
        return new Cryptocurrency(
                rs.getInt("id"),
                rs.getString("symbol"),
                rs.getString("name")
        );
    }
}
