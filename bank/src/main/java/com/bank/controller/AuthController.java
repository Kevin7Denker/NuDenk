package com.bank.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.bank.hooks.AuthContext;
import com.bank.models.ClientStandard;
import com.bank.repository.Auth;
import com.bank.services.Token;

import io.javalin.http.Context;
import io.javalin.http.Cookie;
import io.javalin.http.Handler;
import io.jsonwebtoken.Claims;

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

        ctx.render("/pages/show.html", dados);
    };

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
            String endereco = ctx.formParam("endereco");

            authRepo.register(ctx, nome, sobrenome, cpf, email, password, endereco);

            ctx.redirect("/show");

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

                String token = Token.gerarToken(client.getEmail(), client.getNome(), client.getSobrenome(), client.getCpf(), client.getSaldo());
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

            if (authContext.isLoggedIn()) {

                Map<String, Object> dados = new HashMap<>();

                String userName = authContext.getUserName();
                String userSurname = authContext.getUserSurname();
                String userEmail = authContext.getUserEmail();
                String userCPF = authContext.getUserCPF();
                double userBalance = authContext.getUserBalance();

                dados.put("userName", userName);
                dados.put("userSurname", userSurname);
                dados.put("userEmail", userEmail);
                dados.put("userCPF", userCPF);
                dados.put("userBalance", userBalance);

                ctx.render("/pages/private/home.html", dados);
            }

        } catch (Exception e) {
            ctx.status(500);
            ctx.result("Erro inesperado: " + e.getMessage());
            ctx.redirect("/");
        }

    };

}
