package com.bank;

import com.bank.data.jdbc.JDBCBank;
import com.bank.routes.Routes;
import com.bank.server.Server;

public class Main {

    public static void main(String[] args) {

        Routes routes = new Routes();

        var app = Server.serverInit(7000);
        System.out.println("Server running on port 7000");

        routes.RoutesInit(app);

        JDBCBank jdbcBank = new JDBCBank();
        jdbcBank.JDBCBankInit();
    }

}
