public class Board {
    public int[][] board = new int[3][3];
    public int game;
    public Board(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++)
                board[i][j] = 0;
        }
        game = 0;
    }
    public boolean win(){
        for(int i=0;i<3;i++) {
            if (board[i][0]==board[i][1] && board[i][1]==board[i][2] && board[i][2]!=0)
                return true;
            else if (board[0][i]==board[1][i] && board[1][i]==board[2][i] && board[2][i]!=0)
                return true;
        }
        if (board[0][0]==board[1][1] && board[1][1]==board[2][2] && board[2][2]!=0)
            return true;
        else return board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[2][0]!=0;
    }
}
