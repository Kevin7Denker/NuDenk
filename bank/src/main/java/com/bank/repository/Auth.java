package com.bank.repository;

import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

import com.bank.data.dao.ClientStandardDAO;
import com.bank.hooks.AuthContext;
import com.bank.models.Balance;
import com.bank.models.ClientStandard;

import io.javalin.http.Context;

public class Auth {

    private static ClientStandardDAO clientStandardDAO;

    private static Bank bankService;

    private static AuthContext context;

    public Auth() {
        clientStandardDAO = new ClientStandardDAO();
        bankService = new Bank();
        context = new AuthContext();
    }

    public ClientStandard login(Context ctx, String email, String password) throws Error {
        try {
            ClientStandard client = clientStandardDAO.getClientByEmail(email);

            if (client == null) {
                throw new Error("Cliente não encontrado.");
            }

            if (!checkPassword(password, client.getPassword())) {
                throw new Error("Senha incorreta.");
            }

            context.login(client.getEmail(), client.getCpf(), client.getNome(), client.getSobrenome());

            System.out.println("Cliente logado: " + client.getNome());

            return client;

        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }

    public void register(Context ctx, String nome, String sobrenome, String cpf, String email, String password) throws Error {

        try {

            if (getClientByEmail(email) != null) {
                throw new Error("Cliente ja cadastrado.");
            }

            String hash = hashPassword(password);

            ClientStandard client = new ClientStandard(nome, sobrenome, cpf, email, hash);

            clientStandardDAO.createClientStandard(client);

            boolean result = bankService.createBalance(ctx, 0.0, 1000, 0.0);
            System.out.println(result);

        } catch (Exception e) {
            throw new Error(e.getMessage());
        }

    }

    public ArrayList<ClientStandard> getClientes() {
        return clientStandardDAO.getClientes();
    }

    public ClientStandard getClientByEmail(String email) {
        return clientStandardDAO.getClientByEmail(email);
    }

    public ClientStandard getClientByCPF(String cpf) {
        return clientStandardDAO.getClientByCpf(cpf);
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    public boolean updateUser(Context ctx, String nome, String sobrenome, String email, String cpf) {
        return clientStandardDAO.updateClientStandard(nome, sobrenome, email, cpf);
    }

}
