package Tic_Tac_Ultimate;

import java.util.HashMap;
import java.util.Map;

public class Board {
    public int[][] board;
    public int game;
    public int winValue;
    public static Map<Integer, int[][]> dictionary;

    static {
        dictionary = new HashMap<>();
        int count = 1;
        for(int i=0; i<3; i++){
            dictionary.put(count++, new int[][]{{i,0},{i,1},{i,2}});
            dictionary.put(count++, new int[][]{{0, i},{1, i},{2, i}});
        }
        dictionary.put(count++, new int[][]{{0,0},{1,1},{2,2}});
        dictionary.put(count, new int[][]{{0,2},{1,1},{2,0}});
    }
    public Board(){
        board = new int[3][3];
        game = -1;
        winValue = 0;
    }
    public int check(){
        if(win())
            return 1;
        else if(draw())
            return 0;
        else
            return -1;
    }
    private boolean win(){
        int count = 1;
        for(int i=0;i<3;i++) {
            if (board[i][0]==board[i][1] && board[i][1]==board[i][2] && board[i][2]!=0){
                winValue = count;
                return true;
            }
            else if (board[0][i]==board[1][i] && board[1][i]==board[2][i] && board[2][i]!=0){
                winValue = ++count;
                return true;
            }
            count +=2;
        }
        if (board[0][0]==board[1][1] && board[1][1]==board[2][2] && board[2][2]!=0){
            winValue = count;
            return true;
        }
        else if(board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[2][0] != 0){
            winValue = ++count;
            return true;
        }
        return false;
    }
    private boolean draw(){
        for(int i=0;i<3;i++) {
            for(int j=1;j<=3;j++){
                if (board[i][j-1] == board[i][j%3] && (board[i][j % 3] == 0 || board[i][(j+1)%3] == 0))
                    return false;
                else if (board[j-1][i] == board[j%3][i] && (board[j%3][i] == 0 || board[(j+1)%3][i] == 0))
                    return false;
            }
        }
        int[][] indexes = new int[][]{{0,2},{1,1},{2,0}};
        for(int i=1; i<=3; i++){
            if (board[i-1][i-1] == board[i%3][i%3] && (board[i%3][i%3] == 0 || board[(i+1)%3][(i+1)%3] == 0))
                return false;
            else if(board[indexes[i-1][0]][indexes[i-1][1]] == board[indexes[i%3][0]][indexes[i%3][1]] && (board[indexes[i%3][0]][indexes[i%3][1]] == 0 || board[indexes[(i+1)%3][0]][indexes[(i+1)%3][1]] == 0))
                return false;
        }
        return true;
    }
}
