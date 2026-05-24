package com.aviacao.dao;

import com.aviacao.model.Reserva;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {
    private final ConnectionFactory cf = ConnectionFactory.getInstance();

    public List<Reserva> findAll() {
        String sql = "SELECT * FROM reserva ORDER BY cod_reserva";
        List<Reserva> list = new ArrayList<>();
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) list.add(mapper(rs));
        } catch (SQLException e) {
            System.err.println("Erro ao listar reservas: " + e.getMessage());
        }
        return list;
    }

    public Reserva findById(String codReserva) {
        String sql = "SELECT * FROM reserva WHERE cod_reserva = ?";
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codReserva);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapper(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar reserva: " + e.getMessage());
        }
        return null;
    }

    public List<Reserva> findByPassageiro(int idPassageiro) {
        String sql = "SELECT * FROM reserva WHERE id_passageiro = ? ORDER BY dt_reserva";
        List<Reserva> list = new ArrayList<>();
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPassageiro);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapper(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar reservas do passageiro: " + e.getMessage());
        }
        return list;
    }

    public List<Reserva> findByVoo(String codVoo) {
        String sql = "SELECT * FROM reserva WHERE cod_voo = ? ORDER BY assento";
        List<Reserva> list = new ArrayList<>();
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codVoo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapper(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar reservas do voo: " + e.getMessage());
        }
        return list;
    }

    public boolean assentoOcupado(String codVoo, String assento) {
        String sql = "SELECT COUNT(*) FROM reserva WHERE cod_voo = ? AND assento = ?";
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codVoo);
            stmt.setString(2, assento);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar assento: " + e.getMessage());
        }
        return false;
    }

    public boolean insert(Reserva r) {
        String sql = "INSERT INTO reserva (cod_reserva, id_passageiro, cod_voo, assento, dt_reserva) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, r.getCodReserva());
            stmt.setInt(2, r.getIdPassageiro());
            stmt.setString(3, r.getCodVoo());
            stmt.setString(4, r.getAssento());
            stmt.setDate(5, Date.valueOf(r.getDtReserva() != null ? r.getDtReserva() : LocalDate.now()));
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir reserva: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(String codReserva) {
        String sql = "DELETE FROM reserva WHERE cod_reserva = ?";
        try (Connection conn = cf.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codReserva);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir reserva: " + e.getMessage());
            return false;
        }
    }

    private Reserva mapper(ResultSet rs) throws SQLException {
        Reserva r = new Reserva();
        r.setCodReserva(rs.getString("cod_reserva"));
        r.setIdPassageiro(rs.getInt("id_passageiro"));
        r.setCodVoo(rs.getString("cod_voo"));
        r.setAssento(rs.getString("assento"));
        Date d = rs.getDate("dt_reserva");
        if (d != null) r.setDtReserva(d.toLocalDate());
        return r;
    }
}
