package com.example.Broken_Hammer.dao;

import com.example.Broken_Hammer.DBManager;
import com.example.Broken_Hammer.entity.User;
import com.example.Broken_Hammer.repository.UserRepository;

import javax.naming.NamingException;
import javax.sql.rowset.spi.SyncResolver;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public boolean addUser(Map<String, String[]> parametersMap) throws SQLException {
        if (!confirmPassword(parametersMap)) return false;

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
            return false;
        }

        return true;
    }

    private boolean confirmPassword(Map<String, String[]> parametersMap) {
        return parametersMap.get(FIRST_PASSWORD)[0].equals(parametersMap.get(SECOND_PASSWORD)[0]);
    }

    public String checkUser(User user) {
        String sql = "select * from users where login = ? and password = ?";

        ResultSet resultSet = null;

        try(Connection connection = dbManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.execute();

            resultSet = statement.getResultSet();

            if (resultSet.next()) {
                return resultSet.getString("role");
            }

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
