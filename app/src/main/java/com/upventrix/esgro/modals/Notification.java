package com.upventrix.esgro.modals;

public class Notification {
    private String status;
    private String n1;
    private String n2;

    public Notification() {
    }

    public Notification(String status, String n1, String n2) {
        this.status = status;
        this.n1 = n1;
        this.n2 = n2;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getN1() {
        return n1;
    }

    public void setN1(String n1) {
        this.n1 = n1;
    }

    public String getN2() {
        return n2;
    }

    public void setN2(String n2) {
        this.n2 = n2;
    }
}
