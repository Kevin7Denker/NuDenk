package com.bank.data.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.bank.data.jdbc.JDBCBank;
import com.bank.hooks.AuthContext;
import com.bank.models.Balance;
import com.bank.models.Deposit;

import io.javalin.http.Context;

public class BalanceDAO {

    private final JDBCBank jdbcBank = new JDBCBank();
    private final Connection connection = jdbcBank.getConnection();

    
    public String   createBalance(Balance balance) {
        
        String sql = "INSERT INTO Balance_bank (cpf, saldo, limite, divida) VALUES (?,?,?,?)";
        
        try (var stmt = connection.prepareStatement(sql)) {
    
            stmt.setString(1, balance.getCpf());
            stmt.setDouble(2, balance.getSaldo());
            stmt.setDouble(3, balance.getLimite());
            stmt.setDouble(4, balance.getDivida());
            
            System.out.println(stmt.toString());

            boolean res = stmt.execute();

            if(res) {
                return "Balance created";
            } else {
                throw new Error("Balance not created");
            }

        } catch (Exception e) {
            return e.getMessage(); 
        }
    }

    public Balance getBalance(Context ctx){

        try {   
            AuthContext authContext = ctx.attribute("authContext");
        

            var sql = "SELECT * FROM Balance_bank WHERE cpf = ?";

            var stmt = connection.prepareStatement(sql);

            stmt.setString(1, authContext.getUserCPF());

            var rs = stmt.executeQuery();

            if (rs.next()) {
                Balance balance = new Balance(rs.getString("cpf"), rs.getDouble("saldo"), rs.getDouble("limite"), rs.getDouble("divida"));
                
                System.out.println(balance);

                return balance;
            } else {
                return null;
            }

        } catch (Exception e) {
            return null;
        }
    }
    public String creditar(Deposit deposit) {
        try (var stmt = connection.prepareStatement("UPDATE Balance_bank SET saldo = saldo + ? WHERE cpf = ?")) {
            
            stmt.setDouble(1, deposit.getValor());
            stmt.setString(2, deposit.getCpf());
            
            int res = stmt.executeUpdate();
            
            if (res > 0) {
                return "Valor creditado com sucesso.";
            } else {
                return "Erro ao creditar valor.";
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return "Erro ao creditar valor.";
        }
    }

    public boolean Debitar(double valor, String cpf) {
        try {
            var sql = "UPDATE Balance_bank SET saldo = saldo - ? WHERE cpf = ?";
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

        public String updateDivida (Context ctx, Double valor) {
            
            String sql = "UPDATE Balance_bank SET divida = divida + ? WHERE cpf = ?";

            AuthContext authContext = ctx.attribute("authContext");

            try (var stmt = connection.prepareStatement(sql)) {
        
                stmt.setDouble(1, valor);
                stmt.setString(2, authContext.getUserCPF());
                
                System.out.println(stmt.toString());

                int res = stmt.executeUpdate();

                if (res > 0) {
                    return "Divida updated successfully.";
                } else {
                    return "Failed to update divida.";
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "Internal Server Error: Unable to process the loan. " + e.getMessage();
            }

        }
        
}
