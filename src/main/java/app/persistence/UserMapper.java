package app.persistence;

import app.entities.User;
import app.execeptions.DatabaseExeception;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {

    public static User login(String name, String password, ConnectionPool connectionPool) throws DatabaseExeception {
        String sql =    "SELECT * FROM \"user\" " +
                        "WHERE name=? AND password=?";
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
            int id = rs.getInt("id");
            return new User(id, name, password);
            } else{
                throw new DatabaseExeception("brugerlogin fejl");
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static void createuser(String name, String password, ConnectionPool connectionPool) throws DatabaseExeception {
    String sql =    "INSERT INTO public.user (name, password) VALUES (?, ?)";
    try(Connection connection = connectionPool.getConnection();
    PreparedStatement ps = connection.prepareStatement(sql);) {
        ps.setString(1,name);
        ps.setString(2, password);
        int rowsAffected = ps.executeUpdate();
        if(rowsAffected !=1){
            throw new DatabaseExeception("Fejl ved oprettelse af bruger");
        }
    } catch ( SQLException e){
        if (e.getMessage().startsWith("ERROR: duplicate key value")) {
            throw new DatabaseExeception("Prøv med et andet brugernavn");
        }
        throw new DatabaseExeception("fejl, prøv igen");
    }
    }
}
