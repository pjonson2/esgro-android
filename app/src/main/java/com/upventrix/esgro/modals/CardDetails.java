package com.upventrix.esgro.modals;

import com.google.gson.annotations.SerializedName;

public class CardDetails {

    @SerializedName("id")
    private String id;

    @SerializedName("brand")
    private String brand;

    @SerializedName("exp_year")
    private int exp_year;

    @SerializedName("exp_month")
    private int exp_month;

    @SerializedName("last_digits")
    private int last_digits;

    @SerializedName("card_icon")
    private String card_icon;

    public CardDetails(String id, String brand, int exp_year, int exp_month, int last_digits, String card_icon) {
        this.id = id;
        this.brand = brand;
        this.exp_year = exp_year;
        this.exp_month = exp_month;
        this.last_digits = last_digits;
        this.card_icon = card_icon;
    }

    public CardDetails() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getExp_year() {
        return exp_year;
    }

    public void setExp_year(int exp_year) {
        this.exp_year = exp_year;
    }

    public int getExp_month() {
        return exp_month;
    }

    public void setExp_month(int exp_month) {
        this.exp_month = exp_month;
    }

    public int getLast_digits() {
        return last_digits;
    }

    public void setLast_digits(int last_digits) {
        this.last_digits = last_digits;
    }

    public String getCard_icon() {
        return card_icon;
    }

    public void setCard_icon(String card_icon) {
        this.card_icon = card_icon;
    }
}
