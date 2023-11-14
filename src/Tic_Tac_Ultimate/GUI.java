package Tic_Tac_Ultimate;

import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
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

import static Tic_Tac_Ultimate.Board.dictionary;
import static Tic_Tac_Ultimate.Tic_Tac_Ultimate.*;
//import static Tic_Tac_Ultimate.Tic_Tac_Ultimate.setGameOptions;

public class GUI extends Application  {
    private BorderPane game ;
    private static Pane marks;
    private static StackPane root;
    private static double cell;
    private static Color backGround = Color.web("#f2f2f2");
    private static Color midGround = Color.web("#fff");
    private ToggleGroup difficultyToggleGroup;
    private ToggleGroup playerToggleGroup;
    private ToggleGroup gameToggleGroup;

    @Override
    public void start(Stage stage){
        Image icon = new Image("U.png");
        stage.getIcons().add(icon);
        stage.setTitle("Tic Tac Ultimate");
        stage.setMinWidth(1280);
        stage.setMinHeight(720);
        stage.setResizable(false);
        stage.setFullScreen(true);
        stage.setX(0);
        stage.setY(0);
        root = new StackPane();
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
        Rectangle plane = new Rectangle();
        StackPane transitionComplex = new StackPane(background, ultimate, plane, toe);
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
        ultimate.setTranslateY(-ultimate.getLayoutBounds().getHeight()/2);

        plane.setWidth(ultimate.getLayoutBounds().getWidth()*1.2);
        plane.setHeight(ultimate.getLayoutBounds().getWidth());
        plane.setFill(Color.WHITE);

        background.setWidth(ultimate.getLayoutBounds().getWidth());
        background.setHeight(ultimate.getLayoutBounds().getHeight());
        background.setFill(Color.RED);
        background.setTranslateY(-ultimate.getLayoutBounds().getHeight()/2);

        Region empty = new Region();
        TranslateTransition emptyTransition = new TranslateTransition(Duration.seconds(2), empty);
        emptyTransition.play();
        emptyTransition.setOnFinished(event -> {
            System.out.println("Transition completed");
            transitionComplex.getChildren().setAll(plane, toe, background, ultimate);
            rotate(0,90, toe);
            translate(ultimate.getLayoutBounds().getHeight()/2, toe);

            rotate(-90,0, ultimate);
            translate(0, ultimate);

            rotate(-90, 0, background);
            translate(0, background).setOnFinished(event2 -> {
                System.out.println("Transition2 completed");
//            displayGameSelection();
            });
        });


    }
    private TranslateTransition translate(double magnitude, Node node){
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), node);
        translateTransition.setToY(magnitude);
        translateTransition.setCycleCount(1);
        translateTransition.play();
        return translateTransition;
    }
    private RotateTransition rotate(int fromAngle, int toAngle, Node node){
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(2), node);
        rotateTransition.setAxis(Rotate.X_AXIS);
        rotateTransition.setFromAngle(fromAngle);
        rotateTransition.setToAngle(toAngle);
        rotateTransition.setCycleCount(1);
        rotateTransition.play();
        return rotateTransition;
    }
    private void showNavPage() {
        VBox menuPanel = new VBox();
        Text title = new Text("Tic Tac Ultimate");
        Font customFont = Font.font("Times New Roman", FontWeight.BOLD, 50);
        title.setFont(customFont);

        menuPanel.getChildren().add(title);
        menuPanel.setAlignment(Pos.TOP_CENTER);
        root.getChildren().add(menuPanel);

        Button startButton = new Button("Start");
        startButton.setMinSize(150,50);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    System.out.println("Start button was Pressed!");
                    displayGameSelection();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Button optionButton = new Button("Options");
        optionButton.setMinSize(150,50);
        optionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Options button was pressed!");
            }
        });

        Button exitButton = new Button("Exit Game");
        exitButton.setMinSize(150,50);
        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Exit button was pressed!");
            }
        });
        menuPanel.getChildren().add(startButton);
        menuPanel.getChildren().add(optionButton);
        menuPanel.getChildren().add(exitButton);
    }
    private void displayGameSelection() {
        root.getChildren().clear();
        VBox startWindow = new VBox();
        startWindow.setAlignment(Pos.CENTER);
        root.getChildren().add(startWindow);


        Rectangle simple = new Rectangle();
        simple.setFill(Color.LIGHTBLUE);
        simple.widthProperty().bind(root.widthProperty().divide(2));
        Pane ticTacToe = new Pane();
        ticTacToe.prefWidthProperty().bind(root.widthProperty().divide(2));
        simple.setOnMouseEntered(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {

             }
        });
        simple.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });


