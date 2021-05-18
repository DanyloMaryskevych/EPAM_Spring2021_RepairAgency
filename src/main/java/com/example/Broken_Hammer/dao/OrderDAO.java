package com.example.Broken_Hammer.dao;

import com.example.Broken_Hammer.DBManager;
import com.example.Broken_Hammer.entity.Order;
import com.example.Broken_Hammer.repository.OrderRepository;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.Broken_Hammer.dao.UserDAO.close;

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

    public List<Order> getOrdersByUserId(int id) {
        List<Order> orders = new ArrayList<>();
        ResultSet resultSet = null;

        String sql = "select id, worker_id, title, description, payment_status, " +
                "performance_status, price from orders where customer_id = ?";

        try(Connection connection = dbManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Order order = new Order();

                order.setId(resultSet.getInt("id"));
                order.setWorkerId(resultSet.getInt("worker_id"));
                order.setTitle(resultSet.getString("title"));
                order.setDescription(resultSet.getString("description"));
                order.setPaymentStatus(resultSet.getString("payment_status"));
                order.setPerformanceStatus(resultSet.getString("performance_status"));
                order.setPrice(resultSet.getInt("price"));

                orders.add(order);
            }

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
        }

        return orders;
    }


}
