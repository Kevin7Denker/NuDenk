package com.bank.data.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.bank.data.jdbc.JDBCBank;
import com.bank.models.ClientStandard;

public class ClientStandardDAO {

    private final JDBCBank jdbcBank = new JDBCBank();
    private final Connection connection = jdbcBank.getConnection();

    public boolean createClientStandard(ClientStandard clientStandard) {

        try {

            var sql = "INSERT INTO ClientStandard_bank (nome, sobrenome , cpf, email, password, saldo) VALUES (?, ?, ?, ?, ?, ?, ?)";

            var stmt = connection.prepareStatement(sql);

            stmt.setString(1, clientStandard.getNome());
            stmt.setString(2, clientStandard.getSobrenome());
            stmt.setString(3, clientStandard.getCpf());
            stmt.setString(4, clientStandard.getEmail());
            stmt.setString(5, clientStandard.getPassword());
            stmt.setDouble(6, clientStandard.getSaldo());

            stmt.execute();

            return true;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }

    }

    public ClientStandard getClientByEmail(String email) {

        try {

            var sql = "SELECT * FROM ClientStandard_bank WHERE email = ?";

            var stmt = connection.prepareStatement(sql);

            stmt.setString(1, email);

            var rs = stmt.executeQuery();

            if (rs.next()) {

                ClientStandard client = new ClientStandard(rs.getString("nome"), rs.getString("sobrenome"), rs.getString("cpf"), rs.getString("email"),
                        rs.getString("password"));

                return client;
            }

            return null;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }

    }

    public ClientStandard getClientByCpf(String cpf) {

        try {

            var sql = "SELECT * FROM ClientStandard_bank WHERE cpf = ?";

            var stmt = connection.prepareStatement(sql);

            stmt.setString(1, cpf);

            var rs = stmt.executeQuery();

            if (rs.next()) {

                ClientStandard client = new ClientStandard(rs.getString("nome"), rs.getString("sobrenome"), rs.getString("cpf"), rs.getString("email"),
                        rs.getString("password"));

                return client;
            }

            return null;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }

    }

    public ArrayList<ClientStandard> getClientes() {

        try {

            var sql = "SELECT * FROM ClientStandard_bank";

            var stmt = connection.prepareStatement(sql);

            var rs = stmt.executeQuery();

            ArrayList<ClientStandard> clientes = new ArrayList<>();

            while (rs.next()) {

                ClientStandard client = new ClientStandard(rs.getString("nome"), rs.getString("sobrenome"), rs.getString("cpf"),
                        rs.getString("email"), rs.getString("password"));

                clientes.add(client);
            }

            return clientes;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }

    }

    public boolean updateClientStandard(String nome, String sobrenome, String email, String cpf) {

        try {

            var sql = "UPDATE ClientStandard_bank SET nome = ?, sobrenome = ?, email = ? WHERE cpf = ?";

            var stmt = connection.prepareStatement(sql);

            System.out.println(cpf);

            stmt.setString(1, nome);
            stmt.setString(2, sobrenome);
            stmt.setString(3, email);
            stmt.setString(4, cpf);

            int rowsUpdated = stmt.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }

    }

    public boolean Creditar(double valor, String cpf) {
        try {
            var sql = "UPDATE ClientStandard_bank SET saldo = saldo + ? WHERE cpf = ?";
            var stmt = connection.prepareStatement(sql);
            stmt.setDouble(1, valor);
            stmt.setString(2, cpf);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean Debitar(double valor, String cpf) {
        try {
            var sql = "UPDATE ClientStandard_bank SET saldo = saldo - ? WHERE cpf = ?";
            var stmt = connection.prepareStatement(sql);
            stmt.setDouble(1, valor);
            stmt.setString(2, cpf);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public Double getBalance(String cpf) {
        try {
            var sql = "SELECT saldo FROM ClientStandard_bank WHERE cpf = ?";
            var stmt = connection.prepareStatement(sql);
            stmt.setString(1, cpf);
            var rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("saldo");
            }
            return 0.0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return 0.0;
        }
    }

}
