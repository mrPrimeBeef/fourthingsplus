package app.persistence;

import app.entities.Task;
import app.entities.User;
import app.execeptions.DatabaseExeception;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskMapper {
    public static List<Task> getAllTasksPerUser(int user_id, ConnectionPool connectionPool) throws DatabaseExeception {
        ArrayList<Task> tasks = new ArrayList<Task>();
        String sql = "SELECT * FROM task " +
                "WHERE user_id=?";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, user_id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Boolean done = rs.getBoolean("done");

                tasks.add(new Task(id, name, done, user_id));
            }
        } catch (SQLException e) {
            throw new DatabaseExeception("Fejl");
        }
        return tasks;
    }

    public static Task addtask(User user, String taskName, ConnectionPool connectionPool) throws DatabaseExeception {
        Task newTask = null;
        String sql = "INSERT INTO public.task (name, done, user_id) VALUES (?, ?, ?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            ps.setString(1, taskName);
            ps.setBoolean(2, false);
            ps.setInt(3, user.getId());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                int newID = rs.getInt(1);
                newTask = new Task(newID, taskName, false,user.getId());
            } else {
                throw new DatabaseExeception("Fejl under indsætning af task: " + taskName);
            }

        } catch (SQLException e) {
            throw new DatabaseExeception("Fejl i db task");
        }
    return newTask;
    }
}
