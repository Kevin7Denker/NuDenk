package com.bank.controller;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class IndexController {

    public Handler welcome = (Context ctx) -> {
        ctx.render("index.html");
    };

}
