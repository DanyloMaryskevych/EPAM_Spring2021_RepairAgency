package com.example.Broken_Hammer.entity;

import java.sql.Date;

public class OrderDTO {
    private int id;
    private String title;
    private String description;
    private Date date;
    private int workerID;
    private int expectedWorkerID;
    private String workerName;
    private int customerID;
    private String customerName;
    private String paymentStatus;
    private int paymentStatusId;
    private String performanceStatus;
    private int performanceStatusId;
    private int price;
    private int rating;
    private String comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getWorkerID() {
        return workerID;
    }

    public void setWorkerID(int workerID) {
        this.workerID = workerID;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPerformanceStatus() {
        return performanceStatus;
    }

    public void setPerformanceStatus(String performanceStatus) {
        this.performanceStatus = performanceStatus;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getExpectedWorkerID() {
        return expectedWorkerID;
    }

    public void setExpectedWorkerID(int expectedWorkerID) {
        this.expectedWorkerID = expectedWorkerID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getPaymentStatusId() {
        return paymentStatusId;
    }

    public void setPaymentStatusId(int paymentStatusId) {
        this.paymentStatusId = paymentStatusId;
    }

    public int getPerformanceStatusId() {
        return performanceStatusId;
    }

    public void setPerformanceStatusId(int performanceStatusId) {
        this.performanceStatusId = performanceStatusId;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", workerID=" + workerID +
                ", expectedWorkerID=" + expectedWorkerID +
                ", workerName='" + workerName + '\'' +
                ", customerID=" + customerID +
                ", customerName='" + customerName + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", paymentStatusId=" + paymentStatusId +
                ", performanceStatus='" + performanceStatus + '\'' +
                ", performanceStatusId=" + performanceStatusId +
                ", price=" + price +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                '}';
    }
}
