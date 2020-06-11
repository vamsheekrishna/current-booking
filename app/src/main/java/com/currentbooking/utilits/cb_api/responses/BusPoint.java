package com.currentbooking.utilits.cb_api.responses;

public class BusPoint {
    String name;
    int id;

    public BusPoint(String s, int s1) {
        name = s;
        id = s1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
