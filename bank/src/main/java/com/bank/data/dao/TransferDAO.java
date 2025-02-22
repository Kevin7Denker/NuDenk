package com.bank.data.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bank.data.jdbc.JDBCBank;
import com.bank.hooks.AuthContext;
import com.bank.models.Transfer;

import io.javalin.http.Context;

public class TransferDAO {

    private final JDBCBank jdbcBank = new JDBCBank();
    private final Connection connection = jdbcBank.getConnection();

    public boolean createTranfer(Transfer transfer) {

        try {

            var sql = "INSERT INTO Transfers_bank (cpf_remetente, cpf_destinatario,data_transferencia, valor) VALUES (?, ?, ?, ?)";

            var stmt = connection.prepareStatement(sql);

            stmt.setString(1, transfer.getCpf_remetente());
            stmt.setString(2, transfer.getCpf_destinatario());
            stmt.setDate(3, new java.sql.Date(transfer.getData().getTime()));
            stmt.setDouble(4, transfer.getValor());

            stmt.execute();

            return true;
        } catch (SQLException e) {
            return false;
        }

    }

    public Transfer getTransfers(String cpf) {

        try {

            var sql = "SELECT * FROM Transfer_bank WHERE cpf_remetente = ?";

            var stmt = connection.prepareStatement(sql);

            stmt.setString(1, cpf);

            var rs = stmt.executeQuery();

            while (rs.next()) {

                Transfer transfer = new Transfer(rs.getString("cpf_remetente"), rs.getString("cpf_destinatario"),
                        rs.getDouble("valor"), rs.getDate("data_transferencia"));

                return transfer;
            }

            return null;

        } catch (SQLException e) {
            return null;
        }
    }

    public List<Transfer> getTransfersLast7Days(Context ctx) {
    List<Transfer> transfers = new ArrayList<>();
    try {
        
        AuthContext authContext = ctx.attribute("authContext");
        
        var sql = "SELECT * FROM Transfers_bank WHERE data_transferencia >= CURDATE() - INTERVAL 7 DAY AND cpf_remetente = ?";
        var stmt = connection.prepareStatement(sql);

        stmt.setString(1, authContext.getUserCPF());

        var rs = stmt.executeQuery();
        
        while (rs.next()) {
            Transfer transfer = new Transfer(
                rs.getString("cpf_remetente"),
                rs.getString("cpf_destinatario"),
                rs.getDouble("valor"),
                rs.getDate("data_transferencia")
            );
            transfers.add(transfer);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return transfers;
}

}
