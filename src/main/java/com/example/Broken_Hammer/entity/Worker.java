package com.example.Broken_Hammer.entity;

public class Worker {
    private String login;
    private int id;
    private String bio;
    private int ordersAmount;
    private double average;
    private double wilsonScore;
    private int positiveGrades;
    private int negativeGrades;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getOrdersAmount() {
        return ordersAmount;
    }

    public void setOrdersAmount(int ordersAmount) {
        this.ordersAmount = ordersAmount;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public double getWilsonScore() {
        return wilsonScore;
    }

    public void setWilsonScore(double wilsonScore) {
        this.wilsonScore = wilsonScore;
    }

    public int getPositiveGrades() {
        return positiveGrades;
    }

    public void setPositiveGrades(int positiveGrades) {
        this.positiveGrades = positiveGrades;
    }

    public int getNegativeGrades() {
        return negativeGrades;
    }

    public void setNegativeGrades(int negativeGrades) {
        this.negativeGrades = negativeGrades;
    }
}
