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

        app.get("/", ctx -> {
            if (ctx.attribute("authContext") != null) {
                authController.home.handle(ctx);
            } else {

                indexController.welcome.handle(ctx);
            }

        });

        app.get("/show", authController.show);
        app.get("/logout", authController.logout);

        app.get("/register", ctx -> {
            if (ctx.attribute("authContext") != null) {
                ctx.status(403).result("Already logged in");
            } else {
                authController.signUp.handle(ctx);
            }
        });

        app.post("/register", ctx -> {
            if (ctx.attribute("authContext") != null) {
                ctx.status(403).result("Already logged in");
            } else {
                authController.register.handle(ctx);
            }
        });

        app.get("/login", ctx -> {
            if (ctx.attribute("authContext") != null) {
                ctx.status(403).result("Already logged in");
            } else {
                authController.signIn.handle(ctx);
            }
        });

        app.post("/login", ctx -> {
            if (ctx.attribute("authContext") != null) {
                ctx.status(403).result("Already logged in");
            } else {
                authController.login.handle(ctx);
            }
        });
    }

}
