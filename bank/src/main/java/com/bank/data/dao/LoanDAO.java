package com.bank.data.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.bank.data.jdbc.JDBCBank;
import com.bank.hooks.AuthContext;
import com.bank.models.Loan;

import io.javalin.http.Context;

public class LoanDAO {
    

    private final JDBCBank jdbcBank = new JDBCBank();
    private Connection connection;

   public Loan createLoan(Loan loan) {
    String sql = "INSERT INTO Loan_bank (cpf, valor, juros, data_vencimento, data_emprestimo) VALUES (?,?,?,?,?)";

    try {
        connection = jdbcBank.getConnection();
        var stmt = connection.prepareStatement(sql);

        stmt.setString(1, loan.getCpf());
        stmt.setDouble(2, loan.getValor());
        stmt.setDouble(3, loan.getJuros());
        stmt.setDate(4, new java.sql.Date(loan.getData_vencimento().getTime()));
        stmt.setDate(5, new java.sql.Date(loan.getData_emprestimo().getTime()));

        int rowsAffected = stmt.executeUpdate();

        if (rowsAffected > 0) {
            return loan;
        } else {
            throw new SQLException("Loan not created: No rows affected.");
        }

    } catch (SQLException e) {
        System.err.println("Error creating loan: " + e.getMessage());
        e.printStackTrace(); 
        return null;
    }
}


    public String updateLoan(Loan loan) {
        String sql = "UPDATE Loan_bank SET data_emprestimo = ?, valor = ?, juros = ?, data_vencimento = ? WHERE cpf = ?";

        try {
            connection = jdbcBank.getConnection();
            var stmt = connection.prepareStatement(sql);

            stmt.setDate(1, new java.sql.Date(loan.getData_emprestimo().getTime()));
            stmt.setDouble(2, loan.getValor());
            stmt.setDouble(3, loan.getJuros());
            stmt.setDate(4, new java.sql.Date(loan.getData_vencimento().getTime()));
            stmt.setString(5, loan.getCpf());

            int res = stmt.executeUpdate();

            if (res > 0) {
                return "Loan updated";
            } else {
                throw new Error("Loan not updated");
            }

        } catch (Exception e) {
            return e.getMessage();
        } finally {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public Loan getLoan(Context ctx){

        try {

          
            var sql = "SELECT * FROM Loan_bank WHERE cpf = ?";

            var stmt = connection.prepareStatement(sql);

            AuthContext authContext = ctx.attribute("authContext");
        
            stmt.setString(1, authContext.getUserCPF());

            var rs = stmt.executeQuery();

            if (rs.next()) {
                Loan loan = new Loan(rs.getString("cpf"), rs.getDouble("valor"), rs.getDouble("juros"), rs.getDate("data_vencimento"), rs.getDate("data_emprestimo"));
                
                System.out.println(loan);

                return loan;
            } else {
                return null;
            }

        } catch (Exception e) {
            return null;
        }
    }

}
