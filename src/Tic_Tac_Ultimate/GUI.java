package Tic_Tac_Ultimate;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import static Tic_Tac_Ultimate.Board.dictionary;
import static Tic_Tac_Ultimate.Tic_Tac_Ultimate.*;

public class GUI extends Application  {
    private Scene game ;
    public static Pane marks;
    public static double cell;
    private static Color backGround = Color.web("#f2f2f2");
    private static Color midGround = Color.web("#fff");

    @Override
    public void start(Stage stage) throws Exception {
        Image icon = new Image("U.png");
        stage.getIcons().add(icon);
        stage.setTitle("Tic Tac Ultimate");
        stage.setWidth(1280);
        stage.setHeight(720);
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.setX(0);
        stage.setY(0);
        displayPlay(stage);
    }
    public void displayStart(Stage stage) throws Exception {
        HBox root = new HBox();
        root.setBackground(new javafx.scene.layout.Background(
                new javafx.scene.layout.BackgroundFill(Color.BLACK, null, null)));
        Scene scene = new Scene(root, midGround);

        Rectangle simple = new Rectangle();
        simple.setFill(Color.LIGHTBLUE);
        simple.widthProperty().bind(root.widthProperty().divide(2));
        simple.setHeight(stage.getHeight());
        System.out.println("this is width: " + stage.getWidth());
//
//        Text text = new Text("Tic Tac Toe");
//
        root.getChildren().add(simple);
//
//
        Rectangle ultimate = new Rectangle();
        ultimate.setFill(Color.BLUE);
        ultimate.widthProperty().bind(root.widthProperty().divide(2));
        ultimate.setHeight(stage.getHeight());

        Text text2 = new Text("Super Tic Tac Toe");

        root.getChildren().add(ultimate);

//        //Set the width of the content pane to half the width of the stage
//        stage.widthProperty().addListener((obs, oldWidth, newWidth) -> {
//            double halfWidth = newWidth.doubleValue() / 2.0;
//            simple.setWidth(halfWidth);
//        });

        stage.setScene(scene);
        stage.show();
    }
    private void showNavPage(Stage stage) throws Exception{

    }
    private void displayPlay(Stage stage) throws Exception{
        BorderPane root = new BorderPane();
        game =  new Scene(root, backGround);
        StackPane board = new StackPane();
        root.setCenter(board);

        Rectangle rectangle = new Rectangle();
        rectangle.setFill(midGround);
        rectangle.widthProperty().bind(root.heightProperty().multiply(0.95));
        rectangle.heightProperty().bind(root.heightProperty().multiply(0.95));
        rectangle.setArcWidth(50);
        rectangle.setArcHeight(50);

        Pane grid = new Pane();
        grid.maxWidthProperty().bind(root.heightProperty().multiply(0.8));
        grid.maxHeightProperty().bind(root.heightProperty().multiply(0.8));
        Platform.runLater(()->{
            cell = grid.getHeight()/3;
            System.out.println("Cell size: " + cell);
        });

        board.getChildren().add(rectangle);
        board.getChildren().add(grid);

        superTicTacToe(grid);
        stage.setScene(game);
        stage.show();
        Toss();
    }
    private void displayOptions(Stage stage) throws Exception{

    }
    private void displayRuleBook(Stage stage) throws Exception{

    }
    private void displayHelp(Stage stage) throws Exception{

    }
    private void displayAboutUs(Stage stage) throws Exception{

    }
    private void displayProfile(Stage stage) throws Exception{

    }
//    private void ticTacToe(Group root) throws Exception{
//
////        root.set
////        root.setFillHeight(true);
////        root.setBackground(new Background(new BackgroundFill(midGround, CornerRadii.EMPTY, Insets.EMPTY)));
//
////        Line line = new Line();
////        line.setStartY(0);
////        line.setEndY(stage.getHeight());
////        line.setStartX(Math.floor(stage.getWidth()/stage.getHeight()));
////        line.setEndX(Math.floor(stage.getWidth()/stage.getHeight())+stage.getHeight());
//
//
//        game.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                System.out.println("mouse Clicked!!");
//                int x = (int) event.getX();
//                int y = (int) event.getY();
//                int j = (x-500)/cell;
//                int i = (y-100)/cell;
//                if(getPlayer()!=2 || !isSinglePlayer()){
//                    Tic_Tac_Ultimate.turn(i, j);
//                    System.out.println("registered!!...............");
//                }
//            }
//        });
//        for (int row = 0; row < 4; row++) {
//            int y = (row * cell) + 500;
//            Line line = new Line();
//            line.setStartY(100);
//            line.setEndY(700);
//            line.setStartX(y);
//            line.setEndX(y);
//            line.setStrokeWidth(5);
//            line.setPickOnBounds(false);
//            root.getChildren().add(line);
//        }
//        for (int col = 0; col < 4; col++) {
//            int x = (col * cell) + 100;
//            Line line = new Line();
//            line.setStartY(x);
//            line.setEndY(x);
//            line.setStartX(500);
//            line.setEndX(1100);
//            line.setStrokeWidth(5);
//            line.setPickOnBounds(false);
//            root.getChildren().add(line);
//        }
//        marks = new Group();
//        root.getChildren().add(marks);
//    }
    private void ticTacToe(Pane grid) throws Exception{
        Platform.runLater(() -> {
            grid.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.out.println("mouse Clicked!!");
                    double x = event.getX();
                    double y = event.getY();
                    int row = (int) (y/cell);
                    int col = (int) (x/cell);
                    if(getPlayer()!=2 || !isSinglePlayer()){
                        if(turn(row, col))
                            System.out.println("registered!!...............");
                    }
                }
            });
            for (int i = 0; i<4; i++) {
                Line hLine = new Line();
                hLine.setStartY(0);
                hLine.endYProperty().bind(grid.maxHeightProperty());
                hLine.startXProperty().bind(grid.maxWidthProperty().multiply((double) i/3));
                hLine.endXProperty().bind(grid.maxWidthProperty().multiply((double) i/3));
                hLine.setStrokeWidth(5);
                grid.getChildren().add(hLine);

                Line vLine = new Line();
                vLine.startYProperty().bind(grid.maxHeightProperty().multiply((double) i/3));
                vLine.endYProperty().bind(grid.maxHeightProperty().multiply((double) i/3));
                vLine.setStartX(0);
                vLine.endXProperty().bind(grid.maxWidthProperty());
                vLine.setStrokeWidth(5);
                grid.getChildren().add(vLine);
            }
            marks = new Pane();
            grid.getChildren().add(marks);
        });
    }
    private void superTicTacToe(Pane grid) throws Exception{
        Platform.runLater(() -> {
            grid.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.out.println("mouse Clicked!!");
                    double x = event.getX();
                    double y = event.getY();
                    int j = (int) (x  / (cell / 3)) % 3;
                    int sJ = (int) (x  / cell);
                    int i = (int) (y  / (cell / 3)) % 3;
                    int sI = (int) (y  / cell);
                    if (getPlayer() != 2 || !isSinglePlayer()) {
                        turn(i, j, sI, sJ);
                    }
                }
            });
            for (int i = 0; i < 10; i++) {
                Line hLine = new Line();
                hLine.setStartY(0);
                hLine.endYProperty().bind(grid.maxHeightProperty());
                hLine.startXProperty().bind(grid.maxWidthProperty().multiply((double) i/9));
                hLine.endXProperty().bind(grid.maxWidthProperty().multiply((double) i/9));
                if (i == 0 || i == 3 || i == 6 || i == 9)
                    hLine.setStrokeWidth(8);
                else
                    hLine.setStrokeWidth(3);
                hLine.setPickOnBounds(false);
                grid.getChildren().add(hLine);

                Line vLine = new Line();
                vLine.startYProperty().bind(grid.maxHeightProperty().multiply((double) i/9));
                vLine.endYProperty().bind(grid.maxHeightProperty().multiply((double) i/9));
                vLine.setStartX(0);
                vLine.endXProperty().bind(grid.maxWidthProperty());
                if (i == 0 || i == 3 || i == 6 || i == 9)
                    vLine.setStrokeWidth(8);
                else
                    vLine.setStrokeWidth(3);
                grid.getChildren().add(vLine);
            }
            marks = new Pane();
            grid.getChildren().add(marks);
        });
    }
    public static void showTurn(int row, int col, int[] superIndex){
        int y = (int) ((int) (row*(cell/3))+(superIndex[0]*cell)+((cell/3)*0.2));
        int x = (int) ((int) (col*(cell/3))+(superIndex[1]*cell)+((cell/3)*0.2));
        showMark(x,y,true,true, false);
    }
    public static void showTurn(int row, int col){
        int y = (int)((row*cell)+(cell*0.2));
        int x = (int)((col*cell)+(cell*0.2));
        showMark(x,y,true,false,false);
    }
    public static void markDraw(int[] superIndex){
        int x = (int)(superIndex[1]*cell);
        int y = (int)(superIndex[0]*cell);
        showMark(x,y,false,false,true);
    }
    private static void showMark(int x, int y, boolean win, boolean small, boolean dark){
        Image xIcon = new Image("x.png");
        Image p1Icon = xIcon;
        Image oIcon = new Image("o.png");
        Image p2Icon = oIcon;
        Image dIcon = new Image("D.png");
        Image darkIcon = new Image("dark.png");

        Image mark;
        mark = !dark? win? getPlayer()==1? p1Icon : p2Icon : dIcon : darkIcon;
        System.out.println("Mark set acc to player!");
        int width, height;
        width = height = !dark? small? (int)((cell/3)*0.6) : (int)(cell*0.6) : (int)cell;

        ImageView imageView = new ImageView(mark);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        marks.getChildren().add(imageView);
        System.out.println("Marked on grid!");
        if(dark)
            showMark(x+=(int)(cell*0.2),y+=(int)(cell*0.2),win,small, false);
    }
    public static void markLine(int value){
        int[][] lineIndex = dictionary.get(value);
        int startX =(int)((lineIndex[0][1]*cell)+(int)(cell*0.5));
        int startY =(int)((lineIndex[0][0]*cell)+(int)(cell*0.5));
        int endX = (int) ((lineIndex[2][1]*cell)+(int)(cell*0.5));
        int endY = (int) ((lineIndex[2][0]*cell)+(int)(cell*0.5));

        markLine(startX,startY,endX,endY);
    }
    public static void markLine(int[] superIndex,int value){
        int[][] lineIndex = dictionary.get(value);
        int startX =(int)((superIndex[1]*cell)+(lineIndex[0][1]*(cell/3))+(int)((cell/3)*0.5));
        int startY =(int)((superIndex[0]*cell)+(lineIndex[0][0]*(cell/3))+(int)((cell/3)*0.5));
        int endX = (int) ((superIndex[1]*cell)+(lineIndex[2][1]*(cell/3))+(int)((cell/3)*0.5));
        int endY = (int) ((superIndex[0]*cell)+(lineIndex[2][0]*(cell/3))+(int)((cell/3)*0.5));

        markLine(startX,startY,endX,endY);

        int x =(int) (superIndex[1]*cell);
        int y =(int) (superIndex[0]*cell);
        showMark(x,y,true,false,true);
    }
    public static void markLine(int startX, int startY, int endX, int endY){
        Line line = new Line(startX, startY, endX, endY);
        line.setStroke(Color.GREEN);
        line.setStrokeWidth(10);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        marks.getChildren().add(line);

        Timeline timeline = new Timeline();
        KeyFrame startFrame = new KeyFrame(Duration.ZERO,
                new KeyValue(line.endXProperty(), startX),
                new KeyValue(line.endYProperty(), startY));
        KeyFrame endFrame = new KeyFrame(Duration.seconds(0.5),
                new KeyValue(line.endXProperty(), endX),
                new KeyValue(line.endYProperty(), endY));
        timeline.getKeyFrames().addAll(startFrame, endFrame);
        timeline.play();
    }
    public static void clearMarks(){
        marks.getChildren().clear();
    }
