package Tic_Tac_Ultimate;

import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.Serializable;

import static Tic_Tac_Ultimate.GUI.*;
import static Tic_Tac_Ultimate.GuiUtility.*;
import static Tic_Tac_Ultimate.Runner.*;

public class Quxio extends GamePane implements Serializable {
    Quxio(){
        super.initialize();
    }
    @Override
    public void makeBoard(Pane board){
        Rectangle backRectangle = makeRectangle(0.8,0.8);
        backRectangle.fillProperty().bind(foreGround);
        boxPane = new Pane();
        boxPane.setTranslateX(10);
        boxPane.setTranslateY(10);
        marks = new Group();
        arrowGroup = new Group();
        board.getChildren().addAll(backRectangle, boxPane, arrowGroup);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Rectangle box = makeBox();
                box.setTranslateX(130 * j);
                box.setTranslateY(130 * i);
                box.setId(""+i+j);
                System.out.println(box.getId());
                boxPane.getChildren().add(box);
            }
        }
        boxPane.getChildren().add(marks);
        marks.setId("marks");
    }
    private Rectangle makeBox(){
        Rectangle box = makeRectangle(0.15,0.15);
        Color original = (Color) box.getFill();
//        AtomicBoolean flag = new AtomicBoolean(true);
        box.setOnMouseEntered(mouseEvent -> {
            int[] index = getId(box);
            int row = index[0];
            int col = index[1];
            if((row ==0 || row ==4 || col ==0 || col ==4) && listen)
                box.setFill(Color.YELLOW);
        });
        box.setOnMouseExited(mouseEvent -> {
            int[] index = getId(box);
            int row = index[0];
            int col = index[1];
            if(row ==0 || row ==4 || col ==0 || col ==4) {
                if (listen)
                    box.setFill(original);
            }
        });
        box.setOnMouseClicked(mouseEvent -> {
            int[] index = getId(box);
            int row = index[0];
            int col = index[1];
            System.out.println("Received row: " + row + " and Col: " + col);
            check = checkDraw(row, col);
            if(check == 10){
                listen = true;
                box.setFill(Color.WHITE);
                arrowGroup.getChildren().clear();
                clearDraw();
            }
            else if((row ==0 || row ==4 || col ==0 || col ==4) && listen && check!=-1) {
                listen = false;
                box.setFill(Color.DARKGRAY);
                setDraw(row, col);
                showArrows(row, col);
//                draw(row, col);
            }
            else{
                blink(box, 1);
            }
//            turn(Integer.parseInt(box.getId().substring(0,1)),Integer.parseInt(box.getId().substring(1)));
            System.out.println("mouse Clicked!");
        });
        return box;
    }
    private int[] getId(Node node){
        String id = node.getId();
        int row = Integer.parseInt(id.substring(0, 1));
        int col = Integer.parseInt(id.substring(1));
        return new int[] {row, col};
    }
    public void showArrows(int row, int col) {
        if(row!=0)
            arrowGroup.getChildren().add(arrow(row,col,"down"));
        if(row!=4)
            arrowGroup.getChildren().add(arrow(row,col,"up"));
        if(col!=0)
            arrowGroup.getChildren().add(arrow(row,col,"right"));
        if(col!=4)
            arrowGroup.getChildren().add(arrow(row,col,"left"));
    }
    private Pane arrow(int row, int col, String imageName){
        double x,y;
        if(imageName.equals("up") || imageName.equals("down")){
            x = (130*col)+20;
            if(imageName.equals("up"))
                y = 130*5;

            else
                y = -90;
        }
        else {
            y = (130*row)+20;
            if(imageName.equals("right"))
                x = -90;
            else
                x = 130*5;
        }
        Pane pane = new Pane();
        pane.setTranslateX(x);
        pane.setTranslateY(y);
        Image arrowImage = new Image( "Arrows/" + imageName + ".png");
        ImageView imageView = new ImageView(arrowImage);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        pane.getChildren().add(imageView);
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
        pane.setOnMouseEntered(MouseEvent -> {
            pane.setStyle("-fx-cursor: pointer");
        });
        pane.setOnMouseClicked(MouseEvent->{
            System.out.println("Listen value BEFORE completion of move: " + listen);
            if(imageName.equalsIgnoreCase("up") || imageName.equalsIgnoreCase("down")){
                slide(row, col, y<0?0:4,col);
//                turn2(row, col, y<0?0:4,col);
            }
            else{
                slide(row, col, row, x<0?0:4);
//                turn2(row, col, row, x<0?0:4);
            }
            clearDraw();
            arrowGroup.getChildren().clear();
//            arrowPane.setPrefHeight(0);
//            arrowPane.setPrefWidth(0);
            System.out.println("Show GUI mark Done!");

        });
        return pane;
    }
    public static void slide(int row, int col, int rowI, int colI){
        int length = 0;
        if(row == rowI){//row ke liye
            int value = col;
            if(value<colI) {// left se draw right pe insert
                boxPane.lookup("#" + row + value).setId("temp");
                value++;
                for (; value <= colI; value++) {
                    Node box = boxPane.lookup("#" + row + value);
                    translateX(box.getTranslateX(),box.getTranslateX()-130,box, 1);
                    box.setId("" + row + (value - 1));
                    length++;
                }
                Rectangle rect = (Rectangle) boxPane.lookup("#temp");
                rect.setId(""+ rowI + colI);
                double size = rect.getScaleX();
                scale(0,0, rect,1);
                TranslateTransition transition = translateX(rect.getTranslateX(), rect.getTranslateX()-65, rect, 1);
                int finalLength = length;
                transition.setOnFinished(event->{
                    rect.setTranslateX(rect.getTranslateX()+(finalLength *130)+65);
                    rect.setFill(Color.WHITE);
                    ScaleTransition scaleTransition = scale(size,size, rect,0.5);
                    scaleTransition.setOnFinished(scaleEvent ->{
                        if(check==0)mark(rowI, colI);
                        turn2(row, col, rowI, colI);
                        listen = true;
                        System.out.println("Listen value after completion of move: " + listen);
                    });
                });
            }
            else {// right se draw left pe insert
                boxPane.lookup("#" + row + value).setId("temp");
                value--;
                for (; value >= colI; value--) {
                    Node box = boxPane.lookup("#" + row + value);
                    translateX(box.getTranslateX(),box.getTranslateX()+130,box, 1);
                    box.setId("" + row + (value + 1));
                    length++;
                }
                Rectangle rect = (Rectangle) boxPane.lookup("#temp");
                rect.setId(""+ rowI + colI);
                double size = rect.getScaleX();
                scale(0,0,rect,1);
                TranslateTransition transition = translateX(rect.getTranslateX(), rect.getTranslateX()+65, rect, 1);
                int finalLength = length;
                transition.setOnFinished(event->{
                    rect.setTranslateX(rect.getTranslateX()-(finalLength *130)-65);
                    rect.setFill(Color.WHITE);
                    ScaleTransition scaleTransition = scale(size,size, rect,0.5);
                    scaleTransition.setOnFinished(scaleEvent ->{
                        if(check==0)mark(rowI, colI);
                        turn2(row, col, rowI, colI);
                        listen = true;
                        System.out.println("Listen value after completion of move: " + listen);
                    });
                });
            }
        }
        else {//col ke liye
            int value = row;
            if (value < rowI) {// uphar se draw neche insert
                boxPane.lookup("#" + value + col).setId("temp");
                value++;
                for (; value <= rowI; value++) {
                    Node box = boxPane.lookup("#" + value + col);
                    translateY(box.getTranslateY(),box.getTranslateY()-130,box, 1);
                    box.setId("" + (value - 1) + col);
                    length++;
                }
                Rectangle rect = (Rectangle) boxPane.lookup("#temp");
                rect.setId(""+ rowI + colI);
                double size = rect.getScaleX();
                scale(0,0,rect,1);
                TranslateTransition transition = translateY(rect.getTranslateY(), rect.getTranslateY()-65, rect, 1);
                int finalLength = length;
                transition.setOnFinished(event->{
                    rect.setTranslateY(rect.getTranslateY()+(finalLength *130)+65);
                    rect.setFill(Color.WHITE);
                    ScaleTransition scaleTransition = scale(size,size, rect,0.5);
                    scaleTransition.setOnFinished(scaleEvent ->{
                        if(check==0)mark(rowI, colI);
                        turn2(row, col, rowI, colI);
                        listen = true;
                        System.out.println("Listen value after completion of move: " + listen);
                    });
                });
            }
            else {// neche se draw uphar insert
                boxPane.lookup("#" + value + col).setId("temp");
                value--;
                for (; value >= rowI; value--) {
                    Node box = boxPane.lookup("#" + value + col);
                    translateY(box.getTranslateY(),box.getTranslateY()+130,box, 1);
                    box.setId("" + (value + 1) + col);
                    length++;
                }
                Rectangle rect = (Rectangle) boxPane.lookup("#temp");
                rect.setId(""+ rowI + colI);
                double size = rect.getScaleX();
                scale(0,0,rect,1);
                TranslateTransition transition = translateY(rect.getTranslateY(), rect.getTranslateY()+65, rect, 1);
                int finalLength = length;
                transition.setOnFinished(event->{
                    rect.setTranslateY(rect.getTranslateY()-(finalLength *130)-65);
                    rect.setFill(Color.WHITE);
                    ScaleTransition scaleTransition = scale(size,size, rect,0.5);
                    scaleTransition.setOnFinished(scaleEvent ->{
                        if(check==0)mark(rowI, colI);;
                        turn2(row, col, rowI, colI);
                        listen = true;
                        System.out.println("Listen value after completion of move: " + listen);
                    });
                });
            }
        }
    }
}
