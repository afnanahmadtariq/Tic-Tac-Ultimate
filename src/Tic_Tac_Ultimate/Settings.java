package Tic_Tac_Ultimate;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Settings extends BorderPane {
    Settings(Stage stage){
        init(stage);
    }
    private void init(Stage stage){
        setCenter(new Profile(stage));
    }
}
