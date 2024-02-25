package bntu.accounting.application.controllers.alerts;

import javafx.scene.Parent;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public interface Alerts {
    Optional<ButtonType> showAlert(String header, String message, Parent parent);
}
