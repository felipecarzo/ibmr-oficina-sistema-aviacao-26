package com.aviacao.dao;

import com.aviacao.model.AeronaveCia;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AeronaveCiaDAO {
    private final ConnectionFactory cf = ConnectionFactory.getInstance();

    public List<AeronaveCia> findAll() {
        String sql = "SELECT * FROM aeronave_cia ORDER BY id_aeronave, id_cia";
        List<AeronaveCia> list = new ArrayList<>();
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) list.add(mapper(rs));
        } catch (SQLException e) {
            System.err.println("Erro ao listar vinculos: " + e.getMessage());
        }
        return list;
    }

    public AeronaveCia findById(int idAeronave, int idCia) {
        String sql = "SELECT * FROM aeronave_cia WHERE id_aeronave = ? AND id_cia = ?";
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAeronave);
            stmt.setInt(2, idCia);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapper(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar vinculo: " + e.getMessage());
        }
        return null;
    }

    public List<AeronaveCia> findByAeronave(int idAeronave) {
        String sql = "SELECT * FROM aeronave_cia WHERE id_aeronave = ?";
        List<AeronaveCia> list = new ArrayList<>();
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAeronave);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapper(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar vinculos por aeronave: " + e.getMessage());
        }
        return list;
    }

    public List<AeronaveCia> findByCia(int idCia) {
        String sql = "SELECT * FROM aeronave_cia WHERE id_cia = ?";
        List<AeronaveCia> list = new ArrayList<>();
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCia);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapper(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar vinculos por cia: " + e.getMessage());
        }
        return list;
    }

    public void insert(AeronaveCia ac) {
        String sql = "INSERT INTO aeronave_cia (id_aeronave, id_cia, data_aquisicao) VALUES (?, ?, ?)";
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ac.getIdAeronave());
            stmt.setInt(2, ac.getIdCia());
            stmt.setDate(3, Date.valueOf(ac.getDataAquisicao()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao inserir vinculo: " + e.getMessage());
        }
    }

    public void delete(int idAeronave, int idCia) {
        String sql = "DELETE FROM aeronave_cia WHERE id_aeronave = ? AND id_cia = ?";
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAeronave);
            stmt.setInt(2, idCia);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao excluir vinculo: " + e.getMessage());
        }
    }

    private AeronaveCia mapper(ResultSet rs) throws SQLException {
        AeronaveCia ac = new AeronaveCia();
        ac.setIdAeronave(rs.getInt("id_aeronave"));
        ac.setIdCia(rs.getInt("id_cia"));
        Date d = rs.getDate("data_aquisicao");
        if (d != null) ac.setDataAquisicao(d.toLocalDate());
        return ac;
    }
}
