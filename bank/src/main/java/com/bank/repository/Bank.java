package com.bank.repository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bank.data.dao.ClientStandardDAO;
import com.bank.data.dao.DepositDAO;
import com.bank.data.dao.TransferDAO;
import com.bank.hooks.AuthContext;
import com.bank.models.ClientStandard;
import com.bank.models.Deposit;
import com.bank.models.Transfer;

import io.javalin.http.Context;

public class Bank {

    private static ClientStandardDAO clientStandardDAO;
    private static TransferDAO transferDAO;
    private static DepositDAO depositDAO;

    public Bank() {
        clientStandardDAO = new ClientStandardDAO();
        depositDAO = new DepositDAO();
        transferDAO = new TransferDAO();
    }

    public Boolean Creditar(Context ctx, Double valor, String cpf) {
        try {

            Date data = new Date();

            ClientStandard client = clientStandardDAO.getClientByCpf(cpf);
            Deposit deposit = new Deposit(cpf, data, valor);

            if (client == null) {
                throw new Error("Cliente não encontrado.");
            }

            if (valor <= 0) {
                throw new Error("Valor inválido.");
            }

            if(clientStandardDAO.creditar(valor, cpf)){
                depositDAO.createDeposit(deposit);
                return true;
            }

            return false;

        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }

    public Boolean Debitar(Context ctx, Double valor, String cpf) {
        try {
            ClientStandard client = clientStandardDAO.getClientByCpf(cpf);

            if (client == null) {
                throw new Error("Cliente não encontrado.");
            }

            if (valor <= 0) {
                throw new Error("Valor inválido.");
            }

            if (clientStandardDAO.getBalance(cpf) < valor) {
                throw new Error("Saldo insuficiente.");
            }

            clientStandardDAO.Debitar(valor, cpf);

            return true;

        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }

    public Double getSaldo(Context ctx, String cpf) {
        try {

            Double val = clientStandardDAO.getBalance(cpf);

            return val;

        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }

    public boolean Transferir(Context ctx, Double valor, String cpfOrigem, String cpfDestino) {
        try {

            Date data = new Date();

            ClientStandard clientOrigem = clientStandardDAO.getClientByCpf(cpfOrigem);
            ClientStandard clientDestino = clientStandardDAO.getClientByCpf(cpfDestino);

            Transfer transfer = new Transfer(cpfOrigem, cpfDestino, valor, data);

            if (clientOrigem == null || clientDestino == null) {
                throw new Error("Cliente não encontrado.");
            }

            if (valor <= 0) {
                throw new Error("Valor inválido.");
            }

            Debitar(ctx, valor, clientOrigem.getCpf());
            Creditar(ctx, valor, clientDestino.getCpf());

            if (transferDAO.createTranfer(transfer)) {
                System.out.println("Transferência realizada com sucesso.");
            }

            return true;

        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }

    public Transfer getTransferencias(Context ctx) {
        try {

           AuthContext authContext = ctx.attribute("authContext");

            return transferDAO.getTransfers(authContext.getUserCPF());

        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }

    public Deposit getDepositos(Context ctx) {
        try {

            AuthContext authContext = ctx.attribute("authContext");

            return depositDAO.getDeposits(authContext.getUserCPF());

        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }

    public List<Transfer> getTransfersLast7Days(Context ctx) {
        try {
            List<Transfer> transfers = new ArrayList<Transfer>();
                
            transfers.addAll(transferDAO.getTransfersLast7Days(ctx));

            return transfers;

        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }
}
