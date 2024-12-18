package Tic_Tac_Ultimate;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicInteger;

import static Tic_Tac_Ultimate.GUI.*;
import static Tic_Tac_Ultimate.Runner.endGame;

final public class GuiUtility {
    public static HBox getTitle(double fontSize, double tranlateY){
        Text ticTac = new Text("Tic tac");
        Text ultimate = new Text(" Ultimate ");
        Rectangle background = new Rectangle();
        StackPane complex = new StackPane(background, ultimate);

        HBox title = new HBox(ticTac,complex);
        title.setAlignment(Pos.CENTER);
        title.setTranslateY(tranlateY);
        title.setSpacing(5);

        ticTac.setFont(Font.font("Franklin Gothic Heavy", FontWeight.BOLD, fontSize));
        ticTac.fillProperty().bind(foreGround);

        ultimate.setFont(Font.font("Franklin Gothic Heavy", FontWeight.BOLD, fontSize));
        ultimate.setFill(Color.WHITE);

        background.setWidth(ultimate.getLayoutBounds().getWidth());
        background.setHeight(ultimate.getLayoutBounds().getHeight());
        background.setFill(Color.RED);

        return title;
    }
    public static Button getButton(String text, double width, double height){
        Button button = new Button(text);
        String style = "-fx-font-size: 17.5;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: rgb(255,255,255);" +
                "-fx-background-radius: 25;" +
                "-fx-border-radius: 25;" +
                "-fx-border-color: rgba(0,0,0,0);";
        button.setStyle("-fx-background-color:  rgb(197,197,197);" + style);
        button.setPrefSize(width,height);
        button.setCursor(Cursor.HAND);
        button.setOnMouseEntered(e -> button.setStyle( "-fx-background-color: blue; " + style));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: rgb(197,197,197); " + style ));
        return button;
    }
    public static TextField textField(String str, boolean[] modified, int index, Scene scene){
        TextField textField = new TextField(str);
        textField.setStyle("-fx-text-fill: #000000;" +
                "-fx-background-color: rgb(180,180,180);" +
                "-fx-background-radius: 25;" +
                "-fx-border-radius: 25;" +
                "-fx-border-color: rgba(0,0,0);");
        textField.setPrefSize(300,45);
        scene.focusOwnerProperty().addListener((observable, oldOwner, newOwner) ->{
            if(textField.getText().isEmpty() && textField == oldOwner) {
                textField.setText(str);
                modified[index] = false;
            }
            if(!modified[index] && textField == newOwner)
                textField.clear();
        });
        textField.setOnKeyTyped( e -> {
            if (!modified[index])
                modified[index] = true;
        });
        return textField;
    }
    public static TranslateTransition translateY(double startY, double endY, Node node, double sec){
        TranslateTransition transition = new TranslateTransition(Duration.seconds(sec), node);
        transition.setFromY(startY);
        transition.setToY(endY);
        transition.setCycleCount(1);
        transition.play();
        return transition;
    }
    public static TranslateTransition translateX(double startX, double endX, Node node, double sec){
        TranslateTransition transition = new TranslateTransition(Duration.seconds(sec), node);
        transition.setFromX(startX);
        transition.setToX(endX);
        transition.setCycleCount(1);
        transition.play();
        return transition;
    }
    public static ScaleTransition scale(double toX, double toY, Node node, double sec){
        ScaleTransition transition = new ScaleTransition(Duration.seconds(sec), node);
        transition.setToX(toX);
        transition.setToY(toY);
        transition.setCycleCount(1);
        transition.play();
        return transition;
    }
    public static RotateTransition rotate(int fromAngle, int toAngle, Node node){
        RotateTransition transition = new RotateTransition(Duration.seconds(2), node);
        transition.setAxis(Rotate.X_AXIS);
        transition.setFromAngle(fromAngle);
        transition.setToAngle(toAngle);
        transition.setCycleCount(1);
        transition.play();
        return transition;
    }
    public static FillTransition fill(Color from, Color to, double sec, Rectangle rect, int count, boolean reverse){
        FillTransition transition = new FillTransition(Duration.seconds(sec), rect);
        transition.setFromValue(from);
        transition.setToValue(to);
        transition.setAutoReverse(reverse);
        transition.setCycleCount(count);
        transition.play();
        return transition;
    }
    public static Rectangle makeRectangle(double widthMultiplier, double heightMultiplier){
        Rectangle rectangle = new Rectangle();
        rectangle.setFill(midGround.get());
        rectangle.widthProperty().bind(root.heightProperty().multiply(widthMultiplier));
        rectangle.heightProperty().bind(root.heightProperty().multiply(heightMultiplier));
        rectangle.setArcWidth(50);
        rectangle.setArcHeight(50);
        return rectangle;
    }
    public static void blink(Rectangle box, int type){
        Color color = (Color) midGround.get();
        FillTransition fill = fill(color, Color.RED, (0.08*type), box, 3, true);
        fill.setOnFinished(event -> box.setFill(midGround.get()));
    }
    public static void blink(Pane pane, int count){
        pane.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
        Pane emptyPane = new Pane();
        TranslateTransition empty = new TranslateTransition(Duration.seconds(0.1), emptyPane);
        empty.play();
        count--;
        int finalCount = count;
        empty.setOnFinished(end -> {
            pane.setBackground(new Background(new BackgroundFill(midGround.get(), CornerRadii.EMPTY, Insets.EMPTY)));
            if(finalCount >=0)
                blink(pane, finalCount);
        });
    }
    public static void popUp(String text, String button1Text, String button2Text, int method){
        BorderPane background = new BorderPane();
        StackPane popUp = new StackPane();
        background.setCenter(popUp);
        background.setBackground(new Background(new BackgroundFill( Color.color(0,0,0,0.8), CornerRadii.EMPTY, Insets.EMPTY)));
        Rectangle box = makeRectangle(0.5,0.25);
        popUp.getChildren().add(box);

        background.setOnMouseClicked(click -> root.getChildren().remove(background));
        root.getChildren().add(background);
        Text header = new Text(text);
        header.setFont(Font.font("Franklin Gothic", FontWeight.BOLD, 20));
        VBox vBox = new VBox(header);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(30);
        popUp.getChildren().add(vBox);

        Button button1 = new Button(button1Text);
        String style = "-fx-padding: 10 20; " +
                "-fx-font-family: 'Franklin Gothic';" +
                "-fx-font-weight: Bold;" +
                "-fx-font-size: 25;";
        if(button1Text.equals("Heads")){
            button1.setStyle("-fx-text-fill: Orange;" + style);
            button1.setOnMouseEntered(e-> button1.setStyle("-fx-text-fill: Orange; " +
                    "-fx-border-radius: 50em; " +
                    "-fx-border-color: Orange; " +
                    "-fx-border-width: 4px;" + style));
            button1.setOnMouseExited(e-> button1.setStyle("-fx-text-fill: Orange; " + style));
        }
        else{
            button1.setStyle("-fx-text-fill: Green;" + style);
            button1.setOnMouseEntered(e-> button1.setStyle("-fx-text-fill: Green; " +
                    "-fx-border-radius: 50em; " +
                    "-fx-border-color: Green; " +
                    "-fx-border-width: 4px;" + style));
            button1.setOnMouseExited(e-> button1.setStyle("-fx-text-fill: Green; " + style));
        }
        button1.setBackground(new Background(new BackgroundFill(Color.color(0,0,0,0), CornerRadii.EMPTY, Insets.EMPTY)));
        button1.setTranslateY(0);
        button1.setMinSize(100,20);
        button1.setOnAction(event -> {
            System.out.println("Button1 was pressed!");
            root.getChildren().remove(background);
            if(method==1)
                Toss(1);
            else if(method==2)
                endGame(1);
            else if(method==3)
                stage.close();
        });

        Button button2 = new Button(button2Text);
        if(button2Text.equals("Tails")){
            button2.setStyle("-fx-text-fill: Orange;" + style);
            button2.setOnMouseEntered(e-> button2.setStyle("-fx-text-fill: Orange; " +
                    "-fx-border-radius: 50em; " +
                    "-fx-border-color: Orange; " +
                    "-fx-border-width: 4px;" + style));
            button2.setOnMouseExited(e-> button2.setStyle("-fx-text-fill: Orange; " + style));
        }
        else{
            button2.setStyle("-fx-text-fill: Red;" + style);
            button2.setOnMouseEntered(e-> button2.setStyle("-fx-text-fill: Red; " +
                    "-fx-border-radius: 50em; " +
                    "-fx-border-color: Red; " +
                    "-fx-border-width: 4px;" + style));
            button2.setOnMouseExited(e-> button2.setStyle("-fx-text-fill: Red; " + style));
        }
        button2.setBackground(new Background(new BackgroundFill(Color.color(0,0,0,0), CornerRadii.EMPTY, Insets.EMPTY)));
        button2.setTranslateY(0);
        button2.setMinSize(100,20);
//        button2.setStyle("-fx-font-size: 20");
        button2.setOnAction(event -> {
            System.out.println("Button2 was pressed!");
            root.getChildren().remove(background);
            if(method==1)
                Toss(2);
            else if(method==2)
                endGame(2);
        });
        HBox buttons = new HBox(button1,button2);
        buttons.setAlignment(Pos.BASELINE_CENTER);
        buttons.setSpacing(30);
        vBox.getChildren().add(buttons);

    }
    public static void shake(Node node) {
        final double originalX = node.getTranslateX();
        final double originalY = node.getTranslateY();
        final Timeline[] timeline = new Timeline[1];
        timeline[0] = new Timeline(new KeyFrame (Duration.millis(1),new EventHandler<ActionEvent>() {
            int shakeCount = 0;
            @Override
            public void handle(ActionEvent event) {
                if (!(shakeCount > 400)) {
                    int dist = shakeCount++ % 10;
                    if((shakeCount >= 100 && shakeCount < 200) || (shakeCount >= 300 && shakeCount < 400)){
                        dist *=-1;
                    }
                    node.setTranslateX(originalX + dist);
                    node.setTranslateY(originalY + dist);
                }
                else {
                    node.setTranslateX(originalX);
                    node.setTranslateY(originalY);
                    timeline[0].stop();
                }
            }
        }
        ));
        timeline[0].setCycleCount(Timeline.INDEFINITE);
        timeline[0].play();
    }
    public static void reject(Pane node){
        shake(node);
        blink(node, 3);
    }
    public static Line makeHLine(double Multiplier, int stroke, Pane grid){
        Line line = new Line();
        line.setStartY(0);
        line.endYProperty().bind(grid.maxHeightProperty());
        line.startXProperty().bind(grid.maxWidthProperty().multiply(Multiplier));
        line.endXProperty().bind(grid.maxWidthProperty().multiply(Multiplier));
        line.setStrokeWidth(stroke);
        return line;
    }
    public static Line makeVLine(double Multiplier, int stroke, Pane grid){
        Line line = new Line();
        line.startYProperty().bind(grid.maxHeightProperty().multiply(Multiplier));
        line.endYProperty().bind(grid.maxHeightProperty().multiply(Multiplier));
        line.setStartX(0);
        line.endXProperty().bind(grid.maxWidthProperty());
        line.setStrokeWidth(stroke);
        return line;
    }
}