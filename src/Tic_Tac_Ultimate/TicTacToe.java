package Tic_Tac_Ultimate;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.io.Serializable;

import static Tic_Tac_Ultimate.GUI.*;
import static Tic_Tac_Ultimate.GuiUtility.*;
import static Tic_Tac_Ultimate.Runner.*;

public class TicTacToe extends GamePane implements Serializable {
    TicTacToe(){
        super.initialize();
    }
    @Override
    public void makeBoard(Pane board){
        Platform.runLater(() -> {
            board.setOnMouseClicked(event -> {
                System.out.println("mouse Clicked!!");
                double x = event.getX();
                double y = event.getY();
                int row = (int) (y/cell);
                int col = (int) (x/cell);
                if(getPlayer()!=2 || !singlePlayer){
                    if(!turn(row, col))
                        reject(board);;
                }
            });
            for (int i = 0; i<4; i++) {
                Line hLine = makeHLine((double) i/3,5, board);
                board.getChildren().add(hLine);

                Line vLine = makeVLine((double) i/3,5, board);
                board.getChildren().add(vLine);
            }
            marks = new Group();
            board.getChildren().add(marks);
        });
    }
}
