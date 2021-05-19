package com.example.Broken_Hammer.dao;

import com.example.Broken_Hammer.DBManager;
import com.example.Broken_Hammer.repository.CustomerRepository;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.Broken_Hammer.dao.UserDAO.close;

public class CustomerDAO implements CustomerRepository {
    private final DBManager dbManager;
    private final OrderDAO orderDAO;

    public CustomerDAO() {
        dbManager = new DBManager();
        orderDAO = new OrderDAO();
    }

    @Override
    public void addCustomer(Connection connection, int id) {
        String sql = "insert into customers_data values (?, default)";

        try(PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deposit(int id, int amount) {
        try(Connection connection = dbManager.getConnection()) {
            updateWallet(connection, id, amount, true);
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void withdraw(int userID, int orderID, int amount) throws SQLException {
        int currentBalance = getBalance(userID);

        if (amount > currentBalance) throw new SQLException();
        else {
            Connection connection = null;

            try {
                connection = dbManager.getConnection();
                connection.setAutoCommit(false);
                connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

                updateWallet(connection, userID, amount, false);
                orderDAO.updatePaymentStatus("Paid", orderID);

                connection.commit();
            } catch (Exception e) {
                if (connection != null) {
                    connection.rollback();
                }
                e.printStackTrace();
            } finally {
                close(connection);
            }
        }
    }

    @Override
    public int getBalance(int id) {
        String sql = "select balance from customers_data where customer_id = ?";

        ResultSet resultSet = null;
        int balance = 0;

        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                balance = resultSet.getInt("balance");
            }

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
        }

        return balance;
    }

    private void updateWallet(Connection connection, int id, int amount, boolean add) {
        String flag = add ? "+" : "-";

        String sql = "update customers_data set balance = balance " + flag + " ? where customer_id = ?";

        try ( PreparedStatement statement = connection.prepareStatement(sql)) {

            int k = 0;
            statement.setInt(++k, amount);
            statement.setInt(++k, id);

            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
