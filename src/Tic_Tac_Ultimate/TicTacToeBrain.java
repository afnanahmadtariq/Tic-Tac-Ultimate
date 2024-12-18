package Tic_Tac_Ultimate;

public class TicTacToeBrain {
    public static int[] compTurn(int[][] board, String difficulty) {
        return switch (difficulty) {
            case "Easy" -> easy(board);
            case "Medium" -> med(board);
            case "Hard" -> hard(board);
            case "Extreme" -> extreme(board);
            case "Practice" -> practice(board);
            default -> new int[]{(int)(Math.random()*3),(int)(Math.random()*3)};
        };
    }
    private static int[] easy(int[][] board) {
        int[] index = new int[]{(int) (Math.random() * 3), (int) (Math.random() * 3)};
        while (board[index[0]][index[1]] != 0)
            index = new int[]{(int) (Math.random() * 3), (int) (Math.random() * 3)};
        return index;
    }
    private static int[] med(int[][] board){
        for(int i=0;i<3;i++) {
            for(int j=1;j<=3;j++){
                if (board[i][j-1] == board[i][j%3] && board[i][j%3]!=0 && board[i][(j+1)%3] == 0)
                    return new int[] {i,(j+1)%3};
                else if (board[j-1][i] == board[j%3][i] && board[j%3][i]!=0 && board[(j+1)%3][i] == 0)
                    return new int[] {(j+1)%3,i};
            }
        }
        int[][] indexes = new int[][]{{0,2},{1,1},{2,0}};
        for(int i=1; i<=3; i++){
            if (board[i-1][i-1] == board[i%3][i%3] && board[i%3][i%3]!=0 && board[(i+1)%3][(i+1)%3] == 0)
                return new int[] {(i+1)%3,(i+1)%3};
            else if(board[indexes[i-1][0]][indexes[i-1][1]] == board[indexes[i%3][0]][indexes[i%3][1]] && board[indexes[i%3][0]][indexes[i%3][1]]!=0 && board[indexes[(i+1)%3][0]][indexes[(i+1)%3][1]] == 0)
                return indexes[(i+1)%3];
        }
        int[] index = new int[]{(int)(Math.random()*3),(int)(Math.random()*3)};
        while(board[index[0]][index[1]] !=0)
            index = new int[]{(int)(Math.random()*3),(int)(Math.random()*3)};
        return index;
    }
    private static int[] hard(int[][] board){
        int chance = (int)(Math.random()*10+1);
        if(chance == 2 || chance == 4 || chance == 6)
            return med(board);
        else if(chance == 5 || chance == 9)
            return easy(board);
        else
            return extreme(board);
    }
    private static int[] extreme(int[][] board){
        boolean empty = true;
        for(int i =0 ; i <3 ; i++){
            for(int j = 0; j < 3 ; j++){
                if (board[i][j] != 0) {
                    empty = false;
                    break;
                }
            }
        }
        if(empty) return(easy(board));
        int row, column;
        row =  column = -1;
        int bestScore = -1000;
        for(int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if(board[i][j] == 0){
                    board[i][j] = 2;
                    int score = bestMove(false, board);
                    board[i][j] = 0;
                    if(score > bestScore){
                        bestScore = score;
                        row = i;
                        column = j;
                    }
                }
            }
        }
        return new int[]{row, column};
    }
    private static boolean available(int[][] board){
        for(int i =0 ; i <3 ; i++){
            for(int j = 0; j < 3 ; j++){
                if(board[i][j] == 0){
                    return true;
                }
            }
        }
        return false;
    }
    private static int bestMove(boolean turn, int[][] board){

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
    private static int checkMove(int[][] board){
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
    private static int[] practice(int[][] board){
        return new int[]{(int)(Math.random()*3),(int)(Math.random()*3)};
    }
}