package com.bank.hooks;

public class AuthContext {

    private boolean loggedIn;
    private String userEmail;
    private String userName;
    private String userSurname;
    private String userCPF;
    private double userBalance;

    public AuthContext() {
        this.loggedIn = false;
        this.userEmail = null;
        this.userCPF = null;
        this.userSurname = null;
        this.userName = null;
    }

    public void login(String email, String cpf, String nome, String sobrenome) {
        this.loggedIn = true;
        this.userEmail = email;
        this.userCPF = cpf;
        this.userName = nome;
        this.userSurname = sobrenome;
    }

    public void logout() {
        this.loggedIn = false;
        this.userEmail = null;
        this.userName = null;
        this.userCPF = null;
        this.userSurname = null;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserSurname() {
        return userSurname;
    }


    public String getUserCPF() {
        return userCPF;
    }
}
