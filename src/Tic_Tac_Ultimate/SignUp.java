package Tic_Tac_Ultimate;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static Tic_Tac_Ultimate.GuiUtility.textField;

public class SignUp extends Stage{
        private boolean[] modified = {false,false,false,false,false};
        SignUp(){
            init();
        }
        public void init() {
            Image icon = new Image("U.png");
            getIcons().add(icon);
            setTitle("Tic Tac Ultimate");
            setWidth(720);
            setHeight(750);
            setResizable(false);
            initModality(Modality.APPLICATION_MODAL);
            initStyle(StageStyle.UNDECORATED);

            VBox root = new VBox();
            Scene scene = new Scene(root);
            setScene(scene);

            HBox title = GuiUtility.getTitle(32, 0);

            TextField nameField = textField("Enter your display Name", modified, 0, getScene());
            TextField usernameField = textField("Enter Username", modified, 1, getScene());
            TextField emailField = textField("Enter your E-mail", modified, 2, getScene());
            TextField passwordField = textField("Password (length 8-16, include alphabets, numbers and special characters)", modified, 3, getScene());
            TextField confirmField = textField("Re-enter your Password to Confirm", modified, 4, getScene());
            Text checkUsername = new Text("");
            usernameField.addEventHandler(KeyEvent.ANY, e -> {
                if (usernameField.getText().contains("@"))
                    checkUsername.setText("Username cannot contain '@'");
                else
                    checkUsername.setText("");
            });
            Text confirmEmail = new Text("");
            emailField.addEventHandler(KeyEvent.ANY, e -> {
                if (!emailField.getText().contains("@") || !emailField.getText().contains(".com"))
                    confirmEmail.setText("Enter a valid E-mail Address");
                else
                    confirmEmail.setText("");
            });
            Text checkPwd = new Text("");
            Text confirmPwd = new Text("");
            passwordField.addEventHandler(KeyEvent.KEY_RELEASED,e -> {
                if (!passwordField.getText().equals(confirmField.getText()) && modified[4])
                    confirmPwd.setText("Password does not match");
                else
                    confirmPwd.setText("");
                switch(new Password(8,16).check(passwordField.getText())){
                    case -10 -> {
                        checkPwd.setStyle("-fx-fill: red");
                        checkPwd.setText("Password should be at-least 8 and Maximum of 16 characters");
                    }
                    case -2 -> {
                        checkPwd.setStyle("-fx-fill: red");
                        checkPwd.setText("Password is very Weak");
                    }
                    case -1 -> {
                        checkPwd.setStyle("-fx-fill: orange");
                        checkPwd.setText("Password is Weak");
                    }
                    case 0 -> {
                        checkPwd.setStyle("-fx-fill: yellow");
                        checkPwd.setText("Password is Okay");
                    }
                    case 1 -> {
                        checkPwd.setStyle("-fx-fill: green");
                        checkPwd.setText("Password is Strong");
                    }
                    case 2 -> {
                        checkPwd.setStyle("-fx-fill: blue");
                        checkPwd.setText("Password is very Strong");
                    }
                }
            });
            confirmField.addEventHandler(KeyEvent.KEY_TYPED,e -> {
                if (!passwordField.getText().equals(confirmField.getText()))
                    confirmPwd.setText("Password does not match");
                else
                    confirmPwd.setText("");
            });

            Group nameGroup = group("Name",nameField);
            Group usernameGroup = group("Username",usernameField, checkUsername);
            Group emailGroup = group("Email Address",emailField, confirmEmail);
            Group pwdGroup = group("Password",passwordField, checkPwd);
            Group confirmGroup = group("Confirm Password",confirmField, confirmPwd);


            Button singUp = GuiUtility.getButton("Sign up", 95, 48);
            singUp.setOnAction(e -> {
                String name = nameField.getText();
                String username = usernameField.getText();
                String email = emailField.getText();
                String pwd = passwordField.getText();
                String confirm = confirmField.getText();
                if(!modified[0] || !modified[1] || !modified[2] || !modified[3] || !modified[4])
                    System.out.println("Please fill all fields!! ");
                else if (!pwd.equals(confirm)) {
                    System.out.println("Enter same passwords!!");
                }
                else{
                    Runner.signUp(name,username, email,pwd);
                    System.out.println("Sing-up Invoked.");
                    close();
                }
            });


            Text back = new Text("Already have an account, log in");
            back.setCursor(Cursor.HAND);
            back.setStyle("-fx-underline: true;" +
                    "-fx-fill: blue;");
            back.setOnMouseClicked(e -> this.close());


            root.getChildren().addAll(title, nameGroup, usernameGroup, emailGroup, pwdGroup, confirmGroup, singUp, back);
            root.setAlignment(Pos.CENTER);
            root.setSpacing(18);
        }
    private Group group(String label, TextField textField, Text confirmMessage){
        Group group = group(label, textField);
        confirmMessage.setStyle("-fx-fill: red");
        confirmMessage.setTranslateX(20);
        confirmMessage.setTranslateY(60);
        group.getChildren().add(confirmMessage);
        return group;
    }
    private Group group(String label, TextField textField){
        Text text = new Text(label);
        text.setTranslateX(20);
        text.setTranslateY(-5);
        text.setStyle("-fx-font-size: 14");;
        textField.setPrefWidth(450);
        return new Group(text, textField);
    }
}