//    line = new Line();
//        line.setStroke(Color.BLACK);
//        line.setVisible(false);
//
//    image = new Rectangle();
//        image.setFill(Color.RED);
//        image.setWidth(50);
//        image.setHeight(50);
//        image.setVisible(false);
//
//    getChildren().add(line);
//    getChildren().add(image);
//
//    setAlignment(Pos.CENTER);
//
//    setOnMouseEntered(new EventHandler<MouseEvent>() {
//        @Override
//        public void handle(MouseEvent event) {
//            line.setVisible(true);
//            image.setVisible(true);
//        }
//    });
//
//    setOnMouseExited(new EventHandler<MouseEvent>() {
//        @Override
//        public void handle(MouseEvent event) {
//            line.setVisible(false);
//            image.setVisible(false);
//        }
//    });
//
//    setOnMouseClicked(new EventHandler<MouseEvent>() {
//        @Override
//        public void handle(MouseEvent event) {
//            isVisible = !isVisible;
//            line.setVisible(isVisible);
//            image.setVisible(isVisible);
//        }
//    });
    public static void Toss(){
        String text = "Select your side for the toss";
        int choice = popUp(text,"Heads","Tails",1);
        if(choice == (int)(Math.random()*2) && 1!=1){
            System.out.println("player = 1");
            Tic_Tac_Ultimate.setPlayer(1);
        }
        else{
            System.out.println("player = 2");
            Tic_Tac_Ultimate.setPlayer(2);
        }
    }
    private static void shakeStage(Stage stage) {
        final double originalX = stage.getX();
        final double originalY = stage.getY();
        final Timeline[] timeline = new Timeline[1];

        timeline[0] = new Timeline(new KeyFrame(
                Duration.millis(100),
                new EventHandler<ActionEvent>() {
                    int shakeCount = 0;
                    @Override
                    public void handle(ActionEvent event) {
                        // Shake the stage
                        if (shakeCount++ % 2 == 0) {
                            stage.setX(originalX + 10);
                            stage.setY(originalY + 10);
                        } else {
                            stage.setX(originalX);
                            stage.setY(originalY);
                        }
                        // Stop shaking after 10 shakes
                        if (shakeCount > 10) {
                            stage.setX(originalX);
                            stage.setY(originalY);
                            timeline[0].stop();
                        }
                    }
                }
        ));
        timeline[0].setCycleCount(Timeline.INDEFINITE);
        timeline[0].play();
    }
    //Another method{
