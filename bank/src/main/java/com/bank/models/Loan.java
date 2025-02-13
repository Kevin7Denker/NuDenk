package com.bank.models;

import java.util.Date;

public class Loan {
    
    private String cpf;
    private double valor;
    private double juros;
    private Date data_vencimento;
    private Date data_emprestimo;

    public Loan(String cpf, double valor, double juros, Date data_vencimento, Date data_emprestimo) {
        this.cpf = cpf;
        this.valor = valor;
        this.juros = juros;
        this.data_vencimento = data_vencimento;
        this.data_emprestimo = data_emprestimo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getJuros() {
        return juros;
    }

    public void setJuros(double juros) {
        this.juros = juros;
    }

    public Date getData_vencimento() {
        return data_vencimento;
    }

    public void setData_vencimento(Date data_vencimento) {
        this.data_vencimento = data_vencimento;
    }

    public Date getData_emprestimo() {
        return data_emprestimo;
    }

    public void setData_emprestimo(Date data_emprestimo) {
        this.data_emprestimo = data_emprestimo;
    }

    
}

