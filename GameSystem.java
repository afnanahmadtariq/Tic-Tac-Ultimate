import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.ArrayList;
public class GameSystem {
    private static String location;
    private static FileOutputStream credentials;
    private static ArrayList<String> usernameList;
    private static ArrayList<String> passwordList;
    private File profilePath;
    private FileOutputStream profile;
    private String username;
    private String name;
    private int level;
    private int exp;

    static{
        location = "Profile Files/";
        try {
            File credentialsPath = new File(location + "credentials.txt");
            credentials = new FileOutputStream(credentialsPath);
            Scanner read = new Scanner(credentialsPath);
            usernameList = new ArrayList<String>();
            passwordList = new ArrayList<String>();
            while(read.hasNext()){
                usernameList.add(read.nextLine());
                passwordList.add(read.nextLine());
            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public GameSystem(){
        username = "Guest" + Math.random()*10000;
        name = "Player" + Math.random()*10000;
        level = 0;
        exp = 0;
    }
    public GameSystem(String username){
        try {
            profilePath = new File(location + username +".txt");
            profile = new FileOutputStream(profilePath);
            Scanner read = new Scanner(profilePath);
            this.username = read.nextLine();
            name = read.nextLine();
            level = Integer.parseInt(read.nextLine());
            exp = Integer.parseInt(read.nextLine());
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    //To check and verify log-in credentials
    public static boolean check(String username, String password){
        for(int i=0;i<usernameList.size();i++){
            if(username.equals(usernameList.get(i)) && password.equals(passwordList.get(i)))
                return true;
        }
        return false;
    }
    public static boolean check(String username){
        for(String name: usernameList){
            if(name.equals(username))
                return true;
        }
        return false;
    }
    public void update(FileOutputStream file){
        PrintStream write ;
        if(file==credentials){
            write = new PrintStream(file);
            for(int i=0;i<usernameList.size();i++){
                write.println(usernameList.get(i));
                write.println(passwordList.get(i));
            }
        }
        else if(file==profile){
            try {
                profilePath.delete();
                profilePath = new File(location + username + ".txt");
                profile = new FileOutputStream(profilePath);
                write = new PrintStream(profile);
                write.println(username);
                write.println(name);
                write.println(level);
                write.println(exp);
            }
            catch (FileNotFoundException e){
                throw new RuntimeException(e);
            }
        }
    }
    public boolean changeUsername(String newName){
        if(check(newName))
            return false;
        for(int i=0;i<usernameList.size();i++){
            if(username.equals(usernameList.get(i))){
                usernameList.set(i,newName);
                break;
            }
        }
        username = newName;
        update(profile);
        return true;
    }
    public void updatePwd(String pwd, String newPwd){
        if(pwd==newPwd)
            return;
        for(int i=0;i<passwordList.size();i++){
            if(pwd.equals(passwordList.get(i))){
               passwordList.set(i,newPwd);
                break;
            }
        }
        update(credentials);
    }
    public void updateName(String newName){
        name = newName;
        update(profile);
    }
    public void levelUp(){
        level++;
        update(profile);
    }
    public void levelUp(int n){
        level += n;
        update(profile);
    }
    public void expUp(){
        exp++;
        update(profile);
    }
    public void expUp(int n){
        exp += n;
        update(profile);
    }
//    private void enc(String str, String location){
//
//    }
//    private void enc(int number, String location){
//
//    }
//    private String dec(String location){
//
//    }
//    private int dec(String location){
//
//    }
}
