package com.company;


import db.DataBaseHandler;

public class Consumption extends Operation implements CountMany {
    private String category;


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String getType() {
        return "Расход";
    }

    @Override
    public String countMany() {
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        double temp = 0;

        Account account = dataBaseHandler.getAccountID(getAccount(),getUser_id());

        temp = Double.valueOf(account.getCurrencyBalance()) - Double.valueOf(getSum());
        account.setCurrencyBalance(String.valueOf(temp));
        dataBaseHandler.updateAccount(account.getId(),account.getCurrencyBalance());
        return null;

    }
}
