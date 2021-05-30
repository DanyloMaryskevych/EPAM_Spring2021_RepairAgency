package com.example.Broken_Hammer.dao;

import com.example.Broken_Hammer.DBManager;
import com.example.Broken_Hammer.repository.WorkerRepository;
import com.example.Broken_Hammer.entity.Worker;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.Broken_Hammer.dao.UserDAO.close;

public class WorkerDAO implements WorkerRepository {
    public static final String LOGIN_COLUMN = "login";
    public static final String WORKER_ID_COLUMN = "worker_id";
    public static final String ORDERS_AMOUNT_COLUMN = "orders_amount";
    public static final String AVERAGE_COLUMN = "average";
    public static final String WILSON_SCORE_COLUMN = "wilson_score";

    private final DBManager dbManager = DBManager.getDBManager();

    @Override
    public void addWorker(Connection connection, int id) throws SQLException {
        String sql = "insert into workers_data values (?, default, default, default, default, default, default)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.execute();
    }

    public List<Worker> getWorkers(String sort) {
        if (sort.equals("rating")) sort = WILSON_SCORE_COLUMN;

        String sql = "select login, worker_id, orders_amount, average, wilson_score " +
                "from workers_data join user u on u.id = workers_data.worker_id order by " + sort + " desc ";

        ResultSet resultSet = null;
        List<Worker> workers = new ArrayList<>();

        try (Connection connection = dbManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Worker worker = new Worker();

                worker.setLogin(resultSet.getString(LOGIN_COLUMN));
                worker.setId(resultSet.getInt(WORKER_ID_COLUMN));
                worker.setOrdersAmount(resultSet.getInt(ORDERS_AMOUNT_COLUMN));
                worker.setAverage(resultSet.getDouble(AVERAGE_COLUMN));
                worker.setWilsonScore(resultSet.getInt(WILSON_SCORE_COLUMN));

                workers.add(worker);
            }

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
        }

        return workers;
    }

    public void updateWorkerAfterFeedback(Connection connection, int rating, int workerID) throws SQLException {
        String sql = "update workers_data set votes = votes + 1," +
                "positive_grades = IF(? > 3, positive_grades + 1, positive_grades),\n" +
                "negative_grades = IF(? <= 3, negative_grades + 1, negative_grades),\n" +
                "average = round ((average * (votes - 1) + ?) / votes, 2),\n" +
                "wilson_score = ( positive_grades / votes + 1.9208 / votes - 1.96 *\n" +
                "sqrt( (positive_grades * (votes - positive_grades)) / (votes * votes * votes) + 0.9604 / (votes * votes)) )\n" +
                "/ (1 + 3.8416/votes)  where worker_id = ?;";

        PreparedStatement statement = connection.prepareStatement(sql);
        int k = 0;
        statement.setInt(++k, rating);
        statement.setInt(++k, rating);
        statement.setInt(++k, rating);
        statement.setInt(++k, workerID);

        statement.execute();

    }

    public String getWorkerById(int workerId) {
        String sql = "select login from user where id = ?";
        ResultSet resultSet = null;

        try(Connection connection = dbManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, workerId);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(LOGIN_COLUMN);
            }

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
        }

        return null;
    }

    public void updateOrdersCounter(int workerID) {
        String sql = "update workers_data set orders_amount = orders_amount + 1 where worker_id = ?";

        try(Connection connection = dbManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, workerID);
            statement.execute();

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
    }

}
