package com.bank.data.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.bank.data.jdbc.JDBCBank;
import com.bank.models.Endereco;

public class EnderecoDAO {

    private final JDBCBank jdbcBank = new JDBCBank();
    private final Connection connection = jdbcBank.getConnection();

    public boolean createEndereco(Endereco endereco) {

        try {

            var sql = "INSERT INTO Endereco_bank (rua, numero, cidade, estado, cep) VALUES (?, ?, ?, ?, ?)";

            var stmt = connection.prepareStatement(sql);

            stmt.setString(1, endereco.getRua());
            stmt.setString(2, endereco.getNumero());
            stmt.setString(3, endereco.getCidade());
            stmt.setString(4, endereco.getEstado());
            stmt.setString(5, endereco.getCep());

            stmt.execute();

            return true;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public Endereco getEndereco(String cpf) {

        try {

            var sql = "SELECT * FROM Endereco_bank WHERE cpf = ?";

            var stmt = connection.prepareStatement(sql);

            stmt.setString(1, cpf);

            var rs = stmt.executeQuery();

            if (rs.next()) {

                Endereco endereco = new Endereco(rs.getString("cpf"), rs.getString("rua"), rs.getString("numero"),
                        rs.getString("cidade"), rs.getString("estado"), rs.getString("cep"));

                return endereco;
            }

            return null;

        } catch (SQLException e) {
            return null;
        }
    }
}
