package Tic_Tac_Ultimate;

import java.util.Arrays;

public class Controller extends Board{
    private final boolean SINGLE_PLAYER;
    private int player;
<<<<<<< HEAD
    private final String difficulty;
=======
    public String difficulty;
    
    Controller(){
        super();
        singlePlayer = true;
        difficulty = "Medium";
        player = 1;
    }
>>>>>>> ee60ea9a0780710a8fcd47bc24e9509bbc7f56d0
    Controller(boolean singlePlayer, String difficulty){
        super();
        this.SINGLE_PLAYER = singlePlayer;
        this.difficulty = difficulty;
        player = 1;
    }
    public void setPlayer(int player){
        this.player = player;
        System.out.println("Player set as: " + player);
        cpuTurn();
    }
<<<<<<< HEAD
=======
    public void setPlayerOptions(boolean singlePlayer){
        this.singlePlayer = singlePlayer;
    }
    public void setDifficulty(String difficulty){
        this.difficulty = difficulty;
    }
>>>>>>> ee60ea9a0780710a8fcd47bc24e9509bbc7f56d0
    public int getPlayer(){
        return this.player;
    }
    private void cpuTurn(){
        if(player==2 && SINGLE_PLAYER) {
            int[] index = Brain.compTurn(super.board, difficulty);
            System.out.println("index of cpu: " + Arrays.toString(index));
            if(!doTurn(index))
                System.out.println("BARi nhi hui cpu se");
        }
    }
    public boolean doTurn(int[] index){
        if(markTurn(index)){
            System.out.println("registered!!...............");
            Runner.showTurn(index);
            boolean end = switch(super.check()){
                case 1 -> end(true);
                case 0 -> end(false);
                default -> false;
            };
            if(!end){
                player = (player%2)+1;
                GUI.updateTurn();
                System.out.println("Player Changed!");
                cpuTurn();
            }
            return true;
        }
        else
            return false;
    }
    private boolean markTurn(int[] index){
        if(super.board[index[0]][index[1]] != 0)
            return false;
        super.board[index[0]][index[1]] = player;
        System.out.println("Player: " + player + " marked ---->i: " + index[0] + "  |  j: " + index[1]);
        return true;
    }
    private boolean end(boolean win){
        if(win) {
            super.game = player;
        }
        else {
            super.game = 0;
        }
        Runner.endGame(win, super.winValue);
        return true;
    }
}