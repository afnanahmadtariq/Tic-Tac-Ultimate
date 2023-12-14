package Tic_Tac_Ultimate;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import static Tic_Tac_Ultimate.GUI.*;
import static Tic_Tac_Ultimate.GuiUtility.makeRectangle;
import static Tic_Tac_Ultimate.Runner.gameType;
import static Tic_Tac_Ultimate.Runner.singlePlayer;

public abstract class GamePane extends BorderPane {
    public void initialize(){
        setPadding(new Insets(10));
        StackPane player1 = playerInfo(1, Color.RED);
        setLeft(player1);

        StackPane player2 = playerInfo(2, Color.BLUE);
        setRight(player2);

        StackPane center = new StackPane();
        setCenter(center);

        Rectangle rectangle = makeRectangle(0.95,0.95);
        Pane board = new Pane();
        board.maxWidthProperty().bind(root.heightProperty().multiply(0.8));
        board.maxHeightProperty().bind(root.heightProperty().multiply(0.8));
        if(cell==0.0 && gameType!=3)
            Platform.runLater(()->{
                cell = board.getHeight()/3;
                System.out.println("Cell size: " + cell);
            });
        makeBoard(board);
        center.getChildren().addAll(rectangle, board);
    }
    private static StackPane playerInfo(int player, Color color){
        Rectangle rectangle = makeRectangle(3.5/9, 0.95);

        Text playerName = new Text("Player " + player);
        if(singlePlayer && player==2)
            playerName = new Text("CPU");
        playerName.setFont(Font.font("Franklin Gothic Heavy", FontWeight.BOLD, 74));
        playerName.setFill(color);
        Group symbol = new Group();
        Text turn = new Text("Turn");
        turn.setFont(Font.font("Franklin Gothic Heavy", 50));
        turn.setFill(Color.LIGHTGREY);
        if(player==1) {
            turn1 = turn;
            showX(symbol);
        }
        else {
            turn2 = turn;
            markO(0, 0, 100, symbol);
        }
        VBox info = new VBox(playerName,symbol,turn);
        info.setAlignment(Pos.TOP_CENTER);
        info.setSpacing(50);
        info.setTranslateY(100);
        if(gameType==2) {
            Group grid = new Group();
            if(player==1)
                grid1 = grid;
            else
                grid2 = grid;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    Rectangle box = makeRectangle(.02, .02);
                    box.setTranslateX(20 * j);
                    box.setTranslateY(20 * i);
                    grid.getChildren().add(box);
                }
            }
            Rectangle back = makeRectangle(0.09, 0.09);
            back.setFill(Color.LIGHTGREY);
            StackPane turnIndicator = new StackPane(back, grid);
            turnIndicator.setAlignment(Pos.CENTER);
            info.getChildren().add(turnIndicator);
        }


        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(rectangle, info);
        return stackPane;
    }
    abstract void makeBoard(Pane board);
}
