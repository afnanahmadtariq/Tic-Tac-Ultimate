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
    private String difficulty;

    public Brain(){
        this.difficulty = Controller.difficulty;
    }
    public static int[] compTurn(int[] superIndex, Board[][] superBoard){
        return switch(Controller.difficulty){
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
       return new int[]{(int)(Math.random()*3),(int)(Math.random()*3)};
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
