package com.bank.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Token {

    private final static Dotenv dotenv = Dotenv.load();
    private final static String SECRET_KEY = dotenv.get("SECRET");

    private static final long EXPIRATION_TIME = 259200000;

    static {
        if (SECRET_KEY == null || SECRET_KEY.isEmpty()) {
            throw new IllegalStateException("SECRET_KEY environment variable is not set.");
        }
    }

    public static String gerarToken(String email, String nome, String sobrenome, String cpf, double saldo) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("nome", nome);
        claims.put("sobrenome", sobrenome);
        claims.put("cpf", cpf);
        claims.put("saldo", saldo);
        claims.put("isLoggedIn", true);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static Claims validarToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public static String pegarEmailToken(String token) {
        return validarToken(token).getSubject();
    }
}
