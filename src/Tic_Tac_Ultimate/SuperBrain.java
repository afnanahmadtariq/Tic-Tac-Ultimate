package Tic_Tac_Ultimate;
public class SuperBrain {
    public static int[][] compTurn(int[] superIndex, Board[][] superBoard, String difficulty) {
        return easy(superIndex, superBoard); //When other difficulty levels would be final they would be called by switch statement, just like in Brain.java
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
}