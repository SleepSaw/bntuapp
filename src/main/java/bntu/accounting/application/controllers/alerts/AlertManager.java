package bntu.accounting.application.controllers.alerts;

import javafx.scene.control.ButtonType;

import java.util.Optional;

public interface AlertManager {
    Optional<ButtonType> showInformationAlert(String header, String message);
    Optional<ButtonType> showWarningAlert(String header, String message);
    Optional<ButtonType> showErrorAlert(String header, String message);
    Optional<ButtonType> showConfirmingAlert(String header, String message);

}
