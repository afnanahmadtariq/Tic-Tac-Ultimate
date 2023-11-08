package Tic_Tac_Ultimate;


public class Controller extends Board{
    private Player players[];
    private boolean singlePlayer;
    private final Thread cpuTurn;
    private final Thread endGame;
    private Thread changeTurn;
    private boolean win;
    private int player;
    protected static String difficulty;
    
    Controller(){
        super();
        singlePlayer = true;
        cpuTurn = new Thread(()->{
            try {
                changeTurn.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            checkTurn();
        });
        endGame = new Thread(()-> end(win));
        changeTurn = new Thread(()->{
            try {
                // Delay for 1 second (1000 milliseconds)
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // Handle any exceptions that may occur
                e.printStackTrace();
            }
            player = (player%2)+1;
        });
        difficulty = "medium";
        player = 1;
    }
    Controller(boolean singlePlayer, String difficulty){
        super();
        this.singlePlayer = singlePlayer;
        cpuTurn = new Thread(()->{
            try {
                changeTurn.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            checkTurn();
        });
        endGame = new Thread(()-> end(win));
        changeTurn = new Thread(()->{
            try {
                // Delay for 1 second (1000 milliseconds)
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // Handle any exceptions that may occur
                e.printStackTrace();
            }
            player = (player%2)+1;
            System.out.println("Player Changed!");
        });
        Controller.difficulty = difficulty;
    }
    public void setPlayer(int player){
        this.player = player;
        System.out.println("Player set as: " + player);
        checkTurn();
    }
    public int getPlayer(){
        return this.player;
    }
    public Thread getThread(){
        return cpuTurn;
    }
    public void checkTurn(){
        if(player==2 && singlePlayer) {
            int[] index = Brain.compTurn(super.board);
            turn(index);
            Tic_Tac_Ultimate.mark(index[0], index[1]);
        }
    }
    public boolean turn(int[] index){
        if(super.board[index[0]][index[1]] != 0)
            return false;
        super.board[index[0]][index[1]] = player;
        System.out.println("Player: " + player + " did ----i: " + index[0] + "  j: " + index[1]);
        boolean end = switch(super.check()){
            case 1 -> {
                win = true;
                endGame.start();
                yield true;
            }
            case 0 -> {
                win = false;
                endGame.start();
                yield true;
            }
            default -> false;
        };
        if(!end){
            changeTurn.start();
            cpuTurn.start();
        }
        return true;
    }
    
    private boolean end(boolean win){
        if(win)
            super.game = player;
        else
            super.game = 0;
        if(Tic_Tac_Ultimate.endGame(win, player, super.winValue)==0){
            //start kr do phir se
        }
        else {
            //exit
        }
        //agr event listener game variable pe lga dain to ye func complete
        //boolean win se win ya draw ka pta chal rha
        //int player se kon jeeta ye pta lag rha
        return true;
    }
}
