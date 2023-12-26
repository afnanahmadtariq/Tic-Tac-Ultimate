package Tic_Tac_Ultimate;

import javafx.animation.*;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

import static Tic_Tac_Ultimate.Board.dictionary;
import static Tic_Tac_Ultimate.GuiUtility.*;
import static Tic_Tac_Ultimate.QuxioBoard.quxioWinValues;
import static Tic_Tac_Ultimate.Runner.*;

public class GUI extends Application {
    private static Stage stage;
    public static Scene scene;
    public static StackPane root;
    public static StringProperty fontColor = new SimpleStringProperty("#FFFFFF");
//    public static ObjectProperty<Paint> FontColor = new SimpleObjectProperty<>(Color.BLACK);
    public static ObjectProperty<Paint> backGround = new SimpleObjectProperty<>(Color.web("#cc9966"));
    public static ObjectProperty<Paint> greenColor = new SimpleObjectProperty<>(Color.GREEN);
    public static ObjectProperty<Paint> midGround = new SimpleObjectProperty<>(Color.web("#996633"));
    public static ObjectProperty<Paint> foreGround = new SimpleObjectProperty<>(Color.web("#663300"));
    public static Text turn1;
    public static Text turn2;
    public static Group grid1;
    public static Group grid2;
    private static BorderPane gamePane;
    public static Pane boxPane;
    public static Group arrowGroup;
    public static Group marks;
    public static String selectedGameType;
    public static String selectedPlayer;
    public static String selectedDifficulty;
    public static int check;
    public static double cell;
    public static boolean listen;

