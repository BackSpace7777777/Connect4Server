package src;
public class Game {
    private Player[] players=new Player[2];
    private Board board;
    private final int gameNumber;
    private boolean turn;//true=player 1
    public Game(int gameNumber,Player p1, Player p2)
    {
        players[0]=p1;
        players[1]=p2;
        players[0].sendData("Game Number:" + gameNumber);//Telling players what game number they are so that it makes it easy to tell what the incoming data is for what game
        players[1].sendData("Game Number:" + gameNumber);
        this.gameNumber=gameNumber;
        turn=true;
        board=new Board();//Creates an imaginary board
    }
    public void play(int x)//1 is player 0 and -1 is player 1
    {
        if(turn)
        {
            if(board.playPiece(x, 1))//When you play the peice for the local player
            {
                players[0].sendData("Turn:false");//Tells player 0 that it is not their turn any more
                players[1].sendData("Turn:true");//Tells opponent that it is their turn
                players[1].sendData("Piece:"+x+",-1");//Tells both clients who moved where so that they can run the animation
                players[0].sendData("Piece:"+x+",1");
                if(board.checkWin())endGame();
                turn=false;
            }
            else
            {
                if(board.checkWin())
                {
                    endGame();
                }
                else
                {
                    players[0].sendData("Turn:true");
                    players[1].sendData("Turn:false");
                    turn=true;
                }
            }
        }
        else
        {
            if(board.playPiece(x,-1))
            {
                players[0].sendData("Turn:true");
                players[1].sendData("Turn:false");
                players[1].sendData("Piece:"+x+",1");
                players[0].sendData("Piece:"+x+",-1");
                if(board.checkWin())endGame();
                turn=true;
            }
            else
            {
                if(board.checkWin())
                {
                    endGame();
                }
                else
                {
                    players[0].sendData("Turn:false");
                    players[1].sendData("Turn:true");
                    turn=false;
                }
            }
        }
    }
    public int getGameNumber()
    {
        return gameNumber;
    }
    public void endGame()//Called when game is won
    {
        switch (board.getWinner()) {
            case 1:
                players[0].sendData("Game Finished:true");
                players[1].sendData("Game Finished:false");
                break;
            case -1:
                players[0].sendData("Game Finished:false");
                players[1].sendData("Game Finished:true");
                break;
            default:
                players[0].sendData("Game Finished:false");
                players[1].sendData("Game Finished:false");
                break;
        }
        Main.finishGame(gameNumber);//Makes a call to the client client Manager through main
    }
    public void endGame(int in)
    {
        switch(in)
        {
            case 1:
                players[0].sendData("Game Finished:true");
                break;
            case 0:
                players[1].sendData("Game Finished:true");
                break;
        }
        Main.finishGame(gameNumber);
    }
    public Player[] getPlayers()
    {
        return players;
    }
    public void start()//Telling the players to start
    {
        players[0].sendData("Game Start:true");
        players[1].sendData("Game Start:false");
    }
}
