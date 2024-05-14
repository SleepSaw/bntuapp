package bntu.accounting.application.bonus;

import bntu.accounting.application.models.fordb.Employee;

public class AssessmentResult {
    private Double defaultSumOfScores;
    private Double weightSumOfScores;
    private Double pieceOfTheBalance;
    private Double bonusValue;

    public AssessmentResult() {
    }

    public AssessmentResult(Double defaultSumOfScores, Double weightSumOfScores) {
        this.defaultSumOfScores = defaultSumOfScores;
        this.weightSumOfScores = weightSumOfScores;
    }

    public AssessmentResult(Double defaultSumOfScores, Double weightSumOfScores, Double pieceOfTheBalance, Double bonusValue) {
        this.defaultSumOfScores = defaultSumOfScores;
        this.weightSumOfScores = weightSumOfScores;
        this.pieceOfTheBalance = pieceOfTheBalance;
        this.bonusValue = bonusValue;
    }

    public Double getDefaultSumOfScores() {
        return defaultSumOfScores;
    }

    public void setDefaultSumOfScores(Double defaultSumOfScores) {
        this.defaultSumOfScores = defaultSumOfScores;
    }

    public Double getWeightSumOfScores() {
        return weightSumOfScores;
    }

    public void setWeightSumOfScores(Double weightSumOfScores) {
        this.weightSumOfScores = weightSumOfScores;
    }

    public Double getPieceOfTheBalance() {
        return pieceOfTheBalance;
    }

    public void setPieceOfTheBalance(Double pieceOfTheBalance) {
        this.pieceOfTheBalance = pieceOfTheBalance;
    }

    public Double getBonusValue() {
        return bonusValue;
    }

    public void setBonusValue(Double bonusValue) {
        this.bonusValue = bonusValue;
    }
}
