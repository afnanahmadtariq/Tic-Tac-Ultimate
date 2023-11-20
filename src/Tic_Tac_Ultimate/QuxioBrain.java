package Tic_Tac_Ultimate;

public class QuxioBrain     {
    public static int[][] compTurn(int[][] board, String difficulty) {
        return switch (difficulty) {
            case "Easy" -> easy(board);
            case "Medium" -> med(board);
            default -> easy(board);
        };
    }
    private static int[][] easy(int[][] board) {
        int[] index = new int[]{(int) (Math.random() * 3), (int) (Math.random() * 3)};
        while (board[index[0]][index[1]] == 1)
            index = new int[]{(int) (Math.random() * 3), (int) (Math.random() * 3)};
        int[] selectKarLoBhai = new int[] {0,4};
        int basHoGaiVariableNameBnaneSe = (int) (Math.random() * 2);
        if(index[0]!=0 && index[0]!=4)
            return new int[][] {index, new int[] {index[0],selectKarLoBhai[basHoGaiVariableNameBnaneSe]}};
        else if(index[1]!=0 && index[1]!=4)
            return new int[][] {index, new int[] {selectKarLoBhai[basHoGaiVariableNameBnaneSe], index[1]}};
        else
            return new int[][] {index, new int[] {index[0], index[1]==4?0:4}};
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
}