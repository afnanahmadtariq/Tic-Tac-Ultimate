package Tic_Tac_Ultimate;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
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
    public static Group root;
    public static Group marks;
    public static int cell = 200;
    private static Color backGround = Color.web("#f2f2f2");
    private static Color midGround = Color.web("#fff");

    @Override
    public void start(Stage stage) throws Exception {
       displayPlay(stage);
    }
    private void showNavPage(Stage stage) throws Exception{

    }
    private void displayPlay(Stage stage) throws Exception{
        Image icon = new Image("U.png");
        stage.getIcons().add(icon);
        stage.setTitle("Tic Tac Ultimate");
        stage.setWidth(1920);
        stage.setHeight(1080);
        stage.setResizable(true);
        stage.setFullScreen(false);
        stage.setX(0);
        stage.setY(0);
        root = new Group();

        Scene mainView = game =  new Scene(root, backGround);
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

        stage.setScene(mainView);
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
    private void superTicTacToe(Group root) throws Exception{

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
                int sJ = j/3;
                j = j-sJ;
                int i = (y-100)/cell;
                int sI = i/3;
                i = i-sJ;
                if(getPlayer()!=2 || !isSinglePlayer()){
                    int[] superIndex = Tic_Tac_Ultimate.turn(i, j, sI, sJ);
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
    public static void showTurn(int i, int j){
        Image xIcon = new Image("x.png");
        Image p1Icon = xIcon;
        Image oIcon = new Image("o.png");
        Image p2Icon = oIcon;

        Image mark;
        if(getPlayer()==1)
            mark = p1Icon;
        else
            mark = p2Icon;
        System.out.println("Mark set acc to player!");
        ImageView imageView = new ImageView(mark);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setLayoutX((j*cell)+550);
        imageView.setLayoutY((i*cell)+150);
        marks.getChildren().add(imageView);
        System.out.println("Marked on grid!");
    }
    public static void showTurn(int i, int j, int[] superIndex){
        Image xIcon = new Image("x.png");
        Image p1Icon = xIcon;
        Image oIcon = new Image("o.png");
        Image p2Icon = oIcon;

        Image mark;
        if(getPlayer()==1)
            mark = p1Icon;
        else
            mark = p2Icon;
        System.out.println("Mark set acc to player!");
        ImageView imageView = new ImageView(mark);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        imageView.setLayoutX((j*cell)+(superIndex[1]*cell)+550);
        imageView.setLayoutY((i*cell)+(superIndex[0]*cell)+150);
        marks.getChildren().add(imageView);
        System.out.println("Marked on grid!");
    }
    public static void markLine(int value){
        int[][] lineIndex = dictionary.get(value);
        int startX = ((lineIndex[0][1]*cell)+(int)(cell*2.8));
        int startY = ((lineIndex[0][0]*cell)+cell);
        int endX = ((lineIndex[2][1]*cell)+(int)(cell*3.2));
        int endY = ((lineIndex[2][0]*cell)+cell);

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
        if(choice == (int)(Math.random()*2)){
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
