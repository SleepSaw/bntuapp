package bntu.accounting.application.bonus;

public class Result {
    private Double fund;
    private Double balance;
    private Double firstRate;
    private Double secondRate;
    private Double thirdRate;

    public Result() {
    }

    public Result(Double fund, Double firstRate, Double secondRate, Double thirdRate) {
        this.fund = fund;
        this.firstRate = firstRate;
        this.secondRate = secondRate;
        this.thirdRate = thirdRate;
    }

    public Double getFund() {
        return fund;
    }

    public void setFund(Double fund) {
        this.fund = fund;
    }

    public Double getFirstRate() {
        return firstRate;
    }

    public void setFirstRate(Double firstRate) {
        this.firstRate = firstRate;
    }

    public Double getSecondRate() {
        return secondRate;
    }

    public void setSecondRate(Double secondRate) {
        this.secondRate = secondRate;
    }

    public Double getThirdRate() {
        return thirdRate;
    }

    public void setThirdRate(Double thirdRate) {
        this.thirdRate = thirdRate;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
