package com.bank.models;

public class ClientStandard extends People {

    private double saldo;

    public ClientStandard(String nome, String sobrenome, String cpf, String email, String password) {
        super(nome, sobrenome, cpf, email, password);
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

}
