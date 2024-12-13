package com.bank.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.bank.models.ClientStandard;
import com.bank.repository.Auth;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class AuthController {

    private final Auth authRepo = new Auth();
    private ArrayList<ClientStandard> clientes = new ArrayList<>();

    public Handler show = (Context ctx) -> {

        clientes = authRepo.getClientes();

        if (clientes == null || clientes.isEmpty()) {
            System.out.println("Nenhum cliente encontrado.");
        } else {
            System.out.println("Clientes encontrados: " + clientes);
        }

        Map<String, Object> dados = new HashMap<>();

        dados.put("clientes", clientes);

        ctx.render("show.html", dados);

    };
    public Handler signUp = (Context ctx) -> {
        ctx.render("/auth/register.html");
    };

    public Handler register = (Context ctx) -> {

        try {

            String nome = ctx.formParam("nome");
            String cpf = ctx.formParam("cpf");
            String email = ctx.formParam("email");
            String password = ctx.formParam("password");
            String telefone = ctx.formParam("telefone");
            String endereco = ctx.formParam("endereco");
            Double saldo = Double.valueOf(ctx.formParam("saldo"));

            authRepo.register(ctx, nome, cpf, email, password, telefone, endereco, saldo);

            ctx.redirect("/show");

        } catch (Exception e) {
            ctx.status(400);
            ctx.result(e.getMessage());
            ctx.redirect("/register.html");
        }

    };

    public Handler signIn = (Context ctx) -> {
        ctx.render("/auth/login.html");
    };

    public Handler login = (Context ctx) -> {

        try {
            String email = ctx.formParam("email");
            String password = ctx.formParam("password");

            authRepo.login(ctx, email, password);

            ctx.redirect("/private/home.html");

        } catch (Exception e) {
            ctx.status(400);
            ctx.result(e.getMessage());
            ctx.redirect("/login.html");

        }

    };
}
