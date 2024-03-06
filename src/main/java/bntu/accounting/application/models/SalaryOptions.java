package bntu.accounting.application.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SalaryOptions {
    @JsonProperty("base_rate")
    private Double baseRate;
    @JsonProperty("load_rate")
    private Double loadRate;
    @JsonProperty("young_specialist_allowance")
    private Double youngSpecialistAllowance;
    @JsonProperty("prof_activity_allowance")
    private Double profActivityAllowance;
    @JsonProperty("work_in_industry_allowance")
    private Double workInIndustryAllowance;

    @JsonProperty("tariffs")
    private Tariff tariffs = new Tariff();
    @JsonProperty("experience")
    private Experience experienceAllowances = new Experience();
    @JsonProperty("qualification")
    private Qualification qualificationAllowances = new Qualification();

    public Double getBaseRate() {
        return baseRate;
    }

    public void setBaseRate(Double baseRate) {
        this.baseRate = baseRate;
    }

    public Double getLoadRate() {
        return loadRate;
    }

    public void setLoadRate(Double loadRate) {
        this.loadRate = loadRate;
    }

    public Double getYoungSpecialistAllowance() {
        return youngSpecialistAllowance;
    }

    public void setYoungSpecialistAllowance(Double youngSpecialistAllowance) {
        this.youngSpecialistAllowance = youngSpecialistAllowance;
    }

    public Double getProfActivityAllowance() {
        return profActivityAllowance;
    }

    public void setProfActivityAllowance(Double profActivityAllowance) {
        this.profActivityAllowance = profActivityAllowance;
    }

    public Double getWorkInIndustryAllowance() {
        return workInIndustryAllowance;
    }

    public void setWorkInIndustryAllowance(Double workInIndustryAllowance) {
        this.workInIndustryAllowance = workInIndustryAllowance;
    }

    public Tariff getTariffs() {
        return tariffs;
    }

    public void setTariffs(Tariff tariffs) {
        this.tariffs = tariffs;
    }

    public Experience getExperienceAllowances() {
        return experienceAllowances;
    }

    public void setExperienceAllowances(Experience experienceAllowances) {
        this.experienceAllowances = experienceAllowances;
    }

    public Qualification getQualificationAllowances() {
        return qualificationAllowances;
    }

    public void setQualificationAllowances(Qualification qualificationAllowances) {
        this.qualificationAllowances = qualificationAllowances;
    }

    @Override
    public String toString() {
        return "SalaryOptions{" +
                "baseRate=" + baseRate +
                ", loadRate=" + loadRate +
                ", youngSpecialistAllowance=" + youngSpecialistAllowance +
                ", profActivityAllowance=" + profActivityAllowance +
                ", workInIndustryAllowance=" + workInIndustryAllowance +
                ", tariffs=" + tariffs +
                ", experienceAllowances=" + experienceAllowances +
                ", qualificationAllowances=" + qualificationAllowances +
                '}';
    }
}
