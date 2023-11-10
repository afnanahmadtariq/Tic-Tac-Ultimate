package Tic_Tac_Ultimate;

public class SuperController extends SuperBoard{
    private Player players[];
    private boolean singlePlayer;
    private int player;
    public String difficulty;
    private int[] superIndex;
    
    SuperController(){
        this(true, "medium");
    }
    SuperController(boolean singlePlayer, String difficulty){
        super();
        this.singlePlayer = singlePlayer;
        this.difficulty = difficulty;
        superIndex = new int[] {-1, -1};
    }
    public void setPlayer(int player){
        this.player = player;
        System.out.println("Player set as: " + player);
        cpuTurn(new int[]{-1, -1});
    }
    public void setDifficulty(String difficulty){
        this.difficulty = difficulty;
    }
    public int getPlayer(){
        return this.player;
    }
    public boolean isSinglePlayer(){
        return singlePlayer;
    }
    public int[] cpuTurn(int[] superIndex){
        if(player==2 && singlePlayer){
            int[] index = SuperBrain.compTurn(superIndex,super.superBoard, difficulty);
            markTurn(index,superIndex);
            return index;
        }
        return superIndex;
    }
    public int[] doTurn(int[] index, int[] superIndex){
        if(markTurn(index,superIndex)){
            System.out.println("registered!!...............");
            Tic_Tac_Ultimate.showTurn(index, superIndex);
            int[] check = super.check(superIndex);
            switch(check[0]){
                case 1 -> end(true,superIndex);
                case 0 -> end(false,superIndex);
            }
            boolean end = switch(check[1]){
                case 1 -> end(true);
                case 0 -> end(false);
                default -> false;
            };
            if(!end){
                player = (player%2)+1;
                if(checkSuperIndex(index))
                    this.superIndex = cpuTurn(this.superIndex=index);
                else
                    this.superIndex = cpuTurn(this.superIndex= new int[] {-1, -1});
            }
        }
        return this.superIndex;
    }
    public boolean checkSuperIndex(int[] superIndex){
        if(super.superBoard[superIndex[0]][superIndex[1]].game != -1)
            return false;
        if(this.superIndex[0] == -1 && this.superIndex[1] == -1)
            return true;
        else if(super.superBoard[this.superIndex[0]][this.superIndex[1]].game != -1) {
            this.superIndex = new int[] {-1, -1};
            return true;
        }
        else return this.superIndex[0] == superIndex[0] && this.superIndex[1] == superIndex[1];
    }
    public boolean markTurn(int[] index, int[] superIndex){
        if(!checkSuperIndex(superIndex))
            return false;
        else if(super.superBoard[superIndex[0]][superIndex[1]].board[index[0]][index[1]] != 0)
            return false;
        super.superBoard[superIndex[0]][superIndex[1]].board[index[0]][index[1]] = player;
        System.out.println("Player: " + player + " did at super index:__ x: "+ superIndex[0] + "y: " + superIndex[1] + "and at index----i: " + index[0] + "  j: " + index[1]);
        return true;
    }
//    public int[] superUser(int a, int b, int i,int j, SuperBoard superGame ){
//        superGame.superBoard[i][j].board[a][b] = 1;
//        if(superGame.superBoard[i][j].win())
//            superGame.superBoard[i][j].game = 1;
//        return new int[]{a,b};
//    }
//    public int[] superCpu(int i,int j, SuperBoard superGame ){
//        int a = 0;
//        int b = 0;
//        superGame.superBoard[i][j].board[a][b] = 2;
//        if(superGame.superBoard[i][j].win())
//            superGame.superBoard[i][j].game = 2;
//        return new int[]{a,b};
//    }
    
    private void end(boolean win, int[] superIndex){
        if(win)
            super.superBoard[superIndex[0]][superIndex[1]].game = player;
        else
            super.superBoard[superIndex[0]][superIndex[1]].game = 0;
        Tic_Tac_Ultimate.endGame(win,player,superIndex);
    }
    private boolean end(boolean win){
        if(win)
            super.game = player;
        else
            super.game = 0;
        Tic_Tac_Ultimate.endGame(win, player, super.winValue, true);
        //agr event listener game variable pe lga dain to ye func complete
        //boolean win se win ya draw ka pta chal rha
        //int player se kon jeeta ye pta lag rha
        return true;
    }
}
