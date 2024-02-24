package bntu.accounting.application.controllers.exceptions;

public class SettingEmptyValue extends Exception{
    private String header;

    public SettingEmptyValue(String message, String header) {
        super(message);
        this.header = header;
    }

    public String getHeader() {
        return header;
    }
}
