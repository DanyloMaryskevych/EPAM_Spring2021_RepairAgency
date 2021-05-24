package com.example.Broken_Hammer.dao;

public class DAOFactory {
    public static OrderDAO getOrderDAO() {
        return new OrderDAO();
    }

    public static UserDAO getUserDAO() {
        return new UserDAO();
    }

    public static WorkerDAO getWorkerDAO() {
        return new WorkerDAO();
    }

    public static CustomerDAO getCustomerDAO() {
        return new CustomerDAO();
    }
}
