package Tic_Tac_Ultimate;


public class Controller extends Board{
    private Player players[];
    private boolean singlePlayer;
    private int player;
    public String difficulty;
    
    Controller(){
        this(true,"medium");
    }
    Controller(boolean singlePlayer, String difficulty){
        super();
        this.singlePlayer = singlePlayer;
        this.difficulty = difficulty;
        player = 1;
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
        return this.player;
    }
    public boolean isSinglePlayer(){
        return singlePlayer;
    }
    public void cpuTurn(){
        if(player==2 && singlePlayer) {
            int[] index = Brain.compTurn(super.board, difficulty);
            doTurn(index);
        }
    }
    public void doTurn(int[] index){
        if(markTurn(index)){
            System.out.println("registered!!...............");
            Tic_Tac_Ultimate.showTurn(index);
            boolean end = switch(super.check()){
                case 1 -> end(true);
                case 0 -> end(false);
                default -> false;
            };
            if(!end){
                player = (player%2)+1;
                System.out.println("Player Changed!");
                cpuTurn();
            }
        }
    }
    private boolean markTurn(int[] index){
        if(super.board[index[0]][index[1]] != 0)
            return false;
        super.board[index[0]][index[1]] = player;
        System.out.println("Player: " + player + " did ----i: " + index[0] + "  j: " + index[1]);
        return true;
    }
    private boolean end(boolean win){
        if(win) {
            super.game = player;
        }
        else {
            super.game = 0;
        }
        Tic_Tac_Ultimate.endGame(win, player, super.winValue, false);
        //agr event listener game variable pe lga dain to ye func complete
        //boolean win se win ya draw ka pta chal rha
        //int player se kon jeeta ye pta lag rha
        return true;
    }
}
