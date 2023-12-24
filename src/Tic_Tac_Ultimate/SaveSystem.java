package Tic_Tac_Ultimate;

import java.io.*;

public class SaveSystem {
    public static void save(){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Profile files/userData.ser"));
            oos.writeObject(Online.ID);
            oos.writeObject(new Player());
        }
        catch (IOException e){
            System.out.println("Input/Output Exception encountered");
        }
    }
    public static Player read(){
        while(true)
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Profile files/userData.ser"));
                Online.ID = (String) ois.readObject();
                return (Player) ois.readObject();
            }
            catch (IOException e){
                System.out.println("Input/Output Exception encountered");
            }
            catch (ClassNotFoundException e) {
                System.out.println("File not Found!!");
            }
    }
}
