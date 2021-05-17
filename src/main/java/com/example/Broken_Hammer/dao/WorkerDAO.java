package com.example.Broken_Hammer.dao;

import com.example.Broken_Hammer.repository.WorkerRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WorkerDAO implements WorkerRepository {

    @Override
    public void addWorker(Connection connection, int id) {
        String sql = "insert into workers_data values (?, default, default, default, default, default, default)";

        try(PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