//        //Set the width of the content pane to half the width of the stage
//        stage.widthProperty().addListener((obs, oldWidth, newWidth) -> {
//            double halfWidth = newWidth.doubleValue() / 2.0;
//            simple.setWidth(halfWidth);
//        });


        HBox gameType = new HBox();

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
            private Pane root;
            private String gameDifficulty;
            private boolean singlePlayer;
            private ToggleGroup gameToggleGroup;

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Play Game button was pressed!");

                RadioButton selectedGameType = (RadioButton) gameToggleGroup.getSelectedToggle();
                RadioButton selectedPlayerOption = (RadioButton) playerToggleGroup.getSelectedToggle();
                RadioButton selectedDifficulty = (RadioButton) difficultyToggleGroup.getSelectedToggle();

                switch (selectedPlayerOption.getText()){
                    case "Single Player" -> singlePlayer = true;
                    case "Double Player" -> singlePlayer = false;
                }

                try {
                    if (selectedGameType != null) {
                        gameDifficulty = selectedDifficulty.getText();
                        System.out.println("Selected Game Type: " + selectedGameType.getText());
                        switch (selectedGameType.getText()) {
                            case "Tic Tac Toe" -> {
                                if (selectedPlayerOption != null && selectedDifficulty != null) {
//                                    setGameOptions();
                                    System.out.println("Tic Tac Toe was Selected");
                                    System.out.println(selectedPlayerOption.getText() + " and " + selectedDifficulty.getText() + "Game was selected");
                                    displayGameView();
                                    Toss();
                                } else if (selectedPlayerOption != null && selectedDifficulty != null) {
                                    System.out.println("Single Player and Easy Game was not selected");
                                }
                            }
                            case "Super Tic Tac Toe" -> System.out.println("Selected STTT");
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        // Assign toggle groups to radio buttons
        assignToggleGroup(gameType, gameToggleGroup);
        assignToggleGroup(playerOptions, playerToggleGroup);
        assignToggleGroup(gameDiffPanel, difficultyToggleGroup);
        playGame.setAlignment(Pos.CENTER);

        startWindow.getChildren().addAll(gameType, playerOptions, gameDiffPanel, playGame);
    }
    private void assignToggleGroup(HBox hbox, ToggleGroup toggleGroup) {
        hbox.getChildren().forEach(node -> {
            if (node instanceof RadioButton) {
                ((RadioButton) node).setToggleGroup(toggleGroup);
            }
        });
    }

    private void displayGameView(){
        root.getChildren().clear();
        game = new BorderPane();
        StackPane board = new StackPane();
        game.setCenter(board);

        Rectangle rectangle = new Rectangle();
        rectangle.setFill(midGround);
        rectangle.widthProperty().bind(root.heightProperty().multiply(0.95));
        rectangle.heightProperty().bind(root.heightProperty().multiply(0.95));
        rectangle.setArcWidth(50);
        rectangle.setArcHeight(50);

        Pane grid = new Pane();
        grid.maxWidthProperty().bind(root.heightProperty().multiply(0.8));
        grid.maxHeightProperty().bind(root.heightProperty().multiply(0.8));
        Platform.runLater(()->{
            cell = grid.getHeight()/3;
            System.out.println("Cell size: " + cell);
        });

        board.getChildren().add(rectangle);
        board.getChildren().add(grid);

        superTicTacToe(grid);
        Toss();
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
            marks = new Pane();
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
            marks = new Pane();
            grid.getChildren().add(marks);
        });
    }
    private void displayOptions(){
        root.getChildren().clear();

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
        int y = (int) ((int) (row*(cell/3))+(superIndex[0]*cell)+((cell/3)*0.2));
        int x = (int) ((int) (col*(cell/3))+(superIndex[1]*cell)+((cell/3)*0.2));
        showMark(x,y,true,true, false);
    }
    public static void showTurn(int row, int col){
        int y = (int)((row*cell)+(cell*0.2));
        int x = (int)((col*cell)+(cell*0.2));
        showMark(x,y,true,false,false);
    }
    public static void markDraw(int[] superIndex){
        int x = (int)(superIndex[1]*cell);
        int y = (int)(superIndex[0]*cell);
        showMark(x,y,false,false,true);
    }
    private static void showMark(int x, int y, boolean win, boolean small, boolean dark){
        Image xIcon = new Image("x.png");
        Image p1Icon = xIcon;
        Image oIcon = new Image("o.png");
        Image p2Icon = oIcon;
        Image dIcon = new Image("D.png");
        Image darkIcon = new Image("dark.png");

        Image mark;
        mark = !dark? win? getPlayer()==1? p1Icon : p2Icon : dIcon : darkIcon;
        System.out.println("Mark set acc to player!");
        int width, height;
        width = height = !dark? small? (int)((cell/3)*0.6) : (int)(cell*0.6) : (int)cell;

        ImageView imageView = new ImageView(mark);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        marks.getChildren().add(imageView);
        System.out.println("Marked on grid!");
        if(dark)
            showMark(x+=(int)(cell*0.2),y+=(int)(cell*0.2),win,small, false);
    }
    public static void markLine(int value){
        int[][] lineIndex = dictionary.get(value);
        int startX =(int)((lineIndex[0][1]*cell)+(int)(cell*0.5));
        int startY =(int)((lineIndex[0][0]*cell)+(int)(cell*0.5));
        int endX = (int) ((lineIndex[2][1]*cell)+(int)(cell*0.5));
        int endY = (int) ((lineIndex[2][0]*cell)+(int)(cell*0.5));

        markLine(startX,startY,endX,endY);
    }
    public static void markLine(int[] superIndex,int value){
        int[][] lineIndex = dictionary.get(value);
        int startX =(int)((superIndex[1]*cell)+(lineIndex[0][1]*(cell/3))+(int)((cell/3)*0.5));
        int startY =(int)((superIndex[0]*cell)+(lineIndex[0][0]*(cell/3))+(int)((cell/3)*0.5));
        int endX = (int) ((superIndex[1]*cell)+(lineIndex[2][1]*(cell/3))+(int)((cell/3)*0.5));
        int endY = (int) ((superIndex[0]*cell)+(lineIndex[2][0]*(cell/3))+(int)((cell/3)*0.5));

        markLine(startX,startY,endX,endY);

        int x =(int) (superIndex[1]*cell);
        int y =(int) (superIndex[0]*cell);
        showMark(x,y,true,false,true);
    }
    public static void markLine(int startX, int startY, int endX, int endY){
        Line line = new Line(startX, startY, endX, endY);
        line.setStroke(Color.GREEN);
        line.setStrokeWidth(10);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        marks.getChildren().add(line);

        Timeline timeline = new Timeline();
        KeyFrame startFrame = new KeyFrame(Duration.ZERO,
                new KeyValue(line.endXProperty(), startX),
                new KeyValue(line.endYProperty(), startY));
        KeyFrame endFrame = new KeyFrame(Duration.seconds(0.5),
                new KeyValue(line.endXProperty(), endX),
                new KeyValue(line.endYProperty(), endY));
        timeline.getKeyFrames().addAll(startFrame, endFrame);
        timeline.play();
    }
    public static void clearMarks(){
        marks.getChildren().clear();
    }
    public static void Toss(){
        String text = "Select your side for the toss";
        int choice = popUp(text,"Heads","Tails",1);
        if(choice == (int)(Math.random()*2)+1 && 1!=1){
            System.out.println("player = 1");
            Tic_Tac_Ultimate.setPlayer(1);
        }
        else{
            System.out.println("player = 2");
            Tic_Tac_Ultimate.setPlayer(2);
        }
    }
    private static void shakeStage(Stage stage) {
        final double originalX = stage.getX();
        final double originalY = stage.getY();
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
                            stage.setX(originalX + dist);
                            stage.setY(originalY + dist);
                        }
                        else {
                            stage.setX(originalX);
                            stage.setY(originalY);
                            timeline[0].stop();
                        }
                    }
                }
        ));
        timeline[0].setCycleCount(Timeline.INDEFINITE);
        timeline[0].play();
    }
    //Another method{
