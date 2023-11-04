package Tic_Tac_Ultimate;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.util.Random;

public class GUI extends Application  {
    private Scene game ;

    private static Color background = Color.web("#f2f2f2");
    private static Color midground = Color.web("#fff");

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
        stage.setWidth(1280);
        stage.setHeight(720);
        stage.setResizable(true);
        stage.setFullScreen(false);

        Group root = new Group();

        Scene mainView = game =  new Scene(root, background);
        ticTacToe(root);

        stage.setScene(mainView);
        stage.show();
        if(Toss() == (int)(Math.random()*2)){
            System.out.println("player = 1");
        }
        else{
            System.out.println("player = 2");
        }
    }
    public void displayOptions(Stage stage) throws Exception{

    }
    public void displayRuleBook(Stage stage) throws Exception{

    }
    public void displayHelp(Stage stage) throws Exception{

    }
    public void displayAboutUs(Stage stage) throws Exception{

    }
    public void displayProfile(Stage stage) throws Exception{

    }
    private void ticTacToe(Group root) throws Exception{

//        root.set
//        root.setFillHeight(true);
//        root.setBackground(new Background(new BackgroundFill(midground, CornerRadii.EMPTY, Insets.EMPTY)));

//        Line line = new Line();
//        line.setStartY(0);
//        line.setEndY(stage.getHeight());
//        line.setStartX(Math.floor(stage.getWidth()/stage.getHeight()));
//        line.setEndX(Math.floor(stage.getWidth()/stage.getHeight())+stage.getHeight());

        int cell = 600 / 3;

        for (int row = 0; row < 4; row++) {
            int y = (row * cell) + 500;
            Line line = new Line();
            line.setStartY(100);
            line.setEndY(700);
            line.setStartX(y);
            line.setEndX(y);
            line.setStrokeWidth(5);
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
            root.getChildren().add(line);
        }
    }
    private int Toss() throws Exception{
        final int[] choice = {-1};
        //stage
        Stage toss = new Stage(StageStyle.UNDECORATED);
        toss.setWidth(500);
        toss.setHeight(200);
        toss.setResizable(false);
        toss.initModality(Modality.APPLICATION_MODAL);
        toss.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                shakeStage(toss);
            }
        });




        //root Node
        VBox root = new VBox(new Text("Select your side for the toss"));
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 200, 100, midground);

        //Buttons
        Button heads = new Button();
        Button tails = new Button();
        heads.setMinSize(150,50);
        heads.setText("Heads");
        heads.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Button was pressed!");
                choice[0] = 0;
                toss.close();
            }
        });
        tails.setMinSize(150,50);
        tails.setText("Tails");
        tails.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Button was pressed!");
                choice[0] = 1;
                toss.close();
            }
        });

        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().add(heads);
        buttons.getChildren().add(tails);

        root.getChildren().add(buttons);


        //Show Stage
        toss.setScene(scene);
        toss.showAndWait();
        return choice[0];
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

    //ANother method{
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


    public static int endGame(){
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
        VBox root = new VBox(new Text("Play Again?"));
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 200, 100, midground);

        //Buttons
        Button playAgain = new Button();
        Button exit = new Button();
        playAgain.setMinSize(150,50);
        playAgain.setText("Yes");
        playAgain.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Button was pressed!");
                choice[0] = 0;
                endGame.close();
            }
        });
        exit.setMinSize(150,50);
        exit.setText("Exit");
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Button was pressed!");
                choice[0] = 1;
                endGame.close();
            }
        });

        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().add(playAgain);
        buttons.getChildren().add(exit);

        root.getChildren().add(buttons);


        //Show Stage
        endGame.setScene(scene);
        endGame.showAndWait();
        return choice[0];
    }
    public static void endGame(boolean win,int player, int[] superIndex){
        if(win)
            System.out.println("place 'player' at index");
        else
            System.out.println("place 'D' at index");
    }


//    public void invalidated(Observable observable) {
//        int newValue = (int) observable.getValue();
//        if (newValue != -1) {
//            int a=1;
////            onValueChanged();
//        }
//    }
}
