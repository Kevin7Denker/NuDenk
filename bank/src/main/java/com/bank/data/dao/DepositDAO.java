package com.bank.data.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.bank.data.jdbc.JDBCBank;
import com.bank.models.Deposit;

public class DepositDAO {

    private final JDBCBank jdbcBank = new JDBCBank();
    private final Connection connection = jdbcBank.getConnection();

    public boolean createDeposit(Deposit deposit) {

        try {

            var sql = "INSERT INTO Deposit_bank (cpf, data_deposito, valor) VALUES (?, ?, ?)";

            var stmt = connection.prepareStatement(sql);

            stmt.setString(1, deposit.getCpf());
            stmt.setDate(2, new java.sql.Date(deposit.getData().getTime()));
            stmt.setDouble(3, deposit.getValor());

            stmt.execute();

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public Deposit getDeposits(String cpf) {

        try {

            var sql = "SELECT * FROM Deposit_bank WHERE cpf = ?";

            var stmt = connection.prepareStatement(sql);

            stmt.setString(1, cpf);

            var rs = stmt.executeQuery();

            while (rs.next()) {

                Deposit deposit = new Deposit(rs.getString("cpf"), rs.getDate("data_deposito"), rs.getDouble("valor"));

                return deposit;
            }

            return null;

        } catch (SQLException e) {
            return null;
        }
    }
}
