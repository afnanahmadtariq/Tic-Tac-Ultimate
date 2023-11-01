/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tic_Tac_Ultimate;

/**
 *
 * @author Pc
 */
public class Brain {
    private String difficulty;

    public Brain(){
        this.difficulty = Controller.difficulty;
    }
    public static int[] compTurn(int[][] board){
        return switch(Controller.difficulty){
            case "easy" -> easy(board);
            case "med" -> med(board);
            case "hard" -> hard(board);
            case "extreme" -> extreme(board);
            case "practice" -> practice(board);
            default -> new int[]{(int)(Math.random()*3),(int)(Math.random()*3)};
        };
        
        
    }
    private static int[] easy(int[][] board){
        int[] index = new int[]{(int)(Math.random()*3),(int)(Math.random()*3)};
        while(board[index[0]][index[1]] !=0)
            index = new int[]{(int)(Math.random()*3),(int)(Math.random()*3)};
        return index;
    }
    private static int[] med(int[][] board){
       return new int[]{(int)(Math.random()*3),(int)(Math.random()*3)};
    }
    private static int[] hard(int[][] board){
        return new int[]{(int)(Math.random()*3),(int)(Math.random()*3)};
    }
    private static int[] extreme(int[][] board){
        return new int[]{(int)(Math.random()*3),(int)(Math.random()*3)};
    }
    private static int[] practice(int[][] board){
        return new int[]{(int)(Math.random()*3),(int)(Math.random()*3)};
    }
    

}

