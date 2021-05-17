package com.example.Broken_Hammer.repository;

import java.sql.SQLException;

public interface CustomerRepository {
    void addCustomer();

    void deposit(int id, int amount);

    void withdraw(int id, int amount) throws SQLException;

    int getBalance(int id);
}
