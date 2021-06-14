package com.example.Broken_Hammer.dao;

import com.example.Broken_Hammer.DBManager;
import com.example.Broken_Hammer.entity.Role;
import com.example.Broken_Hammer.entity.User;
import com.example.Broken_Hammer.helper.PasswordEncryptor;
import com.example.Broken_Hammer.repository.UserRepository;

import javax.naming.NamingException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Map;

public class UserDAO implements UserRepository {
    private static final String ID = "id";
    private static final String LOGIN = "login";
    private static final String ROLE_ID = "role_id";
    private static final String SALT = "salt";
    private static final String PASSWORD = "password";

    private final DBManager dbManager = DBManager.getDBManager();
    private final CustomerDAO customerDAO = DAOFactory.getCustomerDAO();
    private final WorkerDAO workerDAO = DAOFactory.getWorkerDAO();


    /**
     * Returns nothing.
     * Adds user to db and depending of their role adds customer or worker to db. Use transaction.
     *
     * @param parametersMap takes such parameters from URL like 'login', 'password' and 'role_id'
     * @throws NoSuchAlgorithmException if no Provider supports a MessageDigestSpi implementation for the specified algorithm.
     */
    @Override
    public void addUser(Map<String, String[]> parametersMap) throws NoSuchAlgorithmException {
        System.out.println("addUser");
        Connection connection = null;
        ResultSet resultSet = null;

        String sql = "insert into user values(default, ?, ?, ?, ?)";
        int role_id = Integer.parseInt(parametersMap.get(ROLE_ID)[0]);
        Role role = Role.getRoleById(role_id);

        byte[] salt = PasswordEncryptor.createSalt();
        String hash = PasswordEncryptor.generatedHash(parametersMap.get(PASSWORD)[0], salt);
        String saltHex = PasswordEncryptor.bytesToHex(salt);

        try {
            connection = dbManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            int k = 0;
            statement.setString(++k, parametersMap.get(LOGIN)[0]);
            statement.setInt(++k, role_id);
            statement.setString(++k, saltHex);
            statement.setString(++k, hash);

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

    @Override
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
        String sql = "select * from user where login = ?";

        ResultSet resultSet = null;

        try(Connection connection = dbManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, login);
            statement.execute();

            resultSet = statement.getResultSet();

            if (resultSet.next()) {
                String saltHex = resultSet.getString(SALT);
                byte[] salt = PasswordEncryptor.hexStringToByteArray(saltHex);
                String generatedPassword = PasswordEncryptor.generatedHash(password, salt);
                String originalPassword = resultSet.getString(PASSWORD);
                System.out.println("if");
                if (generatedPassword.equals(originalPassword)) return resultSet.getString(ROLE_ID);
            }

        } catch (SQLException | NamingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
        }

        return null;
    }

    public int getId(String login) {
        String sql = "select id from user where login = ?";
        ResultSet resultSet = null;

        try(Connection connection = dbManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, login);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(ID);
            }

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
        }

        return 0;
    }

    public static void close(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
