package Tic_Tac_Ultimate;
import javafx.application.Application;

import java.util.Arrays;

import static Tic_Tac_Ultimate.Board.dictionary;

public class Tic_Tac_Ultimate extends GUI{
    public static SuperController superTicTacToe;
    public static Controller ticTacToe;
    public static boolean ultimate;
    public static void main(String[] args) {
        start(true,"easy",true);
        Application.launch(args);
    }
    public static void start(boolean singlePlayer, boolean ultimate){
        start(singlePlayer,"easy",ultimate);
    }
    public static void start(boolean singlePlayer, String difficulty, boolean ultimate){
        Tic_Tac_Ultimate.ultimate = ultimate;
        if(ultimate)
            superTicTacToe = new SuperController(singlePlayer,difficulty);
        else
            ticTacToe = new Controller(singlePlayer,difficulty);
    }
    public static void setPlayer(int player){
        if(ultimate)
            superTicTacToe.setPlayer(player);
        else
            ticTacToe.setPlayer(player);
    }
    public static int getPlayer(){
        if(ultimate)
            return superTicTacToe.getPlayer();
        else
            return ticTacToe.getPlayer();
    }
    public static boolean isSinglePlayer(){
        if(ultimate)
            return superTicTacToe.isSinglePlayer();
        else
            return ticTacToe.isSinglePlayer();
    }
    public static void showTurn(int[] index){
        showTurn(index[0],index[1]);
        System.out.println("Show GUI mark Done!");
    }
    public static void showTurn(int[] index, int[] superIndex){
        showTurn(index[0],index[1], superIndex);
        System.out.println("Show GUI mark Done!");
    }
    public static boolean turn(int row, int col){
        if(row>=0 && col>=0 && row<=2 && col<=2)
            return ticTacToe.doTurn(new int[] {row, col});
        return false;
    }
    public static boolean turn(int i, int j,int sI, int sJ){
        if(i>=0 && j>=0 && i<=2 && j<=2 && sI>=0 && sJ>=0 && sI<=2 && sJ<=2)
            return superTicTacToe.doTurn(new int[] {i, j}, new int[] {sI, sJ});
        return false;
    }
    public static void endGame(boolean win, int winValue){
        if(win){
            markLine(winValue);
            System.out.println("place 'player' at index");
            System.out.println("Won with value: " + winValue);
            System.out.println("index at: " + Arrays.deepToString(dictionary.get(winValue)));
        }
        else
            System.out.println("place 'D' at index");

        String text = (win? "Player"+getPlayer()+" won!" : "It is a draw!")+" Play Again?";
        System.out.println(win? "Player"+getPlayer()+" won!" : "It is a draw!");
        popUp(text,"Yes","Exit",2);
    }
    public static void endGame(int choice){
        if(choice==1) {
            System.out.println("\n\n\n\n---------   New Game---------");
            if(ultimate)
                superTicTacToe = new SuperController(true, "easy");
            else
                ticTacToe = new Controller(true, "easy");
            clearMarks();
            Toss();
        }
    }
    public static void endGame(int[] superIndex, boolean win, int winValue){
        if(win){
            markLine(superIndex, winValue);
            System.out.println("won at super index: " + Arrays.toString(superIndex));
            System.out.println("Won with value: " + winValue);
            System.out.println("index at: " + Arrays.deepToString(dictionary.get(winValue)));
        }
        else {
            markDraw(superIndex);
            System.out.println("place 'D' at index");
        }
    }
}