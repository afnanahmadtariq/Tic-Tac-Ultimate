/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tic_Tac_Ultimate;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Pc
 */
public class SuperBoard {

    public Board[][] superBoard;
    public int game;
    public int winValue;;
    static Map<Integer, int[][]> dictionary;

    static {
        dictionary = new HashMap<>();
        int count = 1;
        for(int row=0; row<3; row++){
            dictionary.put(count++, new int[][]{{row,0},{row,1},{row,2}});
            int col = row;
            dictionary.put(count++, new int[][]{{0,col},{1,col},{2,col}});
        }
        dictionary.put(count++, new int[][]{{0,0},{1,1},{2,2}});
        dictionary.put(count, new int[][]{{0,2},{1,1},{2,0}});
    }
    public SuperBoard() {
        superBoard = new Board[3][3];
        game = -1;
        winValue = 0;
    }

    public int[] check(int[] superIndex){
        int[] win = new int[2];
        if (win(superIndex)){
            win[0] = 1;
            win[1] = check();
        }
        else if (draw(superIndex)){
            win[0] = 0;
            win[1] = check();
        }
        else{
            win[0] = -1;
            win[1] = check();
        }
        return win;
    }
    public int check() {
        if (win())
            return 1;
        else if (draw())
            return 0;
        else
            return -1;
    }

    public boolean win(int[] superIndex){
        int count = 1;
        for(int i=0;i<3;i++) {
            if (superBoard[superIndex[0]][superIndex[1]].board[i][0] == superBoard[superIndex[0]][superIndex[1]].board[i][1] && superBoard[superIndex[0]][superIndex[1]].board[i][1] == superBoard[superIndex[0]][superIndex[1]].board[i][2] && superBoard[superIndex[0]][superIndex[1]].board[i][2] != 0) {
                winValue = count;
                return true;
            }
            else
            if (superBoard[superIndex[0]][superIndex[1]].board[0][i] == superBoard[superIndex[0]][superIndex[1]].board[1][i] && superBoard[superIndex[0]][superIndex[1]].board[1][i] == superBoard[superIndex[0]][superIndex[1]].board[2][i] && superBoard[superIndex[0]][superIndex[1]].board[2][i] != 0){
                winValue = ++count;
                return true;
            }
            count +=2;
        }
        if (superBoard[superIndex[0]][superIndex[1]].board[0][0]==superBoard[superIndex[0]][superIndex[1]].board[1][1] && superBoard[superIndex[0]][superIndex[1]].board[1][1]==superBoard[superIndex[0]][superIndex[1]].board[2][2] && superBoard[superIndex[0]][superIndex[1]].board[2][2]!=0) {
            winValue = count;
            return true;
        }
        else if(superBoard[superIndex[0]][superIndex[1]].board[0][2] == superBoard[superIndex[0]][superIndex[1]].board[1][1] && superBoard[superIndex[0]][superIndex[1]].board[1][1] == superBoard[superIndex[0]][superIndex[1]].board[2][0] && superBoard[superIndex[0]][superIndex[1]].board[2][0]!=0){
            winValue = ++count;
            return true;
        }
        return false;
    }
    public boolean draw(int[] superIndex){
        for(int i=0;i<3;i++) {
            for(int j=1;j<=3;j++){
                if (superBoard[superIndex[0]][superIndex[1]].board[i][j-1] == superBoard[superIndex[0]][superIndex[1]].board[i][j%3] && (superBoard[superIndex[0]][superIndex[1]].board[i][j % 3] == 0 || superBoard[superIndex[0]][superIndex[1]].board[i][(j+1)%3] == 0))
                    return false;
                else if (superBoard[superIndex[0]][superIndex[1]].board[j-1][i] == superBoard[superIndex[0]][superIndex[1]].board[j%3][i] && (superBoard[superIndex[0]][superIndex[1]].board[j%3][i] == 0 || superBoard[superIndex[0]][superIndex[1]].board[(j+1)%3][i] == 0))
                    return false;
            }
        }
        int[][] indexes = new int[][]{{0,2},{1,1},{2,0}};
        for(int i=1; i<=3; i++){
            if (superBoard[superIndex[0]][superIndex[1]].board[i-1][i-1] == superBoard[superIndex[0]][superIndex[1]].board[i%3][i%3] && (superBoard[superIndex[0]][superIndex[1]].board[i%3][i%3] == 0 || superBoard[superIndex[0]][superIndex[1]].board[(i+1)%3][(i+1)%3] == 0))
                return false;
            else if(superBoard[superIndex[0]][superIndex[1]].board[indexes[i-1][0]][indexes[i-1][1]] == superBoard[superIndex[0]][superIndex[1]].board[indexes[i%3][0]][indexes[i%3][1]] && (superBoard[superIndex[0]][superIndex[1]].board[indexes[i%3][0]][indexes[i%3][1]] == 0 || superBoard[superIndex[0]][superIndex[1]].board[indexes[(i+1)%3][0]][indexes[(i+1)%3][1]] == 0))
                return false;
        }
        return true;
    }
    
    public boolean win() {
        int count = 1;
        for (int i = 0; i < 3; i++) {
            if (superBoard[i][0].game == superBoard[i][1].game && superBoard[i][1].game == superBoard[i][2].game && superBoard[i][2].game != -1 && superBoard[i][2].game != 0) {
                winValue = count;
                return true;
            }
            else if (superBoard[0][i].game == superBoard[1][i].game && superBoard[1][i].game == superBoard[2][i].game && superBoard[2][i].game != -1 && superBoard[2][i].game != 0) {
                winValue = ++count;
                return true;
            }
        }
        if (superBoard[0][0].game == superBoard[1][1].game && superBoard[1][1].game == superBoard[2][2].game && superBoard[2][2].game != -1 && superBoard[2][2].game != 0) {
            winValue = count;
            return true;
        }
        else if(superBoard[0][2].game == superBoard[1][1].game && superBoard[1][1].game == superBoard[2][0].game && superBoard[2][0].game != -1 && superBoard[2][0].game != 0){
            winValue = ++count;
            return true;
        }
        return  false;
    }
    public boolean draw(){
        for(int i=0;i<3;i++) {
            for(int j=1;j<=3;j++){
                if (superBoard[i][j-1].game == superBoard[i][j%3].game && (superBoard[i][j % 3].game == -1 || superBoard[i][(j+1)%3].game == -1))
                    return false;
                else if (superBoard[j-1][i].game == superBoard[j%3][i].game && (superBoard[j%3][i].game == -1 || superBoard[(j+1)%3][i].game == -1))
                    return false;
            }
        }
        int[][] indexes = new int[][]{{0,2},{1,1},{2,0}};
        for(int i=1; i<=3; i++){
            if (superBoard[i-1][i-1].game == superBoard[i%3][i%3].game && (superBoard[i%3][i%3].game == -1 || superBoard[(i+1)%3][(i+1)%3].game == -1))
                return false;
            else if(superBoard[indexes[i-1][0]][indexes[i-1][1]].game == superBoard[indexes[i%3][0]][indexes[i%3][1]].game && (superBoard[indexes[i%3][0]][indexes[i%3][1]].game == -1 || superBoard[indexes[(i+1)%3][0]][indexes[(i+1)%3][1]].game == -1))
                return false;
        }
        return true;
    }
}