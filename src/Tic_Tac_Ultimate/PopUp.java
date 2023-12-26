package Tic_Tac_Ultimate;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class PopUp extends Alert {
    public PopUp(AlertType alertType) {
        super(alertType);
    }

    public PopUp(AlertType alertType, String s, ButtonType... buttonTypes) {
        super(alertType, s, buttonTypes);
    }
    void init(){
        setAlertType(AlertType.CONFIRMATION);
    }
}
