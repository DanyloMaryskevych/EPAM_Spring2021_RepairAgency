package com.example.Broken_Hammer.repository;

import java.sql.Connection;

public interface WorkerRepository {
    void addWorker(Connection connection, int id);
}
