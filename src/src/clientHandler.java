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
                    catch(NullPointerException | IndexOutOfBoundsException ex){}
                }
            }
        });
        matchMaker.start();//Starts thread
    }
    public void finishGame(int gameNumber)
    {
        for(int i=0;i<currentGames.size();i++)
        {
            if(currentGames.get(i).getGameNumber()==gameNumber)
            {
                players.add(currentGames.get(i).getPlayers()[1]);//Getting the players back into the player list
                players.add(currentGames.get(i).getPlayers()[0]);
                currentGames.remove(i);
                return;
            }
        }
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
    }
    public void add(Socket in)
    {
        players.add(new Player(in));
    }
}
