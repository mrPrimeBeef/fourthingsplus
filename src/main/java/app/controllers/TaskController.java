package app.controllers;

import app.entities.Task;
import app.entities.User;
import app.execeptions.DatabaseExeception;
import app.persistence.ConnectionPool;
import app.persistence.TaskMapper;
import io.javalin.http.Context;

import java.util.List;

public class TaskController {

    public static void addtask(Context ctx, ConnectionPool connectionPool) {
    User user = ctx.sessionAttribute("currentUser");
    String taskName = ctx.formParam("addtask");
        try {
            Task newTask = TaskMapper.addtask(user, taskName, connectionPool);

            List<Task> taskList = TaskMapper.getAllTasksPerUser(user.getId(), connectionPool);

            ctx.attribute("taskList", taskList);
            ctx.render("tasks.html");

        } catch (DatabaseExeception e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("/tasks.html");
        }
    }
}
