package com.example.Broken_Hammer.repository;

public interface OrderRepository {
    void addOrder(int customerId, String title, String description, int expectedWorker);
}
