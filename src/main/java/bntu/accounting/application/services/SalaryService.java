package bntu.accounting.application.services;

import bntu.accounting.application.dao.impl.SalaryDAOImpl;
import bntu.accounting.application.dao.interfaces.SalaryDAO;
import bntu.accounting.application.iojson.OptionsJsonHelper;
import bntu.accounting.application.models.Employee;
import bntu.accounting.application.models.Salary;
import bntu.accounting.application.models.SalaryOptions;
import bntu.accounting.application.models.builders.SalaryBuilder;
import bntu.accounting.application.util.db.entityloaders.Observer;
import bntu.accounting.application.util.db.entityloaders.SalaryInstance;
import bntu.accounting.application.util.normalization.Normalizer;

import java.util.List;

public class SalaryService implements Observer {
    private AllowancesService allowancesService = new AllowancesService();
    private OptionsJsonHelper helper = new OptionsJsonHelper();

    public SalaryService() {
        SalaryInstance.getInstance().attach(this);
        update();
    }
    private SalaryOptions options;
    private SalaryDAO salaryDAO = new SalaryDAOImpl();
    public Double calcSalaryPerRate(Integer category) {
        return allowancesService.getBaseRate() * getTariffByCategory(category);
    }
    public Double getTariffByCategory(Integer category) {
        return allowancesService.getTariffByCategory(category.toString());
    }
    public Double calcSalaryPerLoad(Double salaryPerRate, Double totalLoad) {
        return salaryPerRate * totalLoad / options.getLoadRate();
    }
    public Double caclExpAllowance(String key, Double load){
        return options.getBaseRate() * allowancesService.getExpAllowance(key) * load / options.getLoadRate();
    }
    // Расчёт общей суммы надбавок
    public Double getTotalAllowances(Employee employee) {
        double rate = calcSalaryPerRate(employee.getCategory());
        double loadRate = calcSalaryPerLoad(rate, employee.getLoad().getTotalHours());
        double exp = allowancesService.getExpAllowance(employee.getExperience()) * options.getBaseRate() / options.getLoadRate()
                * employee.getLoad().getTotalHours();
        double qual = allowancesService.getQualAllowance(employee.getQualification()) * loadRate;
        double YS = 0;
        if (employee.getYoungSpecialist()) YS = allowancesService.getYoungSpecialistAllowance() * loadRate;
        double contract = employee.getContractValue() / 100 * rate;
        double profActivity = allowancesService.getProfActivityAllowance() * loadRate;
        double industryWork = allowancesService.getWorkInIndustryAllowance() * loadRate;
        return roundValue(exp + qual + YS + profActivity + industryWork + contract);
    }
    // Расчёт общей суммы надбавок для формирования объекта ЗП
    public Double getTotalAllowances(Employee employee, SalaryBuilder builder) {
        double rate = calcSalaryPerRate(employee.getCategory());
        double loadRate = calcSalaryPerLoad(rate, employee.getLoad().getTotalHours());
        builder.setExpAllowance(allowancesService.getExpAllowance(employee.getExperience()) * options.getBaseRate() / options.getLoadRate()
                * employee.getLoad().getTotalHours());
        builder.setQualAllowance(allowancesService.getQualAllowance(employee.getQualification()) * loadRate);
        double YS = 0;
        if (employee.getYoungSpecialist()) YS = allowancesService.getYoungSpecialistAllowance() * loadRate;
        builder.setYSAllowance(YS);
        builder.setContractAllowance(employee.getContractValue() / 100 * loadRate);
        builder.setSpecialActivitiesAllowance(allowancesService.getProfActivityAllowance() * loadRate);
        builder.setIndustryWorkAllowance(allowancesService.getWorkInIndustryAllowance() * loadRate);
        return roundValue(getTotalAllowances(employee));
    }

    public Double getTotalSalary(Employee employee) {
        double rate = calcSalaryPerRate(employee.getCategory());
        double loadRate = calcSalaryPerLoad(rate, employee.getLoad().getTotalHours());
        SalaryBuilder salaryBuilder = new SalaryBuilder();
        salaryBuilder
                .setRateSalary(rate)
                .setLoadSalary(loadRate);
        double totalAllowances = getTotalAllowances(employee,salaryBuilder);
        Salary salary = salaryBuilder.build();
        double total = salary.getLoadSalary() +totalAllowances;
        salary.setEmployee(employee);
        salary.setTotalSalary(total);
        Normalizer.normalizeSalary(salary);
        salaryDAO.updateSalary(employee.getLoad().getId(),salary);
        return roundValue(loadRate + getTotalAllowances(employee));
    }
    public Double getTotalSalary(Salary salary) {
        return
                salary.getLoadSalary() +
                        salary.getExpAllowance() +
                        salary.getQualAllowance() +
                        salary.getYSAllowance() +
                        salary.getProfActivitiesAllowance() +
                        salary.getContractAllowance() +
                        salary.getIndustryWorkAllowance();
    }
    public Double getTotalSalaryOfAllTeachers(List<Employee> employees){
        double result = 0;
        for (Employee teacher: employees) {
            result += teacher.getSalary().getTotalSalary();
        }
        return roundValue(result);
    }
    public Double getSalaryPerRateOfAllTeachers(List<Employee> employees){
        double result = 0;
        for (Employee employee: employees) {
            result += employee.getSalary().getRateSalary();
        }
        return roundValue(result);
    }
    public Double getSalaryByLoadOfAllTeachers(List<Employee> employees){
        double result =0;
        for (Employee employee: employees) {
            result += employee.getSalary().getLoadSalary();
        }
        return roundValue(result);
    }
    public Double getExpAllowanceOfAllTeachers(List<Employee> teachers){
        double result = 0;
        for (Employee employee: teachers) {
            result += employee.getSalary().getExpAllowance();
        }
        return roundValue(result);
    }
    public Double getContractAllowanceOfAllTeachers(List<Employee> employees){
        double result = 0;
        for (Employee employee: employees) {
            result += employee.getSalary().getContractAllowance();
        }
        return roundValue(result);
    }
    public Double getQualAllowanceOfAllTeachers(List<Employee> employees){
        double result = 0;
        for (Employee employee: employees) {
            result += employee.getSalary().getQualAllowance();
        }
        return roundValue(result);
    }
    public Double getYoungSpecAllowanceOfAllTeachers(List<Employee> employees){
        double result = 0;
        for (Employee employee: employees) {
            result += employee.getSalary().getYSAllowance();
        }
        return roundValue(result);
    }
    public Double getProfActivitiesAllowanceOfAllTeachers(List<Employee> employees){
        double result = 0;
        for (Employee employee: employees) {
            result += employee.getSalary().getProfActivitiesAllowance();
        }
        return roundValue(result);
    }
    public Double getWorkInIndustryAllowanceOfAllTeachers(List<Employee> employees){
        double result = 0;
        for (Employee employee: employees) {
            result += employee.getSalary().getIndustryWorkAllowance();
        }
        return roundValue(result);
    }
    private double roundValue(double value) {
        double result = Math.round(value * 100);
        result = result / 100;
        return result;
    }

    @Override
    public void update() {
        options = helper.readFromJson();
    }
}
