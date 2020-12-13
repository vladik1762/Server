package com.company;

import db.DataBaseHandler;

public class Income extends Operation implements CountMany{
    private String payer;

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    @Override
    public String getType() {
        return "Доход";
    }

    @Override
    public String countMany() {
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        double temp = 0;

        Account account = dataBaseHandler.getAccountID(getAccount(),getUser_id());

        temp = Double.valueOf(account.getCurrencyBalance()) + Double.valueOf(getSum());
        account.setCurrencyBalance(String.valueOf(temp));
        dataBaseHandler.updateAccount(account.getId(),account.getCurrencyBalance());
        return null;
    }
}
