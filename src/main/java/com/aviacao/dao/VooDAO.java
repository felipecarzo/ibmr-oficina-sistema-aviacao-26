package com.aviacao.dao;

import com.aviacao.model.Voo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VooDAO {
    private final ConnectionFactory cf = ConnectionFactory.getInstance();

    public Voo findById(String codVoo) {
        String sql = "SELECT * FROM voo WHERE cod_voo = ?";
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codVoo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapper(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar voo: " + e.getMessage());
        }
        return null;
    }

    public List<Voo> findAll() {
        String sql = "SELECT * FROM voo ORDER BY cod_voo";
        List<Voo> list = new ArrayList<>();
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) list.add(mapper(rs));
        } catch (SQLException e) {
            System.err.println("Erro ao listar voos: " + e.getMessage());
        }
        return list;
    }

    public List<Voo> findByOrigemDestino(String origem, String destino) {
        String sql = "SELECT * FROM voo WHERE cidade_origem LIKE ? AND cidade_destino LIKE ? ORDER BY cod_voo";
        List<Voo> list = new ArrayList<>();
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + origem + "%");
            stmt.setString(2, "%" + destino + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapper(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar voos por rota: " + e.getMessage());
        }
        return list;
    }

    public void insert(Voo v) {
        String sql = "INSERT INTO voo (cod_voo, hora_partida, hora_chegada, cod_aeroporto, cidade_origem, cidade_destino) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, v.getCodVoo());
            stmt.setInt(2, v.getHoraPartida());
            stmt.setInt(3, v.getHoraChegada());
            stmt.setInt(4, v.getCodAeroporto());
            stmt.setString(5, v.getCidadeOrigem());
            stmt.setString(6, v.getCidadeDestino());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao inserir voo: " + e.getMessage());
        }
    }

    public void update(Voo v) {
        String sql = "UPDATE voo SET hora_partida=?, hora_chegada=?, cod_aeroporto=?, cidade_origem=?, cidade_destino=? WHERE cod_voo=?";
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, v.getHoraPartida());
            stmt.setInt(2, v.getHoraChegada());
            stmt.setInt(3, v.getCodAeroporto());
            stmt.setString(4, v.getCidadeOrigem());
            stmt.setString(5, v.getCidadeDestino());
            stmt.setString(6, v.getCodVoo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar voo: " + e.getMessage());
        }
    }

    public void delete(String codVoo) {
        String sql = "DELETE FROM voo WHERE cod_voo = ?";
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codVoo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao excluir voo: " + e.getMessage());
        }
    }

    private Voo mapper(ResultSet rs) throws SQLException {
        Voo v = new Voo();
        v.setCodVoo(rs.getString("cod_voo"));
        v.setHoraPartida(rs.getInt("hora_partida"));
        v.setHoraChegada(rs.getInt("hora_chegada"));
        v.setCodAeroporto(rs.getInt("cod_aeroporto"));
        v.setCidadeOrigem(rs.getString("cidade_origem"));
        v.setCidadeDestino(rs.getString("cidade_destino"));
        return v;
    }
}
