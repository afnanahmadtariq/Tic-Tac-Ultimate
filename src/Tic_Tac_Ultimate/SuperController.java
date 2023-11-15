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
        cpuTurn();
    }
    public void setDifficulty(String difficulty){
        this.difficulty = difficulty;
    }
    public int getPlayer(){
        return player;
    }
    public int[] getSuperIndex(){
        return superIndex;
    }
    public boolean isSinglePlayer(){
        return singlePlayer;
    }
    public void cpuTurn(){
        if(player==2 && singlePlayer){
            System.out.println("Super index BEFORE cpu turn_ row:" + this.superIndex[0] + " col: " + this.superIndex[1]);
            int[][] compute = SuperBrain.compTurn(superIndex,super.superBoard, difficulty);
            doTurn(compute[0],compute[1]);
            System.out.println("Super index AFTER cpu turn_ row:" + this.superIndex[0] + " col: " + this.superIndex[1]);
        }
    }
    public boolean doTurn(int[] index, int[] superIndex){
        System.out.println("Super index BEFORE player: " + player +" turn_ row:" + this.superIndex[0] + " col: " + this.superIndex[1]);
        if(markTurn(index,superIndex)){
            System.out.println("registered!!...............");
            Tic_Tac_Ultimate.showTurn(index, superIndex);
            switch(super.check(superIndex)){
                case 1 -> end(true,superIndex);
                case 0 -> end(false,superIndex);
            }
            boolean end = switch(super.check()){
                case 1 -> end(true);
                case 0 -> end(false);
                default -> false;
            };
            if(super.superBoard[index[0]][index[1]].game == -1)
                this.superIndex = index;
            else
                this.superIndex = new int[] {-1, -1};
            if(!end){
                player = (player%2)+1;
                System.out.println("Player Changed!");
                cpuTurn();
            }
            System.out.println("Super index FOR player: " + player +" turn_ row:" + this.superIndex[0] + " col: " + this.superIndex[1]);
            return true;
        }
        else
            return false;
    }
    public boolean checkSuperIndex(int[] superIndex){
        if(super.superBoard[superIndex[0]][superIndex[1]].game != -1)
            return false;
        else if(this.superIndex[0] == -1 && this.superIndex[1] == -1)
            return true;
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
        Tic_Tac_Ultimate.endGame(superIndex, win, super.winValue);
    }
    private boolean end(boolean win){
        if(win)
            super.game = player;
        else
            super.game = 0;
        Tic_Tac_Ultimate.endGame(win, super.winValue);
        //agr event listener game variable pe lga dain to ye func complete
        //boolean win se win ya draw ka pta chal rha
        //int player se kon jeeta ye pta lag rha
        return true;
    }
}
