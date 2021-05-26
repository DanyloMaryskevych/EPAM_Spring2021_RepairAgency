package com.example.Broken_Hammer.entity;

public enum Role {
    ADMIN, CUSTOMER, WORKER;

    public static Role getRoleByUser(User user) {
        int roleId = user.getRoleId();
        return getRoleById(roleId);
    }

    public static Role getRoleById(int id) {
        return Role.values()[id - 1];
    }
}
