package com.example.Broken_Hammer.repository;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Map;

public interface UserRepository {
    void addUser(Map<String, String[]> stringMap) throws SQLException, NoSuchAlgorithmException;

    boolean checkLogin(String login);
}
