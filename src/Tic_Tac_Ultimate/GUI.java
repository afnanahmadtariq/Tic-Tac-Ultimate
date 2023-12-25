package Tic_Tac_Ultimate;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
        stage.setWidth(1280);
        stage.setHeight(720);
        stage.setResizable(false);
        stage.setFullScreen(true);
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
        root.addEventHandler(KeyEvent.KEY_PRESSED,event->{
            if(event.getCode()== KeyCode.F11)
                stage.setFullScreen(!stage.isFullScreen());
        });
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
        stage.setScene(new Scene(root));
        stage.show();
        displaySettings();
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
        HBox title = getTitle(74, root.getHeight()*0.1);

        Button start = makeButton("Start");
        Button options = makeButton("Settings");
        Button exit = makeButton("Exit Game");

        root.getChildren().clear();
        VBox menuPanel = new VBox(title,start,options,exit);
        menuPanel.setAlignment(Pos.TOP_CENTER);
        menuPanel.setSpacing(25);
        root.getChildren().add(menuPanel);
    }
    private static Button makeButton(String text) {
        Button button = new Button(text);
        button.setTranslateY(root.getHeight()*0.25);
        button.setMinSize(200,80);
        String style = "-fx-padding: 10 20; " +
                "-fx-font-family: 'Franklin Gothic';" +
                "-fx-font-size: 35;" +
                "-fx-border-radius: 5;";
        button.setStyle( "-fx-background-color: Black; " + "-fx-text-fill: White; " + style );
        button.setOnMouseEntered(e -> button.setStyle( "-fx-background-color: Red; " + "-fx-text-fill: White; " + style));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: Black; " + "-fx-text-fill: White; " + style ));
        button.setOnAction(event -> {
            System.out.println(text +" button was pressed!");
            if(text.equals("Start"))
                displayGameSelection();
            else if(text.equals("Settings"))
                displayPopupMessage("Under Development", "Option Button is under development");
            else {
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
    private static void displayGameSelection() {
    root.getChildren().clear();
        VBox selectionPanel = new VBox();
        selectionPanel.setAlignment(Pos.CENTER);
        selectionPanel.setSpacing(10);
        root.getChildren().add(selectionPanel);

        HBox gameType = new HBox();
        gameType.setAlignment(Pos.CENTER);
        gameType.getChildren().addAll(new RadioButton("Tic Tac Toe"), new RadioButton("Super Tic Tac Toe"));
        gameType.setAlignment(Pos.TOP_CENTER);

        HBox playerOptions = new HBox();
        playerOptions.getChildren().addAll(new RadioButton("Single Player"), new RadioButton("Double Player"));
        playerOptions.setAlignment(Pos.CENTER);

        HBox gameDiffPanel = new HBox();
        gameDiffPanel.getChildren().addAll(
                new RadioButton("Easy"),
                new RadioButton("Medium"),
                new RadioButton("Hard"),
                new RadioButton("Extreme")
        );
        gameDiffPanel.setAlignment(Pos.BOTTOM_CENTER);

        Button playGame = new Button("Play");

        ToggleGroup gameToggleGroup = new ToggleGroup();
        ToggleGroup playerToggleGroup = new ToggleGroup();
        ToggleGroup difficultyToggleGroup = new ToggleGroup();
        playGame.setOnAction(event -> {
            System.out.println("Play Game button was pressed!");

            RadioButton selectedGameType = (RadioButton) gameToggleGroup.getSelectedToggle();
            RadioButton selectedPlayerOption = (RadioButton) playerToggleGroup.getSelectedToggle();
            RadioButton selectedDifficulty = (RadioButton) difficultyToggleGroup.getSelectedToggle();

            if(selectedGameType != null && selectedPlayerOption != null && selectedDifficulty != null){

                try {
                    switch (selectedPlayerOption.getText()){
                        case "Single Player" -> singlePlayer = true;
                        case "Double Player" -> singlePlayer = false;
                    }
                    if(!singlePlayer)
                        displayPopupMessage("Irrelevant Difficulty", "No Difficulty is required in case of" +
                                " Double Player Game\n\nThe game will now Continue");
                    difficulty = selectedDifficulty.getText();
                    System.out.println("Selected Game Type: " + selectedGameType.getText());
                    switch (selectedGameType.getText()) {
                        case "Tic Tac Toe" -> {
                            if(selectedDifficulty.getText().equals("Easy") || selectedDifficulty.getText().equals("Medium")) {
                                Runner.gameType = 1;
                                startGame();
                                displayGame();
                                System.out.println("Tic Tac Toe was Selected");
                                System.out.println(selectedPlayerOption.getText() + " and " + selectedDifficulty.getText() + " Game was selected");
                            } else {
                                displayPopupMessage("Under Development", "Tic Tac Toe:\n\tHard Mode\n\tExtreme Mode");
                                resetRadioButtons(gameToggleGroup);
                                resetRadioButtons(difficultyToggleGroup);
                            }
                        }
                        case "Super Tic Tac Toe" -> {
                            if(selectedDifficulty.getText().equals("Easy")) {
                                Runner.gameType = 2;
                                startGame();
                                displayGame();
                                System.out.println("Selected Super Tic Tac Toe");
                            } else {
                                displayPopupMessage("Under Development", "Super Tic Tac Toe:\n\tMedium Mode\n\tHard Mode\n\tExtreme Mode");
                                resetRadioButtons(gameToggleGroup);
                                resetRadioButtons(difficultyToggleGroup);
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else
                displayPopupMessage("Missing Outputs", "Please Select all fields");
        });

        // Assign toggle groups to radio buttons
        assignToggleGroup(gameType, gameToggleGroup);
        assignToggleGroup(playerOptions, playerToggleGroup);
        assignToggleGroup(gameDiffPanel, difficultyToggleGroup);
        playGame.setAlignment(Pos.CENTER);

        selectionPanel.getChildren().addAll(gameType, playerOptions, gameDiffPanel, playGame);

    }
    private static void resetRadioButtons(ToggleGroup toggleGroup) {
        toggleGroup.getToggles().forEach(toggle -> {
            RadioButton radioButton = (RadioButton) toggle;
            radioButton.setSelected(false);
        });
    }
    private static void assignToggleGroup(HBox hbox, ToggleGroup toggleGroup) {
        hbox.getChildren().forEach(node -> {
            if (node instanceof RadioButton) {
                ((RadioButton) node).setToggleGroup(toggleGroup);
            }
        });
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
    private void displaySettings(){
        root.getChildren().add(new Settings(stage));

    }
    private void displayRuleBook(){
        root.getChildren().clear();

    }
    private void displayHelp(){
        root.getChildren().clear();

    }
    private void displayAboutUs(){
        root.getChildren().clear();

    }
    private void displayProfile(){
        root.getChildren().clear();

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