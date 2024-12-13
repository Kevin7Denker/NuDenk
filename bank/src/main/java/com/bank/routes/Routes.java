package com.bank.routes;

import com.bank.controller.AuthController;
import com.bank.controller.IndexController;

import io.javalin.Javalin;

public class Routes {

    private final IndexController indexController = new IndexController();
    private final AuthController authController = new AuthController();

    public void RoutesInit(Javalin app) {
        app.get("/", indexController.welcome);
        app.get("/show", authController.show);

        app.get("/register", authController.signUp);
        app.post("/register", authController.register);

        app.get("/login", authController.signIn);
        app.post("/login", authController.login);

    }

}
