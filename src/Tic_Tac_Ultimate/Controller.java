package Tic_Tac_Ultimate;

import java.io.Serializable;
import java.util.*;

import static Tic_Tac_Ultimate.Runner.showTurn;

public class Controller extends Board implements Serializable {
    private final boolean SINGLE_PLAYER;
    private final String difficulty;
    private int player;
    private Stack<int[]> stack;
    Controller(boolean singlePlayer, String difficulty){
        super();
        this.SINGLE_PLAYER = singlePlayer;
        this.difficulty = difficulty;
        player = 1;
        stack = new Stack<>();
    }
    Controller(Controller controller){
        super();
        this.SINGLE_PLAYER = controller.SINGLE_PLAYER;
        this.difficulty = controller.difficulty;
        player = controller.getPlayer();
        stack = controller.stack;
        loadGame();
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
            int[] index = Brain.compTurn(board, difficulty);
            System.out.println("index of cpu: " + Arrays.toString(index));
            if(!doTurn(index))
                System.out.println("BARi nhi hui cpu se");
        }
    }
    public boolean doTurn(int[] index){
        if(markTurn(index)){
            System.out.println("registered!!...............");
            showTurn(index);
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
    public void loadGame(){
        List<int[]> load = stack.stream().toList();
        if(load.size()%2==1)
            player = (player%2)+1;
        for(int[] index: load){
            showTurn(index);
            player = (player%2)+1;
        }
    }
}