/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tic_Tac_Ultimate;

/**
 *
 * @author Pc
 */
public class SuperBrain {
    public static int[] compTurn(int[] superIndex, Board[][] superBoard,String difficulty){
        return switch(difficulty){
            case "easy" -> easy(superIndex, superBoard);
            case "med" -> med(superIndex, superBoard);
            case "hard" -> hard(superIndex, superBoard);
            case "extreme" -> extreme(superIndex, superBoard);
            case "practice" -> practice(superIndex, superBoard);
            default -> new int[]{(int)(Math.random()*3),(int)(Math.random()*3)};
        };
        
        
    }
    private static int[] easy(int[] superIndex,Board[][] superBoard){
        int[] index = new int[]{(int)(Math.random()*3),(int)(Math.random()*3)};
        while(superBoard[superIndex[0]][superIndex[1]].board[index[0]][index[1]] != 0)
            index = new int[]{(int)(Math.random()*3),(int)(Math.random()*3)};
        return index;
    }
    private static int[] med(int[] superIndex,Board[][] superBoard){
        for(int i=0;i<3;i++) {
            for(int j=1;j<=3;j++){
                if (superBoard[superIndex[0]][superIndex[1]].board[i][j-1] == superBoard[superIndex[0]][superIndex[1]].board[i][j%3] && superBoard[superIndex[0]][superIndex[1]].board[i][j%3]!=0 && superBoard[superIndex[0]][superIndex[1]].board[i][(j+1)%3] == 0)
                    return new int[] {i,(j+1)%3};
                else if (superBoard[superIndex[0]][superIndex[1]].board[j-1][i] == superBoard[superIndex[0]][superIndex[1]].board[j%3][i] && superBoard[superIndex[0]][superIndex[1]].board[j%3][i]!=0 && superBoard[superIndex[0]][superIndex[1]].board[(j+1)%3][i] == 0)
                    return new int[] {(j+1)%3,i};
            }
        }
        int[][] indexes = new int[][]{{0,2},{1,1},{2,0}};
        for(int i=1; i<=3; i++){
            if (superBoard[superIndex[0]][superIndex[1]].board[i-1][i-1] == superBoard[superIndex[0]][superIndex[1]].board[i%3][i%3] && superBoard[superIndex[0]][superIndex[1]].board[i%3][i%3]!=0 && superBoard[superIndex[0]][superIndex[1]].board[(i+1)%3][(i+1)%3] == 0)
                return new int[] {(i+1)%3,(i+1)%3};
            else if(superBoard[superIndex[0]][superIndex[1]].board[indexes[i-1][0]][indexes[i-1][1]] == superBoard[superIndex[0]][superIndex[1]].board[indexes[i%3][0]][indexes[i%3][1]] && superBoard[superIndex[0]][superIndex[1]].board[indexes[i%3][0]][indexes[i%3][1]]!=0 && superBoard[superIndex[0]][superIndex[1]].board[indexes[(i+1)%3][0]][indexes[(i+1)%3][1]] == 0)
                return indexes[(i+1)%3];
        }
        int[] index = new int[]{(int)(Math.random()*3),(int)(Math.random()*3)};
        while(superBoard[superIndex[0]][superIndex[1]].board[index[0]][index[1]] !=0)
            index = new int[]{(int)(Math.random()*3),(int)(Math.random()*3)};
        return index;
    }
    private static int[] hard(int[] superIndex,Board[][] superBoard){
        return new int[]{(int)(Math.random()*3),(int)(Math.random()*3)};
    }
    private static int[] extreme(int[] superIndex,Board[][] superBoard){
        return new int[]{(int)(Math.random()*3),(int)(Math.random()*3)};
    }
    private static int[] practice(int[] superIndex,Board[][] superBoard){
        return new int[]{(int)(Math.random()*3),(int)(Math.random()*3)};
    }
    

}
