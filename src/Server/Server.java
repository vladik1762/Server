package Server;

import com.company.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {

    public static final int PORT = 8080;
    public static LinkedList<ServerSomthing> serverList = new LinkedList<>(); // список всех нитей - экземпляров
    // сервера, слушающих каждый своего клиента
    ; // история переписки
    public static User user;
    private static int count = 0;
    private static int countOther = 0;


    public static void connectServer() throws IOException {
        ServerSocket server = null;
        try {
            server = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("\nСервер запущен");
        System.out.println("Порт: 8080");
        System.out.println("Адрес: localhost");
        System.out.println("Количество подключений - " + (serverList.size()));


        try {
            while (true) {
                // Блокируется до возникновения нового соединения:
                Socket socket = server.accept();
                try {



                    serverList.add(new ServerSomthing(socket));
                    System.out.println("\nНовое подключение");
                    System.out.println("Порт: 8080");
                    System.out.println("Адрес: localhost");
                    System.out.println("Количество подключений - " + (serverList.size()));

                } catch (IOException e) {
                    // Если завершится неудачей, закрывается сокет,
                    // в противном случае, нить закроет его:
                    socket.close();
                }

            }
        } finally {
            server.close();
        }
    }
}