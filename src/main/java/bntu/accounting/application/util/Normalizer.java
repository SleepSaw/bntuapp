package bntu.accounting.application.util;

import bntu.accounting.application.models.Salary;

public class Normalizer {
    public static void normalizeSalary(Salary salary){
        salary.setRateSalary(normalizeItem(salary.getRateSalary()));
        salary.setLoadSalary(normalizeItem(salary.getLoadSalary()));
        salary.setTotalSalary(normalizeItem(salary.getTotalSalary()));
        salary.setExpAllowance(normalizeItem(salary.getExpAllowance()));
        salary.setQualAllowance(normalizeItem(salary.getQualAllowance()));
        salary.setContractAllowance(normalizeItem(salary.getContractAllowance()));
        salary.setSpecialActivitiesAllowance(normalizeItem(salary.getSpecialActivitiesAllowance()));
        salary.setYSAllowance(normalizeItem(salary.getYSAllowance()));
        salary.setIndustryWorkAllowance(normalizeItem(salary.getIndustryWorkAllowance()));
    }
    private static Double roundValue(double value) {
        double result = Math.round(value * 100);
        result = result / 100;
        return result;
    }
    private static Double offNegative(Double value){
        if (value < 0) return value * -1;
        return value;
    }
    private static Double normalizeItem(Double value){
        Double result;
        result = offNegative(value);
        result = roundValue(result);
        return result;
    }
}
