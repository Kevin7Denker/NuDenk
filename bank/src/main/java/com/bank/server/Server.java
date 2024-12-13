package com.bank.server;

import freemarker.template.Configuration;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinFreemarker;

public class Server {

    public static Javalin serverInit(int port) {

        Javalin app = Javalin.create(serverConfig -> {

            Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
            cfg.setClassForTemplateLoading(Server.class, "/public");

            serverConfig.fileRenderer(new JavalinFreemarker(cfg));

            serverConfig.requestLogger.http((ctx, ms) -> {
                System.out.println(ctx.method() + "" + ctx.fullUrl());
            });

            serverConfig.staticFiles.add("public", Location.CLASSPATH);

        }).start(port);

        return app;
    }
}
