package com.bank.hooks;

public class AuthContext {

    private boolean loggedIn;
    private String userEmail;
    private String userName;
    private double userBalance;

    public AuthContext() {
        this.loggedIn = false;
        this.userEmail = null;
        this.userName = null;
        this.userBalance = 0.0;
    }

    public void login(String email, String name, double balance) {
        this.loggedIn = true;
        this.userEmail = email;
        this.userName = name;
        this.userBalance = balance;
    }

    public void logout() {
        this.loggedIn = false;
        this.userEmail = null;
        this.userName = null;
        this.userBalance = 0.0;
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

    public double getUserBalance() {
        return userBalance;
    }
}
