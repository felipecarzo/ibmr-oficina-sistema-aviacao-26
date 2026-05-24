package com.aviacao.dao;

import com.aviacao.model.Aeronave;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AeronaveDAO {
    private final ConnectionFactory cf = ConnectionFactory.getInstance();

    public Aeronave findById(int id) {
        String sql = "SELECT * FROM aeronave WHERE id_aeronave = ?";
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapper(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar aeronave: " + e.getMessage());
        }
        return null;
    }

    public List<Aeronave> findAll() {
        String sql = "SELECT * FROM aeronave ORDER BY id_aeronave";
        List<Aeronave> list = new ArrayList<>();
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) list.add(mapper(rs));
        } catch (SQLException e) {
            System.err.println("Erro ao listar aeronaves: " + e.getMessage());
        }
        return list;
    }

    public List<Aeronave> findByFabricante(String fabricante) {
        String sql = "SELECT * FROM aeronave WHERE fabricante LIKE ? ORDER BY id_aeronave";
        List<Aeronave> list = new ArrayList<>();
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + fabricante + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapper(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar por fabricante: " + e.getMessage());
        }
        return list;
    }

    public void insert(Aeronave a) {
        String sql = "INSERT INTO aeronave (modelo, capacidade, envergadura, fabricante, status_ativo) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, a.getModelo());
            stmt.setInt(2, a.getCapacidade());
            stmt.setInt(3, a.getEnvergadura());
            stmt.setString(4, a.getFabricante());
            stmt.setString(5, String.valueOf(a.getStatusAtivo()));
            stmt.executeUpdate();
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) a.setIdAeronave(keys.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir aeronave: " + e.getMessage());
        }
    }

    public void update(Aeronave a) {
        String sql = "UPDATE aeronave SET modelo=?, capacidade=?, envergadura=?, fabricante=?, status_ativo=? WHERE id_aeronave=?";
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, a.getModelo());
            stmt.setInt(2, a.getCapacidade());
            stmt.setInt(3, a.getEnvergadura());
            stmt.setString(4, a.getFabricante());
            stmt.setString(5, String.valueOf(a.getStatusAtivo()));
            stmt.setInt(6, a.getIdAeronave());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar aeronave: " + e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM aeronave WHERE id_aeronave = ?";
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao excluir aeronave: " + e.getMessage());
        }
    }

    private Aeronave mapper(ResultSet rs) throws SQLException {
        Aeronave a = new Aeronave();
        a.setIdAeronave(rs.getInt("id_aeronave"));
        a.setModelo(rs.getString("modelo"));
        a.setCapacidade(rs.getInt("capacidade"));
        a.setEnvergadura(rs.getInt("envergadura"));
        a.setFabricante(rs.getString("fabricante"));
        String s = rs.getString("status_ativo");
        a.setStatusAtivo(s != null && !s.isEmpty() ? s.charAt(0) : 'S');
        return a;
    }
}
