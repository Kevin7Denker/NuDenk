package com.bank.middleware;

import com.bank.hooks.AuthContext;
import com.bank.services.Token;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.jsonwebtoken.Claims;

public class AuthMiddleware {

    public Handler authMiddleware = (Context ctx) -> {
        String token = ctx.cookie("jwtToken");

        if (token != null) {
            try {
                Claims claims = Token.validarToken(token);
                AuthContext authContext = new AuthContext();
                authContext.login(claims.get("email").toString(), claims.get("nome").toString(), Double.parseDouble(claims.get("saldo").toString()));

                ctx.attribute("authContext", authContext);

            } catch (NumberFormatException e) {
                ctx.status(401).json("Token inválido.");
            }
        } else {

            ctx.attribute("isAuthenticated", false);
        }
    };
}
