package com.example.Broken_Hammer.dao;

import com.example.Broken_Hammer.DBManager;
import com.example.Broken_Hammer.entity.Order;
import com.example.Broken_Hammer.entity.OrderDTO;
import com.example.Broken_Hammer.entity.Role;
import com.example.Broken_Hammer.repository.OrderRepository;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.Broken_Hammer.dao.UserDAO.close;

public class OrderDAO implements OrderRepository {
    public static final int LIMIT = 5;
    public static final String ORDERS_TABLE = "`order`";
    public static final String ID_COLUMN = "id";
    public static final String CUSTOMER_ID_COLUMN = "customer_id";
    public static final String CUSTOMER_NAME_COLUMN = "customer_name";
    public static final String WORKER_ID_COLUMN = "worker_id";
    public static final String WORKER_NAME_COLUMN = "worker_name";
    public static final String DATE_COLUMN = "date";
    public static final String TITLE_COLUMN = "title";
    public static final String DESCRIPTION_COLUMN = "description";
    public static final String EXPECTED_WORKER_ID_COLUMN = "expected_worker_id";
    public static final String PAYMENT_STATUS_COLUMN = "payment_status";
    public static final String PAYMENT_STATUS_ID_COLUMN = "payment_status_id";
    public static final String PERFORMANCE_STATUS_COLUMN = "performance_status";
    public static final String PERFORMANCE_STATUS_ID_COLUMN = "performance_status_id";
    public static final String PRICE_COLUMN = "price";
    public static final String RATING_COLUMN = "rating";
    public static final String COMMENT_COLUMN = "comment";

    private final DBManager dbManager = DBManager.getDBManager();
    private final WorkerDAO workerDAO = DAOFactory.getWorkerDAO();

    @Override
    public int addOrder(int userID, Map<String, String[]> parametersMap) {
        String sql = "insert into " + ORDERS_TABLE + " (" +
                CUSTOMER_ID_COLUMN + ", " +
                TITLE_COLUMN + ", " +
                DESCRIPTION_COLUMN + ", " +
                EXPECTED_WORKER_ID_COLUMN +
                ") value (?, ?, ?, ?)";

        ResultSet resultSet = null;

        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            int k = 0;
            statement.setInt(++k, userID);
            statement.setString(++k, parametersMap.get(TITLE_COLUMN)[0]);
            statement.setString(++k, parametersMap.get(DESCRIPTION_COLUMN)[0]);
            statement.setInt(++k, Integer.parseInt(parametersMap.get(EXPECTED_WORKER_ID_COLUMN)[0]));

            statement.execute();

            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
        }

