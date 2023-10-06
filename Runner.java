public class Runner{
    public static void main(String[] args){


    }
    private void start(int toss){
        Board game = new Board();
        if(toss==1)
            user();
        while(true){
            cpu();
            if(game.win())
                break;
            user();
            if(game.win())
                break;
        }
    }
    private void superStart(int toss){
        SuperBoard superGame = new SuperBoard();
        int[] index = new int[]{-1,-1};
        if(toss==1)
            index = superUser(-1,-1, superGame);
        while(true){
            index = superCpu(index[0],index[1], superGame);
            if(superGame.win())
                break;
            index = superUser(index[0],index[1], superGame);
            if(superGame.win())
                break;
        }
    }
    public void user(){

    }
    public void cpu(){

    }
    public int[] superUser(int i,int j, SuperBoard superGame ){
        int a = 0;
        int b = 0;
        superGame.superBoard[i][j].board[a][b] = 1;
        if(superGame.superBoard[i][j].win())
            superGame.superBoard[i][j].game = 1;
        return new int[]{a,b};
    }
    public int[] superCpu(int i,int j, SuperBoard superGame ){
        int a = 0;
        int b = 0;
        superGame.superBoard[i][j].board[a][b] = 2;
        if(superGame.superBoard[i][j].win())
            superGame.superBoard[i][j].game = 2;
        return new int[]{a,b};
    }
}