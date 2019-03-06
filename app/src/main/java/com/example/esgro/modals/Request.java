package com.example.esgro.modals;

public class Request {
    private String name;
    private String days;
    private int image;

    public Request(String name, String days, int image) {
        this.name = name;
        this.days = days;
        this.image = image;
    }

    public Request() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
