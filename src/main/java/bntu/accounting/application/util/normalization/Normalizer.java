package bntu.accounting.application.util.normalization;

import bntu.accounting.application.models.Load;
import bntu.accounting.application.models.Salary;

public class Normalizer extends RounderValues {
    public static void normalizeSalary(Salary salary){
        salary.setRateSalary(normalizeItem(salary.getRateSalary()));
        salary.setLoadSalary(normalizeItem(salary.getLoadSalary()));
        salary.setTotalSalary(normalizeItem(salary.getTotalSalary()));
        salary.setExpAllowance(normalizeItem(salary.getExpAllowance()));
        salary.setQualAllowance(normalizeItem(salary.getQualAllowance()));
        salary.setContractAllowance(normalizeItem(salary.getContractAllowance()));
        salary.setProfActivitiesAllowance(normalizeItem(salary.getProfActivitiesAllowance()));
        salary.setYSAllowance(normalizeItem(salary.getYSAllowance()));
        salary.setIndustryWorkAllowance(normalizeItem(salary.getIndustryWorkAllowance()));
    }
    public static void normalizeLoad(Load load){
        load.setAcademicHours(normalizeItem(load.getAcademicHours()));
        load.setOrganizationHours(normalizeItem(load.getOrganizationHours()));
        load.setAdditionalHours(normalizeItem(load.getAdditionalHours()));
        load.setTotalHours(normalizeItem(load.getTotalHours()));
    }
}
