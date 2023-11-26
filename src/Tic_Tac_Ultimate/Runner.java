package Tic_Tac_Ultimate;

import java.util.Arrays;

import static Tic_Tac_Ultimate.Board.dictionary;

public class Runner {
    private static QuxioController quxio;
    private static SuperController superTicTacToe;
    private static Controller ticTacToe;
    public static int gameType = 3;
    public static boolean singlePlayer = false;
    public static String difficulty = "none";
    public static void main(String[] args) {
        startGame();
        GUI.initialize(args);
    }
    public static void startGame(){
        switch(gameType){
            case 2-> superTicTacToe = new SuperController(singlePlayer,difficulty);
            case 3-> quxio = new QuxioController(singlePlayer,difficulty);
            default -> ticTacToe = new Controller(singlePlayer,difficulty);
        }
    }
    public static void setPlayer(int player){
        switch(gameType){
            case 2-> superTicTacToe.setPlayer(player);
            case 3-> quxio.setPlayer(player);
            default -> ticTacToe.setPlayer(player);
        }
    }
    public static int getPlayer(){
        return switch(gameType){
            case 2-> superTicTacToe.getPlayer();
            case 3-> quxio.getPlayer();
            default -> ticTacToe.getPlayer();
        };
    }
    public static int[] getSuperIndex(){
        return superTicTacToe.getSuperIndex();
    }
    public static void showTurn(int[] index){
        GUI.showTurn(index[0],index[1]);
        System.out.println("Show GUI mark Done!");
    }
    public static void showTurn(int[] index, int[] superIndex){
        GUI.showTurn(index[0],index[1], superIndex);
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
    public static int checkDraw(int row, int col){
        return quxio.checkDraw(new int[] {row, col});
    }
    public static boolean turn2(int row, int col, int rowI, int colI){
        return quxio.doTurn(new int[]{row, col}, new int[]{rowI, colI});
    }
    public static void endGame(boolean win, int winValue){
        if(win){
            GUI.markLine(winValue);
            System.out.println("Won with value: " + winValue);
            System.out.println("index at: " + Arrays.deepToString(dictionary.get(winValue)));
        }
        else
            System.out.println("place 'D' at index");

        String text = (win? "Player"+getPlayer()+" won!" : "It is a draw!")+" Play Again?";
        System.out.println(win? "Player"+getPlayer()+" won!" : "It is a draw!");
        GUI.popUp(text,"Yes","Exit",2);
    }
    public static void endGame(int choice){
        if(choice==1) {
            System.out.println("\n\n\n\n---------   New Game---------");
            startGame();
            GUI.clearMarks();
            GUI.clearTurn();
            GUI.Toss();
        }
        else{
            System.out.println("\n\n\n\n---------  Exit Game---------");
//            GUI.clearGame();
        }
    }
    public static void endGame(int[] superIndex, boolean win, int winValue){
        if(win){
            GUI.markLine(superIndex, winValue);
            System.out.println("won at super index: " + Arrays.toString(superIndex));
            System.out.println("Won with value: " + winValue);
            System.out.println("index at: " + Arrays.deepToString(dictionary.get(winValue)));
        }
        else {
            GUI.markDraw(superIndex);
            System.out.println("place 'D' at index");
        }
    }
}