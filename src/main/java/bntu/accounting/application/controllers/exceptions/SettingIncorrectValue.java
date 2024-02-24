package bntu.accounting.application.controllers.exceptions;

public class SettingIncorrectValue extends Exception{
    private String header;

    public SettingIncorrectValue(String message, String header) {
        super(message);
        this.header = header;
    }

    public String getHeader() {
        return header;
    }
}
