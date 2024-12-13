package com.bank.models;

public class ClientStandard extends People {

    private double saldo;

    public ClientStandard(String nome, String cpf, String email, String password, String telefone, String endereco, double saldo) {
        super(nome, cpf, email, password, telefone, endereco);
        this.saldo = saldo;
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
                + ", getTelefone()=" + getTelefone() + ", getEndereco()=" + getEndereco() + "]";
    }

}
