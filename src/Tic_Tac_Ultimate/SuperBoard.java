package Tic_Tac_Ultimate;

public class SuperBoard {

    public Board[][] superBoard;
    public int game;
    public int winValue;
    public SuperBoard() {
        superBoard = new Board[3][3];
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++)
                superBoard[i][j] = new Board();
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

    public boolean win(int[] superIndex){
        int count = 1;
        for(int i=0;i<3;i++) {
            if (superBoard[superIndex[0]][superIndex[1]].board[i][0] == superBoard[superIndex[0]][superIndex[1]].board[i][1] && superBoard[superIndex[0]][superIndex[1]].board[i][1] == superBoard[superIndex[0]][superIndex[1]].board[i][2] && superBoard[superIndex[0]][superIndex[1]].board[i][2] != 0) {
                winValue = count;
                return true;
            }
            else
            if (superBoard[superIndex[0]][superIndex[1]].board[0][i] == superBoard[superIndex[0]][superIndex[1]].board[1][i] && superBoard[superIndex[0]][superIndex[1]].board[1][i] == superBoard[superIndex[0]][superIndex[1]].board[2][i] && superBoard[superIndex[0]][superIndex[1]].board[2][i] != 0){
                winValue = ++count;
                return true;
            }
            count +=2;
        }
        if (superBoard[superIndex[0]][superIndex[1]].board[0][0]==superBoard[superIndex[0]][superIndex[1]].board[1][1] && superBoard[superIndex[0]][superIndex[1]].board[1][1]==superBoard[superIndex[0]][superIndex[1]].board[2][2] && superBoard[superIndex[0]][superIndex[1]].board[2][2]!=0) {
            winValue = count;
            return true;
        }
        else if(superBoard[superIndex[0]][superIndex[1]].board[0][2] == superBoard[superIndex[0]][superIndex[1]].board[1][1] && superBoard[superIndex[0]][superIndex[1]].board[1][1] == superBoard[superIndex[0]][superIndex[1]].board[2][0] && superBoard[superIndex[0]][superIndex[1]].board[2][0]!=0){
            winValue = ++count;
            return true;
        }
        return false;
    }
    public boolean draw(int[] superIndex){
        for(int i=0;i<3;i++) {
            for(int j=1;j<=3;j++){
                if (superBoard[superIndex[0]][superIndex[1]].board[i][j-1] == superBoard[superIndex[0]][superIndex[1]].board[i][j%3] && (superBoard[superIndex[0]][superIndex[1]].board[i][j % 3] == 0 || superBoard[superIndex[0]][superIndex[1]].board[i][(j+1)%3] == 0))
                    return false;
                else if (superBoard[superIndex[0]][superIndex[1]].board[j-1][i] == superBoard[superIndex[0]][superIndex[1]].board[j%3][i] && (superBoard[superIndex[0]][superIndex[1]].board[j%3][i] == 0 || superBoard[superIndex[0]][superIndex[1]].board[(j+1)%3][i] == 0))
                    return false;
            }
        }
        int[][] indexes = new int[][]{{0,2},{1,1},{2,0}};
        for(int i=1; i<=3; i++){
            if (superBoard[superIndex[0]][superIndex[1]].board[i-1][i-1] == superBoard[superIndex[0]][superIndex[1]].board[i%3][i%3] && (superBoard[superIndex[0]][superIndex[1]].board[i%3][i%3] == 0 || superBoard[superIndex[0]][superIndex[1]].board[(i+1)%3][(i+1)%3] == 0))
                return false;
            else if(superBoard[superIndex[0]][superIndex[1]].board[indexes[i-1][0]][indexes[i-1][1]] == superBoard[superIndex[0]][superIndex[1]].board[indexes[i%3][0]][indexes[i%3][1]] && (superBoard[superIndex[0]][superIndex[1]].board[indexes[i%3][0]][indexes[i%3][1]] == 0 || superBoard[superIndex[0]][superIndex[1]].board[indexes[(i+1)%3][0]][indexes[(i+1)%3][1]] == 0))
                return false;
        }
        return true;
    }
    
    public boolean win() {
        int count = 1;
        for (int i = 0; i < 3; i++) {
            if (superBoard[i][0].game == superBoard[i][1].game && superBoard[i][1].game == superBoard[i][2].game && superBoard[i][2].game != -1 && superBoard[i][2].game != 0) {
                winValue = count;
                return true;
            }
            else if (superBoard[0][i].game == superBoard[1][i].game && superBoard[1][i].game == superBoard[2][i].game && superBoard[2][i].game != -1 && superBoard[2][i].game != 0) {
                winValue = ++count;
                return true;
            }
            count +=2;
        }
        if (superBoard[0][0].game == superBoard[1][1].game && superBoard[1][1].game == superBoard[2][2].game && superBoard[2][2].game != -1 && superBoard[2][2].game != 0) {
            winValue = count;
            return true;
        }
        else if(superBoard[0][2].game == superBoard[1][1].game && superBoard[1][1].game == superBoard[2][0].game && superBoard[2][0].game != -1 && superBoard[2][0].game != 0){
            winValue = ++count;
            return true;
        }
        return  false;
    }
    public boolean draw(){
        for(int i=0;i<3;i++) {
            for(int j=1;j<=3;j++){
                if (superBoard[i][j-1].game == superBoard[i][j%3].game && (superBoard[i][j % 3].game == -1 || superBoard[i][(j+1)%3].game == -1))
                    return false;
                else if (superBoard[j-1][i].game == superBoard[j%3][i].game && (superBoard[j%3][i].game == -1 || superBoard[(j+1)%3][i].game == -1))
                    return false;
            }
        }
        int[][] indexes = new int[][]{{0,2},{1,1},{2,0}};
        for(int i=1; i<=3; i++){
            if (superBoard[i-1][i-1].game == superBoard[i%3][i%3].game && (superBoard[i%3][i%3].game == -1 || superBoard[(i+1)%3][(i+1)%3].game == -1))
                return false;
            else if(superBoard[indexes[i-1][0]][indexes[i-1][1]].game == superBoard[indexes[i%3][0]][indexes[i%3][1]].game && (superBoard[indexes[i%3][0]][indexes[i%3][1]].game == -1 || superBoard[indexes[(i+1)%3][0]][indexes[(i+1)%3][1]].game == -1))
                return false;
        }
        return true;
    }
}