package Tic_Tac_Ultimate;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static Tic_Tac_Ultimate.GUI.root;
import static Tic_Tac_Ultimate.GUI.stage;
import static Tic_Tac_Ultimate.GuiUtility.makeRectangle;
import static Tic_Tac_Ultimate.Runner.sleep;


public class Profile extends Pane {
    Profile(Stage stage){
        init(stage);
    }
    private void init(Stage stage){
        maxWidthProperty().bind(stage.widthProperty().multiply(0.8));
        translateXProperty().bind(maxWidthProperty().divide(8));
        setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        ImageView dp = new ImageView();
        StringProperty imagePathProperty;
        File file = new File("Profile files/User pictures/user.jpg");
        if(file.exists())
            imagePathProperty = new SimpleStringProperty("Profile files/User pictures/user.jpg");
        else
            imagePathProperty = new SimpleStringProperty("Profile files/default.png");
        dp.imageProperty().bind(Bindings.createObjectBinding(() -> {
            try {
                return new Image(imagePathProperty.get());
            } catch (Exception e) {
                // Handle loading errors gracefully
                e.printStackTrace();
                return null;
            }
        }, imagePathProperty));
        dp.setFitWidth(200);
        dp.setPreserveRatio(true);
//            dp.setTranslateY(dp.getFitWidth()/2);
//            dp.xProperty().bind(this.maxWidthProperty().subtract(dp.getFitWidth()*1.3));
//            dp.yProperty().bind(dp.fitWidthProperty().multiply(0.25));
        dp.setMouseTransparent(true);
        Circle frame = new Circle();
            frame.centerXProperty().bind(dp.xProperty().add(dp.getFitWidth()/2));
            frame.centerYProperty().bind(dp.yProperty().add(dp.getFitWidth()/2));
        frame.radiusProperty().bind(dp.fitWidthProperty().divide(2));
//        frame.setStroke(Color.GRAY);
        frame.setFill(Color.BLUE);
        dp.setClip(frame);
//        frame.setStrokeWidth(dp.getFitWidth()/2);
//        frame.setStrokeType(StrokeType.OUTSIDE);
        frame.setMouseTransparent(true);

        Arc arc = new Arc(dp.getFitWidth()*1.5,0, frame.getRadius()*1.1,frame.getRadius(), 200, 140); // 180 for semicircle angle
        arc.setTranslateY(dp.getFitWidth()*0.34);
        arc.setFill(Color.TRANSPARENT);
        ImageView edit = new ImageView(new Image("Profile files/edit_profile.png"));
        edit.setFitWidth(40);
        edit.setPreserveRatio(true);
        edit.setTranslateY(arc.getTranslateY()*1.01);
        edit.setMouseTransparent(true);
        edit.setVisible(false);
        arc.setCursor(Cursor.HAND);
        arc.setOnMouseEntered(e -> {
            arc.setFill(Color.rgb(0,0,0,0.6));
            edit.setVisible(true);
        });
        arc.setOnMouseExited(e -> {
            arc.setFill(Color.TRANSPARENT);
            edit.setVisible(false);
        });
        arc.setOnMouseClicked(e ->{
            arc.setFill(Color.BLUE);
            System.out.println("Clicked");
            changeImage(dp,imagePathProperty);
        });
        StackPane photo = new StackPane(dp,arc,edit);
        photo.translateXProperty().bind(this.maxWidthProperty().subtract(dp.getFitWidth()*1.2));
        photo.translateYProperty().bind(dp.fitWidthProperty().multiply(0.5));
        photo.setAlignment(Pos.CENTER);
        getChildren().add(photo);

    }
    private void changeImage(ImageView image, StringProperty imagePathProperty){
        BorderPane background = new BorderPane();
        root.getChildren().add(background);
        StackPane popUp = new StackPane();
        background.setCenter(popUp);
        background.setBackground(new Background(new BackgroundFill( Color.color(0,0,0,0.8), CornerRadii.EMPTY, Insets.EMPTY)));
        background.setOnMouseClicked(e -> root.getChildren().remove(background));
        Rectangle box = makeRectangle(1,0.7);
        String style ="-fx-font-size: 20;" +
                "-fx-text-alignment: center;";
        Text text = new Text("Drag and drop the file here you want to change profile picture to\nor");
        text.setStyle(style);
        Text browse = new Text("browse the file");
        browse.setStyle( "-fx-fill: blue;" +
                "-fx-underline: true;" + style);
        browse.setCursor(Cursor.HAND);
        browse.setOnMouseClicked(e ->{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Image File");
            // Optionally set initial directory or filters
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                // File selected, proceed with file copying
                try {
                    String ext =  selectedFile.getName().substring(selectedFile.getName().lastIndexOf("."));
                    Files.copy(Path.of(selectedFile.getAbsolutePath()), new File("src/Profile files/User pictures",selectedFile.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                    imagePathProperty.set(selectedFile.toURI().toString());
                    //                    sleep(0.2);
//                    image.setImage(new Image("Profile files/User pictures/user"+ext));
                    root.getChildren().remove(background);
                    // Handle successful copy
                } catch (IOException e1) {
                    // Handle copying errors
                    e1.printStackTrace();
                }
            }
        });
        VBox vBox = new VBox(text, browse);
        vBox.setAlignment(Pos.CENTER);
        popUp.getChildren().addAll(box,vBox);
        popUp.setOnDragOver(event -> {
            if (event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY); // Allow only copying
            }
            event.consume();
        });
        popUp.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            if (db.hasFiles()) {
                List<File> files = db.getFiles();
                // Copy files to destination
                for (File file : files) {
                    try {
                        Files.copy(file.toPath(), new File("src/Profile files/User pictures", file.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                        sleep(0.2);
                        image.setImage(new Image("Profile files/User pictures/"+file.getName()));
                        root.getChildren().remove(background);
                    }
                    catch (IOException e) {
                        // Handle copying errors here
                        e.printStackTrace();
                    }
                }
                event.setDropCompleted(true);
            }
            event.consume();
        });
//        popUp.setOnDragDropped(e -> {
//            Dragboard dragboard = e.getDragboard();
//            if (dragboard.hasFiles()) {
//                for (File file : dragboard.getFiles()) {
//                    String filePath = file.toURI().toString();
//                    System.out.println("FIle path: " + filePath);
//                    // Specify the predetermined destination path
//                    String destinationPath = "Profile files/";
//
//                    // Copy the file to the destination path
//                    try {
//                        Files.copy(Paths.get(filePath), Paths.get(destinationPath + file.getName()), StandardCopyOption.REPLACE_EXISTING);
//                        System.out.println("File copied to: " + destinationPath);
//                    }
//                    catch (IOException e1) {
//                        e1.printStackTrace();
//                        // Handle the error gracefully, e.g., display an error message to the user
//                    }
//                }
//                e.setDropCompleted(true);
//            }
//            e.consume();
//            });
    }
}