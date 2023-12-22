package Tic_Tac_Ultimate;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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
    public static Color backGround = Color.web("#f2f2f2");
    public static Color midGround = Color.web("#fff");
    public static Color foreGround = Color.web("#000000");
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
        root.setBackground(new Background(new BackgroundFill(backGround, CornerRadii.EMPTY, Insets.EMPTY)));

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
        displayStart();
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

        Text ticTac = new Text("Tic tac");
        Text ultimate = new Text(" Ultimate ");
        Rectangle background = new Rectangle();
        StackPane complex = new StackPane(background, ultimate);

        HBox title = new HBox(ticTac,complex);
        title.setAlignment(Pos.CENTER);
        title.setTranslateY(root.getHeight()*0);
        title.setSpacing(10);

        ticTac.setFont(Font.font("Franklin Gothic Heavy", FontWeight.BOLD, 74));
        ticTac.setFill(Color.BLACK);

        ultimate.setFont(Font.font("Franklin Gothic Heavy", FontWeight.BOLD, 74));
        ultimate.setFill(Color.WHITE);

        background.setWidth(ultimate.getLayoutBounds().getWidth());
        background.setHeight(ultimate.getLayoutBounds().getHeight());
        background.setFill(Color.RED);

        Button start = makeButton("Start New");
        Button loadGame = makeButton("Continue");
        Button options = makeButton("Settings");
        Button exit = makeButton("Exit Game");

        HBox helpButtons = new HBox();
        Button ruleBookButton = roundButton("Rules");
        Button aboutUsButton = roundButton("About Us");
        Button helpButton = roundButton("Help");
        helpButtons.getChildren().addAll(ruleBookButton, aboutUsButton, helpButton);
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
                "-fx-font-family: 'Comic Sans MS';" +
                "-fx-font-weight: Bold;" +
                "-fx-font-size: 45;" +
                "-fx-border-radius: 5;";
        button.setStyle(style);
