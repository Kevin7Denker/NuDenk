package com.bank.models;

import java.util.Date;

public class Transfer {

    private String cpf_remetente;
    private String cpf_destinatario;
    private double valor;
    private Date data;

    public Transfer(String cpf_remetente, String cpf_destinatario, double valor, Date data) {
        this.cpf_remetente = cpf_remetente;
        this.cpf_destinatario = cpf_destinatario;
        this.valor = valor;
        this.data = data;
    }

    public String getCpf_remetente() {
        return cpf_remetente;
    }

    public void setCpf_remetente(String cpf_remetente) {
        this.cpf_remetente = cpf_remetente;
    }

    public String getCpf_destinatario() {
        return cpf_destinatario;
    }

    public void setCpf_destinatario(String cpf_destinatario) {
        this.cpf_destinatario = cpf_destinatario;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

}
