package bntu.accounting.application.models.serializable;

import bntu.accounting.application.util.normalization.Normalizer;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RatingOptions {
    @JsonProperty("actual_first_rate")
    private Double actualFirstRate;
    @JsonProperty("actual_second_rate")
    private Double actualSecondRate;
    @JsonProperty("actual_third_rate")
    private Double actualThirdRate;
    @JsonProperty("default_first_rate")
    private Double defaultFirstRate;
    @JsonProperty("default_second_rate")
    private Double defaultSecondRate;
    @JsonProperty("default_third_rate")
    private Double defaultThirdRate;

    public Double getActualFirstRate() {
        return  Normalizer.normalizeItem(actualFirstRate);
    }

    public void setActualFirstRate(Double actualFirstRate) {
        this.actualFirstRate = actualFirstRate;
    }

    public Double getActualSecondRate() {
        return  Normalizer.normalizeItem(actualSecondRate);
    }

    public void setActualSecondRate(Double actualSecondRate) {
        this.actualSecondRate = actualSecondRate;
    }

    public Double getActualThirdRate() {
        return Normalizer.normalizeItem(actualThirdRate);
    }

    public void setActualThirdRate(Double actualThirdRate) {
        this.actualThirdRate = actualThirdRate;
    }

    public Double getDefaultFirstRate() {
        return defaultFirstRate;
    }

    public void setDefaultFirstRate(Double defaultFirstRate) {
        this.defaultFirstRate = defaultFirstRate;
    }

    public Double getDefaultSecondRate() {
        return defaultSecondRate;
    }

    public void setDefaultSecondRate(Double defaultSecondRate) {
        this.defaultSecondRate = defaultSecondRate;
    }

    public Double getDefaultThirdRate() {
        return defaultThirdRate;
    }

    public void setDefaultThirdRate(Double defaultThirdRate) {
        this.defaultThirdRate = defaultThirdRate;
    }
}
