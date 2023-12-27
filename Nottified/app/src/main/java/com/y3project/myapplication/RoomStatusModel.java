package com.y3project.myapplication;

public class RoomStatusModel {
    private String room_no;
    private int ac_bg;
    private int lights_bg;

    // Constructor
    public RoomStatusModel(String room_No, int AC_bg, int Lights_bg) {
        this.room_no = room_No;
        this.ac_bg = AC_bg;
        this.lights_bg = Lights_bg;
    }

    // Getter and Setter
    public String getRoom_no() {
        return room_no;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }

    public int getAc_bg() {
        return ac_bg;
    }

    public void setAc_bg(int ac_bg) {
        this.ac_bg = ac_bg;
    }

    public int getLights_bg() {
        return lights_bg;
    }

    public void setLights_bg(int lights_bg) {
        this.lights_bg = lights_bg;
    }
}
