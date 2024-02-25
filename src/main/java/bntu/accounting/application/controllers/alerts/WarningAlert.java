package bntu.accounting.application.controllers.alerts;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class WarningAlert implements Alerts {
    private ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
    private ButtonType cancelButton = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
    @Override
    public Optional<ButtonType> showAlert(String header, String message, Parent parent) {
        Alert agreementAlert = new Alert(Alert.AlertType.WARNING,"Предупреждение", okButton, cancelButton);
        AlertInitializer alertInitializer = new AlertInitializer(parent, agreementAlert, header, message);
        alertInitializer.init();
        return agreementAlert.showAndWait();
    }

    public ButtonType getOkButton() {
        return okButton;
    }

    public void setOkButton(ButtonType okButton) {
        this.okButton = okButton;
    }
}
