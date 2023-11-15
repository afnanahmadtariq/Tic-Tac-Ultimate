package Tic_Tac_Ultimate;

import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

import static Tic_Tac_Ultimate.Board.dictionary;
import static Tic_Tac_Ultimate.Tic_Tac_Ultimate.*;

public class GUI extends Application {
    private Stage stage;
    private BorderPane game ;
    private Group marks;
    private StackPane root;
    private double cell;
    private Text turn1;
    private Text turn2;
    private Group grid1;
    private Group grid2;
    private Color backGround = Color.web("#f2f2f2");
    private Color midGround = Color.web("#fff");
    public void initialize(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage stage){
        this.stage = stage;
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
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        displayStart();
    }
    private void displayStart(){
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
            translate(0,ultimate.getLayoutBounds().getHeight()/2, toe);

            rotate(-90,0, ultimate);
            translate(-ultimate.getLayoutBounds().getHeight()/2,0, ultimate);

            rotate(-90, 0, background);
            translate(-ultimate.getLayoutBounds().getHeight()/2,0, background);

            FillTransition color = new FillTransition(Duration.seconds(2.5),background);
            color.setToValue(Color.RED);
            color.play();
            color.setOnFinished(event2 -> {
                System.out.println("Transition2 completed");
                displayMainMenu();
            });
        });
    }
    private void translate(double startY, double endY, Node node){
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), node);
        translateTransition.setFromY(startY);
        translateTransition.setToY(endY);
        translateTransition.setCycleCount(1);
        translateTransition.play();
    }
    private void rotate(int fromAngle, int toAngle, Node node){
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(2), node);
        rotateTransition.setAxis(Rotate.X_AXIS);
        rotateTransition.setFromAngle(fromAngle);
        rotateTransition.setToAngle(toAngle);
        rotateTransition.setCycleCount(1);
        rotateTransition.play();
    }
    private void displayMainMenu() {
        Text ticTac = new Text("Tic tac");
        Text ultimate = new Text(" Ultimate ");
        Rectangle background = new Rectangle();
        StackPane complex = new StackPane(background, ultimate);

        HBox title = new HBox(ticTac,complex);
        title.setAlignment(Pos.CENTER);
        title.setTranslateY(root.getHeight()*0.1);
        title.setSpacing(10);

        ticTac.setFont(Font.font("Franklin Gothic Heavy", FontWeight.BOLD, 74));
        ticTac.setFill(Color.BLACK);

        ultimate.setFont(Font.font("Franklin Gothic Heavy", FontWeight.BOLD, 74));
        ultimate.setFill(Color.WHITE);

        background.setWidth(ultimate.getLayoutBounds().getWidth());
        background.setHeight(ultimate.getLayoutBounds().getHeight());
        background.setFill(Color.RED);

        Button start = makeButton("Start");
        Button options = makeButton("Options");
        Button exit = makeButton("Exit Game");

        root.getChildren().clear();
        VBox menuPanel = new VBox(title,start,options,exit);
        menuPanel.setAlignment(Pos.TOP_CENTER);
        menuPanel.setSpacing(25);
        root.getChildren().add(menuPanel);
    }
    private Button makeButton(String text) {
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
            else if(text.equals("Options"))
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
    private void displayPopupMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null); // Setting header text to null means no header
        alert.setContentText(message);
        alert.showAndWait(); // Wait for user action (OK button click) before continuing
    }
    private void displayGameSelection() {
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
                if((selectedGameType.getText().equals("Super Tic Tac Toe") && !selectedDifficulty.getText().equals("Easy"))
                        || selectedDifficulty.getText().equals("Hard")){
                    displayPopupMessage("Under Development", "Super Tic Tac Toe\nHard Mode");
                    if(selectedGameType.getText().equals("Super Tic Tac Toe")){
                        resetRadioButtons(gameToggleGroup);
                    }
                    resetRadioButtons(difficultyToggleGroup);
                }
                try {
                    switch (selectedPlayerOption.getText()){
                        case "Single Player" -> singlePlayer = true;
                        case "Double Player" -> singlePlayer = false;
                    }
                    difficulty = selectedDifficulty.getText();
                    System.out.println("Selected Game Type: " + selectedGameType.getText());
                    switch (selectedGameType.getText()) {
                        case "Tic Tac Toe" -> {
                            ultimate = false;
                            startGame();
                            displayGame();
                            System.out.println("Tic Tac Toe was Selected");
                            System.out.println(selectedPlayerOption.getText() + " and " + selectedDifficulty.getText() + " Game was selected");
                        }
                        case "Super Tic Tac Toe" -> {
                            ultimate = true;
                            startGame();
                            displayGame();
                            System.out.println("Selected Super Tic Tac Toe");
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
    private void resetRadioButtons(ToggleGroup toggleGroup) {
        toggleGroup.getToggles().forEach(toggle -> {
            RadioButton radioButton = (RadioButton) toggle;
            radioButton.setSelected(false);
        });
    }
    private void assignToggleGroup(HBox hbox, ToggleGroup toggleGroup) {
        hbox.getChildren().forEach(node -> {
            if (node instanceof RadioButton) {
                ((RadioButton) node).setToggleGroup(toggleGroup);
            }
        });
    }
    private void displayGame() {
        root.getChildren().clear();
        game = new BorderPane();
        game.setPadding(new Insets(10));
        root.getChildren().add(game);

        StackPane player1 = playerInfo(1, Color.RED);
        game.setLeft(player1);

        StackPane player2 = playerInfo(2, Color.BLUE);
        game.setRight(player2);

        StackPane board = new StackPane();
        game.setCenter(board);

        Rectangle rectangle = makeRectangle(0.95,0.95);
        Pane grid = new Pane();
        grid.maxWidthProperty().bind(root.heightProperty().multiply(0.8));
        grid.maxHeightProperty().bind(root.heightProperty().multiply(0.8));
        if(cell==0.0)
            Platform.runLater(()->{
                cell = grid.getHeight()/3;
                System.out.println("Cell size: " + cell);
            });
        board.getChildren().addAll(rectangle, grid);

        if(ultimate)
            superTicTacToe(grid);
        else
            ticTacToe(grid);
        Toss();
    }
    private StackPane playerInfo(int player, Color color){
        Rectangle rectangle = makeRectangle(3.5/9, 0.95);

        Text playerName = new Text("Player " + player);
        if(isSinglePlayer() && player==2)
            playerName = new Text("CPU");
        playerName.setFont(Font.font("Franklin Gothic Heavy", FontWeight.BOLD, 74));
        playerName.setFill(color);
        Group symbol = new Group();
        Text turn = new Text("Turn");
        turn.setFont(Font.font("Franklin Gothic Heavy", 50));
        turn.setFill(Color.LIGHTGREY);
        if(player==1) {
            turn1 = turn;
            showX(symbol);
        }
        else {
            turn2 = turn;
            markO(0, 0, 100, symbol);
        }
        VBox info = new VBox(playerName,symbol,turn);
        info.setAlignment(Pos.TOP_CENTER);
        info.setSpacing(50);
        info.setTranslateY(100);
        if(ultimate) {
            Group grid = new Group();
            if(player==1)
                grid1 = grid;
            else
                grid2 = grid;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    Rectangle box = makeRectangle(.02, .02);
                    box.setTranslateX(20 * j);
                    box.setTranslateY(20 * i);
                    grid.getChildren().add(box);
                }
            }
            Rectangle back = makeRectangle(0.09, 0.09);
            back.setFill(Color.LIGHTGREY);
            StackPane turnIndicator = new StackPane(back, grid);
            turnIndicator.setAlignment(Pos.CENTER);
            info.getChildren().add(turnIndicator);
        }


        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(rectangle, info);
        return stackPane;
    }
    public void Toss(){
        String text = "Select your side for the toss";
        popUp(text,"Heads","Tails",1);
    }
    private void Toss(int choice){
        if(choice == (int)(Math.random()*2)+1){
            System.out.println("player = 1");
            Tic_Tac_Ultimate.setPlayer(1);
            setTurn(1);
        }
        else{
            System.out.println("player = 2");
            Tic_Tac_Ultimate.setPlayer(2);
            setTurn(2);
        }
    }
    public void popUp(String text, String button1Text, String button2Text, int method){
        BorderPane background = new BorderPane();
        StackPane popUp = new StackPane();
        background.setCenter(popUp);
        background.setBackground(new Background(new BackgroundFill( Color.color(0,0,0,0.8), CornerRadii.EMPTY, Insets.EMPTY)));
        Rectangle box = makeRectangle(0.4,0.2);
        popUp.getChildren().add(box);

        List<Node> appNodes = root.getChildren();
        for(Node node: appNodes){
            node.setDisable(true);
        }
        root.getChildren().add(background);
        VBox vBox = new VBox(new Text(text));
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(25);
        popUp.getChildren().add(vBox);


        Button button1 = new Button(button1Text);
        button1.setMinSize(80,20);
        button1.setOnAction(event -> {
            System.out.println("Button1 was pressed!");
            root.getChildren().remove(background);
            List<Node> appNodes1 = root.getChildren();
            for(Node node: appNodes1){
                node.setDisable(false);
            }
            if(method==1)
                Toss(1);
            else if(method==2)
                endGame(1);
        });
        Button button2 = new Button(button2Text);
        button2.setMinSize(80,20);
        button2.setOnAction(event -> {
            System.out.println("Button2 was pressed!");
            root.getChildren().remove(background);
            List<Node> appNodes12 = root.getChildren();
            for(Node node: appNodes12){
                node.setDisable(false);
            }
            if(method==1)
                Toss(2);
            else if(method==2)
                endGame(2);
        });
        HBox buttons = new HBox(button1,button2);
        buttons.setAlignment(Pos.BASELINE_CENTER);
        buttons.setSpacing(25);
        vBox.getChildren().add(buttons);

    }
    public void updateTurn(){
        if(turn1.getFill()==Color.GREEN) {
            turn1.setFill(Color.LIGHTGREY);
            turn2.setFill(Color.GREEN);
        }
        else {
            turn1.setFill(Color.GREEN);
            turn2.setFill(Color.LIGHTGREY);
        }
        if(ultimate) {
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
    private void setTurn(int player){
        if(player==1)
            turn1.setFill(Color.GREEN);
        else
            turn2.setFill(Color.GREEN);
        if(ultimate){
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
    public void clearTurn(){
        turn1.setFill(Color.LIGHTGREY);
        turn2.setFill(Color.LIGHTGREY);
        if(ultimate){
            for (int i=0; i<9; i++) {
                Rectangle box;
                box = (Rectangle) grid1.getChildren().get(i);
                box.setFill(Color.WHITE);
                box = (Rectangle) grid2.getChildren().get(i);
                box.setFill(Color.WHITE);
            }
        }
    }
    private void showX(Group node){
        markLine(0,0,100,100,Color.RED,0,node);
        markLine(100,0,0,100,Color.RED,0.2,node);
    }
    private Rectangle makeRectangle(double widthMultiplier, double heightMultiplier){
        Rectangle rectangle = new Rectangle();
        rectangle.setFill(midGround);
        rectangle.widthProperty().bind(root.heightProperty().multiply(widthMultiplier));
        rectangle.heightProperty().bind(root.heightProperty().multiply(heightMultiplier));
        rectangle.setArcWidth(50);
        rectangle.setArcHeight(50);
        return rectangle;
    }
    private void ticTacToe(Pane grid){
        Platform.runLater(() -> {
            grid.setOnMouseClicked(event -> {
                System.out.println("mouse Clicked!!");
                double x = event.getX();
                double y = event.getY();
                int row = (int) (y/cell);
                int col = (int) (x/cell);
                if(getPlayer()!=2 || !isSinglePlayer()){
                    if(turn(row, col))
                        System.out.println("registered!!...............");
                }
            });
            for (int i = 0; i<4; i++) {
                Line hLine = makeHLine((double) i/3,5, grid);
                grid.getChildren().add(hLine);

                Line vLine = makeVLine((double) i/3,5, grid);
                grid.getChildren().add(vLine);
            }
            marks = new Group();
            grid.getChildren().add(marks);
        });
    }
    private void superTicTacToe(Pane grid){
        Platform.runLater(() -> {
            grid.setOnMouseClicked(event -> {
                System.out.println("mouse Clicked!!");
                double x = event.getX();
                double y = event.getY();
                int j = (int) (x  / (cell / 3)) % 3;
                int sJ = (int) (x  / cell);
                int i = (int) (y  / (cell / 3)) % 3;
                int sI = (int) (y  / cell);
                if (getPlayer() != 2 || !isSinglePlayer()) {
                    turn(i, j, sI, sJ);
                }
            });
            for (int i = 0; i < 10; i++) {
                Line hLine;
                if (i == 0 || i == 3 || i == 6 || i == 9)
                    hLine = makeHLine((double) i/9, 8, grid);
                else
                    hLine = makeHLine((double) i/9, 3, grid);
                grid.getChildren().add(hLine);

                Line vLine;
                if (i == 0 || i == 3 || i == 6 || i == 9)
                    vLine = makeVLine((double) i/9, 8, grid);
                else
                    vLine = makeVLine((double) i/9, 3, grid);
                grid.getChildren().add(vLine);
            }
            marks = new Group();
            grid.getChildren().add(marks);
        });
    }
    private Line makeHLine(double Multiplier, int stroke, Pane grid){
        Line line = new Line();
        line.setStartY(0);
        line.endYProperty().bind(grid.maxHeightProperty());
        line.startXProperty().bind(grid.maxWidthProperty().multiply(Multiplier));
        line.endXProperty().bind(grid.maxWidthProperty().multiply(Multiplier));
        line.setStrokeWidth(stroke);
        return line;
    }
    private Line makeVLine(double Multiplier, int stroke, Pane grid){
        Line line = new Line();
        line.startYProperty().bind(grid.maxHeightProperty().multiply(Multiplier));
        line.endYProperty().bind(grid.maxHeightProperty().multiply(Multiplier));
        line.setStartX(0);
        line.endXProperty().bind(grid.maxWidthProperty());
        line.setStrokeWidth(stroke);
        return line;
    }
    public void showTurn(int row, int col, int[] superIndex){
        if(getPlayer()==1)
            markX(row,col,superIndex);
        else
            markO(row, col,superIndex);
    }
    public void showTurn(int row, int col){
        if(getPlayer()==1)
            markX(row,col);
        else
            markO(row, col);
    }
    public void markDraw(int[] superIndex){
        int x = (int)(superIndex[1]*cell)+4;
        int y = (int)(superIndex[0]*cell)+4;
        showMark(x,y,true);
        showMark(x + ((int) (cell * 0.2) - 4), y + ((int) (cell * 0.2) - 4), false);
    }
    private void showMark(int x, int y,  boolean white){
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
    public void markLine(int value){
        int[][] lineIndex = dictionary.get(value);
        int startX =(int)((lineIndex[0][1]*cell)+(int)(cell*0.5));
        int startY =(int)((lineIndex[0][0]*cell)+(int)(cell*0.5));
        int endX = (int) ((lineIndex[2][1]*cell)+(int)(cell*0.5));
        int endY = (int) ((lineIndex[2][0]*cell)+(int)(cell*0.5));

        markLine(startX,startY,endX,endY,Color.LIGHTGOLDENRODYELLOW,0,marks);
    }
    public void markLine(int[] superIndex,int value){
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
    private void markLine(int startX, int startY, int endX, int endY, Color color, double delay,Group node){
        Line line = new Line(startX, startY,startX, startY);
        line.setStrokeWidth(0);
        line.setStroke(color);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
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
    }
    private void markX(int row, int col, int[] superIndex){
        int startY = (int) ((int) (row*(cell/3))+(superIndex[0]*cell)+((cell/3)*0.2));
        int startX = (int) ((int) (col*(cell/3))+(superIndex[1]*cell)+((cell/3)*0.2));
        int endY = startY + (int)((cell/3)*0.6);
        int endX = startX + (int)((cell/3)*0.6);
        markLine(startX,startY,endX,endY,Color.RED,0,marks);
        startX = endX;
        endX = endX - (int)((cell/3)*0.6);
        markLine(startX,startY,endX,endY,Color.RED,0.2,marks);
    }
    private void markX(int row, int col){
        int startY = (int)((row*cell)+(cell*0.2));
        int startX = (int)((col*cell)+(cell*0.2));
        int endY = startY + (int)(cell*0.6);
        int endX = startX + (int)(cell*0.6);
        markLine(startX,startY,endX,endY,Color.RED,0,marks);
        startX = endX;
        endX = endX - (int)(cell*0.6);
        markLine(startX,startY,endX,endY,Color.RED,0.2,marks);
    }
    private void markO(int row, int col){
        double y = (row*cell)+(cell*0.5);
        double x = (col*cell)+(cell*0.5);
        markO(x,y,cell*0.6,marks);
    }

    private void markO(int row, int col, int[] superIndex){
        double y =  (row*(cell/3))+(superIndex[0]*cell)+((cell/3)*0.5);
        double x =  (col*(cell/3))+(superIndex[1]*cell)+((cell/3)*0.5);
        markO(x,y,((cell/3)*0.3),marks);
    }
    private void markO(double x, double y, double radius,Group node){
        Circle circle = new Circle(x,y,0);
        circle.setFill(Color.BLUE);
        Circle inner = new Circle(x,y,0);
        inner.setFill(Color.WHITE);


        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        KeyValue keyValueRadius = new KeyValue(circle.radiusProperty(), (radius*0.5));
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.25), keyValueRadius);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();

        Timeline innerTimeLine = new Timeline();
        innerTimeLine.setCycleCount(1);
        KeyValue keyValueRadiusInner = new KeyValue(inner.radiusProperty(), (radius*0.38));
        KeyFrame keyFrameInner = new KeyFrame(Duration.seconds(0.25), keyValueRadiusInner);
        innerTimeLine.getKeyFrames().add(keyFrameInner);
        innerTimeLine.play();

        node.getChildren().addAll(circle,inner);
    }
    public void clearMarks(){
        marks.getChildren().clear();
    }
    public void clearGame(){
        clearMarks();
        marks = null;
        game = null;
        displayMainMenu();
    }
}