package com.aviacao.dao;

import com.aviacao.model.CiaAerea;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CiaAereaDAO {
    private final ConnectionFactory cf = ConnectionFactory.getInstance();

    public CiaAerea findById(int id) {
        String sql = "SELECT * FROM cia_aerea WHERE id_cia = ?";
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapper(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cia: " + e.getMessage());
        }
        return null;
    }

    public List<CiaAerea> findAll() {
        String sql = "SELECT * FROM cia_aerea ORDER BY id_cia";
        List<CiaAerea> list = new ArrayList<>();
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) list.add(mapper(rs));
        } catch (SQLException e) {
            System.err.println("Erro ao listar cias: " + e.getMessage());
        }
        return list;
    }

    public boolean insert(CiaAerea c) {
        String sql = "INSERT INTO cia_aerea (nome, cnpj, email, status_ativo) VALUES (?, ?, ?, ?)";
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getCnpj());
            stmt.setString(3, c.getEmail());
            stmt.setString(4, String.valueOf(c.getStatusAtivo()));
            stmt.executeUpdate();
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) c.setIdCia(keys.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir cia: " + e.getMessage());
            return false;
        }
    }

    public boolean update(CiaAerea c) {
        String sql = "UPDATE cia_aerea SET nome=?, cnpj=?, email=?, status_ativo=? WHERE id_cia=?";
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getCnpj());
            stmt.setString(3, c.getEmail());
            stmt.setString(4, String.valueOf(c.getStatusAtivo()));
            stmt.setInt(5, c.getIdCia());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar cia: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM cia_aerea WHERE id_cia = ?";
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir cia: " + e.getMessage());
            return false;
        }
    }

    private CiaAerea mapper(ResultSet rs) throws SQLException {
        CiaAerea c = new CiaAerea();
        c.setIdCia(rs.getInt("id_cia"));
        c.setNome(rs.getString("nome"));
        c.setCnpj(rs.getString("cnpj"));
        c.setEmail(rs.getString("email"));
        String s = rs.getString("status_ativo");
        c.setStatusAtivo(s != null && !s.isEmpty() ? s.charAt(0) : 'S');
        return c;
    }
}
