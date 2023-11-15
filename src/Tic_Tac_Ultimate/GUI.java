package Tic_Tac_Ultimate;

import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.util.List;

import static Tic_Tac_Ultimate.Board.dictionary;
import static Tic_Tac_Ultimate.Tic_Tac_Ultimate.*;

public class GUI extends Application {
    private Stage stage;
    private static BorderPane game ;
    private static Group marks;
    private static StackPane root;
    private static double cell;
    private static Color backGround = Color.web("#f2f2f2");
    private static Color midGround = Color.web("#fff");
    private ToggleGroup gameToggleGroup = new ToggleGroup();
    private ToggleGroup playerToggleGroup = new ToggleGroup();
    private ToggleGroup difficultyToggleGroup = new ToggleGroup();

    @Override
    public void start(Stage stage) throws Exception{
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
                displayNavPage();
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
    private void displayNavPage() {
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
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(text +" button was pressed!");
                if(text.equals("Start"))
                    displayGameSelection();
                else if(text.equals("Options"))
                    displayPopupMessage("Under Development", "Option Button is under development", Alert.AlertType.INFORMATION);
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
            }
        });
        return button;
    }
    private static void displayPopupMessage(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
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
        playGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Play Game button was pressed!");

                RadioButton selectedGameType = (RadioButton) gameToggleGroup.getSelectedToggle();
                RadioButton selectedPlayerOption = (RadioButton) playerToggleGroup.getSelectedToggle();
                RadioButton selectedDifficulty = (RadioButton) difficultyToggleGroup.getSelectedToggle();

                if(selectedGameType != null && selectedPlayerOption != null && selectedDifficulty != null){
                    if((selectedGameType.getText().equals("Super Tic Tac Toe") && !selectedDifficulty.getText().equals("Easy"))
                            || selectedDifficulty.getText().equals("Hard")){
                        displayPopupMessage("Under Development", "Super Tic Tac Toe\nHard Mode", Alert.AlertType.INFORMATION);
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
                        String gameDifficulty = selectedDifficulty.getText();
                        System.out.println("Selected Game Type: " + selectedGameType.getText());
                        switch (selectedGameType.getText()) {
                            case "Tic Tac Toe" -> {
                                startGame(singlePlayer,gameDifficulty,false);
                                displayGame();
                                System.out.println("Tic Tac Toe was Selected");
                                System.out.println(selectedPlayerOption.getText() + " and " + selectedDifficulty.getText() + " Game was selected");
                            }
                            case "Super Tic Tac Toe" -> {
                                startGame(singlePlayer,gameDifficulty,true);
                                displayGame();
                                System.out.println("Selected STTT");
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else
                    displayPopupMessage("Missing Outputs", "Please Select all fields", Alert.AlertType.INFORMATION);
            }
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

        Rectangle rectangle = rectangle(0.95,0.95);
        Pane grid = new Pane();
        grid.maxWidthProperty().bind(root.heightProperty().multiply(0.8));
        grid.maxHeightProperty().bind(root.heightProperty().multiply(0.8));
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
        Rectangle rectangle = rectangle(3.5/9, 0.95);

        Text text = new Text("PLayer " + player);
        text.setFont(Font.font("Franklin Gothic Heavy", FontWeight.BOLD, 74));
        text.setFill(color);
        text.setTranslateY(100);
        VBox info = new VBox(text);
        info.setAlignment(Pos.TOP_CENTER);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(rectangle, info);
        return stackPane;
    }
    private static Rectangle rectangle(double widthMultiplier, double heightMultiplier){
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
            grid.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.out.println("mouse Clicked!!");
                    double x = event.getX();
                    double y = event.getY();
                    int row = (int) (y/cell);
                    int col = (int) (x/cell);
                    if(getPlayer()!=2 || !isSinglePlayer()){
                        if(turn(row, col))
                            System.out.println("registered!!...............");
                    }
                }
            });
            for (int i = 0; i<4; i++) {
                Line hLine = new Line();
                hLine.setStartY(0);
                hLine.endYProperty().bind(grid.maxHeightProperty());
                hLine.startXProperty().bind(grid.maxWidthProperty().multiply((double) i/3));
                hLine.endXProperty().bind(grid.maxWidthProperty().multiply((double) i/3));
                hLine.setStrokeWidth(5);
                grid.getChildren().add(hLine);

                Line vLine = new Line();
                vLine.startYProperty().bind(grid.maxHeightProperty().multiply((double) i/3));
                vLine.endYProperty().bind(grid.maxHeightProperty().multiply((double) i/3));
                vLine.setStartX(0);
                vLine.endXProperty().bind(grid.maxWidthProperty());
                vLine.setStrokeWidth(5);
                grid.getChildren().add(vLine);
            }
            marks = new Group();
            grid.getChildren().add(marks);
        });
    }
    private void superTicTacToe(Pane grid){
        Platform.runLater(() -> {
            grid.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
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
                }
            });
            for (int i = 0; i < 10; i++) {
                Line hLine = new Line();
                hLine.setStartY(0);
                hLine.endYProperty().bind(grid.maxHeightProperty());
                hLine.startXProperty().bind(grid.maxWidthProperty().multiply((double) i/9));
                hLine.endXProperty().bind(grid.maxWidthProperty().multiply((double) i/9));
                if (i == 0 || i == 3 || i == 6 || i == 9)
                    hLine.setStrokeWidth(8);
                else
                    hLine.setStrokeWidth(3);
                hLine.setPickOnBounds(false);
                grid.getChildren().add(hLine);

                Line vLine = new Line();
                vLine.startYProperty().bind(grid.maxHeightProperty().multiply((double) i/9));
                vLine.endYProperty().bind(grid.maxHeightProperty().multiply((double) i/9));
                vLine.setStartX(0);
                vLine.endXProperty().bind(grid.maxWidthProperty());
                if (i == 0 || i == 3 || i == 6 || i == 9)
                    vLine.setStrokeWidth(8);
                else
                    vLine.setStrokeWidth(3);
                grid.getChildren().add(vLine);
            }
            marks = new Group();
            grid.getChildren().add(marks);
        });
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
        showMark(x+=(int)(cell*0.2)-4,y+=(int)(cell*0.2)-4, false);
    }
    private static void showMark(int x, int y,  boolean dark){
        Image dIcon = new Image("D.png");
        Image darkIcon = new Image("dark.png");

        Image mark;
        mark = dark? darkIcon : dIcon ;
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

        markLine(startX,startY,endX,endY,Color.LIGHTGOLDENRODYELLOW,0);
    }
    public static void markLine(int[] superIndex,int value){
        int[][] lineIndex = dictionary.get(value);
        int startX =(int)((superIndex[1]*cell)+(lineIndex[0][1]*(cell/3))+(int)((cell/3)*0.5));
        int startY =(int)((superIndex[0]*cell)+(lineIndex[0][0]*(cell/3))+(int)((cell/3)*0.5));
        int endX = (int) ((superIndex[1]*cell)+(lineIndex[2][1]*(cell/3))+(int)((cell/3)*0.5));
        int endY = (int) ((superIndex[0]*cell)+(lineIndex[2][0]*(cell/3))+(int)((cell/3)*0.5));

        markLine(startX,startY,endX,endY,Color.LIGHTGOLDENRODYELLOW,0);

        int x =(int) (superIndex[1]*cell)+4;
        int y =(int) (superIndex[0]*cell)+4;
        showMark(x,y,true);
        if(getPlayer()==1)
            markX(superIndex[0],superIndex[1]);
        else
            markO(superIndex[0],superIndex[1]);
    }
    public static void markLine(int startX, int startY, int endX, int endY, Color color, double delay){
        Line line = new Line(startX, startY,startX, startY);
        line.setStrokeWidth(0);
        line.setStroke(color);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        marks.getChildren().add(line);
        int width;
        double time;
        if(startX-endX<(cell/4)) {
            System.out.println("ayya low");
            width = 10;
            time = 0.2;
        }
        else if(startX-endX>cell) {
            System.out.println("ayya high");
            width = 250;
            time = 2;
        }
        else {
            System.out.println("ayya med");
            width = 20;
            time = 0.5;
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
    public static void markX(int row, int col, int[] superIndex){
        int startY = (int) ((int) (row*(cell/3))+(superIndex[0]*cell)+((cell/3)*0.2));
        int startX = (int) ((int) (col*(cell/3))+(superIndex[1]*cell)+((cell/3)*0.2));
        int endY = startY + (int)((cell/3)*0.6);
        int endX = startX + (int)((cell/3)*0.6);
        markLine(startX,startY,endX,endY,Color.RED,0);
        startX = endX;
        endX = endX - (int)((cell/3)*0.6);
        markLine(startX,startY,endX,endY,Color.RED,0.2);
    }
    public static void markX(int row, int col){
        int startY = (int)((row*cell)+(cell*0.2));
        int startX = (int)((col*cell)+(cell*0.2));
        int endY = startY + (int)(cell*0.6);
        int endX = startX + (int)(cell*0.6);
        markLine(startX,startY,endX,endY,Color.RED,0);
        startX = endX;
        endX = endX - (int)(cell*0.6);
        markLine(startX,startY,endX,endY,Color.RED,0.2);
    }
    public static void markO(int row, int col, int[] superIndex){
        double y =  (row*(cell/3))+(superIndex[0]*cell)+((cell/3)*0.5);
        double x =  (col*(cell/3))+(superIndex[1]*cell)+((cell/3)*0.5);
        Circle circle = new Circle(x, y, 0);
        circle.setFill(Color.BLUE);
        Circle inner = new Circle(x,y,0);
        inner.setFill(Color.WHITE);

        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        KeyValue keyValueRadius = new KeyValue(circle.radiusProperty(), ((cell/3)*0.3));
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5), keyValueRadius);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();

        Timeline innerTimeLine = new Timeline();
        innerTimeLine.setCycleCount(1);
        KeyValue keyValueRadiusInner = new KeyValue(inner.radiusProperty(), ((cell/3)*0.2));
        KeyFrame keyFrameInner = new KeyFrame(Duration.seconds(0.5), keyValueRadiusInner);
        innerTimeLine.getKeyFrames().add(keyFrameInner);
        innerTimeLine.play();

        marks.getChildren().addAll(circle,inner);
    }
    public static void markO(int row, int col){
        double y = (row*cell)+(cell*0.5);
        double x = (col*cell)+(cell*0.5);
        Circle circle = new Circle(x,y,0);
        circle.setFill(Color.BLUE);
        Circle inner = new Circle(x,y,0);
        inner.setFill(Color.WHITE);


        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        KeyValue keyValueRadius = new KeyValue(circle.radiusProperty(), (cell*0.3));
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5), keyValueRadius);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();

        Timeline innerTimeLine = new Timeline();
        innerTimeLine.setCycleCount(1);
        KeyValue keyValueRadiusInner = new KeyValue(inner.radiusProperty(), (cell*0.2));
        KeyFrame keyFrameInner = new KeyFrame(Duration.seconds(0.5), keyValueRadiusInner);
        innerTimeLine.getKeyFrames().add(keyFrameInner);
        innerTimeLine.play();

        marks.getChildren().addAll(circle,inner);
    }
    public static void clearMarks(){
        marks.getChildren().clear();
    }
    public void clearGame(){
        clearMarks();
        marks = null;
        game = null;
        displayNavPage();
    }
    public static void Toss(){
        String text = "Select your side for the toss";
        popUp(text,"Heads","Tails",1);
    }
    private static void Toss(int choice){
        if(choice == (int)(Math.random()*2)+1){
            System.out.println("player = 1");
            Tic_Tac_Ultimate.setPlayer(1);
        }
        else{
            System.out.println("player = 2");
            Tic_Tac_Ultimate.setPlayer(2);
        }
    }
    public static void popUp(String text, String button1Text, String button2Text, int method){
        StackPane popUp = new StackPane();
        BorderPane background = new BorderPane();
        background.setCenter(popUp);
        background.setBackground(new Background(new BackgroundFill( Color.color(0,0,0,0.8), CornerRadii.EMPTY, Insets.EMPTY)));
        Rectangle box = rectangle(0.4,0.2);
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
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Button1 was pressed!");
                root.getChildren().remove(background);
                List<Node> appNodes = root.getChildren();
                for(Node node: appNodes){
                    node.setDisable(false);
                }
                if(method==1)
                    Toss(1);
                else if(method==2)
                    endGame(1);
            }
        });
        Button button2 = new Button(button2Text);
        button2.setMinSize(80,20);
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Button2 was pressed!");
                root.getChildren().remove(background);
                List<Node> appNodes = root.getChildren();
                for(Node node: appNodes){
                    node.setDisable(false);
                }
                if(method==1)
                    Toss(2);
                else if(method==2)
                    endGame(2);
            }
        });
        HBox buttons = new HBox(button1,button2);
        buttons.setAlignment(Pos.BASELINE_CENTER);
        buttons.setSpacing(25);
        vBox.getChildren().add(buttons);

        appNodes = root.getChildren();
        for(Node node: appNodes){
            node.setDisable(false);
        }
    }
}