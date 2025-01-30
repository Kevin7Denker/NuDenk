package com.bank.controller;

import java.util.List;

import com.bank.hooks.AuthContext;
import com.bank.models.Transfer;
import com.bank.repository.Bank;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class BankController {

    private final Bank bankService = new Bank();
    

    public Handler credit = (Context ctx) -> {
        AuthContext authContext = ctx.attribute("authContext");
        if (authContext == null) {
            ctx.status(401).result("Unauthorized: Please log in.");
            return;
        }

        String amountStr = ctx.formParam("amount");
        try {
            double amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                ctx.status(400).result("Bad Request: Amount must be greater than zero.");
                return;
            }

            String cpf = authContext.getUserCPF();

            boolean success = bankService.Creditar(ctx, amount, cpf);
            if (success) {
                ctx.status(200).redirect("/");
            } else {
                ctx.status(500).result("Internal Server Error: Unable to process the deposit.");
            }
        } catch (NumberFormatException e) {
            ctx.status(400).result("Bad Request: Invalid amount format.");
        } catch (Exception e) {
            System.err.println("Error during credit operation: " + e.getMessage());
            ctx.status(500).result("Internal Server Error: " + e.getMessage());
        }
    };

    public Handler transfer = (Context ctx) -> {
        AuthContext authContext = ctx.attribute("authContext");
        if (authContext == null) {
            ctx.status(401).result("Unauthorized: Please log in.");
            return;
        }

        String amountStr = ctx.formParam("amount");
        String cpf = ctx.formParam("cpf");
        try {
            double amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                ctx.status(400).result("Bad Request: Amount must be greater than zero.");
                return;
            }

            boolean success = bankService.Transferir(ctx, amount, authContext.getUserCPF(), cpf);
            if (success) {
                ctx.redirect("/");
            } else {
                ctx.status(500).result("Internal Server Error: Unable to process the transfer.");
            }
        } catch (NumberFormatException e) {
            ctx.status(400).result("Bad Request: Invalid amount format.");
        } catch (Exception e) {
            System.err.println("Error during transfer operation: " + e.getMessage());
            ctx.status(500).result("Internal Server Error: " + e.getMessage());
        }
    };

    public Handler getTransfers = (Context ctx) -> {
        AuthContext authContext = ctx.attribute("authContext");
        if (authContext == null) {
            ctx.status(401).result("Unauthorized: Please log in.");
            return;
        }

        try {
            ctx.json(bankService.getTransferencias(ctx));
        } catch (Exception e) {
            System.err.println("Error during getTransfers operation: " + e.getMessage());
            ctx.status(500).result("Internal Server Error: " + e.getMessage());
        }
    };

    public Handler getDeposits = (Context ctx) -> {
        AuthContext authContext = ctx.attribute("authContext");
        if (authContext == null) {
            ctx.status(401).result("Unauthorized: Please log in.");
            return;
        }

        try {
            ctx.json(bankService.getDepositos(ctx));
        } catch (Exception e) {
            System.err.println("Error during getDeposits operation: " + e.getMessage());
            ctx.status(500).result("Internal Server Error: " + e.getMessage());
        }
    };

    public Handler getRecentTransfers = (Context ctx) -> {
    AuthContext authContext = ctx.attribute("authContext");
    if (authContext == null) {
        ctx.status(401).result("Unauthorized");
        return;
    }

    try {
        List<Transfer> transfers = bankService.getTransfersLast7Days(ctx);
        ctx.json(transfers);
        return;
    } catch (Exception e) {
        ctx.status(500).result("Internal Server Error: " + e.getMessage());
    }
};

}
