package Tic_Tac_Ultimate;
public class Brain {
    public static int[] compTurn(int[][] board, String difficulty){
        return switch(difficulty){
            case "easy" -> easy(board);
            case "med" -> med(board);
            case "hard" -> hard(board);
            case "extreme" -> extreme(board);
            case "practice" -> practice(board);
            default -> new int[]{(int)(Math.random()*3),(int)(Math.random()*3)};
        };
    }
    private static int[] easy(int[][] board){
        int[] index = new int[]{(int)(Math.random()*3),(int)(Math.random()*3)};
        while(board[index[0]][index[1]] !=0)
            index = new int[]{(int)(Math.random()*3),(int)(Math.random()*3)};
//        try {
//            // Delay for 1 second (1000 milliseconds)
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            // Handle any exceptions that may occur
//            e.printStackTrace();
//        }
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
        return new int[]{(int)(Math.random()*3),(int)(Math.random()*3)};
    }
    private static int[] extreme(int[][] board){
        return new int[]{(int)(Math.random()*3),(int)(Math.random()*3)};
    }
    private static int[] practice(int[][] board){
        return new int[]{(int)(Math.random()*3),(int)(Math.random()*3)};
    }
    

}

