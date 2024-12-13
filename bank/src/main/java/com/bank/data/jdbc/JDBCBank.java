package com.bank.data.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class JDBCBank {

    private final static Dotenv dotenv = Dotenv.load();

    private static final String URL = dotenv.get("URL");
    private static final String USER = dotenv.get("USER");
    private static final String PASSWORD = dotenv.get("PASSWORD");

    public static final Connection connection = new JDBCBank().JDBCBankInit();

    public Connection getConnection() {
        return connection;
    }

    public Connection JDBCBankInit() {

        try {

            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            if (con != null) {
                System.out.println("Conexão realizada com sucesso.");
                return con;
            }

            return null;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }

    }

}
