package Tic_Tac_Ultimate;

import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
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


import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static Tic_Tac_Ultimate.Board.dictionary;
import static Tic_Tac_Ultimate.Runner.*;

public class GUI extends Application {
    private static Stage stage;
    private static StackPane root;
    private static Color backGround = Color.web("#f2f2f2");
    private static Color midGround = Color.web("#fff");
    private static Color foreGround = Color.web("#000000");
    private static Text turn1;
    private static Text turn2;
    private static Group grid1;
    private static Group grid2;
    private static Group box1;
    private static Group box2;
    private static BorderPane gamePane;
    private static Pane boxPane;
    private static Pane arrowPane;
    private static double cell;
    private static Group marks;
    public static void initialize(String[] args){
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
        displayGame();
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
            translateY(0,ultimate.getLayoutBounds().getHeight()/2, toe);

            rotate(-90,0, ultimate);
            translateY(-ultimate.getLayoutBounds().getHeight()/2,0, ultimate);

            rotate(-90, 0, background);
            translateY(-ultimate.getLayoutBounds().getHeight()/2,0, background);

            FillTransition color = new FillTransition(Duration.seconds(2.5),background);
            color.setToValue(Color.RED);
            color.play();
            color.setOnFinished(event2 -> {
                System.out.println("Transition2 completed");
                displayMainMenu();
            });
        });
    }
    private static void translateY(double startY, double endY, Node node){
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), node);
        translateTransition.setFromY(startY);
        translateTransition.setToY(endY);
        translateTransition.setCycleCount(1);
        translateTransition.play();
    }
    private static void translateX(double startX, double endX, Node node){
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), node);
        translateTransition.setFromX(startX);
        translateTransition.setToX(endX);
        translateTransition.setCycleCount(1);
        translateTransition.play();
    }
    private static void rotate(int fromAngle, int toAngle, Node node){
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(2), node);
        rotateTransition.setAxis(Rotate.X_AXIS);
        rotateTransition.setFromAngle(fromAngle);
        rotateTransition.setToAngle(toAngle);
        rotateTransition.setCycleCount(1);
        rotateTransition.play();
    }
    private static void displayMainMenu() {
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
        gamePane = new BorderPane();
        gamePane.setPadding(new Insets(10));
        root.getChildren().add(gamePane);

        StackPane player1 = playerInfo(1, Color.RED);
        gamePane.setLeft(player1);

        StackPane player2 = playerInfo(2, Color.BLUE);
        gamePane.setRight(player2);

        StackPane center = new StackPane();
        gamePane.setCenter(center);

        Rectangle rectangle = makeRectangle(0.95,0.95);
        Pane board = new Pane();
        board.maxWidthProperty().bind(root.heightProperty().multiply(0.8));
        board.maxHeightProperty().bind(root.heightProperty().multiply(0.8));
        if(cell==0.0 && gameType!=3)
            Platform.runLater(()->{
                cell = board.getHeight()/3;
                System.out.println("Cell size: " + cell);
            });
        center.getChildren().addAll(rectangle, board);
        switch(gameType){
            case 2-> superTicTacToe(board);
            case 3-> quxio(board);
            default -> ticTacToe(board);
        }
        Toss();
    }
    private static void quxio(Pane board){
        Rectangle backRectangle = makeRectangle(0.8,0.8);
        backRectangle.setFill(foreGround);
        boxPane = new Pane();
        boxPane.setTranslateX(10);
        boxPane.setTranslateY(10);
        marks = new Group();
        arrowPane = new Pane();
        board.getChildren().addAll(backRectangle, boxPane, arrowPane);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Rectangle box = makeBox();
                box.setTranslateX(130 * j);
                box.setTranslateY(130 * i);
                box.setId(""+i+j);
                boxPane.getChildren().add(box);
            }
        }
        boxPane.getChildren().add(marks);

    }
    private static Rectangle makeBox(){
        Rectangle box = makeRectangle(0.15,0.15);
        Color original = (Color) box.getFill();
        AtomicBoolean flag = new AtomicBoolean(true);
        AtomicInteger row = new AtomicInteger();
        AtomicInteger col = new AtomicInteger();
        Platform.runLater(()->{
            String id = box.getId();
            row.set(Integer.parseInt(id.substring(0, 1)));
            col.set(Integer.parseInt(id.substring(1)));
        });
        box.setOnMouseEntered(mouseEvent -> {
            if(row.get() ==0 || row.get() ==4 || col.get() ==0 || col.get() ==4)
                box.setFill(Color.YELLOW);
        });
        box.setOnMouseExited(mouseEvent -> {
            if(row.get() ==0 || row.get() ==4 || col.get() ==0 || col.get() ==4) {
                if (flag.get())
                    box.setFill(original);
                else
                    box.setFill(Color.DARKGRAY);
                flag.set(true);
            }
        });
        box.setOnMouseClicked(mouseEvent -> {
            System.out.println("Received row: " + row + " and Col: " + col);
            if(row.get() ==0 || row.get() ==4 || col.get() ==0 || col.get() ==4) {
                flag.set(false);
                showArrows(row.get(), col.get());
//                draw(row, col);
            }
//            turn(Integer.parseInt(box.getId().substring(0,1)),Integer.parseInt(box.getId().substring(1)));
            System.out.println("mouse Clicked!");
        });
        return box;
    }
    public static void showArrows(int row, int col) {
        if(row!=0){
            Pane arrow = arrow((130*col)+20,-90,"down", col);
            arrowPane.getChildren().add(arrow);
        }
        if(row!=4){
            Pane arrow = arrow((130*col)+20,130*5,"up", col);
            arrowPane.getChildren().add(arrow);
        }
        if(col!=0){
            Pane arrow = arrow(-90,(130*row)+20,"right", row);
            arrowPane.getChildren().add(arrow);
        }
        if(col!=4){
            Pane arrow = arrow(130*5,(130*row)+20,"left", row);
            arrowPane.getChildren().add(arrow);
        }
        arrowPane.setPrefWidth(root.getHeight()*0.8);
        arrowPane.setPrefHeight(root.getHeight()*0.8);
    }
    private static Pane arrow(int x, int y, String imageName, int draw){
        Pane pane = new Pane();
        pane.setTranslateX(x);
        pane.setTranslateY(y);
        Image arrowImage = new Image( "Arrows/" + imageName + ".png");
        ImageView imageView = new ImageView(arrowImage);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        pane.getChildren().add(imageView);
        pane.setStyle("-fx-cursor: pointer");
        if(imageName.equalsIgnoreCase("up") || imageName.equalsIgnoreCase("down")) {
            TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), pane);
            transition.setFromY(y);
            transition.setToY(imageName.equals("down")?y-20:y+20);
            transition.setAutoReverse(true);
            transition.setCycleCount(Animation.INDEFINITE);
            transition.play();
        }
        else {
            TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), pane);
            transition.setFromX(x);
            transition.setToX(imageName.equals("right")?x-20:x+20);
            transition.setAutoReverse(true);
            transition.setCycleCount(Animation.INDEFINITE);
            transition.play();
        }
        pane.setOnMouseClicked(MouseEvent->{
            if(imageName.equalsIgnoreCase("up") || imageName.equalsIgnoreCase("down")){
                turn((x-20)/130, draw, y<0?0:4,(x-20)/130);
                slide((x-20)/130, draw, y<0?0:4,(x-20)/130);
            }
            else{
                turn(draw,(y-20)/130, (y-20)/130, x<0?0:4);
                slide(draw,(y-20)/130, (y-20)/130, x<0?0:4);
            }
            arrowPane.getChildren().clear();
            arrowPane.setPrefHeight(0);
            arrowPane.setPrefWidth(0);
            System.out.println("Show GUI mark Done!");

        });
        return pane;
    }
    private static void slide(int row, int col, int rowI, int colI){
        if(row == rowI){//row ke liye
            int value = col;
            int length = 0;
            if(value<colI) {
                System.out.println("Row kon si hai: " + row + "\nAnd COl: " + col);
                boxPane.lookup("" + row + col).setId("temp");
                value++;
                for (; value < colI; value++) {
                    Rectangle select = (Rectangle) boxPane.lookup("" + row + value);
                    translateX(select.getTranslateX(),select.getTranslateX()-130,select);
//                    select.setTranslateX(select.getTranslateX()-130);
                    select.setId("" + row + (value - 1));
//                    super.board[row][value] = super.board[row][value+1];
                    length++;
                }
                Rectangle select = (Rectangle) boxPane.lookup("temp");
                select.setTranslateX(select.getTranslateX()+(length*130));
                select.setId(""+ row + colI);
            }
//            else
//                for(;value>colI;value--){
//                    super.board[drawIndex[0]][value] = super.board[drawIndex[0]][value-1];
//                }
//        }
//        else {//col ke liye
//            int value = row;
//            if (value < rowI)
//                for (; value < rowI; value++) {
//                    super.board[value][drawIndex[1]] = super.board[value + 1][drawIndex[1]];
//                }
//            else
//                for (; value > rowI; value--) {
//                    super.board[value][drawIndex[0]] = super.board[value - 1][drawIndex[0]];
//                }
        }
    }
    private static StackPane playerInfo(int player, Color color){
        Rectangle rectangle = makeRectangle(3.5/9, 0.95);

        Text playerName = new Text("Player " + player);
        if(singlePlayer && player==2)
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
        if(gameType==2) {
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
    public static void Toss(){
        String text = "Select your side for the toss";
        popUp(text,"Heads","Tails",1);
    }
    private static void Toss(int choice){
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
    public static void popUp(String text, String button1Text, String button2Text, int method){
        BorderPane background = new BorderPane();
        StackPane popUp = new StackPane();
        background.setCenter(popUp);
        background.setBackground(new Background(new BackgroundFill( Color.color(0,0,0,0.8), CornerRadii.EMPTY, Insets.EMPTY)));
        Rectangle box = makeRectangle(0.4,0.2);
        popUp.getChildren().add(box);

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
    private static void showX(Group node){
        markLine(0,0,100,100,Color.RED,0,node);
        markLine(100,0,0,100,Color.RED,0.2,node);
    }
    private static Rectangle makeRectangle(double widthMultiplier, double heightMultiplier){
        Rectangle rectangle = new Rectangle();
        rectangle.setFill(midGround);
        rectangle.widthProperty().bind(root.heightProperty().multiply(widthMultiplier));
        rectangle.heightProperty().bind(root.heightProperty().multiply(heightMultiplier));
        rectangle.setArcWidth(50);
        rectangle.setArcHeight(50);
        return rectangle;
    }
    private static void ticTacToe(Pane grid){
        Platform.runLater(() -> {
            grid.setOnMouseClicked(event -> {
                System.out.println("mouse Clicked!!");
                double x = event.getX();
                double y = event.getY();
                int row = (int) (y/cell);
                int col = (int) (x/cell);
                if(getPlayer()!=2 || !singlePlayer){
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
    private static void superTicTacToe(Pane grid){
        Platform.runLater(() -> {
            grid.setOnMouseClicked(event -> {
                System.out.println("mouse Clicked!!");
                double x = event.getX();
                double y = event.getY();
                int j = (int) (x  / (cell / 3)) % 3;
                int sJ = (int) (x  / cell);
                int i = (int) (y  / (cell / 3)) % 3;
                int sI = (int) (y  / cell);
                if (getPlayer() != 2 || !singlePlayer) {
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
    private static Line makeHLine(double Multiplier, int stroke, Pane grid){
        Line line = new Line();
        line.setStartY(0);
        line.endYProperty().bind(grid.maxHeightProperty());
        line.startXProperty().bind(grid.maxWidthProperty().multiply(Multiplier));
        line.endXProperty().bind(grid.maxWidthProperty().multiply(Multiplier));
        line.setStrokeWidth(stroke);
        return line;
    }
    private static Line makeVLine(double Multiplier, int stroke, Pane grid){
        Line line = new Line();
        line.startYProperty().bind(grid.maxHeightProperty().multiply(Multiplier));
        line.endYProperty().bind(grid.maxHeightProperty().multiply(Multiplier));
        line.setStartX(0);
        line.endXProperty().bind(grid.maxWidthProperty());
        line.setStrokeWidth(stroke);
        return line;
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
        int[][] lineIndex = dictionary.get(value);
        int startX =(int)((lineIndex[0][1]*cell)+(int)(cell*0.5));
        int startY =(int)((lineIndex[0][0]*cell)+(int)(cell*0.5));
        int endX = (int) ((lineIndex[2][1]*cell)+(int)(cell*0.5));
        int endY = (int) ((lineIndex[2][0]*cell)+(int)(cell*0.5));

        markLine(startX,startY,endX,endY,Color.LIGHTGOLDENRODYELLOW,0,marks);
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
    private static void markLine(int startX, int startY, int endX, int endY, Color color, double delay,Group node){
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
    private static void markO(double x, double y, double diameter,Group node){
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

        node.getChildren().addAll(circle,inner);
    }
    private static void markX2(int row, int col){
        Rectangle box = (Rectangle) boxPane.lookup("#"+row+col);
        DoubleBinding startY = box.translateYProperty().add(box.heightProperty().multiply(0.2));
        DoubleBinding startX = box.translateYProperty().add(box.widthProperty().multiply(0.2));
        DoubleBinding endY = box.translateYProperty().add(box.heightProperty().multiply(0.6));
        DoubleBinding endX = box.translateYProperty().add(box.widthProperty().multiply(0.6));
        markLine2(startX,startY,endX,endY,Color.RED,0,marks);
        startX = endX;
        endX = box.translateYProperty().add(box.widthProperty().multiply(0.2));
        markLine2(startX,startY,endX,endY,Color.RED,0.2,marks);
    }
    private static void markLine2(DoubleBinding startX, DoubleBinding startY, DoubleBinding endX, DoubleBinding endY, Color color, double delay, Group node){
        Line line = new Line();
        line.startXProperty().bind(startX);
        line.startYProperty().bind(startY);
        line.endXProperty().bind(endX);
        line.endYProperty().bind(endY);
        line.setStrokeWidth(20);
        line.setStroke(color);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        node.getChildren().add(line);
        Timeline timeline = new Timeline();
        KeyFrame startFrame = new KeyFrame(Duration.seconds(delay),
                new KeyValue(line.endXProperty(), startX.getValue()),
                new KeyValue(line.endYProperty(), startY.getValue()));
        KeyFrame endFrame = new KeyFrame(Duration.seconds(delay+0.3),
                new KeyValue(line.endXProperty(), endX.getValue()),
                new KeyValue(line.endYProperty(), endY.getValue()));
        timeline.getKeyFrames().addAll(startFrame, endFrame);
        timeline.play();
    }
    private static void markO2(int row, int col){
        Rectangle box = (Rectangle) boxPane.lookup("#"+row+col);
        DoubleBinding y = box.translateYProperty().add(box.heightProperty().multiply(0.5));
        DoubleBinding x = box.translateYProperty().add(box.widthProperty().multiply(0.5));
        markO2(x,y,box.widthProperty(),marks);
    }
    private static void markO2(DoubleBinding x, DoubleBinding y, DoubleProperty radiusFull, Group node){
        Circle circle = new Circle(0);
        circle.radiusProperty().bind(radiusFull.multiply(0.3));
        circle.centerXProperty().bind(x);
        circle.centerYProperty().bind(y);
        circle.setFill(Color.BLUE);
        Circle inner = new Circle(0);
        inner.radiusProperty().bind(radiusFull.multiply(0.2));
        inner.centerXProperty().bind(x);
        inner.centerYProperty().bind(y);
        inner.setFill(Color.WHITE);


        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        KeyValue keyValueRadius = new KeyValue(circle.radiusProperty(), (radiusFull.getValue()*0.3));
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.25), keyValueRadius);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();

        Timeline innerTimeLine = new Timeline();
        innerTimeLine.setCycleCount(1);
        KeyValue keyValueRadiusInner = new KeyValue(inner.radiusProperty(), (radiusFull.getValue()*0.2));
        KeyFrame keyFrameInner = new KeyFrame(Duration.seconds(0.25), keyValueRadiusInner);
        innerTimeLine.getKeyFrames().add(keyFrameInner);
        innerTimeLine.play();

        node.getChildren().addAll(circle,inner);
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