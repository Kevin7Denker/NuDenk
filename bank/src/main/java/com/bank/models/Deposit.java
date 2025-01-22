package com.bank.models;

import java.util.Date;

public class Deposit {

    private String cpf;
    private Date data;
    private double valor;

    public Deposit(String cpf, Date data, double valor) {
        this.cpf = cpf;
        this.data = data;
        this.valor = valor;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
