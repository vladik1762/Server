package com.company;

import db.ConstUser;
import db.DataBaseHandler;
import javafx.beans.property.SimpleStringProperty;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User {

    private SimpleStringProperty firstName;
    private SimpleStringProperty userName;
    private SimpleStringProperty password;
    private SimpleStringProperty email;
    private SimpleStringProperty role;
    private int accounts[];

    public User() {
    }



    public String getFirstName() {
        return firstName.get();
    }



    public void setFirstName(String firstName) {
        this.firstName = new SimpleStringProperty(firstName);
    }

    public String getUserName() {
        return userName.get();
    }



    public void setUserName(String userName) {
        this.userName = new SimpleStringProperty(userName);
    }

    public String getPassword() {
        return password.get();
    }

    public void setRole(String role) {
        this.role = new SimpleStringProperty(role);
    }

    public String getRole() {
        return role.get();
    }


    public void setPassword(String password) {
        this.password = new SimpleStringProperty(password);
    }

    public String getEmail() {
        return email.get();
    }



    public void setEmail(String email) {
        this.email = new SimpleStringProperty(email);
    }




    public boolean isUserName(String userName){
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + ConstUser.USERS_TABLE +
                " WHERE " + ConstUser.USERS_USERMAME + " =? ";

        int counter = 0;
        try {
            PreparedStatement preparedStatement = dataBaseHandler.getConnection().prepareStatement(select);
            preparedStatement.setString(1, userName);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                counter++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }

        if (counter == 0)
        {

            return true;
        }
        else{
            return false;
        }
    }

    //    public void insertUpdateDeleteUser(char operation, Integer id, User user ) {
//        DataBaseHandler dataBaseHandler = new DataBaseHandler();
//        String insert = "INSERT INTO " + ConstUser.USERS_TABLE + "(" +
//                ConstUser.USERS_FIRSTNAME + "," + ConstUser.USERS_SECONDNAME + "," +
//                ConstUser.USERS_NUMBERPHONE + "," + ConstUser.USERS_USERMAME + "," +
//                ConstUser.USERS_PASSWORD + "," + ConstUser.USERS_GENDER + ")" +
//                "VALUES(?,?,?,?,?,?)";
//
//        if (operation == 'i')   //i for insert
//            try {
//                PreparedStatement preparedStatement = dataBaseHandler.getConnection().prepareStatement(insert);
//                preparedStatement.setString(1, user.getFirstName());
//                preparedStatement.setString(2, user.getSecondName());
//                preparedStatement.setString(3, user.getNumberPhone());
//                preparedStatement.setString(4, user.getUserName());
//                preparedStatement.setString(5, user.getPassword());
//                preparedStatement.setString(6, user.getGender());
//
//                if (preparedStatement.executeUpdate() < 0) {
//                    JOptionPane.showMessageDialog(null, "Поздравляем! Вы зарегистрированы.");
//
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
//                e.printStackTrace();
//            }
//
//
//    }
//
//
//
//


}


