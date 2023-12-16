package Tic_Tac_Ultimate;
public class Player {
    private String name;
    private String username;
    private int level;
    private int exp;
    private String symbol;
    Player(){
        name = "Unknown";
        symbol = "X";
        level = 0;
        exp  = 0;
    }
    Player(String name, String symbol, int level, int exp){
        this.name = name;
        this.symbol = symbol;
        this.level = level;
        this.exp  = exp;
    }
}
