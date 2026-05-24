package com.aviacao.dao;

import com.aviacao.model.Passageiro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PassageiroDAO {
    private final ConnectionFactory cf = ConnectionFactory.getInstance();

    public Passageiro findById(int id) {
        String sql = "SELECT * FROM passageiro WHERE id_passageiro = ?";
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapper(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar passageiro: " + e.getMessage());
        }
        return null;
    }

    public List<Passageiro> findAll() {
        String sql = "SELECT * FROM passageiro ORDER BY id_passageiro";
        List<Passageiro> list = new ArrayList<>();
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) list.add(mapper(rs));
        } catch (SQLException e) {
            System.err.println("Erro ao listar passageiros: " + e.getMessage());
        }
        return list;
    }

    public boolean insert(Passageiro p) {
        String sql = "INSERT INTO passageiro (nome, email, tel, dt_nasc) VALUES (?, ?, ?, ?)";
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getEmail());
            stmt.setString(3, p.getTel());
            stmt.setDate(4, Date.valueOf(p.getDtNasc()));
            stmt.executeUpdate();
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) p.setIdPassageiro(keys.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir passageiro: " + e.getMessage());
            return false;
        }
    }

    public boolean update(Passageiro p) {
        String sql = "UPDATE passageiro SET nome=?, email=?, tel=?, dt_nasc=? WHERE id_passageiro=?";
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getEmail());
            stmt.setString(3, p.getTel());
            stmt.setDate(4, Date.valueOf(p.getDtNasc()));
            stmt.setInt(5, p.getIdPassageiro());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar passageiro: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM passageiro WHERE id_passageiro = ?";
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir passageiro: " + e.getMessage());
            return false;
        }
    }

    private Passageiro mapper(ResultSet rs) throws SQLException {
        Passageiro p = new Passageiro();
        p.setIdPassageiro(rs.getInt("id_passageiro"));
        p.setNome(rs.getString("nome"));
        p.setEmail(rs.getString("email"));
        p.setTel(rs.getString("tel"));
        Date d = rs.getDate("dt_nasc");
        if (d != null) p.setDtNasc(d.toLocalDate());
        return p;
    }
}
