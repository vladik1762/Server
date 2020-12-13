package com.company;

import db.DataBaseHandler;

public class Debt extends Operation implements CountMany{
    private String destination;
    private String kindOfDebt;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getKindOfDebt() {
        return kindOfDebt;
    }

    public void setKindOfDebt(String kindOfDebt) {
        this.kindOfDebt = kindOfDebt;
    }

    @Override
    public String getType() {
        return "Долг";
    }

    @Override
    public String countMany() {
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        double temp = 0;

        Account account = dataBaseHandler.getAccountID(getAccount(),getUser_id());
        if(kindOfDebt.equals("я дал долг/ я вернул долг")){

            temp = Double.valueOf(account.getCurrencyBalance()) - Double.valueOf(getSum());
            account.setCurrencyBalance(String.valueOf(temp));

        }
        else{

            temp = Double.valueOf(account.getCurrencyBalance()) + Double.valueOf(getSum());
            account.setCurrencyBalance(String.valueOf(temp));
        }
        dataBaseHandler.updateAccount(account.getId(),account.getCurrencyBalance());
        return null;
    }
}
