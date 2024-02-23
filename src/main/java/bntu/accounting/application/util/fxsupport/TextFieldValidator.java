package bntu.accounting.application.util.fxsupport;

import javafx.scene.control.TextField;

import java.util.regex.Pattern;

public class TextFieldValidator {
    public static void preventLettersInput(TextField field){
        Pattern p = Pattern.compile("(\\d+\\.?\\d{0,2})?");
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!p.matcher(newValue).matches()) field.setText(oldValue);
        });
    }

}
