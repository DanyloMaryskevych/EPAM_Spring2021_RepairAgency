package com.example.Broken_Hammer.dao;

import com.example.Broken_Hammer.DBManager;
import com.example.Broken_Hammer.repository.UserRepository;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAO implements UserRepository {
    private final DBManager dbManager;

    public UserDAO() {
        dbManager = new DBManager();
    }

    @Override
    public void addUser(String login, String password, String role) {
        String sql = "insert into users values(default, ?, ?, ?)";

        try(Connection connection = dbManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            int k = 0;
            statement.setString(++k, login);
            statement.setString(++k, password);
            statement.setString(++k, role);

            statement.execute();

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
    }
}
