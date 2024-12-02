package Tic_Tac_Ultimate;

import java.io.Serializable;

public class Credentials implements Serializable {
    String username;
    String email;
    String password;

    public Credentials(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
