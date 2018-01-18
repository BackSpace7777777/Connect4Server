package src;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Player {
    private final Socket socket;
    private BufferedReader inR;
    private DataOutputStream out;
    private final Thread inT;
    private boolean isConnected;
    public Player(Socket in)
    {
        socket=in;
        try {
            this.inR=new BufferedReader(new InputStreamReader(socket.getInputStream()));//Setting up incoming Reader
            out=new DataOutputStream(socket.getOutputStream());//setting up outgoing data
            isConnected=true;//Thread boolean
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
        inT=new Thread(new Runnable() {//Thread for incoming Data
            @Override
            public void run() {
                String[] splitData=new String[2];
                while(isConnected)
                {
                    try {
                        splitData=inR.readLine().split(":");//Splits incoming data
                    } catch (IOException ex) {
                        isConnected=false;
                        Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if(splitData[0].equals("Disconnect"))
                    {
                        String reason=splitData[1];
                        drop();
                    }
                    else
                    {
                        Main.clientHandlerCommand(splitData[0]+splitData[1]);//Sends all other data to the client handler
                    }
                }
            }
        });
        inT.start();
    }
    public void drop()
    {
        isConnected=false;
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public boolean isConnected()
    {
        return isConnected;
    }
    public void sendData(String data)
    {
        try {
            out.writeBytes(data);
        } catch (IOException ex) {
            isConnected=false;
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
