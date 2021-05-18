package com.example.Broken_Hammer.repository;

import java.util.Map;

public interface OrderRepository {
    void addOrder(int userID, Map<String, String[]> parametersMap);
}
