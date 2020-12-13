package com.company;

import db.DataBaseHandler;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {
	// write your code here




        try {
//            File myFile = new File("D:\\курсовая 5 сем\\Server\\src\\file.txt");
//            Desktop.getDesktop().open(myFile);


            DataBaseHandler dataBaseHandler = new DataBaseHandler();
            Connection connection = dataBaseHandler.getConnection();
            Statement statement = connection.createStatement();


            statement.execute("CREATE TABLE IF NOT EXISTS Users(id int NOT NULL auto_increment , firstName varchar (30) null, " +
                    "userName varchar(40) not null, password varchar(20) not null, email varchar(20) null, role varchar(20) not null, PRIMARY KEY(id))");




            statement.execute("CREATE TABLE IF NOT EXISTS accounts(id int NOT NULL auto_increment ,user_id varchar(5), nameofaccount varchar (30) not null, " +
                    "kindofaccount varchar(20) not null, kindofmoney varchar(20) not null, balance varchar(20) not null, uniqueNumber varchar(10),  PRIMARY KEY(id))");

            statement.execute("CREATE TABLE IF NOT EXISTS debts(id int NOT NULL auto_increment ,nameOfOperation varchar(40) not null, sumOfOperation varchar (20) not null, " +
                    "dateOfOperarion varchar(20) not null, kindofDebt varchar(40) not null, destination varchar(20) null, account_id varchar(20) not null,user_id varchar(20) not null, PRIMARY KEY(id))");

            statement.execute("CREATE TABLE IF NOT EXISTS transfers(id int NOT NULL auto_increment ,nameOfOperation varchar(40) not null, sumOfOperation varchar (20) not null, " +
                    "dateOfOperarion varchar(20) not null,  accountFrom_id varchar(20) not null, accountTo_id varchar(20) not null, user_id varchar(20) not null, PRIMARY KEY(id))");

            statement.execute("CREATE TABLE IF NOT EXISTS incomes(id int NOT NULL auto_increment ,nameOfOperation varchar(40) not null, sumOfOperation varchar (20) not null, " +
                    "dateOfOperarion varchar(20) not null, payer varchar(40) not null, account_id varchar(20) not null,user_id varchar(20) not null, PRIMARY KEY(id))");

            statement.execute("CREATE TABLE IF NOT EXISTS consumptions(id int NOT NULL auto_increment ,nameOfOperation varchar(40) not null, sumOfOperation varchar (20) not null, " +
                    "dateOfOperarion varchar(20) not null, category varchar(40) not null, account_id varchar(20) not null,user_id varchar(20) not null, PRIMARY KEY(id))");

            Server.Server.connectServer();
        } catch (IOException | SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
        // write your code here

    }
}
