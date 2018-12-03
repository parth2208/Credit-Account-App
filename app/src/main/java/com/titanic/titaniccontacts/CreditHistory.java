package com.titanic.titaniccontacts;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "credit_history")
public class CreditHistory {

    @PrimaryKey
    @NonNull
    private String mobile;

    private int amount;

    private String dateStamp;


    public String getMobile() {
        return mobile;
    }

    public void setMobile( String mobile) {
        this.mobile = mobile;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp(String dateStamp) {
        this.dateStamp = dateStamp;
    }
}
