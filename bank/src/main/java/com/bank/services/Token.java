package com.bank.services;

import java.util.Date;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Token {

    private final static Dotenv dotenv = Dotenv.load();
    private final static String SECRET_KEY = dotenv.get("SECRET"); // Correctly retrieve from .env

    private static final long EXPIRATION_TIME = 259200000; // 3 dias em milissegundos

    static {
        if (SECRET_KEY == null || SECRET_KEY.isEmpty()) {
            throw new IllegalStateException("SECRET_KEY environment variable is not set.");
        } else {
            System.out.println("SECRET_KEY loaded: " + SECRET_KEY);
        }
    }

    public static String gerarToken(String email) {
        return Jwts.builder()
                .setSubject(email)
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