//    private void shakeStage(Stage stage) {
//        final int shakeDistance = 10;
//        final int numShakes = 10;
//        final double shakeDuration = 50; // milliseconds
//
//        TranslateTransition tt = new TranslateTransition(Duration.millis(shakeDuration), stage.getScene().getRoot());
//        tt.setCycleCount(2 * numShakes);
//        tt.setAutoReverse(true);
//        tt.setByX(shakeDistance);
//        tt.setByY(shakeDistance);
//
//        tt.playFromStart();
//    }
    public static int popUp(String text, String button1Text, String button2Text, int design){
        final int[] choice = {-1};
        //stage
        Stage endGame = new Stage(StageStyle.UNDECORATED);
        endGame.setWidth(500);
        endGame.setHeight(200);
        endGame.setResizable(false);
        endGame.initModality(Modality.APPLICATION_MODAL);
        endGame.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                shakeStage(endGame);
            }
        });

        //root Node
        VBox root = new VBox(new Text(text));
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 200, 100, midGround);

        //Buttons
        Button button1 = new Button(button1Text);
        button1.setMinSize(80,20);
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Button was pressed!");
                choice[0] = 0;
                endGame.close();
            }
        });

        Button button2 = new Button(button2Text);
        button2.setMinSize(80,20);
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Button was pressed!");
                choice[0] = 1;
                endGame.close();
            }
        });

        HBox buttons = new HBox();
        buttons.setAlignment(Pos.BASELINE_CENTER);
        buttons.getChildren().add(button1);
        buttons.getChildren().add(button2);
        root.getChildren().add(buttons);

        //Show Stage
        endGame.setScene(scene);
        endGame.showAndWait();
        return choice[0];
    }

//    public void invalidated(Observable observable) {
//        int newValue = (int) observable.getValue();
//        if (newValue != -1) {
//            int a=1;
////            onValueChanged();
//        }
//    }
}
