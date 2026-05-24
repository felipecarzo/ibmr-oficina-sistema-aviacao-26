package com.aviacao.dao;

import com.aviacao.model.Aeroporto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AeroportoDAO {
    private final ConnectionFactory cf = ConnectionFactory.getInstance();

    public Aeroporto findById(int id) {
        String sql = "SELECT * FROM aeroporto WHERE cod_aeroporto = ?";
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapper(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar aeroporto: " + e.getMessage());
        }
        return null;
    }

    public List<Aeroporto> findAll() {
        String sql = "SELECT * FROM aeroporto ORDER BY cod_aeroporto";
        List<Aeroporto> list = new ArrayList<>();
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) list.add(mapper(rs));
        } catch (SQLException e) {
            System.err.println("Erro ao listar aeroportos: " + e.getMessage());
        }
        return list;
    }

    public boolean insert(Aeroporto a) {
        String sql = "INSERT INTO aeroporto (nome, sigla, cidade) VALUES (?, ?, ?)";
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, a.getNome());
            stmt.setString(2, a.getSigla());
            stmt.setString(3, a.getCidade());
            stmt.executeUpdate();
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) a.setCodAeroporto(keys.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir aeroporto: " + e.getMessage());
            return false;
        }
    }

    public boolean update(Aeroporto a) {
        String sql = "UPDATE aeroporto SET nome=?, sigla=?, cidade=? WHERE cod_aeroporto=?";
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, a.getNome());
            stmt.setString(2, a.getSigla());
            stmt.setString(3, a.getCidade());
            stmt.setInt(4, a.getCodAeroporto());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar aeroporto: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM aeroporto WHERE cod_aeroporto = ?";
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir aeroporto: " + e.getMessage());
            return false;
        }
    }

    private Aeroporto mapper(ResultSet rs) throws SQLException {
        Aeroporto a = new Aeroporto();
        a.setCodAeroporto(rs.getInt("cod_aeroporto"));
        a.setNome(rs.getString("nome"));
        a.setSigla(rs.getString("sigla"));
        a.setCidade(rs.getString("cidade"));
        return a;
    }
}
