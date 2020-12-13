package db;

import com.company.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class DataBaseHandler extends Configs {

    Connection connection;

    public Connection getConnection() throws SQLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        String connectionString = "jdbc:mysql://" + dbHost + ":"
                + dbPort +"/" + dbName;
        Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();


        connection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return connection;
    }



    public String saveUser(com.company.User user)  {

        String insert = "INSERT INTO " + ConstUser.USERS_TABLE + "("+
                ConstUser.USERS_FIRSTNAME +"," + ConstUser.USERS_EMAIL +"," + ConstUser.USERS_USERMAME + "," +
                ConstUser.USERS_PASSWORD +", role "+")"+
                "VALUES(?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(insert);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getRole());
            if(preparedStatement.executeUpdate() > 0){

                    return "Успешно";
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }

        return "НеУспешно";

    }

    public ResultSet getUser(User user) {
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + ConstUser.USERS_TABLE +
                " WHERE " + ConstUser.USERS_USERMAME + " =? AND " +
                ConstUser.USERS_PASSWORD + " =? ";

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(select);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());

            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }

        return resultSet;
    }



    public ResultSet getUserEmail(User user) {
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + "Username_Email" +
                " WHERE " + "email" + " =? AND " +
                "password" + " =? ";

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(select);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());

            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public String saveAccount(com.company.Account account)  {

        String insert = "INSERT INTO " + ConstAccount.ACCOUNT_TABLE + "("+
                ConstAccount.ACCOUNT_NAME +"," + ConstAccount.ACCOUNT_KINDOFACCOUNT +"," + ConstAccount.ACCOUNT_KINDOFMONEY + "," +
                ConstAccount.ACCOUNT_BALANCE +","+ConstAccount.ACCOUNT_UERSID+","+ConstAccount.ACCOUNT_UNIQUENUMBER+")"+
                "VALUES(?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(insert);
            preparedStatement.setString(1, account.getName());
            preparedStatement.setString(2, account.getKindOfAccount());
            preparedStatement.setString(3, account.getKindOfMoney());
            preparedStatement.setString(4, account.getCurrencyBalance());
            preparedStatement.setString(5, account.getUser_id());
            preparedStatement.setString(6, account.getUniqueNumber());
            if(preparedStatement.executeUpdate() > 0){

                return "Успешно";
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }

        return "НеУспешно";

    }



    public void updateAccount(String id, String sum) {

        String update = "UPDATE " + ConstAccount.ACCOUNT_TABLE + " SET " + ConstAccount.ACCOUNT_BALANCE + " = ?"
                + " WHERE " + ConstAccount.ACCOUNT_ID + " = ?";


        DataBaseHandler dataBaseHandler = new DataBaseHandler();

        try {
            PreparedStatement preparedStatement = dataBaseHandler.getConnection().prepareStatement(update);
            preparedStatement.setString(1,sum);
            preparedStatement.setString(2,id);

            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }


    public void updateAccount(String uniqueId, String sum, String user_id, String name) {

        String update = "UPDATE " + ConstAccount.ACCOUNT_TABLE + " SET " + ConstAccount.ACCOUNT_BALANCE + " = ? ,"+
                ConstAccount.ACCOUNT_NAME + " =?"
                + " WHERE " + ConstAccount.ACCOUNT_UNIQUENUMBER + " = ? AND "+ ConstAccount.ACCOUNT_UERSID + " =?";


        DataBaseHandler dataBaseHandler = new DataBaseHandler();

        try {
            PreparedStatement preparedStatement = dataBaseHandler.getConnection().prepareStatement(update);
            preparedStatement.setString(1,sum);
            preparedStatement.setString(2,name);
            preparedStatement.setString(3,uniqueId);
            preparedStatement.setString(4,user_id);


            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public String saveDebt(com.company.Debt debt)  {

        String insert = "INSERT INTO " + ConstDebt.DEBT_TABLE + "("+
                ConstDebt.DEBT_NAME +"," + ConstDebt.DEBT_KIND +"," + ConstDebt.DEBT_SUM + "," +
                ConstDebt.DEBT_DESTINATION + "," +ConstDebt.DEBT_DATE +"," + ConstDebt.DEBT_ACCOUNTID +"," + ConstDebt.DEBT_USERID +")"+
                "VALUES(?,?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(insert);
            preparedStatement.setString(1, debt.getName());
            preparedStatement.setString(2, debt.getKindOfDebt());
            preparedStatement.setString(3, debt.getSum());
            preparedStatement.setString(4, debt.getDestination());
            preparedStatement.setString(5, debt.getDate());
            preparedStatement.setString(6, debt.getAccount());
            preparedStatement.setString(7, debt.getUser_id());

            if(preparedStatement.executeUpdate() > 0){

                return "Успешно";
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }

        return "НеУспешно";

    }


    public void removeDebt(String id) {
        String update = "DELETE FROM " + ConstDebt.DEBT_TABLE
                + " WHERE " + ConstDebt.DEBT_ID + " = ?";
        DataBaseHandler dataBaseHandler = new DataBaseHandler();

        try {
            PreparedStatement preparedStatement = dataBaseHandler.getConnection().prepareStatement(update);
            preparedStatement.setString(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public void removeTransfer(String id) {
        String update = "DELETE FROM " + ConstTransfer.TRANSFER_TABLE
                + " WHERE " + ConstTransfer.TRANSFER_ID + " =?";
        DataBaseHandler dataBaseHandler = new DataBaseHandler();

        try {
            PreparedStatement preparedStatement = dataBaseHandler.getConnection().prepareStatement(update);
            preparedStatement.setString(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public Account getAccountID(String id, String user_id){
        DataBaseHandler dataBaseHandler = new DataBaseHandler();

        String select = "SELECT * FROM " + ConstAccount.ACCOUNT_TABLE + " WHERE " + ConstAccount.ACCOUNT_UNIQUENUMBER + " =? AND "
                +ConstAccount.ACCOUNT_UERSID + " =?";

        Account account = new Account();
        PreparedStatement preparedStatement;


        try {
            preparedStatement = dataBaseHandler.getConnection().prepareStatement(select);


            preparedStatement.setString(1,id);
            preparedStatement.setString(2,user_id);


        ResultSet result = preparedStatement.executeQuery();

            while(result.next()){


                account.setId(result.getString(1));
                account.setUser_id(result.getString(2));
                account.setName(result.getString(3));
                account.setKindOfAccount(result.getString(4));
                account.setKindOfMoney(result.getString(5));
                account.setCurrencyBalance(result.getString(6));
                account.setUniqueNumber(result.getString(7));

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }



        return account;
    }

    public String saveTransfer(Transfer transfer)  {

        String insert = "INSERT INTO " + ConstTransfer.TRANSFER_TABLE + "("+
                ConstTransfer.TRANSFER_NAME +"," + ConstTransfer.TRANSFER_SUM + ","
                +ConstTransfer.TRANSFER_DATE +"," + ConstTransfer.TRANSFER_ACCOUNTFROMID+"," + ConstTransfer.TRANSFER_ACCOUNTTOID +"," + ConstDebt.DEBT_USERID +")"+
                "VALUES(?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(insert);
            preparedStatement.setString(1, transfer.getName());
            preparedStatement.setString(2, transfer.getSum());
            preparedStatement.setString(3, transfer.getDate());
            preparedStatement.setString(4, transfer.getAccount());
            preparedStatement.setString(5, transfer.getId_accountTo());
            preparedStatement.setString(6, transfer.getUser_id());

            if(preparedStatement.executeUpdate() > 0){

                return "Успешно";
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }

        return "НеУспешно";

    }

    public String saveIncome(Income income)  {

        String insert = "INSERT INTO " + ConstIncome.INCOME_TABLE + "("+
                ConstIncome.INCOME_NAME +"," + ConstIncome.INCOME_PAYER +"," + ConstIncome.INCOME_SUM+ "," +
                 ConstIncome.INCOME_DATE +"," + ConstIncome.INCOME_ACCOUNTID +"," + ConstIncome.INCOME_USERID +")"+
                "VALUES(?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(insert);
            preparedStatement.setString(1, income.getName());
            preparedStatement.setString(2, income.getPayer());
            preparedStatement.setString(3, income.getSum());
            preparedStatement.setString(4, income.getDate());
            preparedStatement.setString(5, income.getAccount());
            preparedStatement.setString(6, income.getUser_id());

            if(preparedStatement.executeUpdate() > 0){

                return "Успешно";
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }

        return "НеУспешно";

    }

    public void removeIncome(String id) {
        String update = "DELETE FROM " + ConstIncome.INCOME_TABLE
                + " WHERE " + ConstIncome.INCOME_ID + " = ?";
        DataBaseHandler dataBaseHandler = new DataBaseHandler();

        try {
            PreparedStatement preparedStatement = dataBaseHandler.getConnection().prepareStatement(update);
            preparedStatement.setString(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }


    public String saveConsumption(Consumption consumption)  {

        String insert = "INSERT INTO " + ConstConsumption.Consumption_TABLE + "("+
                ConstConsumption.Consumption_NAME +"," + ConstConsumption.Consumption_CATEGORY +"," + ConstConsumption.Consumption_SUM+ "," +
                ConstConsumption.Consumption_DATE +"," + ConstConsumption.Consumption_ACCOUNTID +"," + ConstConsumption.Consumption_USERID +")"+
                "VALUES(?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(insert);
            preparedStatement.setString(1, consumption.getName());
            preparedStatement.setString(2, consumption.getCategory());
            preparedStatement.setString(3, consumption.getSum());
            preparedStatement.setString(4, consumption.getDate());
            preparedStatement.setString(5, consumption.getAccount());
            preparedStatement.setString(6, consumption.getUser_id());

            if(preparedStatement.executeUpdate() > 0){

                return "Успешно";
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }

        return "НеУспешно";

    }


    public void removeConsumption(String id) {
        String update = "DELETE FROM " + ConstConsumption.Consumption_TABLE
                + " WHERE " + ConstConsumption.Consumption_ID + " = ?";
        DataBaseHandler dataBaseHandler = new DataBaseHandler();

        try {
            PreparedStatement preparedStatement = dataBaseHandler.getConnection().prepareStatement(update);
            preparedStatement.setString(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }
    public void removeUser(String id) {
        String update = "DELETE FROM " + ConstUser.USERS_TABLE
                + " WHERE " + ConstUser.USERS_EMAIL + " = ?";
        DataBaseHandler dataBaseHandler = new DataBaseHandler();

        try {
            PreparedStatement preparedStatement = dataBaseHandler.getConnection().prepareStatement(update);
            preparedStatement.setString(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}


