package Tic_Tac_Ultimate;

import java.util.HashMap;
import java.util.Map;

public class QuixoBoard {
        public int[][] board;
        public int game;
        public int winValue;
        public static Map<Integer, int[][]> quxioWinValues;

        static {
            quxioWinValues = new HashMap<>();
            int count = 1;
            for (int i = 0; i < 5; i++) {
                quxioWinValues.put(count++, new int[][]{{i, 0}, {i, 1}, {i, 2}, {i, 3}, {i, 4}});
                quxioWinValues.put(count++, new int[][]{{0, i}, {1, i}, {2, i}, {3, i}, {4, i}});
            }
            quxioWinValues.put(count++, new int[][]{{0, 0}, {1, 1}, {2, 2}, {3, 3}, {4, 4}});
            quxioWinValues.put(count, new int[][]{{0, 4}, {1, 3}, {2, 2}, {3, 1}, {4, 0}});
        }

        public QuixoBoard() {
            board = new int[5][5];
            game = -1;
            winValue = 0;
        }
        public QuixoBoard(QuixoBoard quixoBoard){
            board = new int[5][5];
            for(int i=0; i<5; i++){
                for(int j=0; j<5; j++){
                    this.board[i][j] = quixoBoard.board[i][j];
                }
            }
            this.game = quixoBoard.game;
            this.winValue = quixoBoard.winValue;
        }

        public int check(int player){
            if(win((player%2)+1))
                return (player%2)+1;
            else if(win(player))
                return player;
            else
                return -1;
        }
        private boolean win(int player){
            int count = 1;
            for(int i=0;i<5;i++) {
                if(board[i][0]==player) {
                    boolean flag = true;
                    for (int j = 0; j < 4; j++) {
                        if (board[i][j] != board[i][j + 1]) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        winValue = count;
                        return flag;
                    }
                }
                else if(board[0][i]==player) {
                    boolean flag = true;
                    for (int j = 0; j < 4; j++) {
                        if (board[j][i] != board[j + 1][i]) {
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        winValue = ++count;;
                        return flag;
                    }
                }
                count +=2;
            }
            if(board[0][0]==player) {
                boolean flag = true;
                for (int i=0; i < 4; i++) {
                    if (board[i][i] != board[i+1][i+1]) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    winValue = count;
                    return flag;
                }
            }
            else if(board[0][4]==player) {
                boolean flag = true;
                for (int i=0; i < 4; i++) {
                    if (board[i][4-i] != board[i+1][4-i-1]) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    winValue = ++count;
                    return flag;
                }
            }
            return false;
        }
}
