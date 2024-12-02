package Tic_Tac_Ultimate;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.io.Serializable;

import static Tic_Tac_Ultimate.GUI.*;
import static Tic_Tac_Ultimate.GuiUtility.*;
import static Tic_Tac_Ultimate.Runner.*;

public class Ultimate extends GamePane {
    Ultimate(){
        super.initialize();
    }
    @Override
    public void makeBoard(Pane board){
        Platform.runLater(() -> {
            board.setOnMouseClicked(event -> {
                System.out.println("mouse Clicked!!");
                double x = event.getX();
                double y = event.getY();
                int j = (int) (x  / (cell / 3)) % 3;
                int sJ = (int) (x  / cell);
                int i = (int) (y  / (cell / 3)) % 3;
                int sI = (int) (y  / cell);
                if (getPlayer() != 2 || !singlePlayer) {
                    if(!turn(i, j, sI, sJ))
                        reject(board);
                }
            });
            for (int i = 0; i < 10; i++) {
                Line hLine;
                if (i == 0 || i == 3 || i == 6 || i == 9)
                    hLine = makeHLine((double) i/9, 8, board);
                else
                    hLine = makeHLine((double) i/9, 3, board);
                board.getChildren().add(hLine);

                Line vLine;
                if (i == 0 || i == 3 || i == 6 || i == 9)
                    vLine = makeVLine((double) i/9, 8, board);
                else
                    vLine = makeVLine((double) i/9, 3, board);
                board.getChildren().add(vLine);
            }
            marks = new Group();
            board.getChildren().add(marks);
        });
    }

}
