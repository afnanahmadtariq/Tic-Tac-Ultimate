package Tic_Tac_Ultimate;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    public static Group marks;
    public static int cell = 600/3;
    private static Color backGround = Color.web("#f2f2f2");
    private static Color midGround = Color.web("#fff");

    @Override
    public void start(Stage stage) throws Exception {
        Image icon = new Image("U.png");
        stage.getIcons().add(icon);
        stage.setTitle("Tic Tac Ultimate");
        stage.setWidth(1920);
        stage.setHeight(1080);
        stage.setResizable(true);
        stage.setFullScreen(true);
        stage.setX(0);
        stage.setY(0);
        displayPlay(stage);
    }
    public void displayStart(Stage stage) throws Exception {
        HBox root = new HBox();
        Scene scene = new Scene(root, midGround);
//        Group simple = new Group(new Text(""));
//        simple.set
//        Group ultimate = new Group(new Text(""));
//        root.getChildren().add(simple);
//        root.getChildren().add(ultimate);

        Rectangle simple = new Rectangle();
        simple.setFill(Color.LIGHTBLUE);
        simple.setWidth(780);
        simple.setHeight(stage.getHeight());
        System.out.println("this is width: " + stage.getWidth());

        Text text = new Text("Tic Tac Toe");

        root.getChildren().add(simple);


        Rectangle ultimate = new Rectangle();
        ultimate.setFill(Color.BLUE);
        ultimate.setWidth(stage.getWidth());
        ultimate.setHeight(stage.getHeight());

        Text text2 = new Text("Super Tic Tac Toe");

        root.getChildren().add(ultimate);


        stage.setScene(scene);
        stage.show();
    }
    private void showNavPage(Stage stage) throws Exception{

    }
    private void displayPlay(Stage stage) throws Exception{

        Group root = new Group();

        game =  new Scene(root, backGround);
//        Rectangle rectangle = new Rectangle();
//        rectangle.setFill(Color.LIGHTBLUE);
//        rectangle.setWidth(200);
//        rectangle.setHeight(200);
//
//        Text text = new Text("Centered Square");
//
//        root.getChildren().add(rectangle);
//        root.getChildren().add(text);
//
//        setAlignment(Pos.CENTER);
        superTicTacToe(root);

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
    private void ticTacToe(Group root) throws Exception{

//        root.set
//        root.setFillHeight(true);
//        root.setBackground(new Background(new BackgroundFill(midGround, CornerRadii.EMPTY, Insets.EMPTY)));

//        Line line = new Line();
//        line.setStartY(0);
//        line.setEndY(stage.getHeight());
//        line.setStartX(Math.floor(stage.getWidth()/stage.getHeight()));
//        line.setEndX(Math.floor(stage.getWidth()/stage.getHeight())+stage.getHeight());


        game.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("mouse Clicked!!");
                int x = (int) event.getX();
                int y = (int) event.getY();
                int j = (x-500)/cell;
                int i = (y-100)/cell;
                if(getPlayer()!=2 || !isSinglePlayer()){
                    if(turn(i, j))
                        System.out.println("registered!!...............");
                }
            }
        });
        for (int row = 0; row < 4; row++) {
            int y = (row * cell) + 500;
            Line line = new Line();
            line.setStartY(100);
            line.setEndY(700);
            line.setStartX(y);
            line.setEndX(y);
            line.setStrokeWidth(5);
            line.setPickOnBounds(false);
            root.getChildren().add(line);
        }
        for (int col = 0; col < 4; col++) {
            int x = (col * cell) + 100;
            Line line = new Line();
            line.setStartY(x);
            line.setEndY(x);
            line.setStartX(500);
            line.setEndX(1100);
            line.setStrokeWidth(5);
            line.setPickOnBounds(false);
            root.getChildren().add(line);
        }
        marks = new Group();
        root.getChildren().add(marks);
    }
    private void superTicTacToe(Group root) throws Exception{
        game.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("mouse Clicked!!");
                int x = (int) event.getX();
                int y = (int) event.getY();
                int j = ((x-500)/(cell/3))%3;
                int sJ = (x-500)/cell;
                int i = ((y-100)/(cell/3))%3;
                int sI = (y-100)/cell;
                if(getPlayer()!=2 || !isSinglePlayer()){
                    Tic_Tac_Ultimate.turn(i, j, sI, sJ);
                }
            }
        });
        for (int row = 0; row < 10; row++) {
            int y = (row * (cell/3)) + 500;
            Line line = new Line();
            line.setStartY(100);
            line.setEndY(700);
            line.setStartX(y);
            line.setEndX(y);
            if(row==0 || row==3 || row==6 || row==9)
                line.setStrokeWidth(8);
            else
                line.setStrokeWidth(3);
            line.setPickOnBounds(false);
            root.getChildren().add(line);
        }
        for (int col = 0; col < 10; col++) {
            int x = (col * (cell/3)) + 100;
            Line line = new Line();
            line.setStartY(x);
            line.setEndY(x);
            line.setStartX(500);
            line.setEndX(1100);
            if(col==0 || col==3 || col==6 || col==9)
                line.setStrokeWidth(8);
            else
                line.setStrokeWidth(3);
            line.setPickOnBounds(false);
            root.getChildren().add(line);
        }
        marks = new Group();
        root.getChildren().add(marks);
    }
    public static void showTurn(int i, int j, int[] superIndex){
        i = (i*(cell/3))+(superIndex[0]*cell)+105;
        j = (j*(cell/3))+(superIndex[1]*cell)+505;
        showMark(i,j,true,true, false);
    }
    public static void showTurn(int i, int j){
        i = (i*cell)+150;
        j = (j*cell)+550;
        showMark(i,j,true,false,false);
    }
    public static void markDraw(int[] superIndex){
        int i = (superIndex[1]*cell)+150;
        int j = (superIndex[0]*cell)+550;
        showMark(i,j,false,false,true);
    }
    private static void showMark(int i, int j, boolean win, boolean small, boolean dark){
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
        width = height = !dark? small? 50 : 100 : cell;

        ImageView imageView = new ImageView(mark);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setLayoutX(j);
        imageView.setLayoutY(i);
        marks.getChildren().add(imageView);
        System.out.println("Marked on grid!");
        if(dark)
            showMark(i,j,win,small, false);
    }
    public static void markLine(int value){
        int[][] lineIndex = dictionary.get(value);
        int startX = ((lineIndex[0][1]*cell)+(int)(cell*2.8));
        int startY = ((lineIndex[0][0]*cell)+cell);
        int endX = ((lineIndex[2][1]*cell)+(int)(cell*3.2));
        int endY = ((lineIndex[2][0]*cell)+cell);

        markLine(startX,startY,endX,endY);
    }
    public static void markLine(int[] superIndex,int value){
        int[][] lineIndex = dictionary.get(value);
        int startX = ((lineIndex[0][1]*(cell/3))+(int)((cell/3)*2.8)+superIndex[1]*cell);
        int startY = ((lineIndex[0][0]*(cell/3))+(cell/3)+superIndex[0]*cell);
        int endX = ((lineIndex[2][1]*(cell/3))+(int)((cell/3)*3.2)+superIndex[1]*cell);
        int endY = ((lineIndex[2][0]*(cell/3))+(cell/3)+superIndex[0]*cell);

        markLine(startX,startY,endX,endY);

        int i = (superIndex[1]*cell)+150;
        int j = (superIndex[0]*cell)+550;
        showMark(i,j,true,false,true);
    }
    public static void markLine(int startX, int startY, int endX, int endY){
        //is index pe line lag gaye gi :)
        Line line = new Line(startX, startY, endX, endY);
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(10);
        line.setStrokeLineCap(StrokeLineCap.ROUND);

        marks.getChildren().add(line);

        // Create a Timeline for the animation
        Timeline timeline = new Timeline();

        // Define the starting and ending keyframes
        KeyFrame startFrame = new KeyFrame(Duration.ZERO,
                new KeyValue(line.endXProperty(), startX),
                new KeyValue(line.endYProperty(), startY));
        KeyFrame endFrame = new KeyFrame(Duration.seconds(0.5),
                new KeyValue(line.endXProperty(), endX),
                new KeyValue(line.endYProperty(), endY));

        // Add the keyframes to the timeline
        timeline.getKeyFrames().addAll(startFrame, endFrame);

        // Start the animation
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
