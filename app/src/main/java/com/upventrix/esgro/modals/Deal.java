package com.upventrix.esgro.modals;

import com.google.gson.annotations.SerializedName;

public class Deal {

    @SerializedName("description")
    private String description;

    @SerializedName("total")
    private double total;

    @SerializedName("reserve")
    private double reserve;

    @SerializedName("period")
    private String period;

    @SerializedName("from")
    private int from;

    @SerializedName("to")
    private  int to;

    public Deal() {
    }

    public Deal(String description, double total, double reserve, String period, int from, int to) {
        this.description = description;
        this.total = total;
        this.reserve = reserve;
        this.period = period;
        this.from = from;
        this.to = to;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getReserve() {
        return reserve;
    }

    public void setReserve(double reserve) {
        this.reserve = reserve;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }
}
