package Server;



import com.company.*;
import db.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


class ServerSomthing extends Thread {

    private Socket socket;              // сокет, через который сервер общается с клиентом,
                                        // кроме него - клиент и сервер никак не связаны
    private BufferedReader in;          // поток чтения из сокета
    private BufferedWriter out;
    private String userName,role;
    // поток завписи в сокет
static int count=0;

private static Sender tlsSender = new Sender("ivanosivanos2001@gmail.com", "7432735aa");

    public ServerSomthing(Socket socket) throws IOException {
        this.socket = socket;



                                            // если потоку ввода/вывода приведут к генерированию искдючения, оно проброситься дальше
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                // поток вывода передаётся для передачи истории последних 10
                                             // сооюбщений новому поключению
        start();                             // вызываем run()
    }


    @Override
    public void run() {

        String target;

        try {

                while (true) {
                    target = in.readLine();

                    if(target.equals("Выйти")) {


                        break; // если пришла пустая строка - выходим из цикла прослушки
                    }

                    else {
                        menu(target);
                    }

                }

        } catch (IOException | NullPointerException  e) {
            this.downService();
        }
    }




    /**
     * отсылка одного сообщения клиенту по указанному потоку
     * @param msg
     */

    /**
     * закрытие сервера
     * прерывание себя как нити и удаление из списка нитей
     */
    private void downService() {
        try {
            if(!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
                for (ServerSomthing vr : Server.serverList) {
                    if(vr.equals(this)) vr.interrupt();
                    Server.serverList.remove(this);

                }
            }
        } catch (IOException ignored) {}
    }



