package com.example.Broken_Hammer.dao;

import com.example.Broken_Hammer.DBManager;
import com.example.Broken_Hammer.repository.OrderRepository;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class OrderDAO implements OrderRepository {
    private final DBManager dbManager;

    public OrderDAO() {
        dbManager  = new DBManager();
    }

    @Override
    public void addOrder(int userID, Map<String, String[]> parametersMap) {
        String sql = "insert into orders (customer_id, title, description, expected_worker) value (?, ?, ?, ?)";

        try(Connection connection = dbManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            int k = 0;
            statement.setInt(++k, userID);
            statement.setString(++k, parametersMap.get("title")[0]);
            statement.setString(++k, parametersMap.get("description")[0]);
            statement.setInt(++k, Integer.parseInt(parametersMap.get("expectedWorker")[0]));

            statement.execute();

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }

    }
}
