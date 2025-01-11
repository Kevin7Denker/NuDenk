package com.bank.models;

public class ClientStandard extends People {

    private double saldo;

    public ClientStandard(String nome, String sobrenome, String cpf, String email, String password, String endereco) {
        super(nome, sobrenome, cpf, email, password, endereco);
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "ClientStandard [saldo=" + saldo + ", getSaldo()=" + getSaldo() + ", getNome()=" + getNome()
                + ", getCpf()=" + getCpf() + ", getEmail()=" + getEmail() + ", getPassword()=" + getPassword()
                + ", getEndereco()=" + getEndereco() + "]";
    }

}
