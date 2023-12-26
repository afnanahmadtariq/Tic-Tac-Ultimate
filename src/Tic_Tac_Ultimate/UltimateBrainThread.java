package Tic_Tac_Ultimate;


import static Tic_Tac_Ultimate.UltimateBrain.available;
import static Tic_Tac_Ultimate.UltimateBrain.checkMove;

public class UltimateBrainThread extends Thread{
    boolean turn;
    int alpha, beta;
    int[] superIndex = new int[]{-2,-2};
    Board[][] superBoard;
    int score;

    @Override
    public void run() {
        score = bestMove(turn, superIndex, superBoard, alpha, beta);
    }
    private static int bestMove(boolean maximizingPlayer, int[] superIndex, Board[][] superBoard, int alpha, int beta) {
        int result = checkMove(superIndex, superBoard);
        if (result != 0) {
            return result;
        }
        if (!available(superBoard)) {
            return 0;
        }
        if (maximizingPlayer) {
            int bestScore = Integer.MIN_VALUE;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (superBoard[superIndex[0]][superIndex[1]].board[row][col] == 0) {
                        superBoard[superIndex[0]][superIndex[1]].board[row][col] = 2;
                        int score = bestMove(false, new int[]{row, col}, superBoard, alpha, beta);
                        superBoard[superIndex[0]][superIndex[1]].board[row][col] = 0;
                        bestScore = Math.max(bestScore, score);
                        alpha = Math.max(alpha, bestScore);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
//            System.out.println(bestScore);
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (superBoard[superIndex[0]][superIndex[1]].board[row][col] == 0) {
                        superBoard[superIndex[0]][superIndex[1]].board[row][col] = 1;
                        int score = bestMove(true, new int[]{row, col}, superBoard, alpha, beta);
                        superBoard[superIndex[0]][superIndex[1]].board[row][col] = 0;
                        bestScore = Math.min(bestScore, score);
                        beta = Math.min(beta, bestScore);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
//            System.out.println(bestScore);
            return bestScore;
        }
    }
    public int getScore(){
        return score;
    }
}
