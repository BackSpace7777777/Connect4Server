package src;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
/*
To run the game on a single machine you have to change the ip in the client to your own ip
Make sure to run the server first, wait till it says that the server has started then you can run the clients
You have to run 2 clients because of the way match making is, because it takes 2 clients out of the pool of clients
*/
public class Main {
    private static ServerSocket welcomeSocket;
    private static clientHandler ch;
    public static void main(String[] args) {
        try
        {
            welcomeSocket=new ServerSocket(9876);//Opening port to this port
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        ch=new clientHandler();
        while(true)
        {
            try
            {
                Socket in=welcomeSocket.accept();//Accepts new connecting clients
                ch.add(in);//Adds them to the client handler
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static void clientHandlerCommand(String in)
    {
        ch.command(in);
    }
    public static void finishGame(int gameNumber)
    {
        ch.finishGame(gameNumber);
    }
}