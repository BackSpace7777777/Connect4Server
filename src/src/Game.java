package src;
public class Game {
    private Player[] players=new Player[2];
    private Board board;
    private final int gameNumber;
    public Game(int gameNumber,Player p1, Player p2)
    {
        players[0]=p1;
        players[1]=p2;
        players[0].sendData("Game Number:" + gameNumber);//Telling players what game number they are so that it makes it easy to tell what the incoming data is for what game
        players[1].sendData("Game Number:" + gameNumber);
        this.gameNumber=gameNumber;
        board=new Board();//Creates an imaginary board
    }
    public int getGameNumber()
    {
        return gameNumber;
    }
    public void endGame()//Called when game is won
    {
        int winner=board.getWinner();
        if(winner==1)
        {
            players[0].sendData("Game Finished:true");
            players[1].sendData("Game Finished:false");
        }
        else if(winner==-1)
        {
            players[0].sendData("Game Finished:false");
            players[1].sendData("Game Finished:true");
        }
        else
        {
            players[0].sendData("Game Finished:false");
            players[1].sendData("Game Finished:false");
        }
        Main.finishGame(gameNumber);
    }
    public Player[] getPlayers()
    {
        return players;
    }
    public void start()
    {
        players[0].sendData("Game Start:true");
        players[1].sendData("Game Start:false");
    }
}