//    private void shakeStage(Stage stage) {
//        final int shakeDistance = 10;
//        final int numShakes = 10;
//        final double shakeDuration = 50; // milliseconds
//
//        TranslateTransition tt = new TranslateTransition(Duration.millis(shakeDuration), stage.getScene().getRoot());
//        tt.setCycleCount(2 * numShakes);
//        tt.setAutoReverse(true);
//        tt.setByX(shakeDistance);
//        tt.setByY(shakeDistance);
//
//        tt.playFromStart();
//    }
    public static int popUp(String text, String button1Text, String button2Text, int design) {
        final int[] choice = new int[1];
        Stage popUp = new Stage(StageStyle.UNDECORATED);
        popUp.setWidth(500);
        popUp.setHeight(200);
        popUp.setResizable(false);
        popUp.initModality(Modality.APPLICATION_MODAL);
        //setDisable(true);
        popUp.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                shakeStage(popUp);
            }
        });
        popUp.setOnCloseRequest(event -> {
            event.consume();
            shakeStage(popUp);
        });
        VBox root = new VBox(new Text(text));
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 200, 100, midGround);


        Button button1 = new Button(button1Text);
        button1.setMinSize(80,20);
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Button1 was pressed!");
                choice[0] = 1;
                popUp.close();
            }
        });
        Button button2 = new Button(button2Text);
        button2.setMinSize(80,20);
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Button2 was pressed!");
                choice[0] = 2;
            }
        });
        HBox buttons = new HBox();
        buttons.setAlignment(Pos.BASELINE_CENTER);
        buttons.getChildren().add(button1);
        buttons.getChildren().add(button2);
        root.getChildren().add(buttons);

        popUp.setScene(scene);
        popUp.showAndWait();
        for(;choice[0]==0;);
        return choice[0];
    }
}
