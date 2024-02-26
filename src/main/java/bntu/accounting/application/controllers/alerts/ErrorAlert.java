package bntu.accounting.application.controllers.alerts;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public class ErrorAlert implements Alerts {
    @Override
    public Optional<ButtonType> showAlert(String header, String message, Stage stage) {
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        Alert errorAlert = new Alert(Alert.AlertType.ERROR, "", okButton);
        AlertInitializer alertInitializer = new AlertInitializer(stage, errorAlert, header, message);
        alertInitializer.init();
        return errorAlert.showAndWait();
    }
}
