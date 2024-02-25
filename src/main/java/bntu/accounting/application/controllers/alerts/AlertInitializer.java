package bntu.accounting.application.controllers.alerts;

import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertInitializer {
    private Alert alert;
    private String header;
    private String message;
    private Parent parent;

    public AlertInitializer(Parent parent, Alert alert, String header, String message) {
        this.alert = alert;
        this.header = header;
        this.message = message;
        this.parent = parent;
    }
    public void init(){
        Stage stage = (Stage) parent.getScene().getWindow();
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);
        alert.getDialogPane().setContentText(message);
        alert.getDialogPane().setHeaderText(header);
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }
}
