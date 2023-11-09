package Tic_Tac_Ultimate;

import javafx.application.Application;

public class Tic_Tac_Ultimate extends GUI{
    static Controller ticTacToe;
    public static void main(String[] args) {
        ticTacToe = new Controller(true,"easy");
        Application.launch(args);
    }
    public static void setPlayer(int player){
        ticTacToe.setPlayer(player);
    }
    public static int getPlayer(){
        return ticTacToe.getPlayer();
    }
    public static boolean isSinglePlayer(){
        return ticTacToe.isSinglePlayer();
    }
    public static void showTurn(int[] index){
        showTurn(index[0],index[1]);
        System.out.println("Show GUI mark Done!");
    }
    public static void turn(int i, int j){
        if(i>=0 && j>=0 && i<=2 && j<=2)
            ticTacToe.doTurn(new int[] {i, j});
    }
    public static void endGame(boolean win,int player,int winValue){
        if(win){
            markLine(winValue);
            System.out.println("place 'player' at index");
        }
        else
            System.out.println("place 'D' at index");

        String text = (win? "Player"+player+" won!" : "It is a draw!")+" Play Again?";
        if(popUp(text,"Yes","Exit",1)==0)
            ticTacToe = new Controller(true,"easy");

    }
    public static void endGame(boolean win,int player, int[] superIndex){
        if(win)
            System.out.println("place 'player' at index");
        else
            System.out.println("place 'D' at index");
    }
//    private void start(int toss){
//        Board game = new Board();
//        if(toss==1)
//            user();
//        while(true){
//            cpu();
//            if(game.win())
//                break;
//            user();
//            if(game.win())
//                break;
//        }
//    }
//    private void superStart(int toss){
//        SuperBoard superGame = new SuperBoard();
//        int[] index = new int[]{-1,-1};
//        if(toss==1)
//            index = superUser(-1,-1, superGame);
//        while(true){
//            index = superCpu(index[0],index[1], superGame);
//            if(superGame.win())
//                break;
//            index = superUser(index[0],index[1], superGame);
//            if(superGame.win())
//                break;
//        }
//    }
//    public void user(){
//
//    }
//    public void cpu(){
//
//    }
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
}
