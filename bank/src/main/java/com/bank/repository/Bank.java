package com.bank.repository;

import com.bank.data.dao.ClientStandardDAO;
import com.bank.models.ClientStandard;

import io.javalin.http.Context;

public class Bank {

    private static ClientStandardDAO clientStandardDAO;

    public Bank() {
        clientStandardDAO = new ClientStandardDAO();
    }

    public Boolean Creditar(Context ctx, Double valor, String cpf) {
        try {
            ClientStandard client = clientStandardDAO.getClientByCpf(cpf);

            if (client == null) {
                throw new Error("Cliente não encontrado.");
            }

            if (valor <= 0) {
                throw new Error("Valor inválido.");
            }

            client.setSaldo(client.getSaldo() + valor);

            System.out.println("Valor creditado: " + valor);

            return true;

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

            if (client.getSaldo() < valor) {
                throw new Error("Saldo insuficiente.");
            }

            client.setSaldo(client.getSaldo() - valor);

            System.out.println("Valor debitado: " + valor);

            return true;

        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }

    public boolean Transferir(Context ctx, Double valor, String cpfOrigem, String cpfDestino) {
        try {
            ClientStandard clientOrigem = clientStandardDAO.getClientByCpf(cpfOrigem);
            ClientStandard clientDestino = clientStandardDAO.getClientByCpf(cpfDestino);

            if (clientOrigem == null || clientDestino == null) {
                throw new Error("Cliente não encontrado.");
            }

            if (valor <= 0) {
                throw new Error("Valor inválido.");
            }

            if (clientOrigem.getSaldo() < valor) {
                throw new Error("Saldo insuficiente.");
            }

            clientOrigem.setSaldo(clientOrigem.getSaldo() - valor);
            clientDestino.setSaldo(clientDestino.getSaldo() + valor);

            System.out.println("Valor transferido: " + valor);

            return true;

        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }
}
