package com.example.Broken_Hammer.repository;

import java.util.Map;

public interface OrderRepository {
    int addOrder(int userID, Map<String, String[]> parametersMap);
}
