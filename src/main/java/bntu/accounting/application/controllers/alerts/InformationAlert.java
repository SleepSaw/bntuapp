package bntu.accounting.application.controllers.alerts;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class InformationAlert implements Alerts {
    @Override
    public Optional<ButtonType> showAlert(String header, String message, Parent parent) {
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        Alert successesAlert = new Alert(Alert.AlertType.INFORMATION, "", okButton);
        AlertInitializer alertInitializer = new AlertInitializer(parent, successesAlert, header, message);
        alertInitializer.init();
        return successesAlert.showAndWait();
    }
}
