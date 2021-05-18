package com.example.Broken_Hammer.dao;

import com.example.Broken_Hammer.DBManager;
import com.example.Broken_Hammer.repository.OrderRepository;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderDAO implements OrderRepository {
    private final DBManager dbManager;

    public OrderDAO() {
        dbManager  = new DBManager();
    }

    @Override
    public void addOrder(int customerId, String title, String description, int expectedWorker) {
        String sql = "insert into orders values (default, ?, default, default,\n" +
                "?, ?, ?, default, default, default)";

        try(Connection connection = dbManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            int k = 0;
            statement.setInt(++k, customerId);
            statement.setString(++k, title);
            statement.setString(++k, description);
            statement.setInt(++k, expectedWorker);

            statement.execute();

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }

    }
}