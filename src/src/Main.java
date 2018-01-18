package src;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

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