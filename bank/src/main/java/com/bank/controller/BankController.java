package com.bank.controller;

import com.bank.hooks.AuthContext;
import com.bank.repository.Bank;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class BankController {

    private final Bank bankService = new Bank();
    private AuthContext authContext;

    public Handler credit = (Context ctx)
            -> {
        authContext = ctx.sessionAttribute("auth");
        if (authContext == null) {
            ctx.status(401);
            return;
        }

        double amount = Double.parseDouble(ctx.formParam("amount"));
        bankService.Creditar(ctx, amount, authContext.getUserCPF());
        ctx.status(200);

    };

    public Handler debit = (Context ctx)
            -> {
        authContext = ctx.sessionAttribute("auth");
        if (authContext == null) {
            ctx.status(401);
            return;
        }

        double amount = Double.parseDouble(ctx.formParam("amount"));
        bankService.Debitar(ctx, amount, authContext.getUserCPF());
        ctx.status(200);

    };

    public Handler transfer = (Context ctx)
            -> {
        authContext = ctx.sessionAttribute("auth");
        if (authContext == null) {
            ctx.status(401);
            return;
        }

        double amount = Double.parseDouble(ctx.formParam("amount"));
        String cpf = ctx.formParam("cpf");
        bankService.Transferir(ctx, amount, authContext.getUserCPF(), cpf);
        ctx.status(200);

    };
}
