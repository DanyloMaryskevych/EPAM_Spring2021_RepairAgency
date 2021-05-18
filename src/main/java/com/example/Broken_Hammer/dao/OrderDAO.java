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
    public static final String ORDERS_TABLE = "orders";
    public static final String ID_COLUMN = "id";
    public static final String CUSTOMER_ID_COLUMN = "customer_id";
    public static final String WORKER_ID_COLUMN = "worker_id";
    public static final String DATE_COLUMN = "date";
    public static final String TITLE_COLUMN = "title";
    public static final String DESCRIPTION_COLUMN = "description";
    public static final String EXPECTED_WORKER_ID_COLUMN = "expected_worker";
    public static final String PAYMENT_STATUS_COLUMN = "payment_status";
    public static final String PERFORMANCE_STATUS_COLUMN = "performance_status";
    public static final String PRICE_COLUMN = "price";
    public static final String RATING_COLUMN = "rating";
    public static final String COMMENT_COLUMN = "comment";


    private final DBManager dbManager;

    public OrderDAO() {
        dbManager  = new DBManager();
    }

    @Override
    public void addOrder(int userID, Map<String, String[]> parametersMap) {
        String sql = "insert into " + ORDERS_TABLE + " (" +
                 CUSTOMER_ID_COLUMN + ", " +
                TITLE_COLUMN + ", " +
                DESCRIPTION_COLUMN + ", " +
                EXPECTED_WORKER_ID_COLUMN +
                ") value (?, ?, ?, ?)";

        try(Connection connection = dbManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            int k = 0;
            statement.setInt(++k, userID);
            statement.setString(++k, parametersMap.get(TITLE_COLUMN)[0]);
            statement.setString(++k, parametersMap.get(DESCRIPTION_COLUMN)[0]);
            statement.setInt(++k, Integer.parseInt(parametersMap.get(EXPECTED_WORKER_ID_COLUMN)[0]));

            statement.execute();

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
    }

    public List<Order> getOrdersByCustomersId(int id) {
        List<Order> orders = new ArrayList<>();
        ResultSet resultSet = null;

        String sql = "select id, worker_id, title, description, payment_status, " +
                "performance_status, price from orders where customer_id = ? order by id desc";

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

    public Order getOrderForCustomerById(int orderId) {
        String sql = "select title, description, payment_status, performance_status," +
                " price from orders where id = ?;";
        Order order = null;
        ResultSet resultSet = null;

        try(Connection connection = dbManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, orderId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                order = new Order();

                order.setTitle(resultSet.getString(TITLE_COLUMN));
                order.setDescription(resultSet.getString(DESCRIPTION_COLUMN));
                order.setPaymentStatus(resultSet.getString(PAYMENT_STATUS_COLUMN));
                order.setPerformanceStatus(resultSet.getString(PERFORMANCE_STATUS_COLUMN));
                order.setPrice(resultSet.getInt(PRICE_COLUMN));
            }

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
        }

        return order;
    }

    public String getWorker(int orderId) {
        String sql = "select login from users join orders on worker_id = users.id where orders.id = ?";
        ResultSet resultSet = null;

        try(Connection connection = dbManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, orderId);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("login");
            }

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
        }

        return null;
    }

}
