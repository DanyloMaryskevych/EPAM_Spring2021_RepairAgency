package com.example.Broken_Hammer.repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface WorkerRepository {
    void addWorker(Connection connection, int id) throws SQLException;
}