    static {
        listen = true;
    }
    public static void initialize(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage stage){
        GUI.stage = stage;
        Image icon = new Image("U.png");
        stage.getIcons().add(icon);
        stage.setTitle("Tic Tac Ultimate");
        stage.setWidth(1536);
        stage.setHeight(864);
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.setX(0);
        stage.setY(0);
        root = new StackPane();
        root.setBackground(new Background(new BackgroundFill(backGround.get(), CornerRadii.EMPTY, Insets.EMPTY)));

        root.setOnKeyPressed(event->{
            if(event.getCode()== KeyCode.ESCAPE) {
                List<Node> roots = root.getChildren();
                Node node = roots.getLast();
                System.out.println(node);
                if(node == gamePane)
                    displayGameMenu();
                else if (!(node instanceof Group)){
                    System.out.println("I escaped");
                    root.getChildren().removeLast();
                }
            }
        });

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        displayMainMenu();
    }
    private static void displayStart(){
        Text ticTac = new Text("Tic tac");
        Text toe = new Text(" toe");
        Text ultimate = new Text(" Ultimate ");
        Rectangle background = new Rectangle();
        StackPane transitionComplex = new StackPane(toe, background, ultimate);
        transitionComplex.setAlignment(Pos.CENTER_LEFT);
        HBox title = new HBox(ticTac,transitionComplex);
        title.setAlignment(Pos.CENTER);
        title.setStyle("-fx-background-color: #ffff;");
        title.setSpacing(10);
        root.getChildren().add(title);


        // Create a Text node
        ticTac.setFont(Font.font("Franklin Gothic Heavy", FontWeight.BOLD, 102));
        ticTac.setFill(Color.BLACK);

        toe.setFont(Font.font("Franklin Gothic Heavy", FontWeight.BOLD, 102));
        toe.setFill(Color.BLACK);

        ultimate.setFont(Font.font("Franklin Gothic Heavy", FontWeight.BOLD, 102));
        ultimate.setFill(Color.WHITE);
        ultimate.setTranslateY(-ultimate.getLayoutBounds().getHeight());

        background.setWidth(ultimate.getLayoutBounds().getWidth());
        background.setHeight(ultimate.getLayoutBounds().getHeight());
        background.setTranslateY(-ultimate.getLayoutBounds().getHeight());
        background.setFill(Color.WHITE);


        Region empty = new Region();
        TranslateTransition emptyTransition = new TranslateTransition(Duration.seconds(0.5), empty);
        emptyTransition.play();
        emptyTransition.setOnFinished(event -> {
            System.out.println("Transition completed");
            rotate(0,90, toe);
            translateY(0,ultimate.getLayoutBounds().getHeight()/2, toe, 2);

            rotate(-90,0, ultimate);
            translateY(-ultimate.getLayoutBounds().getHeight()/2,0, ultimate, 2);

            rotate(-90, 0, background);
            translateY(-ultimate.getLayoutBounds().getHeight()/2,0, background, 2);

            FillTransition color = fill((Color)background.getFill(), Color.RED, 2.5, background, 1, false);
            color.setOnFinished(event2 -> {
                System.out.println("Transition2 completed");
                displayMainMenu();
            });
        });
    }
    private static void displayMainMenu() {
        root.getChildren().clear();
        root.setBackground(new Background(new BackgroundFill(backGround.get(),  CornerRadii.EMPTY, Insets.EMPTY)));

        Text ticTac = new Text("Tic tac");
        Text ultimate = new Text(" Ultimate ");
        Rectangle background = new Rectangle();
        StackPane complex = new StackPane(background, ultimate);

        HBox title = new HBox(ticTac,complex);
        title.setAlignment(Pos.CENTER);
        title.setTranslateY(root.getHeight()*0);
        title.setSpacing(10);

        ticTac.setFont(Font.font("Franklin Gothic Heavy", FontWeight.BOLD, 74));
        System.out.println(foreGround.get());
        ticTac.fillProperty().bind(foreGround);

        ultimate.setFont(Font.font("Franklin Gothic Heavy", FontWeight.BOLD, 74));
        ultimate.setFill(Color.WHITE);

        background.setWidth(ultimate.getLayoutBounds().getWidth());
        background.setHeight(ultimate.getLayoutBounds().getHeight());
        background.setFill(Color.RED);

        Button start = makeButton("New Game");
        Button loadGame = makeButton("Load Game");
        Button options = makeButton("Settings");
        Button exit = makeButton("Exit Game");

        HBox helpButtons = new HBox();
        Button ruleBookButton = roundButton("Rules");
//        Button aboutUsButton = roundButton("About Us");
//        Button helpButton = roundButton("Help");
        helpButtons.getChildren().addAll(ruleBookButton);
        helpButtons.setAlignment(Pos.BASELINE_CENTER);
        helpButtons.setSpacing(20);
        helpButtons.setTranslateY(exit.getTranslateY()+100);

        VBox menuPanel = new VBox(title, start, loadGame, options, exit, helpButtons);
        menuPanel.setAlignment(Pos.TOP_CENTER);
        menuPanel.setSpacing(20);
//        BorderPane border = new BorderPane();
//        Image Img = new Image("file:images/background2.png");
//        border.setBackground(new Background(new BackgroundImage(Img,
//                BackgroundRepeat.NO_REPEAT,
//                BackgroundRepeat.NO_REPEAT,
//                BackgroundPosition.CENTER,
//                BackgroundSize.DEFAULT)));
//        border.getChildren().add(menuPanel);
        Group group = new Group(menuPanel);
        root.getChildren().add(group);
    }
    public static Button makeButton(String text) {
        Button button = new Button(text);
        button.setTranslateY(root.getHeight()*0.1);
        button.setMinSize(200,80);
        String style = "-fx-padding: 10 20; " +
                "-fx-font-family: 'Franklin Gothic';" +
                "-fx-font-weight: Bold;" +
                "-fx-font-size: 45;" +
                "-fx-border-radius: 5;";

        String normalStyle = "-fx-text-fill: " + fontColor.get() + "; " + style;

        button.styleProperty().bind(Bindings.when(button.hoverProperty())
                .then("-fx-text-fill: red; " + style)
                .otherwise(normalStyle));

        button.textProperty().bind(Bindings.when(button.hoverProperty())
                .then("> " + text + " <")
                .otherwise(text));
        button.setBackground(new Background(new BackgroundFill(Color.color(0,0,0,0), CornerRadii.EMPTY, Insets.EMPTY)));
        button.setOnMouseClicked(event -> {
            System.out.println(text +" button was pressed!");
            switch (text) {
                case "New Game" -> displayGameSelection();
                case "Load Game" -> System.out.println("Load Game Button was Pressed");

//                displayOptions();
                case "Settings" -> displaySettings("Profile Settings");

//                displayPopupMessage("Under Development", "Option Button is under development");
                case "Exit Game" -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Exit Game");
                    alert.setContentText("Are you sure you want to exit?");

                    ButtonType exitButton = new ButtonType("Exit");
                    ButtonType cancelButton = new ButtonType("Cancel");
                    alert.getButtonTypes().setAll(exitButton, cancelButton);

                    alert.showAndWait().ifPresent(response -> {
                        if (response == exitButton) {
                            stage.close();
                        }
                    });
                }
            }
        });
        return button;
    }
    private static void displaySettings(String button2Text){

        if(root.getChildren().getLast() instanceof Group) {
            root.getChildren().removeLast();
        }

        BorderPane background = new BorderPane();
        StackPane gameMenu = new StackPane();
        background.setCenter(gameMenu);
        gameMenu.setAlignment(Pos.TOP_CENTER);

// Setting a transparent background for the entire BorderPane
        background.setBackground(new Background(new BackgroundFill(Color.color(0, 0, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));

        Rectangle box = new Rectangle();
        box.fillProperty().bind(backGround);
        box.widthProperty().bind(root.widthProperty());
        box.heightProperty().bind(root.heightProperty());
        gameMenu.getChildren().add(box);
        Text header;
        if(button2Text.equals("Profile Settings"))
            header = new Text("Settings");
        else
            header = new Text("Game Options");
        header.setFont(Font.font("Franklin Gothic", FontWeight.BOLD, 70));
        header.setTextAlignment(TextAlignment.CENTER);

        VBox vBox = new VBox();
        vBox.setTranslateY(100);
        vBox.setSpacing(10); // Set spacing between buttons

        Button appearance = makeOptionButton("Appearance");
        Button button2 = makeOptionButton(button2Text);
        Button players = makeOptionButton("Player Settings");
        Button back = makeOptionButton("Back");



        vBox.getChildren().addAll(appearance, button2, players, back);

        Rectangle areaBox = new Rectangle();
        areaBox.fillProperty().bind(midGround);
        areaBox.setArcHeight(50);
        areaBox.setArcWidth(50);
        areaBox.setWidth(1000); // Set the width according to your preference
        areaBox.setHeight(680); // Set the height according to your preference

        StackPane optionPage = new StackPane();
        optionPage.getChildren().addAll(areaBox, vBox);
        StackPane.setAlignment(vBox, Pos.CENTER_LEFT);
        StackPane.setAlignment(areaBox, Pos.BOTTOM_RIGHT);

        gameMenu.getChildren().addAll(header, optionPage);
        background.setPadding(new Insets(10));

        root.getChildren().add(background);
        appearance.setOnAction(e->{
            System.out.println("Appearance Button");
            appearance.setDisable(true);
            button2.setDisable(true);
            players.setDisable(true);
            displayAppearance(gameMenu, appearance, button2, players);
        });
        button2.setOnAction(e->{
            System.out.println("Button 2");
            if(button2Text.equals("Profile Settings")) {
                appearance.setDisable(true);
                button2.setDisable(true);
                players.setDisable(true);
                displayProfile(gameMenu, appearance, button2, players);
            }
        });
        players.setOnAction(e->{
            System.out.println(players.getText() + " Button Pressed");
        });
        back.setOnAction(e->{
            if(button2Text.equals("Profile Settings"))
                displayMainMenu();
            else
                root.getChildren().removeLast();
        });
//        appearance.setOnMouseClicked(e->{
//            System.out.println("Appearance Here");
//            displayAppearance(areaBox);
//        });

    }
    private static Button roundButton(String text){
        Button button = new Button(text);
        button.setMinSize(70, 70);
        String roundButtonStyle;
        if(!text.equals("<"))
            roundButtonStyle = "-fx-padding: 10 20; " +
                    "-fx-font-family: 'Franklin Gothic';" +
                    "-fx-font-size: 13;" +
                    "-fx-background-radius: 40em;";
        else
            roundButtonStyle = "-fx-padding: 20 20; " +
                    "-fx-font-family: 'Franklin Gothic';" +
                    "-fx-font-size: 25;" +
                    "-fx-alignment: center;" +
                    "-fx-background-radius: 40em;";

        System.out.println(backGround.get());
        String normalStyle = "-fx-background-color: " + fontColor.get() + "; -fx-text-fill: " +  backGround.toString() +";" + roundButtonStyle;

        button.styleProperty().bind(Bindings.when(button.hoverProperty())
                .then("-fx-background-color: Red; " + roundButtonStyle)
                .otherwise(normalStyle));

//        button.styleProperty().bind(
//                Bindings.createStringBinding(() ->
//                        "-fx-text-fill: " + fontColor.get() + "; " +
//                                roundButtonStyle,fontColor));
//        button.setOnMouseEntered(e -> {
//            button.styleProperty().bind(
//                    Bindings.createStringBinding(() ->
//                            "-fx-text-fill: " + fontColor.get() + "; -fx-background-color: Red; " +
//                                    roundButtonStyle,fontColor));
//        });

        button.setOnAction(event -> {
            System.out.println(text + " button was pressed!");
            switch (text) {
                case "Rules" -> displayRuleBook();
                case "About Us" -> displayAboutUs();
                case "Help" -> displayHelp();
            }

        });
        return button;
    }
    private static void displayPopupMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null); // Setting header text to null means no header
        alert.setContentText(message);
        alert.showAndWait(); // Wait for user action (OK button click) before continuing
    }
    private static Button SelectGameButtons(String text){
//        String enterColor = "Red";
//        String exitColor = "Black";
        Button button = new Button(text);
//        button.setTranslateY(root.getHeight()*0.1);
        button.setMinSize(root.getWidth()/6-20,100);
        String style;
        if(!text.equals("Play")){
            style = "-fx-padding: 10 20; " +
                    "-fx-font-family: 'Franklin Gothic';" +
                    "-fx-font-size: 35;" +
                    "-fx-border-radius: 30em;";
            String normalStyle = "-fx-text-fill: " + fontColor.get() + "; " + style;

            button.styleProperty().bind(Bindings.when(button.hoverProperty())
                    .then("-fx-text-fill: Red; -fx-border-color: Red; -fx-border-width: 4px;" + style)
                    .otherwise(normalStyle));

//            button.styleProperty().bind(
//                    Bindings.createStringBinding(() ->
//                            "-fx-text-fill: " + fontColor.get() + "; " +
//                                    style,fontColor));
//            button.setOnMouseEntered(e -> {
//                button.styleProperty().bind(
//                        Bindings.createStringBinding(() ->
//                                "-fx-text-fill: " + fontHoverColor.get() + "; -fx-border-color: Red; " +
//                                        "-fx-border-width: 4px;" +
//                                        style,fontHoverColor));
//            });
//            button.setOnMouseExited(e -> {
//                button.styleProperty().bind(
//                        Bindings.createStringBinding(() ->
//                                "-fx-text-fill: " + fontColor.get() + "; " +
//                                        style,fontColor));
//            });
        }
        else{
            style = "-fx-padding: 10 20; " +
                    "-fx-font-family: 'Franklin Gothic';" +
                    "-fx-border-radius: 10em;" +
                    "-fx-font-size: 35;" +
                    "-fx-border-width: 4px;";

            String normalStyle = "-fx-text-fill: " + fontColor.get() + "; -fx-border-color: " + fontColor.get() + "; " + style;

            button.styleProperty().bind(Bindings.when(button.hoverProperty())
                    .then("-fx-text-fill: Red; -fx-border-color: Red; -fx-font-weight: Bold; " + style)
                    .otherwise(normalStyle));

            button.textProperty().bind(Bindings.when(button.hoverProperty())
                    .then(text + " >")
                    .otherwise(text));

//
        }

        button.setBackground(new Background(new BackgroundFill(Color.color(0,0,0,0), CornerRadii.EMPTY, Insets.EMPTY)));

        return button;
    }
    private static void setSelectedType(Button button1, Button button2, Button button3, Button button4){
        String style = "-fx-padding: 10 20; " +
                "-fx-font-family: 'Franklin Gothic';" +
                "-fx-font-size: 35;" +
                "-fx-border-radius: 50em;";

        String selectedStyle = "-fx-text-fill: Green; -fx-border-color: Green; -fx-border-width: 4px; -fx-font-weight: Bold; " + style;
        String deSelectedStyle = "-fx-text-fill: Gray; -fx-border-color: Gray; -fx-border-width: 0px; " + style;

        button1.styleProperty().bind(Bindings.createStringBinding(() ->selectedStyle));
        button2.styleProperty().bind(Bindings.createStringBinding(() ->deSelectedStyle));
        button3.styleProperty().bind(Bindings.createStringBinding(() ->deSelectedStyle));
        button4.styleProperty().bind(Bindings.createStringBinding(() ->deSelectedStyle));

        button1.styleProperty().bind(Bindings.when(button1.hoverProperty())
                .then(selectedStyle)
                .otherwise(selectedStyle));
        button2.styleProperty().bind(Bindings.when(button2.hoverProperty())
                .then(deSelectedStyle)
                .otherwise(deSelectedStyle));
        button3.styleProperty().bind(Bindings.when(button3.hoverProperty())
                .then(deSelectedStyle)
                .otherwise(deSelectedStyle));
        button4.styleProperty().bind(Bindings.when(button4.hoverProperty())
                .then(deSelectedStyle)
                .otherwise(deSelectedStyle));

    }
    private static void setSelectedType(Button button1, Button button2, Button button3){
        String style = "-fx-padding: 10 20; " +
                "-fx-font-family: 'Franklin Gothic';" +
                "-fx-font-size: 35;" +
                "-fx-border-radius: 50em;";

        String selectedStyle = "-fx-text-fill: Green; -fx-border-color: Green; -fx-border-width: 4px; -fx-font-weight: Bold; " + style;
        String deSelectedStyle = "-fx-text-fill: Gray; -fx-border-color: Gray; -fx-border-width: 0px; " + style;

        button1.styleProperty().bind(Bindings.createStringBinding(() ->selectedStyle));
        button2.styleProperty().bind(Bindings.createStringBinding(() ->deSelectedStyle));
        button3.styleProperty().bind(Bindings.createStringBinding(() ->deSelectedStyle));

        button1.styleProperty().bind(Bindings.when(button1.hoverProperty())
                .then(selectedStyle)
                .otherwise(selectedStyle));
        button2.styleProperty().bind(Bindings.when(button2.hoverProperty())
                .then(deSelectedStyle)
                .otherwise(deSelectedStyle));
        button3.styleProperty().bind(Bindings.when(button3.hoverProperty())
                .then(deSelectedStyle)
                .otherwise(deSelectedStyle));
//
//
//        button1.setStyle( "-fx-border-color: Green; " + "-fx-text-fill: Green; -fx-border-width: 4px; -fx-font-weight: Bold;" + style);
//        button2.setStyle( "-fx-border-color: Gray; " + "-fx-text-fill: Gray; -fx-border-width: 0px; " + style);
//        button3.setStyle( "-fx-border-color: Gray; " + "-fx-text-fill: Gray; -fx-border-width: 0px; " + style);
//        button1.setOnMouseEntered(e -> button1.setStyle(button1.getStyle()));
//        button1.setOnMouseExited(e -> button1.setStyle(button1.getStyle()));
//        button2.setOnMouseEntered(e -> button2.setStyle(button2.getStyle()));
//        button2.setOnMouseExited(e -> button2.setStyle(button2.getStyle()));
//        button3.setOnMouseEntered(e -> button3.setStyle(button3.getStyle()));
//        button3.setOnMouseExited(e -> button3.setStyle(button3.getStyle()));
    }
    private static void setSelectedType(Button button1, Button button2){
        String style = "-fx-padding: 10 20; " +
                "-fx-font-family: 'Franklin Gothic';" +
                "-fx-font-size: 35;" +
                "-fx-border-radius: 30em;";

        String selectedStyle = "-fx-text-fill: Green; -fx-border-color: Green; -fx-border-width: 4px; -fx-font-weight: Bold; " + style;
        String deSelectedStyle = "-fx-text-fill: Gray; -fx-border-color: Gray; -fx-border-width: 0px; " + style;

        button1.styleProperty().bind(Bindings.createStringBinding(() ->selectedStyle));
        button2.styleProperty().bind(Bindings.createStringBinding(() ->deSelectedStyle));

        button1.styleProperty().bind(Bindings.when(button1.hoverProperty())
                .then(selectedStyle)
                .otherwise(selectedStyle));
        button2.styleProperty().bind(Bindings.when(button2.hoverProperty())
                .then(deSelectedStyle)
                .otherwise(deSelectedStyle));

//        button1.setStyle( "-fx-border-color: Green; " + "-fx-text-fill: Green; -fx-border-width: 4px; -fx-font-weight: Bold;" + style);
//        button2.setStyle( "-fx-border-color: Gray; " + "-fx-text-fill: Gray; -fx-border-width: 0px; " + style);
//        button1.setOnMouseEntered(e -> button1.setStyle(button1.getStyle()));
//        button1.setOnMouseExited(e -> button1.setStyle(button1.getStyle()));
//        button2.setOnMouseEntered(e -> button2.setStyle(button2.getStyle()));
//        button2.setOnMouseExited(e -> button2.setStyle(button2.getStyle()));
    }
    private static void displayGameSelection() {
        StackPane gameSelection = new StackPane();
        root.getChildren().add(gameSelection);
        Rectangle box = new Rectangle();
        box.fillProperty().bind(backGround);
        box.widthProperty().bind(root.widthProperty());
        box.heightProperty().bind(root.heightProperty());

        VBox selectionPanel = new VBox();
        selectionPanel.setAlignment(Pos.CENTER_LEFT);
        selectionPanel.setSpacing(10);
        gameSelection.getChildren().addAll(box, selectionPanel);

        Text gameTypeText = new Text("Select Game");
        gameTypeText.setFont(Font.font("Franklin Gothic", FontWeight.BOLD, 40));
//        gameTypeText.setTextAlignment(TextAlignment.CENTER);
        gameTypeText.setStyle("-fx-underline: true;");
        Text playerOptionText = new Text("Player vs.");
        playerOptionText.setFont(Font.font("Franklin Gothic", FontWeight.BOLD, 40));
        playerOptionText.setStyle("-fx-underline: true;");
        Text gameDiffText = new Text("Select Difficulty");
        gameDiffText.setFont(Font.font("Franklin Gothic", FontWeight.BOLD, 40));
        gameDiffText.setStyle("-fx-underline: true;");

        HBox gameType = new HBox();
        Button tictactoe = SelectGameButtons("Tic Tac Toe");
        Button supertictactoe = SelectGameButtons("Super Tic Tac Toe");
        Button quixo = SelectGameButtons("Quixo");

        HBox playerOptions = new HBox();
        Button singleGame = SelectGameButtons("CPU");
        Button doubleGame = SelectGameButtons("Player");

        HBox gameDiffPanel = new HBox();
        Button easyButton = SelectGameButtons("Easy");
        Button medButton = SelectGameButtons("Medium");
        Button hardButton = SelectGameButtons("Hard");
        Button extremeButton = SelectGameButtons("Extreme");

        tictactoe.setOnMouseClicked(event -> {
            setSelectedType(tictactoe, supertictactoe, quixo);
            System.out.println(tictactoe.getText() +" button was pressed!");
            selectedGameType = tictactoe.getText();
        });
        supertictactoe.setOnMouseClicked(event -> {
            setSelectedType(supertictactoe, tictactoe, quixo);
            System.out.println(supertictactoe.getText() +" button was pressed!");
            selectedGameType = supertictactoe.getText();
        });
        quixo.setOnMouseClicked(event -> {
            setSelectedType(quixo, tictactoe, supertictactoe);
            System.out.println(quixo.getText() +" button was pressed!");
            selectedGameType = quixo.getText();
        });
        singleGame.setOnMouseClicked(event -> {
            setSelectedType(singleGame, doubleGame);
            System.out.println(singleGame.getText() +" button was pressed!");
            selectedPlayer = singleGame.getText();
            easyButton.setDisable(false);
            medButton.setDisable(false);
            hardButton.setDisable(false);
            extremeButton.setDisable(false);
        });
        doubleGame.setOnMouseClicked(event -> {
            setSelectedType(doubleGame, singleGame);
            System.out.println(doubleGame.getText() +" button was pressed!");
            selectedPlayer = doubleGame.getText();
            easyButton.setDisable(true);
            medButton.setDisable(true);
            hardButton.setDisable(true);
            extremeButton.setDisable(true);
        });
        easyButton.setOnMouseClicked(event -> {
            setSelectedType(easyButton, medButton, hardButton, extremeButton);
            System.out.println(easyButton.getText() +" button was pressed!");
            selectedDifficulty = easyButton.getText();
        });
        medButton.setOnMouseClicked(event -> {
            setSelectedType(medButton, easyButton, hardButton, extremeButton);
            System.out.println(medButton.getText() +" button was pressed!");
            selectedDifficulty = medButton.getText();
        });
        hardButton.setOnMouseClicked(event -> {
            setSelectedType(hardButton, easyButton, medButton, extremeButton);
            System.out.println(hardButton.getText() +" button was pressed!");
            selectedDifficulty = hardButton.getText();
        });
        extremeButton.setOnMouseClicked(event -> {
            setSelectedType(extremeButton, easyButton, medButton, hardButton);
            System.out.println(extremeButton.getText() +" button was pressed!");
            selectedDifficulty = extremeButton.getText();
        });
        gameType.getChildren().addAll(tictactoe, supertictactoe, quixo);
        gameType.setSpacing(20);

        playerOptions.getChildren().addAll(singleGame, doubleGame);
        playerOptions.setSpacing(10);

        gameDiffPanel.getChildren().addAll(easyButton, medButton, hardButton, extremeButton);
        gameDiffPanel.setSpacing(10);

        Button playGame = SelectGameButtons("Play");
        playGame.setMinSize(200, 80);
        playGame.setOnMouseClicked(event -> {
            System.out.println("Play Game button was pressed!");
            if(selectedGameType != null && selectedPlayer != null){
                if(selectedPlayer.equals("Player") || selectedDifficulty != null) {

                    try {
                        switch (selectedPlayer) {
                            case "CPU" -> singlePlayer = true;
                            case "Player" -> singlePlayer = false;
                        }
                        //                    if(!singlePlayer)
                        //                        displayPopupMessage("Irrelevant Difficulty", "No Difficulty is required in case of" +
                        //                                " Player vs. Player Game\n\nThe game will now Continue");
                        difficulty = selectedDifficulty;
                        System.out.println("Selected Game Type: " + selectedGameType);
                        switch (selectedGameType) {
                            case "Tic Tac Toe" -> {
                                Runner.gameType = 1;
                                startGame();
                                displayGame();
                                System.out.println("Tic Tac Toe was Selected");
                                System.out.println(selectedPlayer + " and " + selectedDifficulty + " Game was selected");
                            }
                            case "Super Tic Tac Toe" -> {
                                if (selectedPlayer.equals("Player") || selectedDifficulty.equals("Easy")) {
                                    Runner.gameType = 2;
                                    startGame();
                                    displayGame();
                                    System.out.println("Selected Super Tic Tac Toe");
                                } else {
                                    displayPopupMessage("Under Development", "Super Tic Tac Toe:\n\tMedium Mode\n\tHard Mode\n\tExtreme Mode");
                                }
                            }
                            case "Quixo" -> {
                                if (!singlePlayer) {
                                    Runner.gameType = 3;
                                    startGame();
                                    displayGame();
                                    System.out.println("Quixo was Selected");
                                    System.out.println(selectedPlayer + " and " + selectedDifficulty + " Game was selected");
                                } else {
                                    displayPopupMessage("Under Development", "Quixo:\n\tPlayer vs. CPU");
                                }
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else
                displayPopupMessage("Missing Outputs", "Please Select all fields");
        });
        selectionPanel.getChildren().addAll(gameTypeText, gameType,
                playerOptionText, playerOptions, gameDiffText, gameDiffPanel, playGame);

    }
//    private static void resetRadioButtons(ToggleGroup toggleGroup) {
//        toggleGroup.getToggles().forEach(toggle -> {
//            RadioButton radioButton = (RadioButton) toggle;
//            radioButton.setSelected(false);
//        });
//    }
//    private static void assignToggleGroup(HBox hbox, ToggleGroup toggleGroup) {
//        hbox.getChildren().forEach(node -> {
//            if (node instanceof RadioButton) {
//                ((RadioButton) node).setToggleGroup(toggleGroup);
//            }
//        });
//    }
    private static void displayGame() {
//        if(root.)
//            root.getChildren().removeLast();
//        gamePane = new BorderPane();
//        gamePane.setPadding(new Insets(10));
        gamePane = switch(gameType){
            case 2-> new Ultimate();
            case 3-> new Quxio();
            default -> new TicTacToe();
        };
        root.getChildren().add(gamePane);

//        StackPane player1 = playerInfo(1, Color.RED);
//        gamePane.setLeft(player1);
//
//        StackPane player2 = playerInfo(2, Color.BLUE);
//        gamePane.setRight(player2);
//
//        StackPane center = new StackPane();
//        gamePane.setCenter(center);

//        Rectangle rectangle = makeRectangle(0.95,0.95);
//        Pane board = new Pane();
//        board.maxWidthProperty().bind(root.heightProperty().multiply(0.8));
//        board.maxHeightProperty().bind(root.heightProperty().multiply(0.8));
//        if(cell==0.0 && gameType!=3)
//            Platform.runLater(()->{
//                cell = board.getHeight()/3;
//                System.out.println("Cell size: " + cell);
//            });
//        center.getChildren().addAll(rectangle, board);
//        switch(gameType){
//            case 2-> superTicTacToe(board);
//            case 3-> quxio(board);
//            default -> ticTacToe(board);
//        }
        Toss();
    }
    static void mark(int row, int col) {
        if(getPlayer()==1)
            markX2(row, col, 1);
        else
            markO2(row, col);
    }
    public static void Toss(){
        String text = "Select your side for the toss";
        popUp(text,"Heads","Tails",1);
    }
    static void Toss(int choice){
        if(choice == (int)(Math.random()*2)+1){
            System.out.println("player = 1");
            Runner.setPlayer(1);
            setTurn(1);
        }
        else{
            System.out.println("player = 2");
            Runner.setPlayer(2);
            setTurn(2);
        }
    }
    public static void updateTurn(){
        if(turn1.getFill()==Color.GREEN) {
            turn1.setFill(Color.LIGHTGREY);
            turn2.setFill(Color.GREEN);
        }
        else {
            turn1.setFill(Color.GREEN);
            turn2.setFill(Color.LIGHTGREY);
        }
        if(gameType==2) {
            int[] superIndex = getSuperIndex();
            int number = (superIndex[0] * 3) + superIndex[1];
            for (int i=0; i<9; i++) {
                Rectangle active, neglect;
                if (getPlayer() == 1) {
                    active = (Rectangle) grid1.getChildren().get(i);
                    neglect = (Rectangle) grid2.getChildren().get(i);
                } else {
                    active = (Rectangle) grid2.getChildren().get(i);
                    neglect = (Rectangle) grid1.getChildren().get(i);
                }
                if (superIndex[0] == -1 || number == i)
                    active.fillProperty().bind(greenColor);
                neglect.fillProperty().bind(midGround);
            }
        }
    }
    private static void setTurn(int player){
        if(player==1)
            turn1.setFill(Color.GREEN);
        else
            turn2.setFill(Color.GREEN);
        if(gameType==2){
            for (int i=0; i<9; i++) {
                Rectangle box;
                if(player==1)
                    box = (Rectangle) grid1.getChildren().get(i);
                else
                    box = (Rectangle) grid2.getChildren().get(i);
                box.fillProperty().bind(greenColor);
            }
        }
    }
    public static void clearTurn(){
        turn1.setFill(Color.LIGHTGREY);
        turn2.setFill(Color.LIGHTGREY);
        if(gameType==2){
            for (int i=0; i<9; i++) {
                Rectangle box;
                box = (Rectangle) grid1.getChildren().get(i);
                box.setFill(Color.WHITE);
                box = (Rectangle) grid2.getChildren().get(i);
                box.setFill(Color.WHITE);
            }
        }
    }
    static void showX(Group node){
        markLine(0,0,100,100,Color.RED,0,node);
        markLine(100,0,0,100,Color.RED,0.2,node);
    }
    public static void displayGameMenu(){
        BorderPane background = new BorderPane();
        StackPane gameMenu = new StackPane();
        background.setCenter(gameMenu);
        background.setBackground(new Background(new BackgroundFill( Color.color(0,0,0,0.8), CornerRadii.EMPTY, Insets.EMPTY)));
        Rectangle box = makeRectangle(0.7, 0.9);
        gameMenu.getChildren().add(box);

        root.getChildren().add(background);
        Text header = new Text("Game Menu");
        header.setFont(Font.font("Franklin Gothic", FontWeight.BOLD, 40));
        header.setStyle("-fx-border-width: 4px;" +header.getStyle());

        Button resume = makeButton("Resume");
        resume.setTranslateY(gameMenu.getHeight()*0.25);
        Button saveGame = makeButton("Save");
        saveGame.setTranslateY(gameMenu.getHeight()*0.25);
        Button option = makeButton("Options");
        option.setTranslateY(gameMenu.getHeight()*0.25);
        Button exit = makeButton("Exit");
        exit.setTranslateY(gameMenu.getHeight()*0.25);

        Button ruleBook = roundButton("Rules");
//        ruleBook.setOnMouseClicked(event ->{
//            displayRuleBook();
//        });
        HBox rules = new HBox(ruleBook);
        rules.setAlignment(Pos.BOTTOM_RIGHT);
        rules.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(header, resume, saveGame, option, exit, rules);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(25);
        gameMenu.getChildren().add(vBox);

        System.out.println("I was Here");

        resume.setOnMouseClicked(event-> root.getChildren().remove(background));
        option.setOnMouseClicked(event-> displaySettings("Game Difficulty"));
        exit.setOnMouseClicked(event-> displayMainMenu());
    }
    protected static Button makeOptionButton(String text){
        Button button = new Button(text);
        String style = "-fx-padding: 10 20; " +
                "-fx-font-family: 'Franklin Gothic';" +
                "-fx-font-weight: Bold;" +
                "-fx-font-size: 35;";

//        System.out.println("FontColor: " + fontColor.get());

        button.styleProperty().bind(
                Bindings.createStringBinding(() ->
                        "-fx-text-fill: " + fontColor.get() + "; " +
                                style ,fontColor));
        if(text.equals("Back")) {
            button.setMinSize(120, 80);
            button.setOnMouseEntered(e -> {
                button.setText("< " + text);
                button.styleProperty().bind(
                        Bindings.createStringBinding(() ->
                                "-fx-text-fill: Red; -fx-border-radius: 50em; " +
                                        "-fx-border-color: Red; -fx-border-width: 4px;" + style));
            });
            button.setOnMouseExited(e -> {
                button.setText(text);
                button.styleProperty().bind(
                        Bindings.createStringBinding(() ->
                                "-fx-text-fill: " + fontColor.get() + "; " +
                                        style,fontColor));
            });
        }
        else if(text.equals("<") || text.equals("Edit Name")){
            button.setOnMouseEntered(e-> {
                button.styleProperty().bind(
                        Bindings.createStringBinding(() ->
                                "-fx-text-fill: Red; " + style));
            });
            button.setOnMouseExited(e-> {
                button.styleProperty().bind(
                        Bindings.createStringBinding(() ->
                                "-fx-text-fill: " + fontColor.get() + "; " +
                                        style,fontColor));
            });
            button.setMinSize(80, 80);
        }
        else {
            button.setOnMouseEntered(e -> {
                button.styleProperty().bind(
                        Bindings.createStringBinding(() ->
                                "-fx-text-fill: Red; -fx-border-radius: 50em; " +
                                        "-fx-border-color: Red; " +
                                        "-fx-border-width: 4px;" +
                                        style));
                button.setText(text + " >");
            });
            button.setOnMouseExited(e -> {
                button.styleProperty().bind(
                        Bindings.createStringBinding(() ->
                                "-fx-text-fill: " + fontColor.get() + "; " +
                                        style,fontColor));
                button.setText(text);
            });
            button.setMinSize(200, 80);
        }
        button.setBackground(new Background(new BackgroundFill(Color.color(0,0,0,0), CornerRadii.EMPTY, Insets.EMPTY)));

        return button;
    }
    private static void displayAppearance(StackPane gameMenu, Button button1, Button button2, Button button3){

        System.out.println("I was in Appearance");
        Node box = null;
        box = gameMenu.getChildren().get(gameMenu.getChildren().size()-2);
        double boxHeight = box.getBoundsInLocal().getHeight();
        double boxWidth = box.getBoundsInLocal().getWidth();

//        BorderPane background = new BorderPane();
//         Rectangle appearanceBox = new Rectangle();
//         appearanceBox.setFill(Color.WHITE);
//         appearanceBox.setWidth(boxWidth-50);
//         appearanceBox.setHeight(boxHeight-50);
//         appearanceBox.setArcWidth(50);
//         appearanceBox.setArcHeight(50);
//         appearanceBox.setTranslateX(box.getTranslateX()+100);
//         appearanceBox.setTranslateY(box.getTranslateY()+100);
//         gameMenu.getChildren().add(appearanceBox);

        VBox themes = new VBox();
        themes.setMinSize(boxWidth, boxHeight);
        themes.setTranslateX(box.localToScene(box.getBoundsInLocal()).getMinX());
//        System.out.println(box.set);
        themes.setTranslateY(box.localToScene(box.getBoundsInLocal()).getMinY() + 150);
//        themes.setTranslateX(root.getWidth()-boxWidth);
//        background.setCenter(themes);
//        background.setMaxSize(boxWidth, boxHeight);
//        background.setBackground(new Background(new BackgroundFill( Color.color(0,0,0,0), CornerRadii.EMPTY, Insets.EMPTY)));
        HBox buttons = new HBox();
        buttons.setMinWidth(boxWidth);
        gameMenu.getChildren().add(themes);
//        StackPane.setAlignment(themes);

        HBox header = new HBox();
        Button back = makeOptionButton("<");
        back.setOnMouseClicked(e-> {
            gameMenu.getChildren().removeLast();
            button1.setDisable(false);
            button2.setDisable(false);
            button3.setDisable(false);
        });

        Text title = new Text("Set Appearance");
        title.setFont(Font.font("Franklin Gothic", FontWeight.BOLD, 50));
        header.getChildren().addAll(back, title);
        header.setSpacing(50);
        String style = "-fx-font-family: 'Franklin Gothic'; -fx-font-size: 50;";
//        title.setStyle(style);
        title.styleProperty().bind(
                Bindings.createStringBinding(() ->
                        "-fx-text-fill: " + fontColor.get() + "; " + style,fontColor));

        Image defThemeImg = new Image("file:images/DefaultTheme.png");
        Image darkThemeImg = new Image("file:images/DarkTheme.png");
        Image woodThemeImg = new Image("file:images/WoodenTheme.png");
        VBox defaultThemeBox = displayThemeButtons("Default Theme", defThemeImg);
        VBox darkThemeBox = displayThemeButtons("Dark Theme", darkThemeImg);
        VBox woodThemeBox = displayThemeButtons("Wooden Theme", woodThemeImg);
//        Button darkTheme = roundButton("Dark Theme");
//        Button woodenTheme = roundButton("Wooden Theme");

        buttons.getChildren().addAll(defaultThemeBox, darkThemeBox, woodThemeBox);
//        buttons.setTranslateY(root.getHeight()-boxHeight);
//        buttons.setTranslateX(box.localToScene(box.getBoundsInLocal()).getMinX() + 200);
//        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(50);
        themes.getChildren().addAll(header, buttons);
        themes.setSpacing(50);
//        StackPane.setAlignment(themes, Pos.CENTER);
        themes.setAlignment(Pos.TOP_LEFT);
    }
    private static VBox displayThemeButtons(String text, Image buttonImg){
        String style = "-fx-font-family: 'Franklin Gothic';" +
                "-fx-font-size: 20;" +
                "-fx-text-alignment: center;";
        VBox buttonBox = new VBox();
        Button button = new Button();
        button.setMinWidth(116);
        button.setMinHeight(111);
        button.setBackground(new Background(new BackgroundImage(buttonImg,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT)));
        buttonBox.setSpacing(10);
        Text title = new Text(text);
        title.setStyle(style);

        String normalStyle = "-fx-text-fill: " + fontColor.get() + "; " + style;

        title.styleProperty().bind(Bindings.when(button.hoverProperty())
                .then("-fx-text-fill: Red; " + style)
                .otherwise(normalStyle));

//        button.setOnMouseEntered(e->{
//            title.styleProperty().bind(
//                    Bindings.createStringBinding(() ->
//                            "-fx-text-fill: " + fontHoverColor.get() + "; " +
//                                    style,fontHoverColor));
//        });
//        button.setOnMouseExited(e->{
//            title.styleProperty().bind(
//                    Bindings.createStringBinding(() ->
//                            "-fx-text-fill: " + fontColor.get() + "; " +
//                                    style,fontColor));
//        });
        button.setOnMouseClicked(e->{
            System.out.println(text + " Button was Pressed!");
            if(text.equals("Dark Theme")){
                backGround.set(Color.BLACK);
                midGround.set(Color.web("#666666"));
                fontColor.set("#FFFFFF");
                foreGround.set(Color.WHITE);
            } else if (text.equals("Default Theme")) {
                backGround.set(Color.WHITE);
                midGround.set(Color.LIGHTGREY);
                fontColor.set("#000000");
                foreGround.set(Color.BLACK);
            } else if (text.equals("Wooden Theme")) {
                backGround.set(Color.web("#cc9966"));
                midGround.set(Color.web("#996633"));
                fontColor.set("#FFFFFF");
                foreGround.set(Color.web("#663300"));
            }
        });

        buttonBox.getChildren().addAll(button, title);
        return buttonBox;
    }
    private static void displayRuleBook(){
        BorderPane background = new BorderPane();
        StackPane ruleBook = new StackPane();
        background.setCenter(ruleBook);
        background.setBackground(new Background(new BackgroundFill(midGround.get(), CornerRadii.EMPTY, Insets.EMPTY)));
//        Rectangle  = makeRectangle(1.5, 0.9);
        Rectangle box = new Rectangle();
        box.fillProperty().bind(backGround);
        box.widthProperty().bind(root.widthProperty());
        box.heightProperty().bind(root.heightProperty());
        ruleBook.getChildren().add(box);

        root.getChildren().add(background);
        VBox ruleBookPage = new VBox();
        ruleBookPage.setAlignment(Pos.CENTER_LEFT);
        ruleBookPage.setSpacing(10);
        ruleBook.getChildren().add(ruleBookPage);

        HBox hBox1 = new HBox();
        Button back = makeOptionButton("<");
        Text text = new Text("Select Game");
        text.setFont(Font.font("Franklin Gothic Heavy", 40));
        text.setTranslateX(box.getWidth()/2-40);
        text.setTranslateY(box.getHeight()*0.05);
        hBox1.setSpacing(20);

//        back.setTranslateX(box.getWidth()*(-0.395));
//        back.setTranslateY(box.getHeight()*0.055);
        hBox1.setAlignment(Pos.TOP_LEFT);
        hBox1.getChildren().addAll(back, text);

        HBox gameRule = new HBox();
        Button tictactoe = SelectGameButtons("Tic Tac Toe");
        Button supertictactoe = SelectGameButtons("Super Tic Tac Toe");
        Button quixo = SelectGameButtons("Quixo");

        gameRule.setSpacing(10);
        gameRule.setAlignment(Pos.CENTER);
        gameRule.getChildren().addAll(tictactoe, supertictactoe, quixo);

        Rectangle rulesArea = new Rectangle();
        rulesArea.fillProperty().bind(backGround);

        StringBinding textAreaStyleBinding = Bindings.createStringBinding(() ->
                "-fx-control-inner-background: \'" + midGround.get() + "\';", midGround);

        TextArea rulesTextArea = new TextArea();
        rulesTextArea.setMinSize(root.getWidth(), root.getHeight()-gameRule.getHeight()-hBox1.getHeight());
        rulesTextArea.setEditable(false);
        rulesTextArea.setBackground(new Background(new BackgroundFill(Color.color(0, 0, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));
        rulesTextArea.setWrapText(true);
        rulesTextArea.autosize();
        rulesTextArea.styleProperty().bind(Bindings.createStringBinding(() ->
                "-fx-padding: 10 30; " +
                        "-fx-font-family: 'Franklin Gothic';" +
                        "-fx-font-size: 25;" +
                        "-fx-border-radius: 5;" +
                        textAreaStyleBinding.get() +
                        "-fx-text-fill: " + fontColor.get() + ";", fontColor));

        tictactoe.setOnMouseClicked(event -> {
            setSelectedType(tictactoe, supertictactoe, quixo);
            System.out.println(tictactoe.getText() +" button was pressed!");
            rulesTextArea.setText(
                    """
                            1. The player who wins the Toss will make the first move.
                            2. Each player takes turn to mark their symbol (X or O).
                            3. The first player to get three consecutive symbols in a row, column, or a diagonal wins the game.
                            4. If all 9 squares are filled and no player has won, the game is a draw.""");
        });
        supertictactoe.setOnMouseClicked(event -> {
            setSelectedType(supertictactoe, tictactoe, quixo);
            System.out.println(supertictactoe.getText() +" button was pressed!");
            rulesTextArea.setText(
                    """
                            1. The player who wins the Toss will make the first move.
                            2. Each small 3x3 board represents a local game of Tic Tac Toe.
                            3. Players take turns to make a move in any of the small boards.
                            4. The board in which a player makes a move determines the next board for the opponent.
                            5. The objective is to win three small boards in a row, column, or a diagonal.
                            6. After a Win on a small board, the other player will make move in the corresponding small board.
                            7. The first player to win three small boards in a row, column or a diagonal.""");
        });
        quixo.setOnMouseClicked(event -> {
            setSelectedType(quixo, tictactoe, supertictactoe);
            System.out.println(quixo.getText() +" button was pressed!");
            rulesTextArea.setText(
                    """
                            1. The player who wins the Toss will make the first move.
                            2. Players take turns to move a cube from the edge of the board into the same row or column.
                            3. The objective is to create a line of five cubes with the player's symbol (X or O).
                            4. The line can be horizontal, vertical, or diagonal.
                            5. The first player to create a line of five cubes wins the game.
                            6. If both players win on a single move, then the Opponent will be the Winner.""");
        });

        Pane pane = new Pane(rulesArea, rulesTextArea);
        pane.setMinWidth(root.getWidth());
        pane.setTranslateX(0);
        rulesArea.setWidth(root.getWidth());
        rulesArea.setHeight(root.getHeight()-gameRule.getHeight()-hBox1.getHeight());
//        pane.setTranslateX(root.getWidth()-box.getWidth()-20);

        ruleBookPage.setSpacing(20);
        ruleBookPage.setAlignment(Pos.TOP_CENTER);
        ruleBookPage.getChildren().addAll(hBox1, gameRule, pane);

        back.setOnMouseClicked(event-> root.getChildren().remove(background));
    }
    private static void displayHelp(){
        BorderPane background = new BorderPane();
        StackPane helpPage = new StackPane();
        background.setCenter(helpPage);
        background.setBackground(new Background(new BackgroundFill( Color.color(0,0,0,0), CornerRadii.EMPTY, Insets.EMPTY)));
        Rectangle box = new Rectangle();
        box.fillProperty().bind(backGround);
        box.widthProperty().bind(root.widthProperty());
        box.heightProperty().bind(root.heightProperty());
        helpPage.getChildren().add(box);
        root.getChildren().add(background);

        Text title = new Text("Help");
        title.setFont(Font.font("Franklin Gothic", FontWeight.BOLD, 70));
        helpPage.getChildren().add(title);

    }
    private static void displayAboutUs(){
        BorderPane background = new BorderPane();
        StackPane aboutUsPage = new StackPane();
        background.setCenter(aboutUsPage);
        background.setBackground(new Background(new BackgroundFill( Color.color(0,0,0,0), CornerRadii.EMPTY, Insets.EMPTY)));
        Rectangle box = new Rectangle();
        box.fillProperty().bind(backGround);
        box.widthProperty().bind(root.widthProperty());
        box.heightProperty().bind(root.heightProperty());
        aboutUsPage.getChildren().add(box);
        root.getChildren().add(background);

        Text title = new Text("About Us");
        title.setFont(Font.font("Franklin Gothic", FontWeight.BOLD, 70));
        aboutUsPage.getChildren().add(title);

    }
    private static void displayProfile(StackPane gameMenu, Button button1, Button button2, Button button3){

        System.out.println("I was in Profile");
        Node box = null;
        box = gameMenu.getChildren().get(gameMenu.getChildren().size()-2);
        double boxHeight = box.getBoundsInLocal().getHeight();
        double boxWidth = box.getBoundsInLocal().getWidth();

        VBox themes = new VBox();
        themes.setMinSize(boxWidth, boxHeight);
        themes.setTranslateX(box.localToScene(box.getBoundsInLocal()).getMinX());
        themes.setTranslateY(box.localToScene(box.getBoundsInLocal()).getMinY() + 150);
//        HBox buttons = new HBox();
//        buttons.setMinWidth(boxWidth);
        gameMenu.getChildren().add(themes);

        HBox header = new HBox();
        Button back = makeOptionButton("<");
        back.setOnMouseClicked(e-> {
            gameMenu.getChildren().removeLast();
            button1.setDisable(false);
            button2.setDisable(false);
            button3.setDisable(false);
        });

        Text title = new Text("Profile");
        String style = "-fx-font-family: 'Franklin Gothic'; -fx-font-size: 50; -fx-font-weight: Bold; ";
        System.out.println(fontColor.get());
        title.styleProperty().bind(
                Bindings.createStringBinding(() ->
                        "-fx-text-fill: " + fontColor.get() + "; " + style ,fontColor));
        System.out.println(fontColor.get());
//        title.setFont(Font.font("Franklin Gothic", FontWeight.BOLD, 50));
        header.getChildren().addAll(back, title);
        header.setSpacing(50);
//        title.setStyle(style);


        HBox profile = new HBox();
        profile.setMinWidth(boxWidth);

        TextArea profileDescription = new TextArea();
        profileDescription.setMaxWidth(boxWidth*0.97);
        profileDescription.setEditable(false);
        profileDescription.setBackground(new Background(new BackgroundFill(midGround.get(), CornerRadii.EMPTY, Insets.EMPTY)));
        profileDescription.setWrapText(true);

        StringBinding textAreaStyleBinding = Bindings.createStringBinding(() ->
                "-fx-control-inner-background: \'" + midGround.get() + "\';", midGround);

        profileDescription.styleProperty().bind(Bindings.createStringBinding(() ->
                "-fx-padding: 10 30; " +
                "-fx-font-family: 'Franklin Gothic';" +
                "-fx-font-size: 30;" +
                textAreaStyleBinding.get() +
                "-fx-text-fill: " + fontColor.get() + ";", fontColor));
//        profileDescription.setStyle("-fx-padding: 10 30; " +
//                "-fx-font-family: 'Franklin Gothic';" +
//                "-fx-font-size: 25;" +
//                "-fx-border-radius: 5;" +
//                "-fx-text-fill: " + fontColor.get() + ";");
        profileDescription.setText(
                "Name:\tPlayer Name\n\n" +
                "Level:\t10\n\n" +
                "Winnings: 0"
        );

        Button edit = makeOptionButton("Edit Name");
        profile.getChildren().addAll(profileDescription, edit);

        themes.getChildren().addAll(header, profile);
        themes.setSpacing(50);
        themes.setAlignment(Pos.TOP_LEFT);
    }
    public static void showTurn(int row, int col, int[] superIndex){
        System.out.println("Here in showTurn (GUI)");
        if(getPlayer()==1)
            markX(row,col,superIndex);
        else
            markO(row, col,superIndex);
    }
    public static void showTurn(int row, int col){
        if(getPlayer()==1)
            markX(row,col);
        else
            markO(row, col);
    }
    public static void markDraw(int[] superIndex){
        int x = (int)(superIndex[1]*cell)+4;
        int y = (int)(superIndex[0]*cell)+4;
        showMark(x,y,true);
        showMark(x + ((int) (cell * 0.2) - 4), y + ((int) (cell * 0.2) - 4), false);
    }
    private static void showMark(int x, int y,  boolean white){
        Image dIcon = new Image("D.png");
        Image darkIcon = new Image("white.png");

        Image mark;
        mark = white? darkIcon : dIcon ;
        System.out.println("Mark set acc to player!");
        int width, height;
        width = height = (int)cell-7;

        ImageView imageView = new ImageView(mark);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        marks.getChildren().add(imageView);
        System.out.println("Marked on grid!");
    }
    public static void markLine(int value){
        int[][] lineIndex;
        if(gameType==3) {
            lineIndex = quxioWinValues.get(value);
            markUp(0, lineIndex);
        }
        else {
            lineIndex = dictionary.get(value);
            int startX = (int) ((lineIndex[0][1] * cell) + (int) (cell * 0.5));
            int startY = (int) ((lineIndex[0][0] * cell) + (int) (cell * 0.5));
            int endX = (int) ((lineIndex[2][1] * cell) + (int) (cell * 0.5));
            int endY = (int) ((lineIndex[2][0] * cell) + (int) (cell * 0.5));

            Timeline timeline = markLine(startX, startY, endX, endY, Color.LIGHTGOLDENRODYELLOW, 0, marks);
            timeline.setOnFinished(end ->  endGame());
        }
    }
    private static void markUp(int num, int[][] lineIndex){
        int[] index = lineIndex[num];
        Rectangle box = (Rectangle) boxPane.lookup("#" + index[0] + index[1]);
        Color color = getPlayer()==1?Color.LIGHTSKYBLUE:Color.HOTPINK;
        FillTransition transition = fill((Color)box.getFill(), color, 0.2, box, 1, false);
        num++;
        int finalNum = num;
        transition.setOnFinished(event -> {
            if(finalNum <=4)
                markUp(finalNum, lineIndex);
            else{
                Runner.endGame();
            }
        });
    }
    public static void markLine(int[] superIndex,int value){
        int[][] lineIndex = dictionary.get(value);
        int startX =(int)((superIndex[1]*cell)+(lineIndex[0][1]*(cell/3))+(int)((cell/3)*0.5));
        int startY =(int)((superIndex[0]*cell)+(lineIndex[0][0]*(cell/3))+(int)((cell/3)*0.5));
        int endX = (int) ((superIndex[1]*cell)+(lineIndex[2][1]*(cell/3))+(int)((cell/3)*0.5));
        int endY = (int) ((superIndex[0]*cell)+(lineIndex[2][0]*(cell/3))+(int)((cell/3)*0.5));

        markLine(startX,startY,endX,endY,Color.LIGHTGOLDENRODYELLOW,0,marks);

        int x =(int) (superIndex[1]*cell)+4;
        int y =(int) (superIndex[0]*cell)+4;
        showMark(x,y,true);
        if(getPlayer()==1)
            markX(superIndex[0],superIndex[1]);
        else
            markO(superIndex[0],superIndex[1]);
    }
    private static Timeline markLine(int startX, int startY, int endX, int endY, Color color, double delay,Group node){
        Line line = new Line(startX, startY,startX, startY);
        line.setStrokeWidth(0);
        line.setStroke(color);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        line.setMouseTransparent(true);
        node.getChildren().add(line);
        double width, time;
        if(endY-startY<(cell/3)) {
            width = 10;
            time = 0.2;
        }
        else if(startY-endY<cell) {
            width = 20;
            time = 0.3;
        }
        else {
            width = 25;
            time = 1;
        }
        Timeline timeline = new Timeline();
        KeyFrame startFrame = new KeyFrame(Duration.seconds(delay),
                new KeyValue(line.strokeWidthProperty(), width),
                new KeyValue(line.endXProperty(), startX),
                new KeyValue(line.endYProperty(), startY));
        KeyFrame endFrame = new KeyFrame(Duration.seconds(delay+time),
                new KeyValue(line.strokeWidthProperty(), width),
                new KeyValue(line.endXProperty(), endX),
                new KeyValue(line.endYProperty(), endY));
        timeline.getKeyFrames().addAll(startFrame, endFrame);
        timeline.play();
        return timeline;
    }
    private static void markX(int row, int col, int[] superIndex){
        System.out.println("Here marking x in markX(GUI)\n" + superIndex[0] + " : " + superIndex[1] + "\n" +
                row + " : " +col);
        int startY = (int) ((int) (row*(cell/3))+(superIndex[0]*cell)+((cell/3)*0.2));
        int startX = (int) ((int) (col*(cell/3))+(superIndex[1]*cell)+((cell/3)*0.2));
        int endY = startY + (int)((cell/3)*0.6);
        int endX = startX + (int)((cell/3)*0.6);
        markLine(startX,startY,endX,endY,Color.RED,0,marks);
        startX = endX;
        endX = endX - (int)((cell/3)*0.6);
        markLine(startX,startY,endX,endY,Color.RED,0.2,marks);
        System.out.println("Here marked x in markX(GUI)");
    }
    private static void markX(int row, int col){
        int startY = (int)((row*cell)+(cell*0.2));
        int startX = (int)((col*cell)+(cell*0.2));
        int endY = startY + (int)(cell*0.6);
        int endX = startX + (int)(cell*0.6);
        markLine(startX,startY,endX,endY,Color.RED,0,marks);
        startX = endX;
        endX = endX - (int)(cell*0.6);
        markLine(startX,startY,endX,endY,Color.RED,0.2,marks);
    }
    private static void markO(int row, int col){
        double y = (row*cell)+(cell*0.5);
        double x = (col*cell)+(cell*0.5);
        markO(x,y,cell*0.6,marks);
    }
    private static void markO(int row, int col, int[] superIndex){
        double y =  (row*(cell/3))+(superIndex[0]*cell)+((cell/3)*0.5);
        double x =  (col*(cell/3))+(superIndex[1]*cell)+((cell/3)*0.5);
        markO(x,y,((cell/3)*0.6),marks);
    }
    static void markO(double x, double y, double diameter, Group node){
        Circle circle = new Circle(x,y,0);
        circle.setStroke(Color.BLUE);
        circle.setStrokeWidth(15);
        circle.setFill(null);
        Circle inner = new Circle(0);
        inner.setFill(Color.rgb(0,0,0,0));


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
    private static void markX2(int row, int col, int step){
        Rectangle box = (Rectangle) boxPane.lookup("#"+row+col);
//        System.out.println("\nstartX: -------->" + startX.getValue() + "\nstartY: -------->" + startY.getValue() +"\nendX: -------->" + endX.getValue() + "\nendY: -------->" + endY.getValue());
        double delay = 0;
        if(step == 2) {
            delay = 0.2;
        }
        else
            markX2(row, col, 2);
        Line line = new Line();
        line.setStrokeWidth(20);
        line.setStroke(Color.RED);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        line.setMouseTransparent(true);
        line.scaleXProperty().bind(box.scaleXProperty());
        line.scaleYProperty().bind(box.scaleYProperty());
        marks.getChildren().add(line);
        Timeline timeline = new Timeline();
        KeyFrame startFrame, endFrame;
        if(step == 2) {
            startFrame = new KeyFrame(Duration.seconds(delay),
                    new KeyValue(line.endXProperty(), box.getLayoutX()+(box.getWidth()*0.8)),
                    new KeyValue(line.endYProperty(), box.getLayoutY()+(box.getHeight()*0.2)));
            endFrame = new KeyFrame(Duration.seconds(delay+0.3),
                    new KeyValue(line.endXProperty(), box.getLayoutX()+(box.getWidth()*0.2)),
                    new KeyValue(line.endYProperty(), box.getLayoutY()+(box.getHeight()*0.8)));
        }
        else {
            startFrame = new KeyFrame(Duration.seconds(delay),
                    new KeyValue(line.endXProperty(), box.getLayoutX()+(box.getWidth()*0.2)),
                    new KeyValue(line.endYProperty(), box.getLayoutY()+(box.getHeight()*0.2)));
            endFrame = new KeyFrame(Duration.seconds(delay+0.3),
                    new KeyValue(line.endXProperty(), box.getLayoutX()+(box.getWidth()*0.6)),
                    new KeyValue(line.endYProperty(), box.getLayoutY()+(box.getHeight()*0.8)));
        }

        timeline.getKeyFrames().addAll(startFrame, endFrame);
//        timeline.play();
//        timeline.setOnFinished(event -> {
            if(step == 2) {
                line.startXProperty().bind(box.translateXProperty().add(box.widthProperty().multiply(0.8)));
                line.endXProperty().bind(box.translateXProperty().add(box.widthProperty().multiply(0.2)));
            }
            else {
                line.startXProperty().bind(box.translateXProperty().add(box.widthProperty().multiply(0.2)));
                line.endXProperty().bind(box.translateXProperty().add(box.widthProperty().multiply(0.8)));
            }
            line.startYProperty().bind(box.translateYProperty().add(box.heightProperty().multiply(0.2)));
            line.endYProperty().bind(box.translateYProperty().add(box.heightProperty().multiply(0.8)));
//        });
    }
    private static void markO2(int row, int col){
        Rectangle box = (Rectangle) boxPane.lookup("#"+row+col);
        Circle circle = new Circle(0);
        circle.setStroke(Color.BLUE);
        circle.setStrokeWidth(6);
        circle.setFill(null);
        Circle inner = new Circle(0);
        inner.setFill(Color.rgb(0,0,0,0));


        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        KeyValue keyValueRadius = new KeyValue(circle.radiusProperty(), (box.getWidth()*0.4));
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.25), keyValueRadius);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();

        Timeline innerTimeLine = new Timeline();
        innerTimeLine.setCycleCount(1);
        KeyValue keyValueRadiusInner = new KeyValue(inner.radiusProperty(), (box.getWidth()*0.3));
        KeyFrame keyFrameInner = new KeyFrame(Duration.seconds(0.25), keyValueRadiusInner);
        innerTimeLine.getKeyFrames().add(keyFrameInner);
        innerTimeLine.play();

//        timeline.setOnFinished(event -> {
//            circle.radiusProperty().bind(box.widthProperty().multiply(0.4));
            circle.centerXProperty().bind(box.translateXProperty().add(box.widthProperty().multiply(0.5)));
            circle.centerYProperty().bind(box.translateYProperty().add(box.heightProperty().multiply(0.5)));

//            inner.radiusProperty().bind(box.widthProperty().multiply(0.3));
            inner.centerXProperty().bind(box.translateXProperty().add(box.widthProperty().multiply(0.5)));
            inner.centerYProperty().bind(box.translateYProperty().add(box.heightProperty().multiply(0.5)));
//        });

        circle.scaleXProperty().bind(box.scaleXProperty());
        circle.scaleYProperty().bind(box.scaleYProperty());
        circle.setMouseTransparent(true);
        inner.scaleXProperty().bind(box.scaleXProperty());
        inner.scaleYProperty().bind(box.scaleYProperty());
        inner.fillProperty().bind(box.fillProperty());
        inner.setMouseTransparent(true);
        marks.getChildren().addAll(circle,inner);
    }
    public static void clearMarks(){
        marks.getChildren().clear();
    }
    public static void clearGame(){
        clearMarks();
        marks = null;
        gamePane = null;
        displayMainMenu();
    }
}