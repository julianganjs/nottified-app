package com.y3project.myapplication;

public class StaffOrderModel {
    private String order_no;
    private String dish_name;
    private String dish_quantity;
    private String cstmer_name;
    private String total_paid;
    private String order_time;
    private String status;

    // Constructor
    public StaffOrderModel(String order_no, String dish_name, String dish_quantity, String cstmer_name, String total_paid, String order_time, String status) {
        this.order_no = order_no;
        this.dish_name = dish_name;
        this.dish_quantity = dish_quantity;
        this.cstmer_name = cstmer_name;
        this.total_paid = total_paid;
        this.order_time = order_time;
        this.status = status;
    }

    // Getter and Setter
    public String getOrder_no() {
        return order_no;
    }
    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getDish_name() {
        return dish_name;
    }
    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    public String getDish_quantity() {
        return dish_quantity;
    }
    public void setDish_quantity(String dish_quantity) {
        this.dish_quantity = dish_quantity;
    }

    public String getCstmer_name() {
        return cstmer_name;
    }
    public void setCstmer_name(String cstmer_name) {
        this.cstmer_name = cstmer_name;
    }

    public String getTotal_paid() {
        return total_paid;
    }
    public void setTotal_paid(String total_paid) {
        this.total_paid = total_paid;
    }

    public String getOrder_time() {
        return order_time;
    }
    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getStatus() {
        return status;
    }
}
