package com.bank.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.bank.hooks.AuthContext;
import com.bank.models.Balance;
import com.bank.models.ClientStandard;
import com.bank.repository.Auth;
import com.bank.repository.Bank;
import com.bank.services.Token;

import io.javalin.http.Context;
import io.javalin.http.Cookie;
import io.javalin.http.Handler;
import io.jsonwebtoken.Claims;

public class AuthController {

    private final Auth authRepo = new Auth();



    public Handler signUp = (Context ctx) -> {
        ctx.render("/pages/auth/register.html");
    };

    public Handler register = (Context ctx) -> {
        try {
            String nome = ctx.formParam("nome");
            String sobrenome = ctx.formParam("sobrenome");
            String cpf = ctx.formParam("cpf");
            String email = ctx.formParam("email");
            String password = ctx.formParam("password");

            authRepo.register(ctx, nome, sobrenome, cpf, email, password);
            ctx.redirect("/");

        } catch (NumberFormatException e) {
            ctx.status(400);
            ctx.result(e.getMessage());
            ctx.redirect("/pages/register.html");
        }
    };

    public Handler signIn = (Context ctx) -> {
        ctx.render("/pages/auth/login.html");
    };

    public Handler login = (Context ctx) -> {
        try {
            String email = ctx.formParam("email");
            String password = ctx.formParam("password");

            ClientStandard client = authRepo.login(ctx, email, password);

            if (client != null) {

                String token = Token.gerarToken(client.getEmail(), client.getNome(), client.getSobrenome(), client.getCpf());
                Cookie cookie = new Cookie("jwtToken", token);
                cookie.setMaxAge(3600);
                cookie.setHttpOnly(true);
                cookie.setSecure(true);
                ctx.cookie(cookie);

                ctx.redirect("/");
            }
        } catch (Error e) {
            ctx.status(400);
            ctx.result(e.getMessage());
            System.out.println("erro: " + e.getMessage());
            ctx.redirect("/login");
        } catch (Exception e) {
            ctx.status(500);
            ctx.result("Erro inesperado: " + e.getMessage());
            ctx.redirect("/");
        }
    };

    public Handler logout = (Context ctx) -> {
        ctx.removeCookie("jwtToken");
        ctx.redirect("/");
    };

    public Handler checkAuth = (Context ctx) -> {

        try {

            String token = ctx.cookie("jwtToken");

            if (token != null) {
                try {
                    Claims claims = Token.validarToken(token);
                    System.out.println("Usuário autenticado: " + claims.getSubject());
                    ctx.json("Usuário está logado: " + claims.getSubject());
                } catch (Exception e) {
                    ctx.status(401).json("Token inválido.");
                }
            }

        } catch (Exception e) {
            ctx.status(500);
            ctx.result("Erro inesperado: " + e.getMessage());
            ctx.redirect("/");
        }

    };

    public Handler home = (Context ctx) -> {

        try {

            AuthContext authContext = ctx.attribute("authContext");
            Bank bankService = new Bank();

            if (authContext.isLoggedIn()) {

                Map<String, Object> dados = new HashMap<>();

                String userName = authContext.getUserName();
                String userSurname = authContext.getUserSurname();
                String userEmail = authContext.getUserEmail();
                String userCPF = authContext.getUserCPF();

                System.out.println(userCPF);

                Balance userBalance = bankService.getSaldo(ctx);

                System.out.println(userBalance);

                String  userSaldo = String.valueOf(userBalance.getSaldo());
                dados.put("userName", userName);
                dados.put("userSurname", userSurname);
                dados.put("userEmail", userEmail);
                dados.put("userCPF", userCPF);
                dados.put("userBalance", userSaldo);

                ctx.render("/pages/private/home.html", dados);
            }

        } catch (Exception e) {
            ctx.status(500);
            ctx.result("Erro inesperado: " + e.getMessage());
            ctx.redirect("/");
        }

    };

    public Handler config = (Context ctx) -> {
        AuthContext authContext = ctx.attribute("authContext");

        if (authContext.isLoggedIn()) {
            Map<String, Object> dados = new HashMap<>();

            ClientStandard client = authRepo.getClientByCPF(authContext.getUserCPF());

            String userName = client.getNome();
            String userSurname = client.getSobrenome();
            String userEmail = client.getEmail();

            dados.put("userName", userName);
            dados.put("userSurname", userSurname);
            dados.put("userEmail", userEmail);

            ctx.render("/pages/private/config.html", dados);
        } else {
            ctx.redirect("/login");
        }
    };

    public Handler updateUser = (Context ctx) -> {
        AuthContext authContext = ctx.attribute("authContext");

        if (authContext.isLoggedIn()) {
            try {
                String nome = ctx.formParam("firstName");
                String sobrenome = ctx.formParam("lastName");
                String email = ctx.formParam("email");

                String cpf = authContext.getUserCPF();

                boolean res = authRepo.updateUser(ctx, nome, sobrenome, email, cpf);

                if (res) {

                    logout.handle(ctx);

                } else {
                    ctx.status(400);
                    ctx.result("Erro ao atualizar usuario.");
                }

            } catch (Exception e) {
                ctx.status(500);
                ctx.result("Erro inesperado: " + e.getMessage());
            }
        } else {
            ctx.redirect("/login");
        }
    };

}