        return 0;
    }

    public List<Order> getOrdersByUserId(Role role, int id, int start, String lang) {
        List<Order> orders = new ArrayList<>();
        ResultSet resultSet = null;

        String sqlRoleColumn = null;
        if (role == Role.CUSTOMER) sqlRoleColumn = CUSTOMER_ID_COLUMN;
        else if (role == Role.WORKER) sqlRoleColumn = WORKER_ID_COLUMN;

        String sql = "select `order`.id, worker_id, title, description, payment_status, " +
                "performance_status, price from " + ORDERS_TABLE + " " +
                "join payment_status on `order`.payment_status_id = payment_status.payment_status_id\n" +
                "join performance_status on `order`.performance_status_id = performance_status.performance_status_id\n" +
                "where " + sqlRoleColumn + " = ? " +
                "and payment_status.lang_id = (select id from language where lang = ?)\n" +
                "and performance_status.lang_id = (select id from language where lang = ?)" +
                "order by id desc limit ?, " + LIMIT;

        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            int k = 0;
            statement.setInt(++k, id);
            statement.setString(++k, lang);
            statement.setString(++k, lang);
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

    public List<OrderDTO> getAllOrders(String sort, String order, int start,
                                       Map<String, String> filtersMap, String lang) {
        List<OrderDTO> orders = new ArrayList<>();
        ResultSet resultSet = null;

        String pagination = "";
        if (start != -1) pagination = " limit " + start + ", " + LIMIT;

        String sql = "select " + ORDERS_TABLE + ".id,\n" +
                "title, date, " +
                "worker_id, u1.login as worker_name,\n" +
                "customer_id, u2.login as customer_name,\n" +
                "payment_status, performance_status, price from `order`\n" +
                "left join user u1 on worker_id = u1.id\n" +
                "left join user u2 on customer_id = u2.id\n" +
                "join payment_status on `order`.payment_status_id = payment_status.payment_status_id\n" +
                "join performance_status on `order`.performance_status_id = performance_status.performance_status_id\n" +
                "where 1=1 " + addFilters(filtersMap) + "\n" +
                "and payment_status.lang_id = (select id from language where lang = ?)\n" +
                "and performance_status.lang_id = (select id from language where lang = ?)\n" +
                "order by " + sort + " " + order + pagination;

        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            int k = 0;
            statement.setString(++k, lang);
            statement.setString(++k, lang);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                OrderDTO orderDTO = new OrderDTO();

                orderDTO.setId(resultSet.getInt(ID_COLUMN));
                orderDTO.setTitle(resultSet.getString(TITLE_COLUMN));
                orderDTO.setDate(resultSet.getDate(DATE_COLUMN));
                orderDTO.setWorkerID(resultSet.getInt(WORKER_ID_COLUMN));
                orderDTO.setWorkerName(resultSet.getString(WORKER_NAME_COLUMN));
                orderDTO.setCustomerID(resultSet.getInt(CUSTOMER_ID_COLUMN));
                orderDTO.setCustomerName(resultSet.getString(CUSTOMER_NAME_COLUMN));
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

    private String addFilters(Map<String, String> filtersMap) {
        StringBuilder sb = new StringBuilder();
        for (String s : filtersMap.keySet()) {
            if (filtersMap.get(s) != null) {
                sb.append("and ").append(ORDERS_TABLE).append(".").append(s).append(" = ").append(filtersMap.get(s)).append(" ");
            }
        }
        return sb.toString();
    }

    public int amountOfPages(Role role, int userID) {
        String sql = amountOfPagesSQLQuery(role, userID);
        ResultSet resultSet = null;

        try (Connection connection = dbManager.getConnection();
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

        return 0;
    }

    private String amountOfPagesSQLQuery(Role role, int id) {
        StringBuilder sql = new StringBuilder("select ceiling(count(id) / ?) as pages from " + ORDERS_TABLE);

        switch (role) {
            case CUSTOMER: {
                sql.append(" where customer_id = ").append(id);
                break;
            }
            case WORKER: {
                sql.append(" where worker_id = ").append(id);
                break;
            }
        }

        return sql.toString();
    }

    public int amountOfPagesForAdmin(Map<String, String> filtersMap) {
        String sql = "select ceiling(count(id) / ?) as pages from " + ORDERS_TABLE + " where 1=1 " + addFilters(filtersMap);
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
        }
        return 0;
    }

    public OrderDTO getAllOrderInfoById(int orderId, String lang) {
        String sql = "select " + ORDERS_TABLE + ".id, worker_id, login as worker_name, " +
                "title, description, payment_status, `order`.payment_status_id, " +
                "performance_status, `order`.performance_status_id, " +
                "price, rating, comment, expected_worker_id from " + ORDERS_TABLE +
                "left join user on " + ORDERS_TABLE + ".worker_id = user.id\n" +
                "join payment_status on  " + ORDERS_TABLE + ".payment_status_id = payment_status.payment_status_id\n" +
                "join performance_status on  " + ORDERS_TABLE + ".performance_status_id = performance_status.performance_status_id\n" +
                "where " + ORDERS_TABLE + ".id = ?\n" +
                "and payment_status.lang_id = (select id from language where lang = ?)\n" +
                "and performance_status.lang_id = (select id from language where lang = ?)";

        OrderDTO order = null;
        ResultSet resultSet = null;

        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            int k = 0;
            statement.setInt(++k, orderId);
            statement.setString(++k, lang);
            statement.setString(++k, lang);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                order = new OrderDTO();

                order.setId(resultSet.getInt(ID_COLUMN));
                order.setWorkerID(resultSet.getInt(WORKER_ID_COLUMN));
                order.setWorkerName(resultSet.getString(WORKER_NAME_COLUMN));
                order.setExpectedWorkerID(resultSet.getInt(EXPECTED_WORKER_ID_COLUMN));
                order.setTitle(resultSet.getString(TITLE_COLUMN));
                order.setDescription(resultSet.getString(DESCRIPTION_COLUMN));
                order.setPaymentStatus(resultSet.getString(PAYMENT_STATUS_COLUMN));
                order.setPaymentStatusId(resultSet.getInt(PAYMENT_STATUS_ID_COLUMN));
                order.setPerformanceStatus(resultSet.getString(PERFORMANCE_STATUS_COLUMN));
                order.setPerformanceStatusId(resultSet.getInt(PERFORMANCE_STATUS_ID_COLUMN));
                order.setPrice(resultSet.getInt(PRICE_COLUMN));
                order.setRating(resultSet.getInt(RATING_COLUMN));
                order.setComment(resultSet.getString(COMMENT_COLUMN));
            }

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
        }

        return order;
    }

    public Order getBasicOrderInfoById(int orderId) {
        String sql = "select id, customer_id, worker_id from " + ORDERS_TABLE + " where id = ?";
        Order order = null;
        ResultSet resultSet = null;

        try(Connection connection = dbManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, orderId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                order = new Order();

                order.setId(resultSet.getInt(ID_COLUMN));
                order.setCustomerId(resultSet.getInt(CUSTOMER_ID_COLUMN));
                order.setWorkerId(resultSet.getInt(WORKER_ID_COLUMN));
            }

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
        }

        return order;
    }

    public void updatePaymentStatus(int status, int orderID) throws SQLException, NamingException {
        String sql = "update " + ORDERS_TABLE + " set payment_status_id = ? where id = ?";

        Connection connection = dbManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, status);
        statement.setInt(2, orderID);

        statement.execute();
    }

    public void updatePrice(int price, int orderID) {
        String sql = "update " + ORDERS_TABLE + " set price = ?,\n" +
                "payment_status_id = 2 where id = ?";

        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, price);
            statement.setInt(2, orderID);
            statement.execute();

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
    }

    public void updateFeedback(int rating, String comment, int orderID, int workerID) {
        String sql = "update " + ORDERS_TABLE + " set rating = ?, comment = ? where id = ?";
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
        String sql = "update " + ORDERS_TABLE + " set worker_id = ? where id = ?";

        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, workerId);
            statement.setInt(2, orderId);

            statement.execute();

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
    }

    public void updateRejectedStatus(int performStatus, int orderID) {
        String sql = "update " + ORDERS_TABLE + " set performance_status_id = ? where id = ?";

        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, performStatus);
            statement.setInt(2, orderID);
            statement.execute();

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
    }


}
