package bntu.accounting.application.models;

import jakarta.persistence.*;

@Entity
@Table(name = "salary")
public class Salary {
    @Id
    @OneToOne()
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Employee employee;
    @Column(name = "total_salary")
    private Double totalSalary;
    @Column(name = "rate_salary")
    private Double rateSalary;
    @Column(name = "load_salary")
    private Double loadSalary;
    @Column(name = "exp_allowance")
    private Double expAllowance;
    @Column(name = "qual_allowance")
    private Double qualAllowance;
    @Column(name = "YS_allowance")
    private Double YSAllowance;
    @Column(name = "industry_work_allowance")
    private Double industryWorkAllowance;
    @Column(name = "contract_allowance")
    private Double contractAllowance;
    @Column(name = "special_activities_allowance")
    private Double specialActivitiesAllowance;
    public Salary() {
    }

    public Salary(Double rateSalary, Double loadSalary, Double expAllowance, Double qualAllowance,
                  Double YSAllowance, Double industryWorkAllowance, Double contractAllowance,
                  Double specialActivitiesAllowance) {
        this.rateSalary = rateSalary;
        this.loadSalary = loadSalary;
        this.expAllowance = expAllowance;
        this.qualAllowance = qualAllowance;
        this.YSAllowance = YSAllowance;
        this.industryWorkAllowance = industryWorkAllowance;
        this.contractAllowance = contractAllowance;
        this.specialActivitiesAllowance = specialActivitiesAllowance;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Double getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(Double totalSalary) {
        this.totalSalary = totalSalary;
    }

    public Double getRateSalary() {
        return rateSalary;
    }

    public void setRateSalary(Double rateSalary) {
        this.rateSalary = rateSalary;
    }

    public Double getLoadSalary() {
        return loadSalary;
    }

    public void setLoadSalary(Double loadSalary) {
        this.loadSalary = loadSalary;
    }

    public Double getExpAllowance() {
        return expAllowance;
    }

    public void setExpAllowance(Double expAllowance) {
        this.expAllowance = expAllowance;
    }

    public Double getQualAllowance() {
        return qualAllowance;
    }

    public void setQualAllowance(Double qualAllowance) {
        this.qualAllowance = qualAllowance;
    }

    public Double getYSAllowance() {
        return YSAllowance;
    }

    public void setYSAllowance(Double YSAllowance) {
        this.YSAllowance = YSAllowance;
    }

    public Double getIndustryWorkAllowance() {
        return industryWorkAllowance;
    }

    public void setIndustryWorkAllowance(Double industryWorkAllowance) {
        this.industryWorkAllowance = industryWorkAllowance;
    }

    public Double getContractAllowance() {
        return contractAllowance;
    }

    public void setContractAllowance(Double contractAllowance) {
        this.contractAllowance = contractAllowance;
    }

    public Double getSpecialActivitiesAllowance() {
        return specialActivitiesAllowance;
    }

    public void setSpecialActivitiesAllowance(Double specialActivitiesAllowance) {
        this.specialActivitiesAllowance = specialActivitiesAllowance;
    }

}
