/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tic_Tac_Ultimate;

/**
 *
 * @author Pc
 */
public class SuperController extends SuperBoard{
    private Player players[];
    private boolean singlePlayer;
    private int player;
    protected static String difficulty;
    private int[] superIndex;
    
    SuperController(){
        super();
        singlePlayer = true;
        difficulty = "medium";
        superIndex = new int[2];
    }
    SuperController(boolean singlePlayer, String difficulty){
        super();
        this.singlePlayer = singlePlayer;
        this.difficulty = difficulty;
    }
    public int[] checkTurn(int[] superIndex){
        if(player==2 && singlePlayer){
            int[] index = SuperBrain.compTurn(superIndex,super.superBoard);
            turn(index,superIndex);
            return index;
        }
        return superIndex;
    }
    public boolean turn(int[] index, int[] superIndex){
        if(super.superBoard[superIndex[0]][superIndex[1]].game != -1)
            return false;
        else if(super.superBoard[superIndex[0]][superIndex[1]].board[index[0]][index[1]] != 0)
            return false;
        if(super.superBoard[this.superIndex[0]][this.superIndex[1]].game != -1){
            this.superIndex = superIndex;
        }
        else if(this.superIndex[0] != superIndex[0] || this.superIndex[1] != superIndex[1])
            return false;

        super.superBoard[superIndex[0]][superIndex[1]].board[index[0]][index[1]] = player;
        int[] check = super.check(superIndex);
        switch(check[0]){
            case 1 -> end(true,superIndex);
            case 0 -> end(false,superIndex);
        }
        switch(check[1]){
            case 1 -> end(true);
            case 0 -> end(false);
        }
        player = (player+1)%2;
        superIndex = index;
        this.superIndex = checkTurn(superIndex);
        return true;
    }
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
    
    private void end(boolean win, int[] superIndex){
        if(win)
            super.superBoard[superIndex[0]][superIndex[1]].game = player;
        else
            super.superBoard[superIndex[0]][superIndex[1]].game = 0;
    }
    private void end(boolean win){
        if(win)
            super.game = player;
        else
            super.game = 0;
        //agr event listener game variable pe lga dain to ye func complete
        //boolean win se win ya draw ka pta chal rha
        //int player se kon jeeta ye pta lag rha
    }
}
