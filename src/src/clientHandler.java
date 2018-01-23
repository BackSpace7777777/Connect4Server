package src;

import java.net.Socket;
import java.util.ArrayList;

public class clientHandler {
    private ArrayList<Player> players;//Array list for player not in game
    private ArrayList<Game> currentGames;//Arraylist of games that takes players out of the players arrray
    private Thread matchMaker;
    public clientHandler()
    {
        players=new ArrayList();
        currentGames=new ArrayList();
        matchMaker=new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Server started");
                while(true)
                {
                    try
                    {
                        Game temp=new Game(currentGames.size(),players.get(0),players.get(1));//Creates new game with the new clients
                        players.remove(0);//Removes the players from the queue
                        players.remove(0);
                        temp.start();
                        currentGames.add(temp);//Adds temp game to the list of current games
                        checkPlayers();
                    }
                    catch(NullPointerException ex){}
                    catch(IndexOutOfBoundsException ex){}
                }
            }
        });
        matchMaker.start();//Starts thread
    }
    public void finishGame(int gameNumber)
    {
        Player[] tempPlayers=currentGames.get(gameNumber).getPlayers();
        currentGames.remove(gameNumber);
        players.add(tempPlayers[1]);//Getting the players back into the player list
        players.add(tempPlayers[0]);
    }
    private void checkPlayers()
    {
        for(int i=0;i<players.size();i++)
        {
            if(!players.get(i).isConnected())
            {
                players.remove(i);
                checkPlayers();
                return;
            }
        }
        for(int i=0;i<currentGames.size();i++)//incase a player gets dropped during a game
        {
            if(!currentGames.get(i).getPlayers()[0].isConnected() && currentGames.get(i).getPlayers()[1].isConnected())
            {
                currentGames.get(i).endGame(0);
            }
            else if(!currentGames.get(i).getPlayers()[1].isConnected() && currentGames.get(i).getPlayers()[0].isConnected())
            {
                currentGames.get(i).endGame(1);
            }
            else if(!currentGames.get(i).getPlayers()[1].isConnected() && !currentGames.get(i).getPlayers()[0].isConnected())
            {
                currentGames.remove(i);
            }
        }
    }
    public void command(String in)
    {
        String splitData[]=in.split(":");
        System.out.println(in);
        if(splitData[0].equals("Move"))
        {
            String split2[]=splitData[1].split(",");
            try
            {
                currentGames.get(Integer.parseInt(split2[1])).play(Integer.parseInt(split2[0]));
            }
            catch(IndexOutOfBoundsException ex){}
        }
    }
    public void add(Socket in)
    {
        players.add(new Player(in));
        System.out.println(in.getInetAddress());
    }
}
