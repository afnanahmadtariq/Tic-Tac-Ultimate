package Tic_Tac_Ultimate;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

import static Tic_Tac_Ultimate.GuiUtility.textField;

public class LogIn extends Stage {
    private boolean[] modified = {false,false};
    LogIn(){
        init();
    }
    public void init() {
        Image icon = new Image("U.png");
        getIcons().add(icon);
        setTitle("Tic Tac Ultimate");
        setWidth(720);
        setHeight(480);
        setResizable(false);
        initModality(Modality.APPLICATION_MODAL);
        initStyle(StageStyle.UNDECORATED);

        HBox root = new HBox();
        root.setSpacing(80);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root);
        setScene(scene);

        HBox title = GuiUtility.getTitle(32, -60);

        Text information = new Text("Enter username password to login \nDon't have an account sing up below:");
        information.setTextAlignment(TextAlignment.CENTER);
        Button singUp = GuiUtility.getButton("Sign up", 95, 48);
        singUp.setOnAction(e -> new SignUp().showAndWait());


        VBox info = new VBox(title, information, singUp);
        info.setAlignment(Pos.CENTER);
        info.setSpacing(40);

        TextField username = textField("username or email",  modified, 0, getScene());
        TextField password = textField("password", modified, 1, getScene());

        Button logIn = GuiUtility.getButton("Log in", 90, 46);
        logIn.setOnAction(e ->{
            if(!modified[0] || !modified[1])
                System.out.println("Please fill all fields!! ");
            else{
                Online.authenticate(username.getText(), password.getText());
                System.out.println("Log-in Invoked.");
            }
        });

        VBox logInArea = new VBox(username,password,logIn);
        logInArea.setSpacing(40);
        logInArea.setAlignment(Pos.CENTER_RIGHT);

        root.getChildren().addAll(info,logInArea);

    }
}
