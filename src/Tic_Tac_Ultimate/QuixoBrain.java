package Tic_Tac_Ultimate;

public class QuixoBrain {
    public static int[][] compTurn(QuixoController board, String difficulty) {
        return switch (difficulty) {
            case "Easy" -> easy(board.board);
            case "Medium" -> med(board.board);
            case "Hard" -> hard(board.board);
            case "Extreme" -> extreme(board);
            default -> easy(board.board);
        };
    }
    private static int[][] easy(int[][] board) {
        int[] index = new int[]{rand(false), rand(false)};
        while (board[index[0]][index[1]] == 1 && (index[0] == 0 || index[0] == 4 || index[1]==0 || index[1] == 4)) {
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
    private static int[][] extreme(QuixoController controller){
        int[] draw = new int[2], insert = new int[2];
        int bestScore = 0;
        for(int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                if(i==0 || i==4 || j==0 || j==4){
                    if(controller.board[i][j] != 1) {
                        if(i!=0){
                            controller.markTurn(new int[] {i,j},new int[] {0,j});
                            int score = bestMove(false, controller);
                            controller.markTurn(new int[] {0,j},new int[] {i,j});
                            if (score > bestScore) {
                                bestScore = score;
                                draw = new int[] {i,j};
                                insert = new int[] {0,j};
                            }
                        }
                        if(i!=4){
                            controller.markTurn(new int[] {i,j},new int[] {4,j});
                            int score = bestMove(false, controller);
                            controller.markTurn(new int[] {4,j},new int[] {i,j});
                            if (score > bestScore) {
                                bestScore = score;
                                draw = new int[] {i,j};
                                insert = new int[] {4,j};
                            }
                        }
                        if(j!=0){
                            controller.markTurn(new int[] {i,j},new int[] {i,0});
                            int score = bestMove(false, controller);
                            controller.markTurn(new int[] {i,0},new int[] {i,j});
                            if (score > bestScore) {
                                bestScore = score;
                                draw = new int[] {i,j};
                                insert = new int[] {i,0};
                            }
                        }
                        if(j!=4){
                            controller.markTurn(new int[] {i,j},new int[] {i,4});
                            int score = bestMove(false, controller);
                            controller.markTurn(new int[] {i,4},new int[] {i,j});
                            if (score > bestScore) {
                                bestScore = score;
                                draw = new int[] {i,j};
                                insert = new int[] {i,4};
                            }
                        }
                    }
                }
            }
        }
        return new int[][]{draw,insert};
    }
    private static int bestMove(boolean userTurn, QuixoController controller){
        if(controller.check(2) == 1) return -1;
        if(controller.check(2) == 2) return 1;

        if(userTurn){
            int bestScore = 0;
            for(int i=0; i<5;i++){
                for(int j=0; j<5; j++){
                    if(i==0 || i==4){
                        if(controller.board[i][j] != 1) {
                            int temp = controller.board[i][j];
                            controller.board[i][j] = 2;
                            int score = bestMove(false, controller);
                            controller.board[i][j] = temp;
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
                    if (controller.board[row][col] == 0) {
                        controller.board[row][col] = 1;
                        int Score = bestMove(true, controller);
                        controller.board[row][col] = 0;
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