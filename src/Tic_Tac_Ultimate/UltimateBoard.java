package Tic_Tac_Ultimate;

public class UltimateBoard {
    public TicTacToeBoard[][] uBoard;
    public int game;
    public int winValue;
    public UltimateBoard() {
        uBoard = new TicTacToeBoard[3][3];
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++)
                uBoard[i][j] = new TicTacToeBoard();
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
            if (uBoard[superIndex[0]][superIndex[1]].board[i][0] == uBoard[superIndex[0]][superIndex[1]].board[i][1] && uBoard[superIndex[0]][superIndex[1]].board[i][1] == uBoard[superIndex[0]][superIndex[1]].board[i][2] && uBoard[superIndex[0]][superIndex[1]].board[i][2] != 0) {
                winValue = count;
                return true;
            }
            else
            if (uBoard[superIndex[0]][superIndex[1]].board[0][i] == uBoard[superIndex[0]][superIndex[1]].board[1][i] && uBoard[superIndex[0]][superIndex[1]].board[1][i] == uBoard[superIndex[0]][superIndex[1]].board[2][i] && uBoard[superIndex[0]][superIndex[1]].board[2][i] != 0){
                winValue = ++count;
                return true;
            }
            count +=2;
        }
        if (uBoard[superIndex[0]][superIndex[1]].board[0][0]== uBoard[superIndex[0]][superIndex[1]].board[1][1] && uBoard[superIndex[0]][superIndex[1]].board[1][1]== uBoard[superIndex[0]][superIndex[1]].board[2][2] && uBoard[superIndex[0]][superIndex[1]].board[2][2]!=0) {
            winValue = count;
            return true;
        }
        else if(uBoard[superIndex[0]][superIndex[1]].board[0][2] == uBoard[superIndex[0]][superIndex[1]].board[1][1] && uBoard[superIndex[0]][superIndex[1]].board[1][1] == uBoard[superIndex[0]][superIndex[1]].board[2][0] && uBoard[superIndex[0]][superIndex[1]].board[2][0]!=0){
            winValue = ++count;
            return true;
        }
        return false;
    }
    private boolean draw(int[] superIndex){
        for(int i=0;i<3;i++) {
            for(int j=1;j<=3;j++){
                if (uBoard[superIndex[0]][superIndex[1]].board[i][j-1] == uBoard[superIndex[0]][superIndex[1]].board[i][j%3] && (uBoard[superIndex[0]][superIndex[1]].board[i][j % 3] == 0 || uBoard[superIndex[0]][superIndex[1]].board[i][(j+1)%3] == 0))
                    return false;
                else if (uBoard[superIndex[0]][superIndex[1]].board[j-1][i] == uBoard[superIndex[0]][superIndex[1]].board[j%3][i] && (uBoard[superIndex[0]][superIndex[1]].board[j%3][i] == 0 || uBoard[superIndex[0]][superIndex[1]].board[(j+1)%3][i] == 0))
                    return false;
            }
        }
        int[][] indexes = new int[][]{{0,2},{1,1},{2,0}};
        for(int i=1; i<=3; i++){
            if (uBoard[superIndex[0]][superIndex[1]].board[i-1][i-1] == uBoard[superIndex[0]][superIndex[1]].board[i%3][i%3] && (uBoard[superIndex[0]][superIndex[1]].board[i%3][i%3] == 0 || uBoard[superIndex[0]][superIndex[1]].board[(i+1)%3][(i+1)%3] == 0))
                return false;
            else if(uBoard[superIndex[0]][superIndex[1]].board[indexes[i-1][0]][indexes[i-1][1]] == uBoard[superIndex[0]][superIndex[1]].board[indexes[i%3][0]][indexes[i%3][1]] && (uBoard[superIndex[0]][superIndex[1]].board[indexes[i%3][0]][indexes[i%3][1]] == 0 || uBoard[superIndex[0]][superIndex[1]].board[indexes[(i+1)%3][0]][indexes[(i+1)%3][1]] == 0))
                return false;
        }
        return true;
    }
    private boolean win() {
        int count = 1;
        for (int i = 0; i < 3; i++) {
            if (uBoard[i][0].game == uBoard[i][1].game && uBoard[i][1].game == uBoard[i][2].game && uBoard[i][2].game != -1 && uBoard[i][2].game != 0) {
                winValue = count;
                return true;
            }
            else if (uBoard[0][i].game == uBoard[1][i].game && uBoard[1][i].game == uBoard[2][i].game && uBoard[2][i].game != -1 && uBoard[2][i].game != 0) {
                winValue = ++count;
                return true;
            }
            count +=2;
        }
        if (uBoard[0][0].game == uBoard[1][1].game && uBoard[1][1].game == uBoard[2][2].game && uBoard[2][2].game != -1 && uBoard[2][2].game != 0) {
            winValue = count;
            return true;
        }
        else if(uBoard[0][2].game == uBoard[1][1].game && uBoard[1][1].game == uBoard[2][0].game && uBoard[2][0].game != -1 && uBoard[2][0].game != 0){
            winValue = ++count;
            return true;
        }
        return  false;
    }
    private boolean draw(){
        for(int i=0;i<3;i++) {
            for(int j=1;j<=3;j++){
                if (uBoard[i][j-1].game == uBoard[i][j%3].game && (uBoard[i][j % 3].game == -1 || uBoard[i][(j+1)%3].game == -1))
                    return false;
                else if (uBoard[j-1][i].game == uBoard[j%3][i].game && (uBoard[j%3][i].game == -1 || uBoard[(j+1)%3][i].game == -1))
                    return false;
            }
        }
        int[][] indexes = new int[][]{{0,2},{1,1},{2,0}};
        for(int i=1; i<=3; i++){
            if (uBoard[i-1][i-1].game == uBoard[i%3][i%3].game && (uBoard[i%3][i%3].game == -1 || uBoard[(i+1)%3][(i+1)%3].game == -1))
                return false;
            else if(uBoard[indexes[i-1][0]][indexes[i-1][1]].game == uBoard[indexes[i%3][0]][indexes[i%3][1]].game && (uBoard[indexes[i%3][0]][indexes[i%3][1]].game == -1 || uBoard[indexes[(i+1)%3][0]][indexes[(i+1)%3][1]].game == -1))
                return false;
        }
        return true;
    }
}