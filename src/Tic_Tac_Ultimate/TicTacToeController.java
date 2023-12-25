package Tic_Tac_Ultimate;

import java.util.Arrays;
import java.util.Stack;

public class TicTacToeController extends TicTacToeBoard {
    private final boolean SINGLE_PLAYER;
    private final String difficulty;
    private int player;
    private Stack<int[]> stack;
    TicTacToeController(boolean singlePlayer, String difficulty){
        super();
        this.SINGLE_PLAYER = singlePlayer;
        this.difficulty = difficulty;
        player = 1;
        stack = new Stack<>();
    }
    public void setPlayer(int player){
        this.player = player;
        System.out.println("Player set as: " + player);
        cpuTurn();
    }
    public int getPlayer(){
        return this.player;
    }
    private void cpuTurn(){
        if(player==2 && SINGLE_PLAYER) {
            int[] index = TicTacToeBrain.compTurn(board, difficulty);
            System.out.println("index of cpu: " + Arrays.toString(index));
            if(!doTurn(index))
                System.out.println("BARi nhi hui cpu se");
        }
    }
    public boolean doTurn(int[] index){
        if(markTurn(index)){
            System.out.println("registered!!...............");
            Runner.showTurn(index);
            boolean end = switch(check()){
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
    public void undoTurn(){
        int[] index = stack.pop();
        board[index[0]][index[1]] = 0;
        player = (player%2)+1;
        //GUI mai show krna
    }
    private boolean markTurn(int[] index){
        if(board[index[0]][index[1]] != 0)
            return false;
        board[index[0]][index[1]] = player;
        stack.push(index);
        System.out.println("Player: " + player + " marked ---->i: " + index[0] + "  |  j: " + index[1]);
        return true;
    }
    private boolean end(boolean win){
        if(win) {
            game = player;
        }
        else {
            game = 0;
        }
        Runner.endGame(win, winValue);
        return true;
    }
}