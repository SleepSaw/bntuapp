package bntu.accounting.application.models.serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Qualification {
    @JsonProperty("б.к.к.")
    private Double step1;
    @JsonProperty("1-я к.к.")
    private Double step2;
    @JsonProperty("2-я к.к.")
    private Double step3;
    @JsonProperty("в.к.к.")
    private Double step4;
    @JsonProperty("уч.-методист")
    private Double step5;

    public Qualification() {
    }

    public Double getStep1() {
        return step1;
    }

    public void setStep1(Double step1) {
        this.step1 = step1;
    }

    public Double getStep2() {
        return step2;
    }

    public void setStep2(Double step2) {
        this.step2 = step2;
    }

    public Double getStep3() {
        return step3;
    }

    public void setStep3(Double step3) {
        this.step3 = step3;
    }

    public Double getStep4() {
        return step4;
    }

    public void setStep4(Double step4) {
        this.step4 = step4;
    }

    public Double getStep5() {
        return step5;
    }

    public void setStep5(Double step5) {
        this.step5 = step5;
    }

    @Override
    public String toString() {
        return "Qualification{" +
                "step1=" + step1 +
                ", step2=" + step2 +
                ", step3=" + step3 +
                ", step4=" + step4 +
                ", step5=" + step5 +
                '}';
    }
}
