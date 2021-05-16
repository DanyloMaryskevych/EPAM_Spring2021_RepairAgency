package com.example.Broken_Hammer.dao;

import com.example.Broken_Hammer.DBManager;
import com.example.Broken_Hammer.repository.UserRepository;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDAO implements UserRepository {
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String ROLE = "role";

    private final DBManager dbManager;

    public UserDAO() {
        dbManager = new DBManager();
    }

    @Override
    public boolean addUser(Map<String, String[]> parametersMap) {
        String sql = "insert into users values(default, ?, ?, ?)";

        try(Connection connection = dbManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            int k = 0;
            statement.setString(++k, parametersMap.get(LOGIN)[0]);
            statement.setString(++k, parametersMap.get(PASSWORD)[0]);
            statement.setString(++k, parametersMap.get(ROLE)[0]);

            statement.execute();

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    // Registration validation:
    public boolean checkLogin(String login) {
        String sql = "select * from users where login = ?";

        ResultSet resultSet = null;

        try(Connection connection = dbManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, login);
            statement.execute();

            resultSet = statement.getResultSet();

            if (resultSet.next()) {
                return false;
            }

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
        }

        return true;
    }

    public boolean passwordsEquality(String password, String password1) {
        return password.equals(password1);
    }

    public boolean validatePassword(String p1) {
        Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
        Matcher matcher = pattern.matcher(p1);

        return matcher.find();

    }

    public String checkIfUserExist(String login, String password) {
        String sql = "select * from users where login = ? and password = ?";

        ResultSet resultSet = null;

        try(Connection connection = dbManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, login);
            statement.setString(2, password);
            statement.execute();

            resultSet = statement.getResultSet();

            if (resultSet.next()) {
                return resultSet.getString(ROLE);
            }

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
        }

        return null;
    }



    // Privat methods
    private void close(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
