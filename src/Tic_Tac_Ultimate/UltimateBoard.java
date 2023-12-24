package Tic_Tac_Ultimate;

public class UltimateBoard {
    public TicTacToeBoard[][] superTicTacToeBoard;
    public int game;
    public int winValue;
    public UltimateBoard() {
        superTicTacToeBoard = new TicTacToeBoard[3][3];
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++)
                superTicTacToeBoard[i][j] = new TicTacToeBoard();
        }
        game = -1;
        winValue = 0;
    }
    public int check(int[] superIndex){
        if (win(superIndex))
            return 1;
        else if (draw(superIndex))
            return 0;
        else
            return -1;
    }
    public int check() {
        if (win())
            return 1;
        else if (draw())
            return 0;
        else
            return -1;
    }
    private boolean win(int[] superIndex){
        int count = 1;
        for(int i=0;i<3;i++) {
            if (superTicTacToeBoard[superIndex[0]][superIndex[1]].board[i][0] == superTicTacToeBoard[superIndex[0]][superIndex[1]].board[i][1] && superTicTacToeBoard[superIndex[0]][superIndex[1]].board[i][1] == superTicTacToeBoard[superIndex[0]][superIndex[1]].board[i][2] && superTicTacToeBoard[superIndex[0]][superIndex[1]].board[i][2] != 0) {
                winValue = count;
                return true;
            }
            else
            if (superTicTacToeBoard[superIndex[0]][superIndex[1]].board[0][i] == superTicTacToeBoard[superIndex[0]][superIndex[1]].board[1][i] && superTicTacToeBoard[superIndex[0]][superIndex[1]].board[1][i] == superTicTacToeBoard[superIndex[0]][superIndex[1]].board[2][i] && superTicTacToeBoard[superIndex[0]][superIndex[1]].board[2][i] != 0){
                winValue = ++count;
                return true;
            }
            count +=2;
        }
        if (superTicTacToeBoard[superIndex[0]][superIndex[1]].board[0][0]== superTicTacToeBoard[superIndex[0]][superIndex[1]].board[1][1] && superTicTacToeBoard[superIndex[0]][superIndex[1]].board[1][1]== superTicTacToeBoard[superIndex[0]][superIndex[1]].board[2][2] && superTicTacToeBoard[superIndex[0]][superIndex[1]].board[2][2]!=0) {
            winValue = count;
            return true;
        }
        else if(superTicTacToeBoard[superIndex[0]][superIndex[1]].board[0][2] == superTicTacToeBoard[superIndex[0]][superIndex[1]].board[1][1] && superTicTacToeBoard[superIndex[0]][superIndex[1]].board[1][1] == superTicTacToeBoard[superIndex[0]][superIndex[1]].board[2][0] && superTicTacToeBoard[superIndex[0]][superIndex[1]].board[2][0]!=0){
            winValue = ++count;
            return true;
        }
        return false;
    }
    private boolean draw(int[] superIndex){
        for(int i=0;i<3;i++) {
            for(int j=1;j<=3;j++){
                if (superTicTacToeBoard[superIndex[0]][superIndex[1]].board[i][j-1] == superTicTacToeBoard[superIndex[0]][superIndex[1]].board[i][j%3] && (superTicTacToeBoard[superIndex[0]][superIndex[1]].board[i][j % 3] == 0 || superTicTacToeBoard[superIndex[0]][superIndex[1]].board[i][(j+1)%3] == 0))
                    return false;
                else if (superTicTacToeBoard[superIndex[0]][superIndex[1]].board[j-1][i] == superTicTacToeBoard[superIndex[0]][superIndex[1]].board[j%3][i] && (superTicTacToeBoard[superIndex[0]][superIndex[1]].board[j%3][i] == 0 || superTicTacToeBoard[superIndex[0]][superIndex[1]].board[(j+1)%3][i] == 0))
                    return false;
            }
        }
        int[][] indexes = new int[][]{{0,2},{1,1},{2,0}};
        for(int i=1; i<=3; i++){
            if (superTicTacToeBoard[superIndex[0]][superIndex[1]].board[i-1][i-1] == superTicTacToeBoard[superIndex[0]][superIndex[1]].board[i%3][i%3] && (superTicTacToeBoard[superIndex[0]][superIndex[1]].board[i%3][i%3] == 0 || superTicTacToeBoard[superIndex[0]][superIndex[1]].board[(i+1)%3][(i+1)%3] == 0))
                return false;
            else if(superTicTacToeBoard[superIndex[0]][superIndex[1]].board[indexes[i-1][0]][indexes[i-1][1]] == superTicTacToeBoard[superIndex[0]][superIndex[1]].board[indexes[i%3][0]][indexes[i%3][1]] && (superTicTacToeBoard[superIndex[0]][superIndex[1]].board[indexes[i%3][0]][indexes[i%3][1]] == 0 || superTicTacToeBoard[superIndex[0]][superIndex[1]].board[indexes[(i+1)%3][0]][indexes[(i+1)%3][1]] == 0))
                return false;
        }
        return true;
    }
    private boolean win() {
        int count = 1;
        for (int i = 0; i < 3; i++) {
            if (superTicTacToeBoard[i][0].game == superTicTacToeBoard[i][1].game && superTicTacToeBoard[i][1].game == superTicTacToeBoard[i][2].game && superTicTacToeBoard[i][2].game != -1 && superTicTacToeBoard[i][2].game != 0) {
                winValue = count;
                return true;
            }
            else if (superTicTacToeBoard[0][i].game == superTicTacToeBoard[1][i].game && superTicTacToeBoard[1][i].game == superTicTacToeBoard[2][i].game && superTicTacToeBoard[2][i].game != -1 && superTicTacToeBoard[2][i].game != 0) {
                winValue = ++count;
                return true;
            }
            count +=2;
        }
        if (superTicTacToeBoard[0][0].game == superTicTacToeBoard[1][1].game && superTicTacToeBoard[1][1].game == superTicTacToeBoard[2][2].game && superTicTacToeBoard[2][2].game != -1 && superTicTacToeBoard[2][2].game != 0) {
            winValue = count;
            return true;
        }
        else if(superTicTacToeBoard[0][2].game == superTicTacToeBoard[1][1].game && superTicTacToeBoard[1][1].game == superTicTacToeBoard[2][0].game && superTicTacToeBoard[2][0].game != -1 && superTicTacToeBoard[2][0].game != 0){
            winValue = ++count;
            return true;
        }
        return  false;
    }
    private boolean draw(){
        for(int i=0;i<3;i++) {
            for(int j=1;j<=3;j++){
                if (superTicTacToeBoard[i][j-1].game == superTicTacToeBoard[i][j%3].game && (superTicTacToeBoard[i][j % 3].game == -1 || superTicTacToeBoard[i][(j+1)%3].game == -1))
                    return false;
                else if (superTicTacToeBoard[j-1][i].game == superTicTacToeBoard[j%3][i].game && (superTicTacToeBoard[j%3][i].game == -1 || superTicTacToeBoard[(j+1)%3][i].game == -1))
                    return false;
            }
        }
        int[][] indexes = new int[][]{{0,2},{1,1},{2,0}};
        for(int i=1; i<=3; i++){
            if (superTicTacToeBoard[i-1][i-1].game == superTicTacToeBoard[i%3][i%3].game && (superTicTacToeBoard[i%3][i%3].game == -1 || superTicTacToeBoard[(i+1)%3][(i+1)%3].game == -1))
                return false;
            else if(superTicTacToeBoard[indexes[i-1][0]][indexes[i-1][1]].game == superTicTacToeBoard[indexes[i%3][0]][indexes[i%3][1]].game && (superTicTacToeBoard[indexes[i%3][0]][indexes[i%3][1]].game == -1 || superTicTacToeBoard[indexes[(i+1)%3][0]][indexes[(i+1)%3][1]].game == -1))
                return false;
        }
        return true;
    }
}