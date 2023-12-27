package com.y3project.myapplication;

public class OrderListModel {

    private String order_name;
    private String cuisine_name;
    private String order_no;
    private String quantity_no;
    private String status;
    private String color;

    // Constructor
    public OrderListModel(String order_name, String cuisine_name, String order_no, String quantity_no, String status, String color) {
        this.order_name = order_name;
        this.cuisine_name = cuisine_name;
        this.order_no = order_no;
        this.quantity_no = quantity_no;
        this.status = status;
        this.color = color;
    }

    // Getter and Setter
    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public String getCuisine_name() {
        return cuisine_name;
    }

    public void setCuisine_name(String cuisine_name) {
        this.cuisine_name = cuisine_name;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getQuantity_no() {
        return quantity_no;
    }

    public void setQuantity_no(String quantity_no) {
        this.quantity_no = quantity_no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getColor() {
        return color;
    }
}
