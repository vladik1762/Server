package com.company;

import java.util.Date;

public abstract class Operation {
    private String name;
    private String id;
    private String sum;
    private String date;
    private String user_id;
    private String type;

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    private String account;

    public void setDate(String date) {
        this.date = date;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public abstract String getType();

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
