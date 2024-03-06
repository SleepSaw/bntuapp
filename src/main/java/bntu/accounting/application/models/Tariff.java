package bntu.accounting.application.models;

import bntu.accounting.application.iojson.FileLoader;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class Tariff {
    @JsonProperty("7")
    private Double category7;
    @JsonProperty("8")
    private Double category8;
    @JsonProperty("9")
    private Double category9;
    @JsonProperty("10")
    private Double category10;
    @JsonProperty("11")
    private Double category11;

    public Tariff() {
    }

    public Double getCategory7() {
        return category7;
    }

    public void setCategory7(Double category7) {
        this.category7 = category7;
    }

    public Double getCategory8() {
        return category8;
    }

    public void setCategory8(Double category8) {
        this.category8 = category8;
    }

    public Double getCategory9() {
        return category9;
    }

    public void setCategory9(Double category9) {
        this.category9 = category9;
    }

    public Double getCategory10() {
        return category10;
    }

    public void setCategory10(Double category10) {
        this.category10 = category10;
    }

    public Double getCategory11() {
        return category11;
    }

    public void setCategory11(Double category11) {
        this.category11 = category11;
    }

    @Override
    public String toString() {
        return "Tariff{" +
                "category7=" + category7 +
                ", category8=" + category8 +
                ", category9=" + category9 +
                ", category10=" + category10 +
                ", category11=" + category11 +
                '}';
    }
}
