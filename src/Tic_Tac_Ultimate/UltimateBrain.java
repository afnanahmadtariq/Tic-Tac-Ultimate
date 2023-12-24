package Tic_Tac_Ultimate;
public class UltimateBrain {
    public static int[][] compTurn(int[] superIndex, TicTacToeBoard[][] superTicTacToeBoard, String difficulty) {
        return switch(difficulty){
            case "Easy" -> easy(superIndex, superTicTacToeBoard);
            case "Medium" -> med(superIndex, superTicTacToeBoard);
            case "Hard" -> hard(superIndex, superTicTacToeBoard);
            case "Extreme" -> extreme(superIndex, superTicTacToeBoard);
            case "Practice" -> practice(superIndex, superTicTacToeBoard);
            default -> new int[][]{{(int)(Math.random()*3),(int)(Math.random()*3)},{(int)(Math.random()*3),(int)(Math.random()*3)}};
        };
    }
    private static int[][] easy(int[] superIndex, TicTacToeBoard[][] superTicTacToeBoard) {
        if (superIndex[0] == -1) {
            superIndex = new int[]{(int) (Math.random() * 3), (int) (Math.random() * 3)};
            while (superTicTacToeBoard[superIndex[0]][superIndex[1]].game != -1)
                superIndex = new int[]{(int) (Math.random() * 3), (int) (Math.random() * 3)};
        }
        int[] index = new int[]{(int) (Math.random() * 3), (int) (Math.random() * 3)};
        while (superTicTacToeBoard[superIndex[0]][superIndex[1]].board[index[0]][index[1]] != 0)
            index = new int[]{(int) (Math.random() * 3), (int) (Math.random() * 3)};
        return new int[][]{index, superIndex};
    }
    private static int[][] med(int[] superIndex, TicTacToeBoard[][] superTicTacToeBoard){
//        for(int i=0;i<3;i++) {
//            for(int j=1;j<=3;j++){
//                if (superTicTacToeBoard[superIndex[0]][superIndex[1]].board[i][j-1] == superTicTacToeBoard[superIndex[0]][superIndex[1]].board[i][j%3] && superTicTacToeBoard[superIndex[0]][superIndex[1]].board[i][j%3]!=0 && superTicTacToeBoard[superIndex[0]][superIndex[1]].board[i][(j+1)%3] == 0)
//                    return new int[] {i,(j+1)%3};
//                else if (superTicTacToeBoard[superIndex[0]][superIndex[1]].board[j-1][i] == superTicTacToeBoard[superIndex[0]][superIndex[1]].board[j%3][i] && superTicTacToeBoard[superIndex[0]][superIndex[1]].board[j%3][i]!=0 && superTicTacToeBoard[superIndex[0]][superIndex[1]].board[(j+1)%3][i] == 0)
//                    return new int[] {(j+1)%3,i};
//            }
//        }
//        int[][] indexes = new int[][]{{0,2},{1,1},{2,0}};
//        for(int i=1; i<=3; i++){
//            if (superTicTacToeBoard[superIndex[0]][superIndex[1]].board[i-1][i-1] == superTicTacToeBoard[superIndex[0]][superIndex[1]].board[i%3][i%3] && superTicTacToeBoard[superIndex[0]][superIndex[1]].board[i%3][i%3]!=0 && superTicTacToeBoard[superIndex[0]][superIndex[1]].board[(i+1)%3][(i+1)%3] == 0)
//                return new int[] {(i+1)%3,(i+1)%3};
//            else if(superTicTacToeBoard[superIndex[0]][superIndex[1]].board[indexes[i-1][0]][indexes[i-1][1]] == superTicTacToeBoard[superIndex[0]][superIndex[1]].board[indexes[i%3][0]][indexes[i%3][1]] && superTicTacToeBoard[superIndex[0]][superIndex[1]].board[indexes[i%3][0]][indexes[i%3][1]]!=0 && superTicTacToeBoard[superIndex[0]][superIndex[1]].board[indexes[(i+1)%3][0]][indexes[(i+1)%3][1]] == 0)
//                return indexes[(i+1)%3];
//        }
        int[] index = new int[]{(int)(Math.random()*3),(int)(Math.random()*3)};
        while(superTicTacToeBoard[superIndex[0]][superIndex[1]].board[index[0]][index[1]] !=0)
            index = new int[]{(int)(Math.random()*3),(int)(Math.random()*3)};
        return new int[][] {index,superIndex};
    }
    private static int[][] hard(int[] superIndex, TicTacToeBoard[][] superTicTacToeBoard){
        return new int[][]{{(int)(Math.random()*3),(int)(Math.random()*3)},{(int)(Math.random()*3),(int)(Math.random()*3)}};
    }
    private static int[][] extreme(int[] superIndex, TicTacToeBoard[][] superTicTacToeBoard){
        return new int[][]{{(int)(Math.random()*3),(int)(Math.random()*3)},{(int)(Math.random()*3),(int)(Math.random()*3)}};
    }
    private static int[][] practice(int[] superIndex, TicTacToeBoard[][] superTicTacToeBoard){
        return new int[][]{{(int)(Math.random()*3),(int)(Math.random()*3)},{(int)(Math.random()*3),(int)(Math.random()*3)}};
    }
}