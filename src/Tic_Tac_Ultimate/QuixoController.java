package Tic_Tac_Ultimate;

import java.util.Arrays;

public class QuixoController extends QuixoBoard  implements Cloneable{
    private final boolean SINGLE_PLAYER;
    private int player;
    private final String difficulty;
    private int[] draw = new int[] {-1, -1};
    QuixoController(boolean singlePlayer, String difficulty){
        super();
        this.SINGLE_PLAYER = singlePlayer;
        this.difficulty = difficulty;
        player = 1;
    }
    QuixoController(QuixoController controller){
        super(controller);
        this.SINGLE_PLAYER = controller.isSINGLE_PLAYER();
        this.difficulty = controller.getDifficulty();
        player = controller.getPlayer();
    }
    public boolean isSINGLE_PLAYER() {
        return SINGLE_PLAYER;
    }
    public String getDifficulty() {
        return difficulty;
    }
    public void setPlayer(int player){
        this.player = player;
        System.out.println("Player set as: " + player);
        cpuTurn();
    }
    public int getPlayer(){
        return this.player;
    }
    private void cpuTurn() {
        if(player==2 && SINGLE_PLAYER) {
            int[][] index = QuixoBrain.compTurn(new QuixoController(this), difficulty);
            System.out.println("index of cpu: " + Arrays.deepToString(index));
            Quixo.slide(index[0][0], index[0][1], index[1][0], index[1][1]);
//            if(!doTurn(index[0], index[1]))
//                System.out.println("BARi nhi hui cpu se");
        }
    }
    public int checkDraw(int[] drawIndex){
        if(draw[0] == drawIndex[0] && draw[1] == drawIndex[1])
            return 10;
        else if (draw[0] == -1) {
            if (super.board[drawIndex[0]][drawIndex[1]] == player) {
                return 1;
            }
            else if (super.board[drawIndex[0]][drawIndex[1]] == 0) {
                return 0;
            }
        }
        return -1;
    }
    public void setDraw(int[] drawIndex){
        draw = drawIndex;
    }
    public void clearDraw(){
        draw = new int[] {-1, -1};
    }
    public boolean doTurn(int[] drawIndex, int[] insertIndex){
        if(markTurn(drawIndex,insertIndex)){
            System.out.println("TicTacToeBoard now: \n" + Arrays.deepToString(super.board));
            int check = super.check(player);
            int value = check;
            System.out.println("check value: "  + value);
            boolean end = switch(value){
                case 1 -> end(true,1);
                case 2 -> end(true,2);
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
    private boolean markTurn(int[] drawIndex, int[] insertIndex){
        if(super.board[drawIndex[0]][drawIndex[1]] == (player%2)+1)
            return false;
        if(drawIndex[0]==insertIndex[0]){//row ke liye
            System.out.println("Player: " + player + " draw: ----> " + Arrays.toString(drawIndex) + "  |  insert: ----> " + Arrays.toString(insertIndex));
            int value = drawIndex[1];
            if(value<insertIndex[1])
                for(;value<insertIndex[1];value++){
                    super.board[drawIndex[0]][value] = super.board[drawIndex[0]][value+1];
                }
            else
                for(;value>insertIndex[1];value--){
                    super.board[drawIndex[0]][value] = super.board[drawIndex[0]][value-1];
                }
        }
        else {//col ke liye
            System.out.println("Player: " + player + " draw: ----> " + Arrays.toString(drawIndex) + "  |  insert: ----> " + Arrays.toString(insertIndex));
            int value = drawIndex[0];
            if(value<insertIndex[0])
                for(;value<insertIndex[0];value++){
                    super.board[value][drawIndex[1]] = super.board[value+1][drawIndex[1]];
                }
            else
                for(;value>insertIndex[0];value--){
                    super.board[value][drawIndex[1]] = super.board[value-1][drawIndex[1]];
                }
        }
        super.board[insertIndex[0]][insertIndex[1]] = player;
        return true;
    }
    public void undoTurn(int[] drawIndex, int[] insertIndex){
        markTurn(insertIndex, drawIndex);
        player = (player%2)+1;
        GUI.updateTurn();
        System.out.println("Player Changed!");

    }
    private boolean end(boolean win, int winner){
        super.game = winner;
        Runner.endGame(win, super.winValue);
        return true;
    }
}
