package Tic_Tac_Ultimate;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.Serializable;

import static Tic_Tac_Ultimate.GUI.*;
import static Tic_Tac_Ultimate.GuiUtility.makeRectangle;
import static Tic_Tac_Ultimate.Runner.gameType;
import static Tic_Tac_Ultimate.Runner.singlePlayer;

public abstract class GamePane extends BorderPane {

    public Group marks;
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
    private StackPane playerInfo(int player, Color color){
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
    public void markX(int row, int col, int[] superIndex){
        int startY = (int) ((int) (row*(cell/3))+(superIndex[0]*cell)+((cell/3)*0.2));
        int startX = (int) ((int) (col*(cell/3))+(superIndex[1]*cell)+((cell/3)*0.2));
        int endY = startY + (int)((cell/3)*0.6);
        int endX = startX + (int)((cell/3)*0.6);
        markLine(startX,startY,endX,endY,Color.RED,0,marks);
        startX = endX;
        endX = endX - (int)((cell/3)*0.6);
        markLine(startX,startY,endX,endY,Color.RED,0.2,marks);
    }
    public void markX(int row, int col){
        int startY = (int)((row*cell)+(cell*0.2));
        int startX = (int)((col*cell)+(cell*0.2));
        int endY = startY + (int)(cell*0.6);
        int endX = startX + (int)(cell*0.6);
        markLine(startX,startY,endX,endY,Color.RED,0,marks);
        startX = endX;
        endX = endX - (int)(cell*0.6);
        markLine(startX,startY,endX,endY,Color.RED,0.2,marks);
    }
    public void markO(int row, int col){
        double y = (row*cell)+(cell*0.5);
        double x = (col*cell)+(cell*0.5);
        markO(x,y,cell*0.6,marks);
    }
    public void markO(int row, int col, int[] superIndex){
        double y =  (row*(cell/3))+(superIndex[0]*cell)+((cell/3)*0.5);
        double x =  (col*(cell/3))+(superIndex[1]*cell)+((cell/3)*0.5);
        markO(x,y,((cell/3)*0.6),marks);
    }
    public void markO(double x, double y, double diameter, Group node){
        Circle circle = new Circle(x,y,0);
        circle.setFill(Color.BLUE);
        Circle inner = new Circle(x,y,0);
        inner.setFill(Color.WHITE);


        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        KeyValue keyValueRadius = new KeyValue(circle.radiusProperty(), (diameter*0.5));
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.25), keyValueRadius);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();

        Timeline innerTimeLine = new Timeline();
        innerTimeLine.setCycleCount(1);
        KeyValue keyValueRadiusInner = new KeyValue(inner.radiusProperty(), (diameter*0.38));
        KeyFrame keyFrameInner = new KeyFrame(Duration.seconds(0.25), keyValueRadiusInner);
        innerTimeLine.getKeyFrames().add(keyFrameInner);
        innerTimeLine.play();

        circle.setMouseTransparent(true);
        inner.setMouseTransparent(true);
        node.getChildren().addAll(circle,inner);
    }
    public void clearMarks(){
        marks.getChildren().clear();
    }
//    abstract void loadGame();
}
