package src;
public class Board {
    private int[][] b=new int[7][6];//0==nobody, 1==player 1, -1==player 2
    private int winner;
    public Board()
    {
        winner=0;
        for(int x=0;x<7;x++)for(int y=0;y<6;y++)b[x][y]=0;//Setting everything to 0
    }
    public boolean checkWin()
    {
        //checks if someone won or not by testing for a diagonal, vertical, and horizontal check
        if(isFilled())
        {
            winner=0;
            return true;
        }
        else if(diagonalCheck()!=0)
        {
            winner=diagonalCheck();
            System.out.println("Diagonal Win");
            return true;
        }
        else if(verticalCheck()!=0)
        {
            winner=verticalCheck();
            System.out.println("Vertical Win");
            return true;
        }
        else if(horizontalCheck()!=0)
        {
            winner=horizontalCheck();
            System.out.println("Horizontal Win");
            return true;
        }
        return false;
    }
    public int getWinner()
    {
        return winner;
    }
    public boolean playPiece(int x,int player)
    {
        if(checkWin())return false;
        for(int i=0;i<6;i++)
        {
            if(b[x][i]==0)
            {
                b[x][i]=player;
                return true;
            }
        }
        return false;
    }
    private boolean isFilled()
    {
        boolean filled=true;
        for(int x=0;x<7;x++)
        {
            for(int y=0;y<6;y++)
            {
                if(b[x][y]==0)filled=false;
            }
        }
        return filled;
    }
    private int diagonalCheck()
    {
        for(int x=0;x<4;x++)
        {
            for(int y=0;y<3;y++)//Checks bottem left to top right
            {
                int temp=b[x][y];
                temp+=b[x+1][y+1];
                temp+=b[x+2][y+2];
                temp+=b[x+3][y+3];
                if(temp==4)return 1;
                else if(temp==-4)return -1;
            }
        }
        for(int x=0;x<4;x++)//Checks top left to bottem right
        {
            for(int y=5;y>2;y--)
            {
                int temp=b[x][y];
                temp+=b[x+1][y-1];
                temp+=b[x+2][y-2];
                temp+=b[x+3][y-3];
                if(temp==4)return 1;
                else if(temp==-4)return -1;
            }
        }
        return 0;
    }
    private int verticalCheck()
    {
        for(int x=0;x<7;x++)
        {
            for(int y=0;y<3;y++)
            {
                int temp=b[x][y];
                temp+=b[x][y+1];
                temp+=b[x][y+2];
                temp+=b[x][y+3];
                if(temp==4)return 1;
                else if(temp==-4)return -1;
            }
        }
        return 0;
    }
    private int horizontalCheck()
    {
        for(int y=0;y<6;y++)
        {
            for(int x=0;x<4;x++)
            {
                int temp=b[x][y];
                temp+=b[x+1][y];
                temp+=b[x+2][y];
                temp+=b[x+3][y];
                if(temp==4)return 1;
                else if(temp==-4)return -1;
            }
        }
        return 0;
    }
}
