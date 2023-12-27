package com.y3project.myapplication;

public class EntryLogModel {

    private String plate_no;
    private String vehicle_model;
    private String duration;
    private String entry_time;
    private String exit_time;

    public EntryLogModel(String plate_no, String vehicle_model, String duration, String entry_time, String exit_time) {
        this.plate_no = plate_no;
        this.vehicle_model = vehicle_model;
        this.duration = duration;
        this.entry_time = entry_time;
        this.exit_time = exit_time;
    }

    public String getPlate_no() {
        return plate_no;
    }

    public void setPlate_no(String plate_no) {
        this.plate_no = plate_no;
    }

    public String getVehicle_model() {
        return vehicle_model;
    }

    public void setVehicle_model(String vehicle_model) {
        this.vehicle_model = vehicle_model;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEntry_time() {
        return entry_time;
    }

    public void setEntry_time(String entry_time) {
        this.entry_time = entry_time;
    }

    public String getExit_time() {
        return exit_time;
    }

    public void setExit_time(String exit_time) {
        this.exit_time = exit_time;
    }
}
