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
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.util.List;

import static Tic_Tac_Ultimate.GuiUtility.*;
import static Tic_Tac_Ultimate.Runner.*;
import static Tic_Tac_Ultimate.TicTacToeBoard.*;
import static Tic_Tac_Ultimate.QuixoBoard.quxioWinValues;

public class GUI extends Application {
    public static Stage stage;
    public static StackPane root;
    public static Color backGround = Color.web("#f2f2f2");
    public static Color midGround = Color.web("#fff");
    public static Color foreGround = Color.web("#000000");
    public static Text turn1;
    public static Text turn2;
    public static Group grid1;
    public static Group grid2;
    private static GamePane gamePane;
    public static double cell;
    public static int check;
    public static String selectedGameType;
    public static String selectedPlayer;
    public static String selectedDifficulty;


    public static void initialize(String[] args){
        launch(args);
    }

    public static void clearMarks() {
        gamePane.clearMarks();
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
//        stage.setFullScreen(true);
        root = new StackPane();
        root.setBackground(new Background(new BackgroundFill(backGround, CornerRadii.EMPTY, Insets.EMPTY)));
        root.setOnMouseClicked(e -> {
            List<Window> windows = Window.getWindows();
            for(Window window: windows) {
                if(!(window instanceof LogIn) && !(window instanceof SignUp) && window != stage){
                    ((Stage) window).close();
                    System.out.println("Done");
                    break;
                }
            }
        });
        root.setOnKeyPressed(event->{
            if(event.getCode()== KeyCode.ESCAPE) {
                Node node = root.getChildren().getLast();
                System.out.println("Excaped");
                System.out.println(node);
                if(node == gamePane)
                    displayGameMenu();
                else if (!(node instanceof Group)){
                    System.out.println("I escaped");
                    root.getChildren().removeLast();
                }
            }
            else if(event.getCode()== KeyCode.F11)
                stage.setFullScreen(!stage.isFullScreen());
        });
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
        stage.setScene(new Scene(root));
        stage.show();
        displayMainMenu();
//        new LogIn().showAndWait();
        root.requestFocus();
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
        TranslateTransition emptyTransition = new TranslateTransition(Duration.seconds(0.6), empty);
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
        HBox title = getTitle(74, root.getHeight()*0.1);
        Button start = makeButton("New Game");
        Button loadGame = makeButton("Load Game");
        Button options = makeButton("Settings");
        Button exit = makeButton("Exit Game");

        VBox menuPanel = new VBox(title, start, loadGame, options, exit);
        menuPanel.setAlignment(Pos.TOP_CENTER);
        menuPanel.setSpacing(25);
        root.getChildren().add(menuPanel);
    }
    private static Button makeButton(String text) {
        Button button = new Button(text);
        button.setTranslateY(root.getHeight()*0.2);
        button.setMinSize(200,80);
        String style = "-fx-padding: 10 20; " +
                "-fx-font-family: 'Franklin Gothic';" +
                "-fx-font-weight: Bold;" +
                "-fx-font-size: 45;" +
                "-fx-border-radius: 5;";
        button.setStyle("-fx-text-fill: Black; " + style);
        button.setBackground(new Background(new BackgroundFill(Color.color(0,0,0,0), CornerRadii.EMPTY, Insets.EMPTY)));
        button.setOnMouseEntered(e -> {
            button.setStyle( "-fx-text-fill: Red; " +
                    "-fx-wrap-text: true;" + style);
            button.setText("> " + text + " <");
        });
        button.setOnMouseExited(e -> {
            button.setStyle("-fx-text-fill: Black; " + style );
            button.setText(text);
        });
        button.setOnAction(event -> {
            System.out.println(text +" button was pressed!");
            switch (text) {
                case "New Game" -> displayGameSelection();
                case "Load Game" -> System.out.println("Load Game Button was Pressed");
                case "Settings" -> displaySettings();
                case "Exit Game" -> popUp("Are you sure you want to exit?","Exit","Cancel",3);
            }
        });
        return button;
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
            roundButtonStyle = "-fx-padding: 10 20; " +
                    "-fx-font-family: 'Franklin Gothic';" +
                    "-fx-font-size: 25;" +
                    "-fx-alignment: center;" +
                    "-fx-background-radius: 40em;";
        button.setStyle( "-fx-background-color: Black; " + "-fx-text-fill: White; " + roundButtonStyle );
        button.setOnMouseEntered(e -> button.setStyle( "-fx-background-color: Red; " + "-fx-text-fill: White; " + roundButtonStyle));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: Black; " + "-fx-text-fill: White; " + roundButtonStyle ));
        button.setOnAction(event -> {
            System.out.println(text + " button was pressed!");
            displayRules();
        });
        return button;
    }
    private static void displayRules(){
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
        rulesArea.setFill(Color.WHITE);

        TextArea rulesTextArea = new TextArea();
        rulesTextArea.setMinSize(root.getWidth(), root.getHeight()-gameRule.getHeight()-hBox1.getHeight());
        rulesTextArea.setEditable(false);
        rulesTextArea.setBackground(new Background(new BackgroundFill(Color.color(0, 0, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));
        rulesTextArea.setWrapText(true);
        rulesTextArea.autosize();
        rulesTextArea.setStyle("-fx-padding: 10 30; " +
                "-fx-font-family: 'Franklin Gothic';" +
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
    protected static Button makeOptionButton(String text){
        Button button = new Button(text);
        String style = "-fx-padding: 10 20; " +
                "-fx-font-family: 'Franklin Gothic';" +
                "-fx-font-weight: Bold;" +
                "-fx-font-size: 35;";
        button.setStyle("-fx-text-fill: Black;" + style);
        if(text.equals("Back")) {
            button.setMinSize(120, 80);
            button.setOnMouseEntered(e -> {
                button.setText("< " + text);
                button.setStyle("-fx-underline: true; " +
                        "-fx-text-fill: Red; " +
                        "-fx-border-radius: 50em; " +
                        "-fx-border-color: Red; " +
                        "-fx-border-width: 4px;" + style);
            });
            button.setOnMouseExited(e -> {
                button.setText(text);
                button.setStyle("-fx-underline: false; -fx-text-fill: Black;" + style);
            });
        }
        else if(text.equals("<")){
            button.setOnMouseEntered(e-> button.setStyle("-fx-text-fill: Red; " + style));
            button.setOnMouseExited(e-> button.setStyle("-fx-text-fill: Black; " + style));
            button.setMinSize(80, 80);
        }
        else {
            button.setOnMouseEntered(e -> {
                button.setStyle("-fx-underline: true; " +
                        "-fx-text-fill: Red; " +
                        "-fx-border-radius: 50em; " +
                        "-fx-border-color: Red; " +
                        "-fx-border-width: 4px;" + style);
                button.setText(text + " >");
//                button.setBorder(Border.stroke(Color.RED));
            });
            button.setOnMouseExited(e -> {
                button.setStyle("-fx-underline: false; -fx-text-fill: Black;" + style);
                button.setText(text);
//                button.setBorder(null);
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
        title.setStyle("-fx-font-family: 'Franklin Gothic';" +
                "-fx-font-size: 50;" +
                "-fx-text-fill: Black;");


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
        title.setStyle("-fx-font-family: 'Franklin Gothic';" +
                "-fx-font-size: 20;" +
                "-fx-text-alignment: center;");
        buttonBox.getChildren().addAll(button, title);
        return buttonBox;
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
        option.setOnMouseClicked(event-> displaySettings());
        exit.setOnMouseClicked(event-> displayMainMenu());
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
            button.setStyle("-fx-text-fill: Black;" + style);
            button.setOnMouseEntered(e -> button.setStyle("-fx-text-fill: Red; " +
                    "-fx-border-color: Red; " +
                    "-fx-border-width: 4px;" + style));
            button.setOnMouseExited(e -> button.setStyle("-fx-text-fill: Black; " + style ));
        }
        else{
            style = "-fx-padding: 10 20; " +
                    "-fx-font-family: 'Franklin Gothic';" +
                    "-fx-border-radius: 10em;" +
                    "-fx-font-size: 35;" +
                    "-fx-border-width: 4px;";
            button.setStyle("-fx-text-fill: Black; -fx-border-color: Black; " + style);
            button.setOnMouseEntered(e -> {
                button.setStyle("-fx-text-fill: Red; -fx-border-color: Red; -fx-font-weight: Bold;" + style);
                button.setText(text + " >");
            });
            button.setOnMouseExited(e -> {
                button.setStyle("-fx-text-fill: Black; -fx-border-color: Black; " + style );
                button.setText(text);
            });
        }

        button.setBackground(new Background(new BackgroundFill(Color.color(0,0,0,0), CornerRadii.EMPTY, Insets.EMPTY)));

        return button;
    }
    private static void setSelectedType(Button button1, Button button2, Button button3, Button button4){
        String style = "-fx-padding: 10 20; " +
                "-fx-font-family: 'Franklin Gothic';" +
                "-fx-font-size: 35;" +
                "-fx-border-radius: 50em;";
        button1.setStyle( "-fx-border-color: Green; " + "-fx-text-fill: Green; -fx-border-width: 4px; -fx-font-weight: Bold;" + style);
        button2.setStyle( "-fx-border-color: Gray; " + "-fx-text-fill: Gray; -fx-border-width: 0px; " + style);
        button3.setStyle( "-fx-border-color: Gray; " + "-fx-text-fill: Gray; -fx-border-width: 0px; " + style);
        button4.setStyle( "-fx-border-color: Gray; " + "-fx-text-fill: Gray; -fx-border-width: 0px; " + style);
        button1.setOnMouseEntered(e -> button1.setStyle(button1.getStyle()));
        button1.setOnMouseExited(e -> button1.setStyle(button1.getStyle()));
        button2.setOnMouseEntered(e -> button2.setStyle(button2.getStyle()));
        button2.setOnMouseExited(e -> button2.setStyle(button2.getStyle()));
        button3.setOnMouseEntered(e -> button3.setStyle(button3.getStyle()));
        button3.setOnMouseExited(e -> button3.setStyle(button3.getStyle()));
        button4.setOnMouseEntered(e -> button4.setStyle(button4.getStyle()));
        button4.setOnMouseExited(e -> button4.setStyle(button4.getStyle()));
    }
    private static void setSelectedType(Button button1, Button button2, Button button3){
        String style = "-fx-padding: 10 20; " +
                "-fx-font-family: 'Franklin Gothic';" +
                "-fx-font-size: 35;" +
                "-fx-border-radius: 50em;";
        button1.setStyle( "-fx-border-color: Green; " + "-fx-text-fill: Green; -fx-border-width: 4px; -fx-font-weight: Bold;" + style);
        button2.setStyle( "-fx-border-color: Gray; " + "-fx-text-fill: Gray; -fx-border-width: 0px; " + style);
        button3.setStyle( "-fx-border-color: Gray; " + "-fx-text-fill: Gray; -fx-border-width: 0px; " + style);
        button1.setOnMouseEntered(e -> button1.setStyle(button1.getStyle()));
        button1.setOnMouseExited(e -> button1.setStyle(button1.getStyle()));
        button2.setOnMouseEntered(e -> button2.setStyle(button2.getStyle()));
        button2.setOnMouseExited(e -> button2.setStyle(button2.getStyle()));
        button3.setOnMouseEntered(e -> button3.setStyle(button3.getStyle()));
        button3.setOnMouseExited(e -> button3.setStyle(button3.getStyle()));
    }
    private static void setSelectedType(Button button1, Button button2){
        String style = "-fx-padding: 10 20; " +
                "-fx-font-family: 'Franklin Gothic';" +
                "-fx-font-size: 35;" +
                "-fx-border-radius: 30em;";
        button1.setStyle( "-fx-border-color: Green; " + "-fx-text-fill: Green; -fx-border-width: 4px; -fx-font-weight: Bold;" + style);
        button2.setStyle( "-fx-border-color: Gray; " + "-fx-text-fill: Gray; -fx-border-width: 0px; " + style);
        button1.setOnMouseEntered(e -> button1.setStyle(button1.getStyle()));
        button1.setOnMouseExited(e -> button1.setStyle(button1.getStyle()));
        button2.setOnMouseEntered(e -> button2.setStyle(button2.getStyle()));
        button2.setOnMouseExited(e -> button2.setStyle(button2.getStyle()));
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
    private static void displayGame() {
        root.getChildren().clear();
//        gamePane = new BorderPane();
//        gamePane.setPadding(new Insets(10));
        gamePane = switch(gameType){
            case 2-> new Ultimate();
            case 3-> new Quixo();
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
    private static void displaySettings(){
        root.getChildren().add(new Settings(stage));

    }
    public static void showTurn(int row, int col, int[] superIndex){
        if(getPlayer()==1)
            gamePane.markX(row,col,superIndex);
        else
            gamePane.markO(row, col,superIndex);
    }
    public static void showTurn(int row, int col){
        if(getPlayer()==1)
            gamePane.markX(row,col);
        else
            gamePane.markO(row, col);
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
        gamePane.marks.getChildren().add(imageView);
        System.out.println("Marked on grid!");
    }
    public static void markLine(int value){
        int[][] lineIndex;
        if(gameType==3) {
            lineIndex = quxioWinValues.get(value);
            Quixo quixo = (Quixo)gamePane;
            quixo.markUp(0, lineIndex);
        }
        else {
            lineIndex = dictionary.get(value);
            int startX = (int) ((lineIndex[0][1] * cell) + (int) (cell * 0.5));
            int startY = (int) ((lineIndex[0][0] * cell) + (int) (cell * 0.5));
            int endX = (int) ((lineIndex[2][1] * cell) + (int) (cell * 0.5));
            int endY = (int) ((lineIndex[2][0] * cell) + (int) (cell * 0.5));

            Timeline timeline = markLine(startX, startY, endX, endY, Color.LIGHTGOLDENRODYELLOW, 0, gamePane.marks);
            timeline.setOnFinished(end ->  endGame());
        }
    }
    public static void markLine(int[] superIndex,int value){
        int[][] lineIndex = dictionary.get(value);
        int startX =(int)((superIndex[1]*cell)+(lineIndex[0][1]*(cell/3))+(int)((cell/3)*0.5));
        int startY =(int)((superIndex[0]*cell)+(lineIndex[0][0]*(cell/3))+(int)((cell/3)*0.5));
        int endX = (int) ((superIndex[1]*cell)+(lineIndex[2][1]*(cell/3))+(int)((cell/3)*0.5));
        int endY = (int) ((superIndex[0]*cell)+(lineIndex[2][0]*(cell/3))+(int)((cell/3)*0.5));

        markLine(startX,startY,endX,endY,Color.LIGHTGOLDENRODYELLOW,0,gamePane.marks);

        int x =(int) (superIndex[1]*cell)+4;
        int y =(int) (superIndex[0]*cell)+4;
        showMark(x,y,true);
        if(getPlayer()==1)
            gamePane.markX(superIndex[0],superIndex[1]);
        else
            gamePane.markO(superIndex[0],superIndex[1]);
    }
    static Timeline markLine(int startX, int startY, int endX, int endY, Color color, double delay, Group node){
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
    public static void slide(int row, int col, int rowI, int colI) {
        Quixo quixo = (Quixo)gamePane;
        quixo.slide(row, col, rowI, colI);
    }

    public static void clearGame(){
        gamePane.clearMarks();
        gamePane.marks = null;
        gamePane = null;
        displayMainMenu();
    }
}