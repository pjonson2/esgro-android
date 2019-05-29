package com.upventrix.esgro.modals;

import com.google.gson.annotations.SerializedName;

public class Deal {

    @SerializedName("deal_id")
    private int deal_id;

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

    @SerializedName("type")
    private String type;

    @SerializedName("no_days")
    private int no_days;



    public Deal() {
    }

    public Deal(int deal_id, String type) {
        this.deal_id = deal_id;
        this.type = type;
    }

    public Deal(int deal_id, int no_days) {
        this.deal_id = deal_id;
        this.no_days = no_days;
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
