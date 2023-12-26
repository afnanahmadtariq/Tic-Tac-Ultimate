package Tic_Tac_Ultimate;
public class UltimateBrain {
    public static int[][] compTurn(int[] superIndex, Board[][] superBoard, String difficulty) {
        return switch(difficulty){
            case "Easy" -> easy(superIndex, superBoard);
            case "Medium" -> med(superIndex, superBoard);
            case "Hard" -> hard(superIndex, superBoard);
            case "Extreme" -> extreme(superIndex, superBoard);
            case "Practice" -> practice(superIndex, superBoard);
            default -> new int[][]{{(int)(Math.random()*3),(int)(Math.random()*3)},{(int)(Math.random()*3),(int)(Math.random()*3)}};
        };
    }
    private static int[][] easy(int[] superIndex, Board[][] superBoard) {
        if (superIndex[0] == -1) {
            superIndex = new int[]{(int) (Math.random() * 3), (int) (Math.random() * 3)};
            while (superBoard[superIndex[0]][superIndex[1]].game != -1)
                superIndex = new int[]{(int) (Math.random() * 3), (int) (Math.random() * 3)};
        }
        int[] index = new int[]{(int) (Math.random() * 3), (int) (Math.random() * 3)};
        while (superBoard[superIndex[0]][superIndex[1]].board[index[0]][index[1]] != 0)
            index = new int[]{(int) (Math.random() * 3), (int) (Math.random() * 3)};
        return new int[][]{index, superIndex};
    }
    private static int[][] med(int[] superIndex,Board[][] superBoard){
//        for(int i=0;i<3;i++) {
//            for(int j=1;j<=3;j++){
//                if (superBoard[superIndex[0]][superIndex[1]].board[i][j-1] == superBoard[superIndex[0]][superIndex[1]].board[i][j%3] && superBoard[superIndex[0]][superIndex[1]].board[i][j%3]!=0 && superBoard[superIndex[0]][superIndex[1]].board[i][(j+1)%3] == 0)
//                    return new int[] {i,(j+1)%3};
//                else if (superBoard[superIndex[0]][superIndex[1]].board[j-1][i] == superBoard[superIndex[0]][superIndex[1]].board[j%3][i] && superBoard[superIndex[0]][superIndex[1]].board[j%3][i]!=0 && superBoard[superIndex[0]][superIndex[1]].board[(j+1)%3][i] == 0)
//                    return new int[] {(j+1)%3,i};
//            }
//        }
//        int[][] indexes = new int[][]{{0,2},{1,1},{2,0}};
//        for(int i=1; i<=3; i++){
//            if (superBoard[superIndex[0]][superIndex[1]].board[i-1][i-1] == superBoard[superIndex[0]][superIndex[1]].board[i%3][i%3] && superBoard[superIndex[0]][superIndex[1]].board[i%3][i%3]!=0 && superBoard[superIndex[0]][superIndex[1]].board[(i+1)%3][(i+1)%3] == 0)
//                return new int[] {(i+1)%3,(i+1)%3};
//            else if(superBoard[superIndex[0]][superIndex[1]].board[indexes[i-1][0]][indexes[i-1][1]] == superBoard[superIndex[0]][superIndex[1]].board[indexes[i%3][0]][indexes[i%3][1]] && superBoard[superIndex[0]][superIndex[1]].board[indexes[i%3][0]][indexes[i%3][1]]!=0 && superBoard[superIndex[0]][superIndex[1]].board[indexes[(i+1)%3][0]][indexes[(i+1)%3][1]] == 0)
//                return indexes[(i+1)%3];
//        }
        int[] index = new int[]{(int)(Math.random()*3),(int)(Math.random()*3)};
        while(superBoard[superIndex[0]][superIndex[1]].board[index[0]][index[1]] !=0)
            index = new int[]{(int)(Math.random()*3),(int)(Math.random()*3)};
        return new int[][] {index,superIndex};
    }
    private static int[][] hard(int[] superIndex,Board[][] superBoard){
        return new int[][]{{(int)(Math.random()*3),(int)(Math.random()*3)},{(int)(Math.random()*3),(int)(Math.random()*3)}};
    }
    private static int[][] extreme(int[] superIndex,Board[][] superBoard){
        boolean empty = true;
        for(int i =0 ; i <3 ; i++){
            for(int j = 0; j < 3 ; j++){
                for (int x =0 ; x <3 ; x++){
                    for(int y =0 ; y <3 ; y++){
                        if (superBoard[i][j].board[x][y] != 0) {
                            empty = false;
                            break;
                        }
                    }
                }

            }
        }
        if(empty) return(easy(superIndex, superBoard));
        int row, column;
        row =  column = -1;
        int bestScore = -1000;
        for(int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                for (int x =0 ; x <3 ; x++) {
                    for (int y = 0; y < 3; y++) {
                        if (superBoard[i][j].board[x][y] == 0) {
                            superBoard[i][j].board[x][y] = 2;
                            int score = bestMove(false, new int[]{x, y}, superBoard, Integer.MIN_VALUE, Integer.MAX_VALUE);
                            superBoard[i][j].board[x][y] = 0;
                            if (score > bestScore) {
                                bestScore = score;
                                row = i;
                                column = j;
                            }
                        }
                    }
                }
            }
        }
        return new int[][]{{(int)(Math.random()*3),(int)(Math.random()*3)},{(int)(Math.random()*3),(int)(Math.random()*3)}};
    }
    public static boolean available(Board[][] superBoard){
        for(int i =0 ; i <3 ; i++){
            for(int j = 0; j < 3 ; j++){
                for (int x =0 ; x <3 ; x++) {
                    for (int y = 0; y < 3; y++) {
                        if (superBoard[i][j].board[x][y] == 0) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    private static int bestMove(boolean maximizingPlayer, int[] superIndex, Board[][] superBoard, int alpha, int beta) {
        int result = checkMove(board);
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
                    for (int x =0 ; x <3 ; x++) {
                        for (int y = 0; y < 3; y++) {
                            if (superBoard[superIndex[0]][superIndex[1]].board[row][col] == 0) {
                                superBoard[row][col].board[x][y] = 2;
                                int score = bestMove(false, new int[]{x, y}, superBoard, alpha, beta);
                                superBoard[row][col].board[x][y] = 0;
                                bestScore = Math.max(bestScore, score);
                                alpha = Math.max(alpha, bestScore);
                                if (beta <= alpha) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    for (int x =0 ; x <3 ; x++) {
                        for (int y = 0; y < 3; y++) {
                            if (superBoard[row][col].board[x][y] == 0) {
                                superBoard[row][col].board[x][y] = 1;
                                int score = bestMove(true, new int[]{x, y}, superBoard, alpha, beta);
                                superBoard[row][col].board[x][y] = 0;
                                bestScore = Math.min(bestScore, score);
                                beta = Math.min(beta, bestScore);
                                if (beta <= alpha) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            return bestScore;
        }
    }

    private static int checkMove(int[] superIndex, Board[][] superBoard){
        for(int row = 0; row < 3; row++)
            if(board[row][0]==board[row][1] && board[row][1]==board[row][2] && board[row][1] != 0) {
                if (board[row][1] == 1)
                    return -1;
                else if(board[row][1] == 2)
                    return 1;
            }

        for(int col = 0; col < 3; col++)
            if(board[0][col]==board[1][col] && board[1][col]==board[2][col] && board[1][col] != 0){
                if (board[1][col] == 1)
                    return -1;
                else if(board[1][col] == 2)
                    return 1;
            }

        if(board[0][0]==board[1][1] && board[1][1]==board[2][2] && board[1][1] != 0)
            if (board[1][1] == 1)
                return -1;
            else if(board[1][1] == 2)
                return 1;
        if(board[0][2]==board[1][1] && board[1][1]==board[2][0] && board[1][1] != 0)
            if (board[1][1] == 1)
                return -1;
            else if(board[1][1] == 2)
                return 1;

        return 0;
    }
    private static int[][] practice(int[] superIndex,Board[][] superBoard){
        return new int[][]{{(int)(Math.random()*3),(int)(Math.random()*3)},{(int)(Math.random()*3),(int)(Math.random()*3)}};
    }
}