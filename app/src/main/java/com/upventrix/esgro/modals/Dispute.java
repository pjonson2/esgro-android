package com.upventrix.esgro.modals;

public class Dispute {

    private int id;
    private String name;
    private String discrption;
    private String price;
    private String days;
    private String image;

    public Dispute(int id,String name, String discrption, String price, String days,String image) {

        this.name = name;
        this.discrption = discrption;
        this.price = price;
        this.days = days;
        this.image = image;
        this.id = id;
    }

    public Dispute() {
    }

    public  int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscrption() {
        return discrption;
    }

    public void setDiscrption(String discrption) {
        this.discrption = discrption;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
