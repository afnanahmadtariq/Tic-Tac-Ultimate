package Tic_Tac_Ultimate;

public class QuixoBrain {
    public static int[][] compTurn(QuixoBoard board, String difficulty) {
        return switch (difficulty) {
            case "Easy" -> easy(board.board);
            case "Medium" -> med(board.board);
            case "Hard" -> hard(board.board);
            case "Extreme" -> extreme(board);
            case "Practice" -> practice(board.board);
            default -> easy(board.board);
        };
    }
    private static int[][] easy(int[][] board) {
        int[] index = new int[]{rand(false), rand(false)};
        while (board[index[0]][index[1]] == 1) {
            index = new int[]{rand(false), rand(false)};
        }
        int[] insert = new int[] {index[0], index[1]};
        while(insert[0]==index[0] && insert[1]==index[1]){
            int randInt = (int)(Math.random()*2);
            if(randInt==0)
                insert = new int[] {rand(true), index[1]};
            else
                insert = new int[] {index[0], rand(true)};
        }
        return new int[][] {index, insert};
    }
    private static int rand(boolean end){
        if(end)
            return new int[]{0, 4}[(int)(Math.random()*2)];
        else
            return (int)(Math.random()*5);
    }
    private static int[][] med(int[][] board) {
//        for (int i = 0; i < 3; i++) {
//            for (int j = 1; j <= 3; j++) {
//                if (board[i][j - 1] == board[i][j % 3] && board[i][j % 3] != 0 && board[i][(j + 1) % 3] == 0)
//                    return new int[]{i, (j + 1) % 3};
//                else if (board[j - 1][i] == board[j % 3][i] && board[j % 3][i] != 0 && board[(j + 1) % 3][i] == 0)
//                    return new int[]{(j + 1) % 3, i};
//            }
//        }
//        int[][] indexes = new int[][]{{0, 2}, {1, 1}, {2, 0}};
//        for (int i = 1; i <= 3; i++) {
//            if (board[i - 1][i - 1] == board[i % 3][i % 3] && board[i % 3][i % 3] != 0 && board[(i + 1) % 3][(i + 1) % 3] == 0)
//                return new int[]{(i + 1) % 3, (i + 1) % 3};
//            else if (board[indexes[i - 1][0]][indexes[i - 1][1]] == board[indexes[i % 3][0]][indexes[i % 3][1]] && board[indexes[i % 3][0]][indexes[i % 3][1]] != 0 && board[indexes[(i + 1) % 3][0]][indexes[(i + 1) % 3][1]] == 0)
//                return indexes[(i + 1) % 3];
//        }
        return easy(board);
    }
    private static int[][] hard(int[][] board){
        return easy(board);
    }
    private static int[][] extreme(QuixoBoard board){
//        QuixoController game = new QuixoController(board.board);
        int[] draw, insert;
        int row, column;
        row =  column = -1;
        int bestScore = 0;
        for(int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                if(i==0 || i==4 || j==0 || j==4){
                    if(board.board[i][j] != 1) {
                        int temp = board.board[i][j];
                        if(0!=i){

                            int score = bestMove(false, board);
                            board.board[i][j] = temp;
                            if (score > bestScore) {
                                bestScore = score;

                            }

                        }
                    }
                }
            }
        }
        return new int[][]{{row, column},{}};
    }
    private static int bestMove(boolean userTurn, QuixoBoard board){
        if(board.check(2) == 1) return -1;
        if(board.check(2) == 2) return 1;

        if(userTurn){
            int bestScore = 0;
            for(int i=0; i<5;i++){
                for(int j=0; j<5; j++){
                    if(i==0 || i==4){
                        if(board.board[i][j] != 1) {
                            int temp = board.board[i][j];
                            board.board[i][j] = 2;
                            int score = bestMove(false, board);
                            board.board[i][j] = temp;
                            if (score > bestScore) {
                                bestScore = score;
                            }
                        }
                    }
                }
            }
            return bestScore;
        }
        else {
            int bestScore = 1000;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board.board[row][col] == 0) {
                        board.board[row][col] = 1;
                        int Score = bestMove(true, board);
                        board.board[row][col] = 0;
                        if(Score < bestScore){
                            bestScore = Score;
                        }
                    }
                }
            }
            return bestScore;
        }
    }
    private static int[][] practice(int[][] board){
        return easy(board);
    }
}