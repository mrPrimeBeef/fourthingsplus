package app.controllers;

import app.entities.Task;
import app.entities.User;
import app.execeptions.DatabaseExeception;
import app.persistence.ConnectionPool;
import app.persistence.TaskMapper;
import app.persistence.UserMapper;
import io.javalin.http.Context;

import java.util.List;

public class UserController {
    public static void login(Context ctx, ConnectionPool connectionPool) {
        String name = ctx.formParam("username");
        String password = ctx.formParam("password");

        try {
            User user = UserMapper.login(name, password, connectionPool);
            ctx.sessionAttribute("currentUser", user);

            // get tasks belonginng to this user
            List<Task> taskList = TaskMapper.getAllTasksPerUser(user.getId(), connectionPool);

            ctx.attribute("taskList", taskList);
            ctx.render("tasks.html");

        } catch (DatabaseExeception e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("index.html");
        }

    }

    public static void createuser(Context ctx, ConnectionPool connectionPool) {
        String name = ctx.formParam("username");
        String password = ctx.formParam("password");
        String password2 = ctx.formParam("password2");

        if (password.equals(password2)) {


            try {
                UserMapper.createuser(name, password, connectionPool);
                ctx.attribute("message", "Du er oprettet, log på at komme ind");
                ctx.render("index.html");

            } catch (DatabaseExeception e) {

                ctx.attribute("message", "du blev ikke oprettet, prøv igen");
                ctx.render("createuser.html");
            }

        } else {
            ctx.attribute("message", "Fejl i passwords");
            ctx.render("createuser.html");
        }
    }

    public static void logout(Context ctx) {
        // invalidate session
        ctx.req().getSession().invalidate();
        ctx.redirect("/");
    }
}
