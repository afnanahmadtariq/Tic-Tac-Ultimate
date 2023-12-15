/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tic_Tac_Ultimate;
public class Player {
    private String name;
    private String symbol;
    private int level;
    private int exp;
    private 
    
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
