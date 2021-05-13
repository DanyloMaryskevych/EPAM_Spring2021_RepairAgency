package com.example.Broken_Hammer.repository;

import java.sql.SQLException;
import java.util.Map;

public interface UserRepository {
    boolean addUser(Map<String, String[]> stringMap) throws SQLException;
}
