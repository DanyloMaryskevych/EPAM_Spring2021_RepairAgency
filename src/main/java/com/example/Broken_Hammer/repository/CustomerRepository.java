package com.example.Broken_Hammer.repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface CustomerRepository {
    void addCustomer(Connection connection, int id);

    void deposit(int id, int amount);

    void withdraw(int userID, int orderID, int amount) throws SQLException;

    int getBalance(int id);
}
