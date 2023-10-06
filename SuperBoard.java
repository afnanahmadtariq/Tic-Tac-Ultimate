public class SuperBoard {
    public Board[][] superBoard = new Board[3][3];

    public SuperBoard(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++)
                superBoard[i][j] = new Board();
        }
    }
    public boolean win(){
        for(int i=0;i<3;i++) {
            if (superBoard[i][0].game==superBoard[i][1].game && superBoard[i][1].game==superBoard[i][2].game && superBoard[i][2].game!=0)
                return true;
            else if (superBoard[0][i].game==superBoard[1][i].game && superBoard[1][i].game==superBoard[2][i].game && superBoard[2][i].game!=0)
                return true;
        }
        if (superBoard[0][0].game==superBoard[1][1].game && superBoard[1][1].game==superBoard[2][2].game && superBoard[2][2].game!=0)
            return true;
        else return superBoard[0][2].game == superBoard[1][1].game && superBoard[1][1].game == superBoard[2][0].game && superBoard[2][0].game!=0;
    }
}
