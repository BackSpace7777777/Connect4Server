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
    private BufferedReader in;
    private DataOutputStream out;
    private final Thread inT;
    private boolean isConnected;
    public Player(Socket in)
    {
        socket=in;
        try {
            this.in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new DataOutputStream(socket.getOutputStream());
            isConnected=true;
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
        inT=new Thread(new Runnable() {
            @Override
            public void run() {
                while(isConnected)
                {
                    
                }
            }
        });
    }
    public void sendData(String data)
    {
        
    }
}
