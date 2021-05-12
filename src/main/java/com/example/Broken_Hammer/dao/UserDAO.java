package com.example.Broken_Hammer.dao;

import com.example.Broken_Hammer.DBManager;
import com.example.Broken_Hammer.repository.UserRepository;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class UserDAO implements UserRepository {
    private static final String LOGIN = "login";
    private static final String FIRST_PASSWORD = "password1";
    private static final String SECOND_PASSWORD = "password2";
    private static final String ROLE = "role";

    private final DBManager dbManager;

    public UserDAO() {
        dbManager = new DBManager();
    }

    @Override
    public void addUser(Map<String, String[]> parametersMap) throws SQLException {
        if (!confirmPassword(parametersMap)) throw new SQLException();

        String sql = "insert into users values(default, ?, ?, ?)";

        try(Connection connection = dbManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            int k = 0;
            statement.setString(++k, parametersMap.get(LOGIN)[0]);
            statement.setString(++k, parametersMap.get(FIRST_PASSWORD)[0]);
            statement.setString(++k, parametersMap.get(ROLE)[0]);

            statement.execute();

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
    }

    private boolean confirmPassword(Map<String, String[]> parametersMap) {
        return parametersMap.get(FIRST_PASSWORD)[0].equals(parametersMap.get(SECOND_PASSWORD)[0]);
    }
}
