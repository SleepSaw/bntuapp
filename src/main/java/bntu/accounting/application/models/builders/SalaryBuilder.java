package bntu.accounting.application.models.builders;

import bntu.accounting.application.models.Salary;
import jakarta.persistence.Column;

public class SalaryBuilder {

    private Double rateSalary;
    private Double loadSalary;
    private Double expAllowance;
    private Double qualAllowance;
    private Double YSAllowance;
    private Double industryWorkAllowance;
    private Double contractAllowance;
    private Double specialActivitiesAllowance;

    public SalaryBuilder setRateSalary(Double rateSalary) {
        this.rateSalary = rateSalary;
        return this;
    }

    public SalaryBuilder setLoadSalary(Double loadSalary) {
        this.loadSalary = loadSalary;
        return this;
    }

    public SalaryBuilder setExpAllowance(Double expAllowance) {
        this.expAllowance = expAllowance;
        return this;
    }

    public SalaryBuilder setQualAllowance(Double qualAllowance) {
        this.qualAllowance = qualAllowance;
        return this;
    }

    public SalaryBuilder setYSAllowance(Double YSAllowance) {
        this.YSAllowance = YSAllowance;
        return this;
    }

    public SalaryBuilder setIndustryWorkAllowance(Double industryWorkAllowance) {
        this.industryWorkAllowance = industryWorkAllowance;
        return this;
    }

    public SalaryBuilder setContractAllowance(Double contractAllowance) {
        this.contractAllowance = contractAllowance;
        return this;
    }

    public SalaryBuilder setSpecialActivitiesAllowance(Double specialActivitiesAllowance) {
        this.specialActivitiesAllowance = specialActivitiesAllowance;
        return this;
    }

    public Salary build(){
        return new Salary(rateSalary,loadSalary,expAllowance,qualAllowance,YSAllowance,industryWorkAllowance,
                contractAllowance,specialActivitiesAllowance);
    }
}
