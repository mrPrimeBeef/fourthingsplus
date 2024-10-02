package app.persistence;

import app.entities.Task;
import app.entities.User;
import app.execeptions.DatabaseExeception;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}
