package com.example.Broken_Hammer.dao;

import com.example.Broken_Hammer.DBManager;
import com.example.Broken_Hammer.entity.Role;
import com.example.Broken_Hammer.entity.User;
import com.example.Broken_Hammer.repository.UserRepository;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UserDAO implements UserRepository {
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String ROLE_ID = "role_id";

    private final DBManager dbManager = DBManager.getDBManager();
    private final CustomerDAO customerDAO = DAOFactory.getCustomerDAO();
    private final WorkerDAO workerDAO = DAOFactory.getWorkerDAO();

    @Override
    public void addUser(Map<String, String[]> parametersMap) {
        Connection connection = null;

        String sql = "insert into user values(default, ?, ?, ?)";
        int role_id = Integer.parseInt(parametersMap.get(ROLE_ID)[0]);
        Role role = Role.getRoleById(role_id);

        ResultSet resultSet = null;

        try {
            connection = dbManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            int k = 0;
            statement.setString(++k, parametersMap.get(LOGIN)[0]);
            statement.setString(++k, parametersMap.get(PASSWORD)[0]);
            statement.setInt(++k, role_id);

            statement.execute();

            if (role != Role.ADMIN) {
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    switch (role) {
                        case CUSTOMER: {
                            customerDAO.addCustomer(connection, resultSet.getInt(1));
                            break;
                        }
                        case WORKER: {
                            workerDAO.addWorker(connection, resultSet.getInt(1));
                            break;
                        }
                        default:
                            System.out.println("wrong role");
                            throw new SQLException();
                    }
                }
            }

            connection.commit();
        } catch (SQLException | NamingException e) {
            try {
                assert connection != null;
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            close(resultSet);
        }

    }

    public List<User> getWorkers() {
        String sql = "select id, login from user where role_id = 3";
        List<User> workers = new ArrayList<>();

        ResultSet resultSet = null;

        try(Connection connection = dbManager.getConnection();
        Statement statement = connection.createStatement()) {

            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                User user = new User();
                user.setLogin(resultSet.getString("login"));
                user.setId(resultSet.getLong("id"));

                workers.add(user);
            }

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
        }

        return workers;
    }

    // Registration validation:
    public boolean checkLogin(String login) {
        String sql = "select * from user where login = ?";

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

    public String checkIfUserExist(String login, String password) {
        String sql = "select * from user where login = ? and password = ?";

        ResultSet resultSet = null;

        try(Connection connection = dbManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, login);
            statement.setString(2, password);
            statement.execute();

            resultSet = statement.getResultSet();

            if (resultSet.next()) {
                return resultSet.getString(ROLE_ID);
            }

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
        }

        return null;
    }



    // Privat methods
    public static void close(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int getId(String login) {
        String sql = "select id from user where login = ?";
        ResultSet resultSet = null;

        try(Connection connection = dbManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, login);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id");
            }

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
        }

        return 0;
    }
}
