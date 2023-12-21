package Tic_Tac_Ultimate;



import static Tic_Tac_Ultimate.Brain.available;
import static Tic_Tac_Ultimate.Brain.checkMove;

public class BrainThread extends Thread{
    boolean turn;
    int[][] board;

    @Override
    public void run() {
        bestMove();
    }
    private int bestMove() {
        return bestMove(turn, board);
    }
    private int bestMove(boolean turn, int[][] board){

        if(checkMove(board) == -1) return -10;
        if(checkMove(board) == 1) return 10;
        if(!available(board)) return 0;

        if(turn){
            int bestScore = -1000;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board[row][col] == 0) {
                        board[row][col] = 2;
                        int Score = bestMove(false, board);
                        board[row][col] = 0;
                        if(Score > bestScore){
                            bestScore = Score;
                        }
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = 1000;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board[row][col] == 0) {
                        board[row][col] = 1;
                        int Score = bestMove(true, board);
                        board[row][col] = 0;
                        if(Score < bestScore){
                            bestScore = Score;
                        }
                    }
                }
            }
            return bestScore;
        }
    }
}
