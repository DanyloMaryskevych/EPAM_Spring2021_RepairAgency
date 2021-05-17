package com.example.Broken_Hammer.dao;

import com.example.Broken_Hammer.DBManager;
import com.example.Broken_Hammer.repository.CustomerRepository;

import javax.naming.NamingException;
import java.sql.*;

import static com.example.Broken_Hammer.dao.UserDAO.close;

public class CustomerDAO implements CustomerRepository {
    private final DBManager dbManager;

    public CustomerDAO() {
        dbManager = new DBManager();
    }

    @Override
    public void addCustomer() {

    }

    @Override
    public void deposit(int id, int amount) {
        updateWallet(id, amount, true);
    }

    @Override
    public void withdraw(int id, int amount) throws SQLException {
        int currentBalance = getBalance(id);

        if (amount > currentBalance) throw new SQLException();
        else {
            updateWallet(id, amount, false);
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

    private void updateWallet(int id, int amount, boolean add) {
        String flag = add ? "+" : "-";

        String sql = "update customers_data set balance = balance " + flag + " ? where customer_id = ?";

        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            int k = 0;
            statement.setInt(++k, amount);
            statement.setInt(++k, id);

            statement.execute();

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        CustomerDAO customerDAO = new CustomerDAO();

        System.out.println(customerDAO.getBalance(1));
    }

}
