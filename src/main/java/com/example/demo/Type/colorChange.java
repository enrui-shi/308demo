package com.example.demo.Type;

public class colorChange {
    Long pid;
    String color;
    public colorChange(Long pid,String color){
        this.pid =pid;
        this.color = color;
    }
    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
