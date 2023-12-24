package Tic_Tac_Ultimate;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static Tic_Tac_Ultimate.GUI.root;
import static Tic_Tac_Ultimate.GuiUtility.makeRectangle;


public class Profile extends Pane {
    Profile(Stage stage){
        init(stage);
    }
    private void init(Stage stage){
        Platform.runLater(()->{
            maxWidthProperty().bind(stage.widthProperty().multiply(0.8));
            translateXProperty().bind(maxWidthProperty().divide(8));
            setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
            ImageView dp = new ImageView(new Image("Profile files/default.png"));
            dp.setFitWidth(200);
            dp.setPreserveRatio(true);
//            dp.setTranslateY(dp.getFitWidth()/2);
//            dp.xProperty().bind(this.maxWidthProperty().subtract(dp.getFitWidth()*1.3));
//            dp.yProperty().bind(dp.fitWidthProperty().multiply(0.25));
            dp.setMouseTransparent(true);
            Circle frame = new Circle();
//            frame.centerXProperty().bind(dp.xProperty().add(dp.getFitWidth()/2));
//            frame.centerYProperty().bind(dp.yProperty().add(dp.getFitWidth()/2));
            frame.radiusProperty().bind(dp.fitWidthProperty().divide(2));
            frame.setStroke(Color.GRAY);
            frame.setFill(Color.TRANSPARENT);
            frame.setStrokeWidth(dp.getFitWidth()/2);
            frame.setStrokeType(StrokeType.OUTSIDE);
            frame.setMouseTransparent(true);

            Arc arc = new Arc(dp.getFitWidth()*1.5,0, frame.getRadius()*1.1,frame.getRadius(), 200, 140); // 180 for semicircle angle
            arc.setTranslateY(dp.getFitWidth()*0.34);
            arc.setFill(Color.BLUE);
            arc.setOnMouseEntered(e -> arc.setFill(Color.rgb(0,0,0,0.6)));
            arc.setOnMouseExited(e -> arc.setFill(Color.TRANSPARENT));
            arc.setOnMouseClicked(e ->{
                arc.setFill(Color.BLUE);
                System.out.println("Clicked");
                changeImage();
            });
            StackPane photo = new StackPane(dp,arc,frame);
            photo.translateXProperty().bind(this.maxWidthProperty().subtract(dp.getFitWidth()*1.8));
            photo.translateYProperty().bind(dp.fitWidthProperty().multiply(-0.2));
            getChildren().add(photo);
        });
    }
    private void changeImage(){
        BorderPane background = new BorderPane();
        root.getChildren().add(background);
        StackPane popUp = new StackPane();
        background.setCenter(popUp);
        background.setBackground(new Background(new BackgroundFill( Color.color(0,0,0,0.8), CornerRadii.EMPTY, Insets.EMPTY)));
        Rectangle box = makeRectangle(0.5,0.5);
        popUp.getChildren().add(box);
        popUp.setOnDragOver(event -> {
            if (event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY);
            }
//            event.consume();
        });
        popUp.setOnDragDropped(e -> {
            Dragboard dragboard = e.getDragboard();
            if (dragboard.hasFiles()) {
                for (File file : dragboard.getFiles()) {
                    String filePath = file.toURI().toString();
                    filePath = filePath.substring(6);
                    System.out.println("FIle path: " + filePath);
                    // Specify the predetermined destination path
                    String destinationPath = "Profile files/";

                    // Copy the file to the destination path
                    try {
                        Files.copy(Paths.get(filePath), Paths.get(destinationPath + file.getName()), StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("File copied to: " + destinationPath);
                    }
                    catch (IOException e1) {
                        e1.printStackTrace();
                        // Handle the error gracefully, e.g., display an error message to the user
                    }
                }
                e.setDropCompleted(true);
            }
            e.consume();
            });
    }
}