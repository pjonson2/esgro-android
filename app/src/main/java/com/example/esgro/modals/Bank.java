package com.example.esgro.modals;

public class Bank {
    private String bankName;
    private int image;

    public Bank(String bankName, int image) {
        this.bankName = bankName;
        this.image = image;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
