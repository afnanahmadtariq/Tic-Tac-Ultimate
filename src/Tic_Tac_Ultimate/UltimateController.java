package Tic_Tac_Ultimate;

import java.io.Serializable;
import java.util.Stack;

public class UltimateController extends UltimateBoard implements Serializable {
    private final boolean singlePlayer;
    private int player;
    public String difficulty;
    private int[] uIndex;
    private Stack<int[][]> stack;
    UltimateController(boolean singlePlayer, String difficulty){
        super();
        this.singlePlayer = singlePlayer;
        this.difficulty = difficulty;
        uIndex = new int[] {-1, -1};
        stack = new Stack<>();
    }
    public void setPlayer(int player){
        this.player = player;
        System.out.println("Player set as: " + player);
        cpuTurn();
    }
    public int getPlayer(){
        return player;
    }
    public int[] getSuperIndex(){
        return uIndex;
    }
    public boolean isSinglePlayer(){
        return singlePlayer;
    }
    private void cpuTurn(){
        if(player==2 && singlePlayer){
            System.out.println("Super index BEFORE cpu turn_ row:" + this.uIndex[0] + " col: " + this.uIndex[1]);
            int[][] compute = UltimateBrain.compTurn(uIndex,uBoard, difficulty);
            if(!doTurn(compute[0],compute[1]))
                System.out.println("BARi nhi hui cpu se");
            System.out.println("Super index AFTER cpu turn_ row:" + this.uIndex[0] + " col: " + this.uIndex[1]);
        }
    }
    public boolean doTurn(int[] index, int[] uIndex){
        System.out.println("Super index BEFORE player: " + player +" turn_ row:" + this.uIndex[0] + " col: " + this.uIndex[1]);
        if(markTurn(index,uIndex)){
            System.out.println("registered!!...............");
            Runner.showTurn(index, uIndex);
            switch(check(uIndex)){
                case 1 -> end(true,uIndex);
                case 0 -> end(false,uIndex);
            }
            boolean end = switch(check()){
                case 1 -> end(true);
                case 0 -> end(false);
                default -> false;
            };
            if(uBoard[index[0]][index[1]].game == -1)
                this.uIndex = index;
            else
                this.uIndex = new int[] {-1, -1};
            if(!end){
                player = (player%2)+1;
                GUI.updateTurn();
                System.out.println("Player Changed!");
                cpuTurn();
            }
            System.out.println("Super index FOR player: " + player +" turn_ row:" + this.uIndex[0] + " col: " + this.uIndex[1]);
            return true;
        }
        else
            return false;
    }
    public void undoTurn(){
        int[] index = stack.peek()[0];
        int[] uIndex = stack.pop()[1];
        uBoard[uIndex[0]][uIndex[1]].board[index[0]][index[1]] = 0;
        player = (player%2)+1;
        //GUI mai show krna
    }
    private boolean checkSuperIndex(int[] uIndex){
        if(uBoard[uIndex[0]][uIndex[1]].game != -1)
            return false;
        else if(this.uIndex[0] == -1 && this.uIndex[1] == -1)
            return true;
        else return this.uIndex[0] == uIndex[0] && this.uIndex[1] == uIndex[1];
    }
    private boolean markTurn(int[] index, int[] uIndex){
        if(!checkSuperIndex(uIndex))
            return false;
        else if(uBoard[uIndex[0]][uIndex[1]].board[index[0]][index[1]] != 0)
            return false;
        uBoard[uIndex[0]][uIndex[1]].board[index[0]][index[1]] = player;
        stack.push(new int[][]{index, uIndex});
        System.out.println("Player: " + player + " did at super index:__ x: "+ uIndex[0] + "y: " + uIndex[1] + "and at index----i: " + index[0] + "  j: " + index[1]);
        return true;
    }
    private void end(boolean win, int[] uIndex){
        if(win)
            uBoard[uIndex[0]][uIndex[1]].game = player;
        else
            uBoard[uIndex[0]][uIndex[1]].game = 0;
        Runner.endGame(uIndex, win, winValue);
    }
    private boolean end(boolean win){
        if(win)
            game = player;
        else
            game = 0;
        Runner.endGame(win, winValue);
        return true;
    }
}