//        button.setStyle( "-fx-background-color: Black; " + "-fx-text-fill: White; " + style );
        button.setBackground(new Background(new BackgroundFill(Color.color(0,0,0,0), CornerRadii.EMPTY, Insets.EMPTY)));
        button.setOnMouseEntered(e -> {
            button.setStyle( "-fx-text-fill: Red; " + style);
            button.setText("> " + text + " <");
        });
        button.setOnMouseExited(e -> {
            button.setStyle("-fx-text-fill: Black; " + style );
            button.setText(text);
        });
        button.setOnMouseClicked(event -> {
            System.out.println(text +" button was pressed!");
            switch (text) {
                case "Start New" -> displayGameSelection();
                case "Continue" -> System.out.println("Continue Button was Pressed");

//                displayOptions();
                case "Settings" -> displaySettings();

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
    private static void displaySettings(){

        BorderPane background = new BorderPane();
        StackPane gameMenu = new StackPane();
        background.setCenter(gameMenu);
        gameMenu.setAlignment(Pos.TOP_CENTER);

// Setting a transparent background for the entire BorderPane
        background.setBackground(new Background(new BackgroundFill(Color.color(0, 0, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));

        Rectangle box = new Rectangle();
        box.setFill(Color.DARKGRAY);
        box.widthProperty().bind(root.widthProperty());
        box.heightProperty().bind(root.heightProperty());
        gameMenu.getChildren().add(box);

        Text header = new Text("Settings");
        header.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 70));
        header.setTextAlignment(TextAlignment.CENTER);

        VBox vBox = new VBox();
        vBox.setSpacing(10); // Set spacing between buttons

        Button appearance = makeOptionButton("Appearance");
        Button changeDifficulty = makeOptionButton("Game Settings");
        Button players = makeOptionButton("Player Settings");
        Button back = makeOptionButton("Back");

        vBox.getChildren().addAll(appearance, changeDifficulty, players, back);

        Rectangle areaBox = new Rectangle();
        areaBox.setFill(Color.LIGHTGREY);
        areaBox.setArcHeight(50);
        areaBox.setArcWidth(50);
        areaBox.setWidth(1000); // Set the width according to your preference
        areaBox.setHeight(680); // Set the height according to your preference

        StackPane optionPage = new StackPane();
        optionPage.getChildren().addAll(areaBox, vBox);
        StackPane.setAlignment(vBox, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(areaBox, Pos.BOTTOM_RIGHT);

        gameMenu.getChildren().addAll(header, optionPage);
        background.setPadding(new Insets(10));

        root.getChildren().add(background);
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
                    "-fx-font-family: 'Comic Sans MS';" +
                    "-fx-font-size: 13;" +
                    "-fx-background-radius: 40em;";
        else
            roundButtonStyle = "-fx-padding: 10 20; " +
                    "-fx-font-family: 'Comic Sans MS';" +
                    "-fx-font-size: 25;" +
                    "-fx-alignment: center;" +
                    "-fx-background-radius: 40em;";
        button.setStyle( "-fx-background-color: Black; " + "-fx-text-fill: White; " + roundButtonStyle );
        button.setOnMouseEntered(e -> button.setStyle( "-fx-background-color: Red; " + "-fx-text-fill: White; " + roundButtonStyle));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: Black; " + "-fx-text-fill: White; " + roundButtonStyle ));

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
        String style = "-fx-padding: 10 20; " +
                "-fx-font-family: 'Comic Sans MS';" +
                "-fx-font-size: 35;" +
                "-fx-border-radius: 5;";
        button.setStyle( "-fx-background-color: Black; " + "-fx-text-fill: White; " + style );
        button.setOnMouseEntered(e -> button.setStyle( "-fx-background-color: Red; " + "-fx-text-fill: White; " + style));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: Black; " + "-fx-text-fill: White; " + style ));

        return button;
    }
    private static void setSelectedType(Button button1, Button button2, Button button3, Button button4){
        String style = "-fx-padding: 10 20; " +
                "-fx-font-family: 'Comic Sans MS';" +
                "-fx-font-size: 35;" +
                "-fx-border-radius: 5;";
        button1.setStyle( "-fx-background-color: Green; " + "-fx-text-fill: White; " + style);
        button2.setStyle( "-fx-background-color: Gray; " + "-fx-text-fill: White; " + style);
        button3.setStyle( "-fx-background-color: Gray; " + "-fx-text-fill: White; " + style);
        button4.setStyle( "-fx-background-color: Gray; " + "-fx-text-fill: White; " + style);
        button1.setOnMouseEntered(e -> button1.setStyle( "-fx-background-color: Green; " + "-fx-text-fill: White; " + style));
        button1.setOnMouseExited(e -> button1.setStyle( "-fx-background-color: Green; " + "-fx-text-fill: White; " + style));
        button2.setOnMouseEntered(e -> button2.setStyle( "-fx-background-color: Gray; " + "-fx-text-fill: White; " + style));
        button2.setOnMouseExited(e -> button2.setStyle( "-fx-background-color: Gray; " + "-fx-text-fill: White; " + style));
        button3.setOnMouseEntered(e -> button3.setStyle( "-fx-background-color: Gray; " + "-fx-text-fill: White; " + style));
        button3.setOnMouseExited(e -> button3.setStyle( "-fx-background-color: Gray; " + "-fx-text-fill: White; " + style));
        button4.setOnMouseEntered(e -> button4.setStyle( "-fx-background-color: Gray; " + "-fx-text-fill: White; " + style));
        button4.setOnMouseExited(e -> button4.setStyle( "-fx-background-color: Gray; " + "-fx-text-fill: White; " + style));
    }
    private static void setSelectedType(Button button1, Button button2, Button button3){
        String style = "-fx-padding: 10 20; " +
                "-fx-font-family: 'Comic Sans MS';" +
                "-fx-font-size: 35;" +
                "-fx-border-radius: 5;";
        button1.setStyle( "-fx-background-color: Green; " + "-fx-text-fill: White; " + style);
        button2.setStyle( "-fx-background-color: Gray; " + "-fx-text-fill: White; " + style);
        button3.setStyle( "-fx-background-color: Gray; " + "-fx-text-fill: White; " + style);
        button1.setOnMouseEntered(e -> button1.setStyle( "-fx-background-color: Green; " + "-fx-text-fill: White; " + style));
        button1.setOnMouseExited(e -> button1.setStyle( "-fx-background-color: Green; " + "-fx-text-fill: White; " + style));
        button2.setOnMouseEntered(e -> button2.setStyle( "-fx-background-color: Gray; " + "-fx-text-fill: White; " + style));
        button2.setOnMouseExited(e -> button2.setStyle( "-fx-background-color: Gray; " + "-fx-text-fill: White; " + style));
        button3.setOnMouseEntered(e -> button3.setStyle( "-fx-background-color: Gray; " + "-fx-text-fill: White; " + style));
        button3.setOnMouseExited(e -> button3.setStyle( "-fx-background-color: Gray; " + "-fx-text-fill: White; " + style));
    }
    private static void setSelectedType(Button button1, Button button2){
        String style = "-fx-padding: 10 20; " +
                "-fx-font-family: 'Comic Sans MS';" +
                "-fx-font-size: 35;" +
                "-fx-border-radius: 5;";
        button1.setStyle( "-fx-background-color: Green; " + "-fx-text-fill: White; " + style);
        button2.setStyle( "-fx-background-color: Gray; " + "-fx-text-fill: White; " + style);
        button1.setOnMouseEntered(e -> button1.setStyle( "-fx-background-color: Green; " + "-fx-text-fill: White; " + style));
        button1.setOnMouseExited(e -> button1.setStyle( "-fx-background-color: Green; " + "-fx-text-fill: White; " + style));
        button2.setOnMouseEntered(e -> button2.setStyle( "-fx-background-color: Gray; " + "-fx-text-fill: White; " + style));
        button2.setOnMouseExited(e -> button2.setStyle( "-fx-background-color: Gray; " + "-fx-text-fill: White; " + style));
    }
    private static void displayGameSelection() {
        StackPane gameSelection = new StackPane();
        root.getChildren().add(gameSelection);
        Rectangle box = new Rectangle();
        box.setFill(midGround);
        box.widthProperty().bind(root.widthProperty());
        box.heightProperty().bind(root.heightProperty());

        VBox selectionPanel = new VBox();
        selectionPanel.setAlignment(Pos.CENTER_LEFT);
        selectionPanel.setSpacing(10);
        gameSelection.getChildren().addAll(box, selectionPanel);

        Text gameTypeText = new Text("Select Game");
        gameTypeText.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 30));
        Text playerOptionText = new Text("Player vs.");
        playerOptionText.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 30));
        Text gameDiffText = new Text("Select Difficulty");
        gameDiffText.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 30));

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
        gameType.setSpacing(10);

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
        root.getChildren().removeLast();
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
                    active.setFill(Color.GREEN);
                neglect.setFill(Color.WHITE);
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
                box.setFill(Color.GREEN);
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
        header.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 40));

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
        option.setOnMouseClicked(event-> displayOptions());
        exit.setOnMouseClicked(event-> displayMainMenu());
    }
    private static void displayOptions(){

        BorderPane background = new BorderPane();
        StackPane gameMenu = new StackPane();
        background.setCenter(gameMenu);
        gameMenu.setAlignment(Pos.TOP_CENTER);

// Setting a transparent background for the entire BorderPane
        background.setBackground(new Background(new BackgroundFill(Color.color(0, 0, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));

        Rectangle box = new Rectangle();
        box.setFill(Color.DARKGRAY);
        box.widthProperty().bind(root.widthProperty());
        box.heightProperty().bind(root.heightProperty());
        gameMenu.getChildren().add(box);

        Text header = new Text("Game Options");
        header.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 70));
        header.setTextAlignment(TextAlignment.CENTER);

        VBox vBox = new VBox();
        vBox.setSpacing(10); // Set spacing between buttons

        Button appearance = makeOptionButton("Appearance");
        Button changeDifficulty = makeOptionButton("Change Difficulty");
        Button players = makeOptionButton("Player Settings");
        Button back = makeOptionButton("Back");

        vBox.getChildren().addAll(appearance, changeDifficulty, players, back);

        Rectangle areaBox = new Rectangle();
        areaBox.setFill(Color.LIGHTGREY);
        areaBox.setArcHeight(50);
        areaBox.setArcWidth(50);
        areaBox.setWidth(1000); // Set the width according to your preference
        areaBox.setHeight(680); // Set the height according to your preference

        StackPane optionPage = new StackPane();
        optionPage.getChildren().addAll(areaBox, vBox);
        StackPane.setAlignment(vBox, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(areaBox, Pos.BOTTOM_RIGHT);

        gameMenu.getChildren().addAll(header, optionPage);
        background.setPadding(new Insets(10));

        root.getChildren().add(background);
//        appearance.setOnMouseClicked(e->{
//            System.out.println("Appearance Here");
//            displayAppearance(areaBox);
//        });

    }
    private static Button makeOptionButton(String text){
        Button button = new Button(text);
        String style = "-fx-padding: 10 20; " +
                "-fx-font-family: 'Comic Sans MS';" +
                "-fx-font-weight: Bold;" +
                "-fx-font-size: 50;";
        button.setStyle(style);
        if(text.equals("Back")) {
            button.setMinSize(120, 80);
            button.setStyle("-fx-text-fill: Red; " + style);
        }
        else if(text.equals("<")){
            button.setOnMouseEntered(e -> button.setStyle("-fx-text-fill: Red; " + style));
            button.setOnMouseExited(e -> button.setStyle("-fx-text-fill: Black; " + style));
            button.setMinSize(80, 80);
        }
        else {
            button.setOnMouseEntered(e -> {
                button.setStyle("-fx-text-fill: Black; -fx-underline: true; " + style);
                button.setText(text + " >");
            });
            button.setOnMouseExited(e -> {
                button.setStyle("-fx-text-fill: #383838; -fx-underline: false; " + style);
                button.setText(text);
            });
            button.setMinSize(200, 80);
        }
        button.setBackground(new Background(new BackgroundFill(Color.color(0,0,0,0), CornerRadii.EMPTY, Insets.EMPTY)));
        button.setOnAction(e->{
            switch (text) {
                case "Change Difficulty" -> System.out.println(text + " Button");
                case "Player Settings" -> System.out.println(text + " Button");
                case "Back" -> {
                    System.out.println(text + " Button");
                    root.getChildren().removeLast();
                }
            }
        });
        return button;
    }
    private static void displayAppearance(Rectangle areaBox){

        BorderPane background = new BorderPane();
        VBox themes = new VBox();
        themes.setMinSize(areaBox.getWidth(), areaBox.getHeight());
        themes.setTranslateX(root.getWidth()-areaBox.getWidth());
        background.setCenter(themes);
        background.setMaxSize(areaBox.getWidth(), areaBox.getHeight());
        background.setBackground(new Background(new BackgroundFill( Color.color(0,0,0,0), CornerRadii.EMPTY, Insets.EMPTY)));
        HBox buttons = new HBox();
        buttons.setMinWidth(areaBox.getWidth());
        root.getChildren().add(background);

//        HBox header = new HBox();
//        Button back = makeOptionButton("<", areaBox);
//        back.setOnMouseEntered(e->back.setStyle("-fx-underline: true; -fx-text-fill: Red; " + back.getStyle()));
//        back.setOnMouseExited(e-> back.setStyle("-fx-underline: false; -fx-text-fill: #000000; " + back.getStyle()));
//        back.setOnMouseClicked(e-> root.getChildren().removeLast());

        Text title = new Text("Set Appearance");
        title.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 50));
