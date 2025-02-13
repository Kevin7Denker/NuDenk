package com.bank.controller;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bank.hooks.AuthContext;
import com.bank.models.Loan;
import com.bank.models.Transfer;
import com.bank.repository.Bank;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class BankController {

    private final Bank bankService = new Bank();
    

    public Handler credit = (Context ctx) -> {
        
        Map<String, Object> data = new HashMap<String, Object>();
        
        AuthContext authContext = ctx.attribute("authContext");

        if (authContext == null) {
            data.put("error", "Unauthorized: Please log in.");
            ctx.redirect("/");
        }

        
        String amountStr = ctx.formParam("amount");
        double amount = Double.parseDouble(amountStr);

            if (amount <= 0) {
                data.put("error", "Bad Request: Amount must be greater than zero.");
                ctx.json(data);
            }

        String cpf = authContext.getUserCPF();
        String success = bankService.Creditar(ctx, amount, cpf);
            
        data.put("success", "Depósito realizado com sucesso.");
        ctx.json(data);
    };

    public Handler transfer = (Context ctx) -> {
        AuthContext authContext = ctx.attribute("authContext");
        if (authContext == null) {
            ctx.status(401).result("Unauthorized: Please log in.");
            return;
        }

        String amountStr = ctx.formParam("amount");
        String cpf = ctx.formParam("cpf");
        try {
            double amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                ctx.status(400).result("Bad Request: Amount must be greater than zero.");
                return;
            }

            boolean success = bankService.Transferir(ctx, amount, authContext.getUserCPF(), cpf);
            if (success) {
                ctx.redirect("/");
            } else {
                ctx.status(500).result("Internal Server Error: Unable to process the transfer.");
            }
        } catch (NumberFormatException e) {
            ctx.status(400).result("Bad Request: Invalid amount format.");
        } catch (Exception e) {
            System.err.println("Error during transfer operation: " + e.getMessage());
            ctx.status(500).result("Internal Server Error: " + e.getMessage());
        }
    };

    public Handler getTransfers = (Context ctx) -> {
        AuthContext authContext = ctx.attribute("authContext");
        if (authContext == null) {
            ctx.status(401).result("Unauthorized: Please log in.");
            return;
        }

        try {
            ctx.json(bankService.getTransferencias(ctx));
        } catch (Exception e) {
            System.err.println("Error during getTransfers operation: " + e.getMessage());
            ctx.status(500).result("Internal Server Error: " + e.getMessage());
        }
    };

    public Handler getBalance = (Context ctx) -> {
        AuthContext authContext = ctx.attribute("authContext");
        if (authContext == null) {
            ctx.status(401).result("Unauthorized: Please log in.");
            return;
        }

        try {
            System.out.println("getBalance" + ctx.json(bankService.getBalance(ctx)));
            ctx.json(bankService.getBalance(ctx));
        } catch (Exception e) {
            System.err.println("Error during getBalance operation: " + e.getMessage());
            ctx.status(500).result("Internal Server Error: " + e.getMessage());
        }
    };

    public Handler createLoan = (Context ctx) -> {
        AuthContext authContext = ctx.attribute("authContext");
        if (authContext == null) {
            ctx.status(401).result("Unauthorized: Please log in.");
            return;
        }
    
        try {
            String cpfStf = authContext.getUserCPF();
            String valorStf = ctx.formParam("valor");
            String data_vencimentoStf = ctx.formParam("data_vencimento");
    
            double valor = Double.parseDouble(valorStf);
            double juros = 0.04;
            Date data = new Date();
    
            Date data_vencimento = new SimpleDateFormat("yyyy-MM-dd").parse(data_vencimentoStf);
            Date data_emprestimo = data;
    
            boolean success = bankService.createLoan(ctx, cpfStf, valor, juros, data_vencimento, data_emprestimo);
    
            if (success) {
                ctx.redirect("/");
            } else {
                throw new Error("Internal Server Error: Unable to process the loan.");
            }
    
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number: " + e.getMessage());
            ctx.status(400).result("Invalid input: Valor must be a number.");
        } catch (ParseException e) {
            System.err.println("Error parsing date: " + e.getMessage());
            ctx.status(400).result("Invalid input: Invalid date format. Use yyyy-MM-dd.");
        } catch (Exception e) {
            System.err.println("Error during createLoan operation: " + e.getMessage());
            e.printStackTrace();
            ctx.status(500).result("Internal Server Error: " + e.getMessage());
        }
    };
    


    public Handler getLoan = (Context ctx) -> {

        AuthContext authContext = ctx.attribute("authContext");
        if (authContext == null) {
            ctx.status(401).result("Unauthorized: Please log in.");
            return;
        }

        try {
            Loan loan = bankService.getLoan(ctx);
            
            if (loan != null) {
                ctx.json(loan);
            } else {
                ctx.status(404).result("Not Found: Loan not found.");
            }

        } catch (Exception e) {
            System.err.println("Error during getLoan operation: " + e.getMessage());
            ctx.status(500).result("Internal Server Error: " + e.getMessage());
        }

    };

    public Handler getDeposits = (Context ctx) -> {
        AuthContext authContext = ctx.attribute("authContext");
        if (authContext == null) {
            ctx.status(401).result("Unauthorized: Please log in.");
            return;
        }

        try {
            ctx.json(bankService.getDepositos(ctx));
        } catch (Exception e) {
            System.err.println("Error during getDeposits operation: " + e.getMessage());
            ctx.status(500).result("Internal Server Error: " + e.getMessage());
        }
    };

    public Handler getRecentTransfers = (Context ctx) -> {
    AuthContext authContext = ctx.attribute("authContext");
    if (authContext == null) {
        ctx.status(401).result("Unauthorized");
        return;
    }

    try {
        List<Transfer> transfers = bankService.getTransfersLast7Days(ctx);
        ctx.json(transfers);
        return;
    } catch (Exception e) {
        ctx.status(500).result("Internal Server Error: " + e.getMessage());
    }
};

}