    public void menu(String target){

        switch (target){

            case "Авторизация": {
                DataBaseHandler dataBaseHandler = new DataBaseHandler();
                try {
                    System.out.println("Выбрана функция  - " +"\" "+ target+" \"");
                    User user = new User();


                    String login;
                    String password;
                    login = in.readLine();
                    password = in.readLine();




                    if (signInUser(login, password) != 0) {

                        out.write(role +"\n");
                        out.flush();
                        out.write("Успешно\n");
                        out.flush();



                    }

                    else {
                        out.write(" "+"\n");
                        out.flush();
                        out.write("НеУспешно\n");
                        out.flush();


                    }
                    out.write("Конец\n");
                    out.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                }


                break;
            }

            case "Регистрация": {

                DataBaseHandler dataBaseHandler = new DataBaseHandler();
                User user = new User();

                String firstName;
                String email;
                String userName;
                String password;
                String role;


                try {
                    System.out.println("Выбрана функция  - " +"\" "+ target+" \"");

                    firstName = in.readLine();
                    userName = in.readLine();
                    password = in.readLine();
                    email = in.readLine();
                    role = in.readLine();


                    user.setFirstName(firstName);
                    user.setEmail(email);
                    user.setUserName(userName);
                    user.setPassword(password);
                    user.setRole(role);

                    String str = dataBaseHandler.saveUser(user);

                    if(str.equals("Успешно")){
                        out.write("Успешно\n");
                        out.flush();

                    }
                    else
                    {
                        out.write("НеУспешно\n");
                        out.flush();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }


                break;
            }

            case "УникальностьЛогина": {

                User user = new User();
                String userName;


                try {
                    userName = in.readLine();

                    if (!user.isUserName(userName)) {
                        out.write("Совпадение\n");
                        out.flush();
                    } else {
                        out.write("НеСовпал\n");
                        out.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


                break;
            }

            case "ДобавитьАккаунт": {

                DataBaseHandler dataBaseHandler = new DataBaseHandler();
                Account acc = new Account();

                String name;
                String typeOfAcc;
                String typeOfMoney;
                String balance;
                String user_id;
                String uniqueNumber;


                try {
                    System.out.println("Выбрана функция  - " +"\" "+ target+" \"");
                    user_id = in.readLine();
                    name = in.readLine();
                    typeOfAcc = in.readLine();
                    typeOfMoney = in.readLine();
                    balance = in.readLine();
                    uniqueNumber = in.readLine();


                    acc.setUser_id(user_id);
                    acc.setName(name);
                    acc.setCurrencyBalance(balance);
                    acc.setKindOfAccount(typeOfAcc);
                    acc.setKindOfMoney(typeOfMoney);
                    acc.setUniqueNumber(uniqueNumber);

                    String str = dataBaseHandler.saveAccount(acc);

                    if(str.equals("Успешно")){
                        out.write("Успешно\n");
                        out.flush();

                    }
                    else
                    {
                        out.write("НеУспешно\n");
                        out.flush();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }


                break;
            }

            case "ОбновитьАккаунт": {

                DataBaseHandler dataBaseHandler = new DataBaseHandler();
                Account acc = new Account();

                String user_id;
                String uniqueNumber;
                String name;
                String balance;



                try {
                    System.out.println("Выбрана функция  - " +"\" "+ target+" \"");
                    user_id = in.readLine();

                    uniqueNumber = in.readLine();
                    name = in.readLine();
                    balance = in.readLine();

                    acc.setUser_id(user_id);
                    acc.setName(name);
                    acc.setCurrencyBalance(balance);

                    acc.setUniqueNumber(uniqueNumber);

                    dataBaseHandler.updateAccount(uniqueNumber,balance,user_id,name);




                } catch (IOException e) {
                    e.printStackTrace();
                }


                break;
            }

            case "ДанныеАккаунтов":{
                try {
                    String user_id = in.readLine();
                    getAccountsFromBD(user_id);
                    out.write("Конец\n");
                    out.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            }

            case "ДобавитьДолг":{
                DataBaseHandler dataBaseHandler = new DataBaseHandler();

                Debt debt = new Debt();


                try {
                    System.out.println("Выбрана функция  - " +"\" "+ target+" \"");

                    debt.setName(in.readLine());
                    debt.setKindOfDebt(in.readLine());
                    debt.setAccount(in.readLine());
                    debt.setSum(in.readLine());
                    debt.setDestination(in.readLine());
                    debt.setDate(in.readLine());

                    debt.setUser_id(in.readLine());

                    debt.countMany();





                    String str = dataBaseHandler.saveDebt(debt);

                    if(str.equals("Успешно")){
                        out.write("Успешно\n");
                        out.flush();

                    }
                    else
                    {
                        out.write("НеУспешно\n");
                        out.flush();
                    }



                } catch (IOException e) {
                    e.printStackTrace();
                }


                break;

            }

            case "ДанныеДолг":{
                try {
                    String user_id = in.readLine();
                    getDebtFromBD(user_id);
                    out.write("Конец\n");
                    out.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            }


            case "ДобавитьПеревод":{
                DataBaseHandler dataBaseHandler = new DataBaseHandler();

                Transfer transfer = new Transfer();


                try {
                    System.out.println("Выбрана функция  - " +"\" "+ target+" \"");

                    transfer.setName(in.readLine());
                    transfer.setAccount(in.readLine());
                    transfer.setId_accountTo(in.readLine());
                    transfer.setSum(in.readLine());
                    transfer.setDate(in.readLine());

                    transfer.setUser_id(in.readLine());



                    String str = dataBaseHandler.saveTransfer(transfer);

                    if(str.equals("Успешно")){
                        out.write("Успешно\n");
                        out.flush();



                    }
                    else
                    {
                        out.write("НеУспешно\n");
                        out.flush();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }


                break;

            }

            case "ПодсчётПеревод":{
                DataBaseHandler dataBaseHandler = new DataBaseHandler();

                Transfer transfer = new Transfer();
                String temp;


                try {
                    System.out.println("Выбрана функция  - " +"\" "+ target+" \"");

                    transfer.setAccount(in.readLine());
                    transfer.setId_accountTo(in.readLine());
                    transfer.setSum(in.readLine());
                    transfer.setUser_id(in.readLine());


                    String str = transfer.countMany();

                    if(str.equals("Успешно")){
                        out.write("Успешно\n");
                        out.flush();



                    }
                    else
                    {
                        out.write("НеУспешно\n");
                        out.flush();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }


                break;

            }

            case "ДанныеПеревод":{
                try {
                    String user_id = in.readLine();
                    getTransferFromBD(user_id);
                    out.write("Конец\n");
                    out.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            }

            case "УдалитьИнформациюизТаблицы":{
                DataBaseHandler dataBaseHandler = new DataBaseHandler();
                String id, type;

                try {

                    type = in.readLine();
                    id = in.readLine();
                    switch (type){
                        case "Долг":
                            dataBaseHandler.removeDebt(id);
                            break;
                        case "Перевод":
                            dataBaseHandler.removeTransfer(id);
                            break;
                        case "Доход":
                            dataBaseHandler.removeIncome(id);
                            break;
                        case "Расход":
                            dataBaseHandler.removeConsumption(id);
                            break;
                        case "Пользователь":
                            dataBaseHandler.removeUser(id);
                            break;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }


            case "ДобавитьДоход":{
                DataBaseHandler dataBaseHandler = new DataBaseHandler();

                Income income = new Income();


                try {
                    System.out.println("Выбрана функция  - " +"\" "+ target+" \"");

                    income.setName(in.readLine());
                    income.setPayer(in.readLine());
                    income.setAccount(in.readLine());
                    income.setSum(in.readLine());

                    income.setDate(in.readLine());

                    income.setUser_id(in.readLine());

                    income.countMany();

                    String str = dataBaseHandler.saveIncome(income);

                    if(str.equals("Успешно")){
                        out.write("Успешно\n");
                        out.flush();

                    }
                    else
                    {
                        out.write("НеУспешно\n");
                        out.flush();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }


                break;

            }

            case "ДанныеДоход":{
                try {
                    String user_id = in.readLine();
                    getIncomeFromBD(user_id);
                    out.write("Конец\n");
                    out.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            }

            case "ДобавитьРасход":{
                DataBaseHandler dataBaseHandler = new DataBaseHandler();

                Consumption consumption = new Consumption();

                try {
                    System.out.println("Выбрана функция  - " +"\" "+ target+" \"");

                    consumption.setName(in.readLine());
                    consumption.setCategory(in.readLine());
                    consumption.setAccount(in.readLine());
                    consumption.setSum(in.readLine());

                    consumption.setDate(in.readLine());

                    consumption.setUser_id(in.readLine());

                    consumption.countMany();

                    String str = dataBaseHandler.saveConsumption(consumption);

                    if(str.equals("Успешно")){
                        out.write("Успешно\n");
                        out.flush();

                    }
                    else
                    {
                        out.write("НеУспешно\n");
                        out.flush();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }


                break;

            }

            case "ДанныеРасход":{
                try {
                    String user_id = in.readLine();
                    getConsumptionFromBD(user_id);
                    out.write("Конец\n");
                    out.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            }

            case "ДобавитьДоступ": {

                DataBaseHandler dataBaseHandler = new DataBaseHandler();
                User user = new User();


                String email;
                String userName;
                String password;
                String role;
                String firstName;

                try {
                    System.out.println("Выбрана функция  - " +"\" "+ target+" \"");


                    userName = in.readLine();
                    password = userName+"123";
                    email = in.readLine();
                    role = in.readLine();
                    firstName="";

                    user.setFirstName(firstName);
                    user.setEmail(email);
                    user.setUserName(userName);
                    user.setPassword(password);
                    user.setRole(role);

                    String str = dataBaseHandler.saveUser(user);
                    String text = "Ваш логин: " + userName + "\n" + "Ваш пароль: " + password +"\n\n" +"Хорошего дня!" ;
                   tlsSender.send("Приглашение в приложение ДзенМани", text, "ivanosivanos2001@gmail.com", email);

                    if(str.equals("Успешно")){
                        out.write("Успешно\n");
                        out.flush();

                    }
                    else
                    {
                        out.write("НеУспешно\n");
                        out.flush();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }


                break;
            }

            case "ДанныеПользователь":{
                try {
                    String user_id = in.readLine();
                    getUserFromBD(user_id);
                    out.write("Конец\n");
                    out.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            }

        }
    }


    public void getAccountsFromBD(String user_id){
        DataBaseHandler dataBaseHandler = new DataBaseHandler();

        String select = "SELECT * FROM " + ConstAccount.ACCOUNT_TABLE + " WHERE " + ConstAccount.ACCOUNT_UERSID + " = ?";

        PreparedStatement preparedStatement;
        try{

            preparedStatement = dataBaseHandler.getConnection().prepareStatement(select);
            preparedStatement.setString(1,user_id);


            ResultSet result = preparedStatement.executeQuery();



            while(result.next()){

                out.write(result.getString(1) + "\n");
                out.flush();


                out.write(result.getString(2) + "\n");
                out.flush();

                out.write(result.getString(3) + "\n");
                out.flush();
                out.write(result.getString(4)+ "\n");
                out.flush();

                out.write(result.getString(5) + "\n");
                out.flush();

                out.write(result.getString(6) + "\n");
                out.flush();
                out.write(result.getString(7) + "\n");
                out.flush();


            }


        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException | NoSuchMethodException | SQLException | InvocationTargetException | IOException e) {
            e.printStackTrace();

        }

    }

    public void getDebtFromBD(String user_id){
        DataBaseHandler dataBaseHandler = new DataBaseHandler();

        String select = "SELECT * FROM " + ConstDebt.DEBT_TABLE + " WHERE " + ConstDebt.DEBT_USERID + " = ?";

        PreparedStatement preparedStatement;
        try{

            preparedStatement = dataBaseHandler.getConnection().prepareStatement(select);
            preparedStatement.setString(1,user_id);


            ResultSet result = preparedStatement.executeQuery();



            while(result.next()){


                out.write(result.getString(1) + "\n");
                out.flush();


                out.write(result.getString(2) + "\n");
                out.flush();

                out.write(result.getString(3) + "\n");
                out.flush();
                out.write(result.getString(4)+ "\n");
                out.flush();

                out.write(result.getString(5) + "\n");
                out.flush();

                out.write(result.getString(6) + "\n");
                out.flush();

                out.write(result.getString(7) + "\n");
                out.flush();


            }


        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException | NoSuchMethodException | SQLException | InvocationTargetException | IOException e) {
            e.printStackTrace();

        }

    }

    public void getTransferFromBD(String user_id){
        DataBaseHandler dataBaseHandler = new DataBaseHandler();

        String select = "SELECT * FROM " + ConstTransfer.TRANSFER_TABLE + " WHERE " + ConstTransfer.TRANSFER_USERID + " = ?";

        PreparedStatement preparedStatement;
        try{

            preparedStatement = dataBaseHandler.getConnection().prepareStatement(select);
            preparedStatement.setString(1,user_id);

            ResultSet result = preparedStatement.executeQuery();

            while(result.next()){


                out.write(result.getString(1) + "\n");
                out.flush();

                out.write(result.getString(2) + "\n");
                out.flush();

                out.write(result.getString(3) + "\n");
                out.flush();
                out.write(result.getString(4)+ "\n");
                out.flush();

                out.write(result.getString(5) + "\n");
                out.flush();

                out.write(result.getString(6) + "\n");
                out.flush();




            }


        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException | NoSuchMethodException | SQLException | InvocationTargetException | IOException e) {
            e.printStackTrace();

        }

    }

    public void getIncomeFromBD(String user_id){
        DataBaseHandler dataBaseHandler = new DataBaseHandler();

        String select = "SELECT * FROM " + ConstIncome.INCOME_TABLE + " WHERE " + ConstIncome.INCOME_USERID + " = ?";

        PreparedStatement preparedStatement;
        try{

            preparedStatement = dataBaseHandler.getConnection().prepareStatement(select);
            preparedStatement.setString(1,user_id);


            ResultSet result = preparedStatement.executeQuery();



            while(result.next()){


                out.write(result.getString(1) + "\n");
                out.flush();


                out.write(result.getString(2) + "\n");
                out.flush();

                out.write(result.getString(3) + "\n");
                out.flush();
                out.write(result.getString(4)+ "\n");
                out.flush();

                out.write(result.getString(5) + "\n");
                out.flush();

                out.write(result.getString(6) + "\n");
                out.flush();




            }


        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException | NoSuchMethodException | SQLException | InvocationTargetException | IOException e) {
            e.printStackTrace();

        }

    }

    public void getConsumptionFromBD(String user_id){
        DataBaseHandler dataBaseHandler = new DataBaseHandler();

        String select = "SELECT * FROM " + ConstConsumption.Consumption_TABLE + " WHERE " + ConstIncome.INCOME_USERID + " = ?";

        PreparedStatement preparedStatement;
        try{

            preparedStatement = dataBaseHandler.getConnection().prepareStatement(select);
            preparedStatement.setString(1,user_id);


            ResultSet result = preparedStatement.executeQuery();



            while(result.next()){


                out.write(result.getString(1) + "\n");
                out.flush();


                out.write(result.getString(2) + "\n");
                out.flush();

                out.write(result.getString(3) + "\n");
                out.flush();
                out.write(result.getString(4)+ "\n");
                out.flush();

                out.write(result.getString(5) + "\n");
                out.flush();

                out.write(result.getString(6) + "\n");
                out.flush();




            }


        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException | NoSuchMethodException | SQLException | InvocationTargetException | IOException e) {
            e.printStackTrace();

        }

    }


    public int signInUser(String userName, String password) {
        int counter = 0;
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);

        try{

                ResultSet result = dataBaseHandler.getUser(user);

                while(result.next()){
                    counter++;
                    role = result.getString(6);
                }



        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return counter;


    }

    public void getUserFromBD(String user_id){
        DataBaseHandler dataBaseHandler = new DataBaseHandler();

        String select = "SELECT * FROM " + ConstUser.USERS_TABLE +
                " WHERE " + ConstUser.USERS_USERMAME + " =? ";
        PreparedStatement preparedStatement;
        try{

            preparedStatement = dataBaseHandler.getConnection().prepareStatement(select);
            preparedStatement.setString(1,user_id);


            ResultSet result = preparedStatement.executeQuery();
            boolean first = true;



            while(result.next()){

                if(first){
                    first = false;
                }else {
                    out.write(result.getString(5) + "\n");
                    out.flush();

                    out.write(result.getString(6) + "\n");
                    out.flush();





                }



            }


        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException | NoSuchMethodException | SQLException | InvocationTargetException | IOException e) {
            e.printStackTrace();

        }

    }


}

/**
 * класс хранящий в ссылочном приватном
 * списке информацию о последних 10 (или меньше) сообщениях
 */

