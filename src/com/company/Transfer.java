package com.company;

import db.DataBaseHandler;

public class Transfer extends Operation  implements CountMany{
    private String id_accountTo;

    public String getId_accountTo() {
        return id_accountTo;
    }

    public void setId_accountTo(String id_accountTo) {
        this.id_accountTo = id_accountTo;
    }

    @Override
    public String getType() {
        return "Перевод";
    }

    @Override
    public String countMany() {

        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        if(getAccount().equals(id_accountTo)){
            Account accountFrom = dataBaseHandler.getAccountID(getAccount(),getUser_id());
            double temp = Double.valueOf(accountFrom.getCurrencyBalance()) + Double.valueOf(getSum());
            accountFrom.setCurrencyBalance(String.valueOf(temp));
            dataBaseHandler.updateAccount(accountFrom.getId(), accountFrom.getCurrencyBalance());
        }

        else{

        Account accountFrom = dataBaseHandler.getAccountID(getAccount(),getUser_id());
        Account accountTo = dataBaseHandler.getAccountID(id_accountTo,getUser_id());



        double temp = Double.valueOf(accountFrom.getCurrencyBalance()) - Double.valueOf(getSum());

        accountFrom.setCurrencyBalance(String.valueOf(temp));
        temp = Double.valueOf(accountTo.getCurrencyBalance()) + Double.valueOf(getSum());
        accountTo.setCurrencyBalance(String.valueOf(temp));
        dataBaseHandler.updateAccount(accountTo.getId(),  accountTo.getCurrencyBalance());
        dataBaseHandler.updateAccount(accountFrom.getId(),accountFrom.getCurrencyBalance());
        }
        return "Успешно";

    }
}
