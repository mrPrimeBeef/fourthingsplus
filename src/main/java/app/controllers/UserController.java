package app.controllers;

import app.entities.User;
import app.execeptions.DatabaseExeception;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import io.javalin.http.Context;

public class UserController {
    public static void login(Context ctx, ConnectionPool connectionPool){
        String name = ctx.pathParam("username");
        String password = ctx.pathParam("password");

        try {
            User user = UserMapper.login(name,password,connectionPool);

        } catch (DatabaseExeception e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("index.html");
        }

    }
}
