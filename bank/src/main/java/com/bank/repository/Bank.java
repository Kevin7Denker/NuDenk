package com.bank.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bank.data.dao.BalanceDAO;
import com.bank.data.dao.ClientStandardDAO;
import com.bank.data.dao.DepositDAO;
import com.bank.data.dao.LoanDAO;
import com.bank.data.dao.TransferDAO;
import com.bank.hooks.AuthContext;
import com.bank.models.Balance;
import com.bank.models.ClientStandard;
import com.bank.models.Deposit;
import com.bank.models.Loan;
import com.bank.models.Transfer;
import io.javalin.http.Context;

public class Bank {

    private static ClientStandardDAO clientStandardDAO;
    private static TransferDAO transferDAO;
    private static DepositDAO depositDAO;
    private static BalanceDAO balanceDAO;
    private static LoanDAO loanDAO;

    private static AuthContext authContext;

    public Bank() {
        clientStandardDAO = new ClientStandardDAO();
        depositDAO = new DepositDAO();
        transferDAO = new TransferDAO();
        balanceDAO = new BalanceDAO();
        loanDAO = new LoanDAO();
        
    }

    public String Creditar(Context ctx, Double valor, String cpf) {
        try {

            Date data = new Date();

            ClientStandard client = clientStandardDAO.getClientByCpf(cpf);
            Deposit deposit = new Deposit(cpf, data, valor);

            if (client == null) {
                throw new Error("Cliente não encontrado.");
            }

            if (valor <= 0) {
                throw new Error("Valor inválido.");
            }

            String res = balanceDAO.creditar(deposit);
            depositDAO.createDeposit(deposit);
            
            return res;

        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }

    public Boolean Debitar(Context ctx, Double valor, String cpf) {
        try {
            ClientStandard client = clientStandardDAO.getClientByCpf(cpf);

            if (client == null) {
                throw new Error("Cliente não encontrado.");
            }

            if (valor <= 0) {
                throw new Error("Valor inválido.");
            }

            if (balanceDAO.getBalance(ctx).getSaldo() < valor) {
                throw new Error("Saldo insuficiente.");
            }

            balanceDAO.Debitar(valor, cpf);

            return true;

        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }

    public Balance getSaldo(Context ctx) {
        try {

            Balance val = balanceDAO.getBalance(ctx);

            System.out.println(val);

            return val;

        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }

    public boolean Transferir(Context ctx, Double valor, String cpfOrigem, String cpfDestino) {
        try {

            Date data = new Date();

            ClientStandard clientOrigem = clientStandardDAO.getClientByCpf(cpfOrigem);
            ClientStandard clientDestino = clientStandardDAO.getClientByCpf(cpfDestino);

            Transfer transfer = new Transfer(cpfOrigem, cpfDestino, valor, data);

            if (clientOrigem == null || clientDestino == null) {
                throw new Error("Cliente não encontrado.");
            }

            if (valor <= 0) {
                throw new Error("Valor inválido.");
            }

            Debitar(ctx, valor, clientOrigem.getCpf());
            Creditar(ctx, valor, clientDestino.getCpf());

            if (transferDAO.createTranfer(transfer)) {
                System.out.println("Transferência realizada com sucesso.");
            }

            return true;

        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }

    public Balance getBalance(Context ctx){

        try {
            authContext = ctx.attribute("authContext");
            return balanceDAO.getBalance(ctx);

        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }

    public Transfer getTransferencias(Context ctx) {
        try {
            authContext = ctx.attribute("authContext");
            return transferDAO.getTransfers(authContext.getUserCPF());

        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }

    public List<Deposit> getDepositos(Context ctx) {
        try {
            authContext = ctx.attribute("authContext");
            return depositDAO.getDeposits(authContext.getUserCPF());

        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    }

    public List<Transfer> getTransfersLast7Days(Context ctx) {
        try {
            List<Transfer> transfers = new ArrayList<Transfer>();      
            transfers.addAll(transferDAO.getTransfersLast7Days(ctx));
            return transfers;

        } catch (Exception e) {
            throw new Error(e.getMessage());
        }
    
    }


    public boolean createBalance(Context ctx, double saldo, double limite, double divida) {
        
        try {
            authContext = ctx.attribute("authContext");
            Balance balance = new Balance(authContext.getUserCPF(),saldo, limite, divida);

            System.out.println(balance);

            String res = balanceDAO.createBalance(balance);

            System.out.println(res);

            return true;    


        } catch (Exception e) {
            return false;
        }
        
    }

    public boolean createLoan(Context ctx, String cpf, double valor, double juros, Date data_vencimento, Date data_emprestimo) {
        try {
            Balance balance = balanceDAO.getBalance(ctx);
    
            if (balance.getLimite() < valor) {
                throw new Error("Limite insuficiente.");
            }
    
            Loan loan = new Loan(cpf, valor, juros, data_vencimento, data_emprestimo);
    
            Deposit deposit = new Deposit(cpf, data_emprestimo, valor);
    
            System.out.println(loan);
    
            Loan createdLoan = loanDAO.createLoan(loan);
    
            if (createdLoan == null) {
                throw new Error("Failed to create loan in database.");
            }
    
            balanceDAO.creditar(deposit);
            balanceDAO.updateDivida(ctx, valor);
    
            return true;
        } catch (Exception e) {
            System.err.println("Error during createLoan in BankService: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    

    public Loan getLoan(Context ctx) {
        try {
            Loan loan = loanDAO.getLoan(ctx);
            return loan;
        } catch (Exception e) {
            return null;
        }
    }

}