//        header.getChildren().addAll(back, title);
//        header.setSpacing(50);
//        title.setStyle("-fx-font-family: 'Comic Sans MS';" +
//                "-fx-font-size: 70;" +
//                "-fx-text-fill: Black;");

        Button defaultTheme = roundButton("Default");
        Button darkTheme = roundButton("Dark Theme");
        Button woodenTheme = roundButton("Wooden Theme");

        buttons.getChildren().addAll(defaultTheme, darkTheme, woodenTheme);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(30);
        themes.getChildren().addAll(title, buttons);
        themes.setSpacing(50);
        themes.setAlignment(Pos.CENTER);
    }
    private static void displayRuleBook(){
        BorderPane background = new BorderPane();
        StackPane ruleBook = new StackPane();
        background.setCenter(ruleBook);
        background.setBackground(new Background(new BackgroundFill( Color.color(0,0,0,0), CornerRadii.EMPTY, Insets.EMPTY)));
//        Rectangle  = makeRectangle(1.5, 0.9);
        Rectangle box = new Rectangle();
        box.setFill(midGround);
        box.widthProperty().bind(root.widthProperty());
        box.heightProperty().bind(root.heightProperty());
        ruleBook.getChildren().add(box);

        root.getChildren().add(background);
        VBox ruleBookPage = new VBox();
        ruleBookPage.setAlignment(Pos.CENTER_LEFT);
        ruleBookPage.setSpacing(10);
        ruleBook.getChildren().add(ruleBookPage);

        HBox hBox1 = new HBox();
        Button back = roundButton("<");
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
        rulesArea.setFill(Color.WHITE);

        TextArea rulesTextArea = new TextArea();
        rulesTextArea.setMinSize(root.getWidth(), root.getHeight()-gameRule.getHeight()-hBox1.getHeight());
        rulesTextArea.setEditable(false);
        rulesTextArea.setBackground(new Background(new BackgroundFill(Color.color(0, 0, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));
        rulesTextArea.setWrapText(true);
        rulesTextArea.autosize();
        rulesTextArea.setStyle("-fx-padding: 10 30; " +
                "-fx-font-family: 'Comic Sans MS';" +
                "-fx-font-size: 25;" +
                "-fx-border-radius: 5;" +
                "-fx-font-color: Black");

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
                            7. Criterion to determine the winner:
                            \ti. The first player to win three small boards in a row, column or a diagonal.
                            \t\tOR
                            \tii. The player with the most smaller wins, will be the Winner.""");
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
        box.setFill(midGround);
        box.widthProperty().bind(root.widthProperty());
        box.heightProperty().bind(root.heightProperty());
        helpPage.getChildren().add(box);
        root.getChildren().add(background);

        Text title = new Text("Help");
        title.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 70));
        helpPage.getChildren().add(title);

    }
    private static void displayAboutUs(){
        BorderPane background = new BorderPane();
        StackPane aboutUsPage = new StackPane();
        background.setCenter(aboutUsPage);
        background.setBackground(new Background(new BackgroundFill( Color.color(0,0,0,0), CornerRadii.EMPTY, Insets.EMPTY)));
        Rectangle box = new Rectangle();
        box.setFill(midGround);
        box.widthProperty().bind(root.widthProperty());
        box.heightProperty().bind(root.heightProperty());
        aboutUsPage.getChildren().add(box);
        root.getChildren().add(background);

        Text title = new Text("About Us");
        title.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 70));
        aboutUsPage.getChildren().add(title);

    }
    private static void displayProfile(){
        root.getChildren().clear();

    }
    public static void showTurn(int row, int col, int[] superIndex){
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
        int startY = (int) ((int) (row*(cell/3))+(superIndex[0]*cell)+((cell/3)*0.2));
        int startX = (int) ((int) (col*(cell/3))+(superIndex[1]*cell)+((cell/3)*0.2));
        int endY = startY + (int)((cell/3)*0.6);
        int endX = startX + (int)((cell/3)*0.6);
        markLine(startX,startY,endX,endY,Color.RED,0,marks);
        startX = endX;
        endX = endX - (int)((cell/3)*0.6);
        markLine(startX,startY,endX,endY,Color.RED,0.2,marks);
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
        circle.setFill(Color.BLUE);
        Circle inner = new Circle(0);
        inner.setFill(Color.WHITE);


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