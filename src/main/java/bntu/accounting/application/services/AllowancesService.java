package bntu.accounting.application.services;

import bntu.accounting.application.iojson.OptionsJsonHelper;
import bntu.accounting.application.models.serializable.SalaryOptions;
import bntu.accounting.application.util.db.entityloaders.Observer;
import bntu.accounting.application.util.db.entityloaders.SalaryInstance;

public class AllowancesService implements Observer {
    private OptionsJsonHelper helper = new OptionsJsonHelper();
    private SalaryOptions options;

    public AllowancesService() {
        SalaryInstance.getInstance().attach(this);
        update();
    }

    public Double getBaseRate() {
        return options.getBaseRate();
    }
    public Double getLoadRate() {
        return options.getLoadRate();
    }
    public Double getTariffByCategory(String key) {
        double result = 0;
        switch (key){
            case "7":
                result = options.getTariffs().getCategory7();
                break;
            case "8":
                result = options.getTariffs().getCategory8();
                break;
            case "9":
                result = options.getTariffs().getCategory9();
                break;
            case "10":
                result = options.getTariffs().getCategory10();
                break;
            case "11":
                result = options.getTariffs().getCategory11();
                break;
        }
        return Double.valueOf(result);
    }
    public Double getExpAllowance(String key){
        double result = 0;
        switch (key){
            case "до 5 лет":
                result = options.getExperienceAllowances().getStep1();
                break;
            case "5-10 лет":
                result = options.getExperienceAllowances().getStep2();
                break;
            case "10-15 лет":
                result = options.getExperienceAllowances().getStep3();
                break;
            case "св. 15 лет":
                result = options.getExperienceAllowances().getStep4();
                break;
            // Самый дебильный кастыль из всех, что я делал
            default:
                result = options.getExperienceAllowances().getStep4();
                break;
        }
            return Double.valueOf(result);
    }
    public Double getQualAllowance(String key){
        double result = 0;

        switch (key){
            case "б.к.к.":
                result = options.getQualificationAllowances().getStep1();
                break;
            case "1-я к.к.":
                result = options.getQualificationAllowances().getStep2();
                break;
            case "2-я к.к.":
                result = options.getQualificationAllowances().getStep3();
                break;
            case "в.к.к.":
                result = options.getQualificationAllowances().getStep4();
                break;
            case "уч.-методист":
                result = options.getQualificationAllowances().getStep5();
                break;
        }
        return Double.valueOf(result);
    }
    public Double getWorkInIndustryAllowance(){
        return options.getWorkInIndustryAllowance();
    }
    public Double getYoungSpecialistAllowance(){
        return options.getYoungSpecialistAllowance();
    }
    public Double getProfActivityAllowance(){
        return options.getProfActivityAllowance();
    }

    @Override
    public void update() {
        options = helper.readFromJson();
    }
}
