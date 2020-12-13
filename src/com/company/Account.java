package com.company;

public class Account {

    private String id;
    private String currencyBalance;
    private String kindOfMoney;
    private String kindOfAccount;
    private String name;
    private String user_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String uniqueNumber;

    public String getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(String uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCurrencyBalance() {
        return currencyBalance;
    }

    public void setCurrencyBalance(String currencyBalance) {
        this.currencyBalance = currencyBalance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKindOfMoney() {
        return kindOfMoney;
    }

    public void setKindOfMoney(String kindOfMoney) {
        this.kindOfMoney = kindOfMoney;
    }

    public String getKindOfAccount() {
        return kindOfAccount;
    }

    public void setKindOfAccount(String kindOfAccount) {
        this.kindOfAccount = kindOfAccount;
    }
}
