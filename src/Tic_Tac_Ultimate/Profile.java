package Tic_Tac_Ultimate;

import java.io.Serializable;

public class Profile  implements Serializable {
    private String name;
    private String username;
    private String email;
    private int level;
    private int xp;
    int winnings;

    public Profile(String name, String username, String email) {
        this();
        this.name = name;
        this.username = username;
        this.email = email;
    }

    public Profile() {
        this.name = "Guest";
        this.level = 0;
        this.xp = 0;
        this.winnings = 0;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getLevel() {
        return level;
    }
    public int getXp() {
        return xp;
    }
    public int getWinnings() {
        return winnings;
    }
    public void setWinnings(int winnings) {
        this.winnings = winnings;
    }
    public void levelUp(){
        level++;
        Runner.saveProfile();
    }
    public void xpUp(int inc){
        xp += inc;
        if(xp>100){
            xp -= 100;
            levelUp();
        }
        Runner.saveProfile();
    }
    public void winUp(){
        winnings++;
        Runner.saveProfile();
    }
}
