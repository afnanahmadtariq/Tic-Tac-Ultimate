package Tic_Tac_Ultimate;

import javafx.application.Platform;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import static Tic_Tac_Ultimate.Board.dictionary;

public class Runner {
    private static QuixoController quixo;
    private static UltimateController ultimateTicTacToe;
    private static Controller ticTacToe;
    public static int gameType;
    public static boolean singlePlayer;
    public static String difficulty ;
    public static ArrayList<Credentials> credentials = new ArrayList<>();
    public static String username;
    public static void main(String[] args) {
        loadCredentials();
        GUI.initialize(args);
    }
    public static void signUp(String name, String username, String email, String password){
        Profile profile = new Profile(name, username, email);
        credentials.add(new Credentials(username, email, password));
        updateCredentials();
        System.out.println("REached");
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(username+".ser"));
            oos.writeObject(profile);
        }
        catch (IOException e){
            System.out.println("IO Exception");
        }
    }
    public static void logIn(String username, String email, String password){
        for(Credentials credential: credentials){
            if(username==null){
                if(credential.email.equals(email) && credential.password.equals(password)) {
                    Runner.username = credential.username;
                }
            }
            else if(email==null){
                if(credential.username.equals(username) && credential.password.equals(password)) {
                    Runner.username = credential.username;
                }
            }
        }
        loadProfile();
    }
    public static void loadCredentials(){
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Credentials.ser"));
            while(true){
                Credentials credential = (Credentials) ois.readObject();
                System.out.println(credential.username);
                credentials.add(credential);
            }
        }
        catch (IOException  e){
            System.out.println("Load Credentials IO Exception");
        }
        catch (ClassNotFoundException e) {
            System.out.println(" ClassNotFoundException");
        }
    }
    public static void updateCredentials(){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(("Credentials.ser")));
            System.out.println("Done");
            for(Credentials credential : credentials)
                oos.writeObject(credential);
            System.out.println(Arrays.deepToString(credentials.toArray()));
        }
        catch (IOException e){
            System.out.println("IO Exception");
        }
    }

    public static void loadProfile(){
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream( username+".ser"));
            GUI.profile = (Profile) ois.readObject();
            System.out.println("Read successfully");
            System.out.println(GUI.profile.getName());
        }
        catch (IOException | ClassNotFoundException e){
            System.out.println("Load File IO Exception");
            GUI.profile = new Profile();
        }
    }
    public static void saveProfile(){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(username +".ser"));
            oos.writeObject(GUI.profile);
        }
        catch (IOException e){
            System.out.println("IO Exception");
        }
    }
    public static void startGame(){
        switch(gameType){
            case 2-> ultimateTicTacToe = new UltimateController(singlePlayer,difficulty);
            case 3-> quixo = new QuixoController(singlePlayer,difficulty);
            default -> ticTacToe = new Controller(singlePlayer,difficulty);
        }
    }
    public static void setPlayer(int player){
        switch(gameType){
            case 2-> ultimateTicTacToe.setPlayer(player);
            case 3-> quixo.setPlayer(player);
            default -> ticTacToe.setPlayer(player);
        }
    }
    public static int getPlayer(){
        return switch(gameType){
            case 2-> ultimateTicTacToe.getPlayer();
            case 3-> quixo.getPlayer();
            default -> ticTacToe.getPlayer();
        };
    }
    public static int[] getSuperIndex(){
        return ultimateTicTacToe.getSuperIndex();
    }
    public static void showTurn(int[] index){
        GUI.showTurn(index[0],index[1]);
        System.out.println("Show GUI mark Done!");
    }
    public static void showTurn(int[] index, int[] superIndex){
        GUI.showTurn(index[0],index[1], superIndex);
        System.out.println("Show GUI mark Done!");
    }
    public static boolean turn(int row, int col){
        if(row>=0 && col>=0 && row<=2 && col<=2)
            return ticTacToe.doTurn(new int[] {row, col});
        return false;
    }
    public static boolean turn(int i, int j,int sI, int sJ){
        if(gameType==3)
            return quixo.doTurn(new int[]{i, j}, new int[]{sI, sJ});
        else if (i >= 0 && j >= 0 && i <= 2 && j <= 2 && sI >= 0 && sJ >= 0 && sI <= 2 && sJ <= 2)
            return ultimateTicTacToe.doTurn(new int[]{i, j}, new int[]{sI, sJ});
        return false;
    }
    public static int checkDraw(int row, int col){
        return quixo.checkDraw(new int[] {row, col});
    }
    public static void setDraw(int row, int col){
        quixo.setDraw(new int[] {row, col});
    }
    public static void clearDraw(){
        quixo.clearDraw();
    }
    public static boolean turn2(int row, int col, int rowI, int colI){
        return quixo.doTurn(new int[]{row, col}, new int[]{rowI, colI});
    }
    public static void endGame(boolean win, int winValue){
        if(win){
            if(singlePlayer) {
                GUI.profile.winUp();
                GUI.profile.xpUp(gameType == 1 ? 20 : gameType == 2 ? 30 : 40);
            }
            GUI.markLine(winValue);
            System.out.println("Won with value: " + winValue);
            System.out.println("index at: " + Arrays.deepToString(dictionary.get(winValue)));
        }
        else {
            String text = "It is a draw! Play Again?";
            System.out.println(text);
            GuiUtility.popUp(text,"Yes","Exit",2);
        }
    }
    public static void endGame(){
        String text = "Player"+getPlayer()+" won! Play Again?";
        System.out.println(text);
        GuiUtility.popUp(text,"Yes","Exit",2);
    }
    public static void endGame(int choice){
        if(choice==1) {
            System.out.println("\n\n\n\n---------   New Game---------");
            startGame();
            GUI.clearMarks();
            GUI.clearTurn();
            GUI.Toss();
        }
        else{
            System.out.println("\n\n\n\n---------  Exit Game---------");
            GUI.clearGame();
        }
    }
    public static void endGame(int[] superIndex, boolean win, int winValue){
        if(win){
            GUI.markLine(superIndex, winValue);
            System.out.println("won at super index: " + Arrays.toString(superIndex));
            System.out.println("Won with value: " + winValue);
            System.out.println("index at: " + Arrays.deepToString(dictionary.get(winValue)));
        }
        else {
            GUI.markDraw(superIndex);
            System.out.println("place 'D' at index");
        }
    }
    public static void save() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("GameSave.ser"));
            switch (gameType) {
                case 2 -> oos.writeObject(ultimateTicTacToe);
                case 3 -> oos.writeObject(quixo);
                default -> oos.writeObject(ticTacToe);
            }
        }
        catch (IOException e){
            System.out.println("IO Exception");
        }
    }
    public static void load(){
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("GameSave.ser"));
            Object game = ois.readObject();
            if(game instanceof Controller) {
                ticTacToe = (Controller) game;
                gameType = 1;
                GUI.displayGame(true);
                Platform.runLater(() -> ticTacToe.loadGame());
            }
            else if(game instanceof UltimateController) {
                ultimateTicTacToe = (UltimateController) game;
                gameType = 2;
                GUI.displayGame(true);
                Platform.runLater(() -> ultimateTicTacToe.loadGame());
            }
            else if(game instanceof QuixoController) {
                quixo = (QuixoController) game;
                gameType = 3;
                GUI.displayGame(true);
                System.out.println("Display done");
                Platform.runLater(() -> quixo.loadGame());
            }
        }
        catch (IOException e){
            System.out.println("Load Game IO Exception");
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void sleep(double seconds){
        try{
            Thread.sleep((long)seconds*1000);
        }
        catch (InterruptedException e) {
            System.out.println("Sleep Interrupted!");
        }
    }
}