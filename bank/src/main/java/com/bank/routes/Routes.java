package com.bank.routes;

import com.bank.controller.AuthController;
import com.bank.controller.IndexController;
import com.bank.middleware.AuthMiddleware;

import io.javalin.Javalin;

public class Routes {

    private final IndexController indexController = new IndexController();
    private final AuthController authController = new AuthController();
    private final AuthMiddleware middleware = new AuthMiddleware();

    public void RoutesInit(Javalin app) {

        app.before(middleware.authMiddleware);
        if (middleware.authMiddleware != null) {
            app.get("/", authController.home);
            app.get("/show", authController.show);

        } else {
            app.get("/", indexController.welcome);
            app.get("/register", authController.signUp);
            app.post("/register", authController.register);

            app.get("/login", authController.signIn);
            app.post("/login", authController.login);
        }

    }

}
