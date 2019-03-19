package com.example.esgro.modals;

public class Request {
    private String name;
    private String days;
    private String price;
    private int image;

    public Request(String name, String days, String price,int image) {
        this.name = name;
        this.days = days;
        this.image = image;
        this.price = price;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
