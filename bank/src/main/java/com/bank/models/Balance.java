package com.bank.models;

public class Balance {
    
    private String cpf;
    private Double saldo;
    private Double limite;
    private Double divida;

    public Balance(String cpf, Double saldo, Double limite, Double divida) {
        this.cpf = cpf;
        this.saldo = saldo;
        this.limite = limite;
        this.divida = divida;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Double getLimite() {
        return limite;
    }

    public void setLimite(Double limite) {
        this.limite = limite;
    }

    public Double getDivida() {
        return divida;
    }

    public void setDivida(Double divida) {
        this.divida = divida;
    }

    
}
