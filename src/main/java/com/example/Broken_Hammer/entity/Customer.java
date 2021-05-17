package com.example.Broken_Hammer.entity;

public class Customer {
    private int customer_id;
    private int balance;

    public Customer() {
    }

    public Customer(int customer_id, int wallet) {
        this.customer_id = customer_id;
        this.balance = wallet;
    }


    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
