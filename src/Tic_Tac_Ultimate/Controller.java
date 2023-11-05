/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tic_Tac_Ultimate;


/**
 *
 * @author Pc
 */
public class Controller extends Board{
    private Player players[];
    private boolean singlePlayer;
    private int player;
    protected static String difficulty;
    
    Controller(){
        super();
        singlePlayer = true;
        difficulty = "medium";
        player = 1;
    }
    Controller(boolean singlePlayer, String difficulty){
        super();
        this.singlePlayer = singlePlayer;
        Controller.difficulty = difficulty;
    }
    public void setPlayer(int player){
        this.player = player;
    }
    public void checkTurn(){
        if(player==2 && singlePlayer) {
            int[] index = Brain.compTurn(super.board);
            turn(index);
            Tic_Tac_Ultimate.mark(index[0], index[1]);
        }
    }
    public boolean turn(int[] index){
        if(super.board[index[0]][index[1]] != 0)
            return false;
        super.board[index[0]][index[1]] = player;
        System.out.println("Player: " + player + " did ----i: " + index[0] + "  j: " + index[1]);
        boolean end = switch(super.check()){
            case 1 -> end(true);
            case 0 -> end(false);
            default -> false;
        };
        if(!end){
            player = (player%2)+1;
            checkTurn();
        }
        return true;
    }
    
    private boolean end(boolean win){
        if(win)
            super.game = player;
        else
            super.game = 0;
        if(Tic_Tac_Ultimate.endGame(win, player, super.winValue)==0){
            //start kr do phir se
        }
        else {
            //exit
        }
        //agr event listener game variable pe lga dain to ye func complete
        //boolean win se win ya draw ka pta chal rha
        //int player se kon jeeta ye pta lag rha
        return true;
    }
}
