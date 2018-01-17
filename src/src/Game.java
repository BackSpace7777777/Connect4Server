package src;
public class Game {
    private Player[] players=new Player[2];
    private Board board;
    private final int gameNumber;
    public Game(int gameNumber,Player p1, Player p2)
    {
        players[0]=p1;
        players[1]=p2;
        players[0].sendData("Game Number:" + gameNumber);
        players[1].sendData("Game Number:" + gameNumber);
        this.gameNumber=gameNumber;
        board=new Board();
    }
    public void endGame()
    {
        
    }
    public void start()
    {
        players[0].sendData("Game Start:true");
        players[1].sendData("Game Start:false");
    }
}
