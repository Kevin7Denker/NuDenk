package com.bank.routes;

import com.bank.controller.AuthController;
import com.bank.controller.BankController;
import com.bank.controller.IndexController;
import com.bank.middleware.AuthMiddleware;

import io.javalin.Javalin;

public class Routes {

    private final IndexController indexController = new IndexController();
    private final AuthController authController = new AuthController();
    private final AuthMiddleware middleware = new AuthMiddleware();
    private final BankController bankController = new BankController();

    public void RoutesInit(Javalin app) {

        app.before(middleware.authMiddleware);

        app.get("/", ctx -> {
            if (ctx.attribute("authContext") != null) {
                authController.home.handle(ctx);
            } else {
                indexController.welcome.handle(ctx);
            }

        });

        app.get("/logout", authController.logout);

        app.get("/register", ctx -> {
            if (ctx.attribute("authContext") != null) {
                ctx.json("Already logged in");
                ctx.redirect("/");
            } else {
                authController.signUp.handle(ctx);
            }
        });

        app.post("/register", ctx -> {
            if (ctx.attribute("authContext") != null) {
                ctx.json("Already logged in");
                ctx.redirect("/");
            } else {
                authController.register.handle(ctx);
                ctx.json("Registered successfully");
                ctx.redirect("/");
            }
        });

        app.get("/login", ctx -> {
            if (ctx.attribute("authContext") != null) {
                ctx.json("Already logged in");
                ctx.redirect("/");
            } else {
                authController.signIn.handle(ctx);
            }
        });

        app.post("/login", ctx -> {
            if (ctx.attribute("authContext") != null) {
                ctx.json("Already logged in");
                ctx.redirect("/");
            } else {
                authController.login.handle(ctx);
                ctx.redirect("/");
            }
        });

        app.post("/credit", ctx -> {
            if (ctx.attribute("authContext") != null) {
                bankController.credit.handle(ctx);
                ctx.redirect("/");
            } else {
                ctx.json("Unauthorized: Please log in.");
                ctx.redirect("/");
            }
        });

        app.get("/config", ctx -> {
            if (ctx.attribute("authContext") != null) {
                authController.config.handle(ctx);
            } else {
                ctx.json("Unauthorized: Please log in.");
                ctx.redirect("/");
            }
        });

        app.post("/transfer", ctx -> {
            if (ctx.attribute("authContext") != null) {
                bankController.transfer.handle(ctx);
                ctx.redirect("/");
            } else {
                ctx.json("Unauthorized: Please log in.");
                ctx.redirect("/");
            }
        });

        app.post("/updateUser", ctx -> {
            if (ctx.attribute("authContext") != null) {
                authController.updateUser.handle(ctx);
            } else {
                ctx.json("Unauthorized: Please log in.");
                ctx.redirect("/");
            }
        });

        app.get("/getTransfersRecent", ctx -> {
            if (ctx.attribute("authContext") != null) {
                bankController.getRecentTransfers.handle(ctx);
            } else {
                ctx.json("Unauthorized: Please log in.");
                ctx.redirect("/");
            }
        });

        app.get("/getBalance", ctx -> {
            if (ctx.attribute("authContext") != null) {
                bankController.getBalance.handle(ctx);
            } else {
                ctx.json("Unauthorized: Please log in.");
                ctx.redirect("/");
            }
        });

        app.get("/getLoan", ctx -> {
            if (ctx.attribute("authContext") != null) {
                bankController.getLoan.handle(ctx);
            } else {
                ctx.json("Unauthorized: Please log in.");
                ctx.redirect("/");
            }
        });

        app.post("/createLoan", ctx -> {
            if (ctx.attribute("authContext") != null) {
                bankController.createLoan.handle(ctx);
            } else {
                ctx.json("Unauthorized: Please log in.");
                ctx.redirect("/");
            }
        });

        app.get("/getDeposits", ctx -> {
            if (ctx.attribute("authContext") != null) {
                bankController.getDeposits.handle(ctx);
            } else {
                ctx.json("Unauthorized: Please log in.");
                ctx.redirect("/");
            }
        });

    }

}
