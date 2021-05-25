package com.example.Broken_Hammer.dao;

import com.example.Broken_Hammer.DBManager;
import com.example.Broken_Hammer.entity.Order;
import com.example.Broken_Hammer.entity.OrderDTO;
import com.example.Broken_Hammer.entity.Worker;
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
    public static final int LIMIT = 5;
    public static final String ORDERS_TABLE = "orders";
    public static final String ID_COLUMN = "id";
    public static final String CUSTOMER_ID_COLUMN = "customer_id";
    public static final String WORKER_ID_COLUMN = "worker_id";
    public static final String WORKER_NAME_COLUMN = "worker_name";
    public static final String DATE_COLUMN = "date";
    public static final String TITLE_COLUMN = "title";
    public static final String DESCRIPTION_COLUMN = "description";
    public static final String EXPECTED_WORKER_ID_COLUMN = "expected_worker";
    public static final String PAYMENT_STATUS_COLUMN = "payment_status";
    public static final String PERFORMANCE_STATUS_COLUMN = "performance_status";
    public static final String PRICE_COLUMN = "price";
    public static final String RATING_COLUMN = "rating";
    public static final String COMMENT_COLUMN = "comment";

    private final DBManager dbManager = DBManager.getDBManager();
    private final WorkerDAO workerDAO = DAOFactory.getWorkerDAO();

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

    public List<Order> getOrdersByUserId(String role, int id, int start) {
        List<Order> orders = new ArrayList<>();
        ResultSet resultSet = null;

        String sqlRoleColumn = null;
        if (role.equals("Customer")) sqlRoleColumn = CUSTOMER_ID_COLUMN;
        else if (role.equals("Worker")) sqlRoleColumn = WORKER_ID_COLUMN;

        String sql = "select id, worker_id, title, description, payment_status, " +
                "performance_status, price from orders where " + sqlRoleColumn + " = ? order by id desc limit ?, " + LIMIT;

        try(Connection connection = dbManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            int k = 0;
            statement.setInt(++k, id);
            statement.setInt(++k, start);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Order order = new Order();

                order.setId(resultSet.getInt(ID_COLUMN));
                order.setWorkerId(resultSet.getInt(WORKER_ID_COLUMN));
                order.setTitle(resultSet.getString(TITLE_COLUMN));
                order.setDescription(resultSet.getString(DESCRIPTION_COLUMN));
                order.setPaymentStatus(resultSet.getString(PAYMENT_STATUS_COLUMN));
                order.setPerformanceStatus(resultSet.getString(PERFORMANCE_STATUS_COLUMN));
                order.setPrice(resultSet.getInt(PRICE_COLUMN));

                orders.add(order);
            }

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
        }

        return orders;
    }

    public List<OrderDTO> getAllOrders(String sort, String order, int start) {
        List<OrderDTO> orders = new ArrayList<>();
        ResultSet resultSet = null;

        String sql = "select orders.id,\n" +
                "       title,\n" +
                "       date,\n" +
                "       worker_id,\n" +
                "       login as worker_name,\n" +
                "       payment_status,\n" +
                "       performance_status,\n" +
                "       price\n" +
                "from orders\n" +
                "left join users u on worker_id = u.id\n" +
                "order by " + sort + " " + order + " limit ?, " + LIMIT;
        try(Connection connection = dbManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

//            statement.setString(1, sort);
            statement.setInt(1, start);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                OrderDTO orderDTO = new OrderDTO();

                orderDTO.setId(resultSet.getInt(ID_COLUMN));
                orderDTO.setTitle(resultSet.getString(TITLE_COLUMN));
                orderDTO.setDate(resultSet.getDate(DATE_COLUMN));
                orderDTO.setWorkerID(resultSet.getInt(WORKER_ID_COLUMN));
                orderDTO.setWorkerName(resultSet.getString(WORKER_NAME_COLUMN));
                orderDTO.setPaymentStatus(resultSet.getString(PAYMENT_STATUS_COLUMN));
                orderDTO.setPerformanceStatus(resultSet.getString(PERFORMANCE_STATUS_COLUMN));
                orderDTO.setPrice(resultSet.getInt(PRICE_COLUMN));

                orders.add(orderDTO);
            }

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
        }

        return orders;
    }

    public int amountOfPages(String role, int userID) {
        String sql = amountOfPagesSQLQuery(role, userID);
        ResultSet resultSet = null;

        try(Connection connection = dbManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, LIMIT);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("pages");
            }

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
        }

        return 1;
    }

    private String amountOfPagesSQLQuery(String role, int id) {
        StringBuilder sql = new StringBuilder("select ceiling(count(id) / ?) as pages from orders");

        if (role.equals("Admin")) return sql.toString();

        switch (role) {
            case "Customer": {
                sql.append(" where customer_id = ").append(id);
                break;
            }
            case "Worker": {
                sql.append(" where worker_id = ").append(id);
                break;
            }
        }

        return sql.toString();
    }

    public Order getOrderById(int orderId) {
        String sql = "select title, description, payment_status, performance_status," +
                " price, rating, comment, expected_worker from orders where id = ?;";
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
                order.setRating(resultSet.getInt(RATING_COLUMN));
                order.setComment(resultSet.getString(COMMENT_COLUMN));
                order.setExpectedWorker(resultSet.getInt(EXPECTED_WORKER_ID_COLUMN));
            }

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
        }

        return order;
    }

    public Worker getWorker(int orderId) {
        String sql = "select login, users.id from users join orders on worker_id = users.id where orders.id = ?";
        ResultSet resultSet = null;
        Worker worker = null;

        try(Connection connection = dbManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, orderId);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                worker = new Worker();

                worker.setId(resultSet.getInt("id"));
                worker.setLogin(resultSet.getString("login"));
                return worker;
            }

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
        }

        return null;
    }

    public void updatePaymentStatus(String status, int orderID) {
        String sql = "update orders set payment_status = ? where id = ?";

        try(Connection connection = dbManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, status);
            statement.setInt(2, orderID);

            statement.execute();

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
    }

    public void updatePrice(int price, int orderID) {
        String sql = "update orders set price = ?,\n" +
                "payment_status = 'Waiting for payment'\n" +
                "where id = ?";

        try(Connection connection = dbManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, price);
            statement.setInt(2, orderID);
            statement.execute();

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
    }

    public void updateFeedback(int rating, String comment, int orderID, int workerID) {
        String sql = "update orders set rating = ?, comment = ? where id = ?";
        Connection connection = null;

        try {
            connection = dbManager.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            PreparedStatement statement = connection.prepareStatement(sql);
            int k = 0;
            statement.setInt(++k, rating);
            statement.setString(++k, comment);
            statement.setInt(++k, orderID);

            statement.execute();
            workerDAO.updateWorkerAfterFeedback(connection, rating, workerID);

            connection.commit();
        } catch (SQLException | NamingException e) {
            try {
                assert connection != null;
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void updateWorker(int workerId, int orderId) {
        String sql = "update orders set worker_id = ? where id = ?";

        try(Connection connection = dbManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, workerId);
            statement.setInt(2, orderId);

            statement.execute();

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
    }

    public void updateRejectedStatus(String performStatus, int orderID) {
        String sql = "update orders set performance_status = ? where id = ?";

        try(Connection connection = dbManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, performStatus);
            statement.setInt(2, orderID);
            statement.execute();

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
    }

}
