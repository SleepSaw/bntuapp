package bntu.accounting.application.services;

import bntu.accounting.application.models.fordb.Employee;
import bntu.accounting.application.util.normalization.Normalizer;

import java.util.List;

public class BonusService {
    private SalaryService salaryService = new SalaryService();
    private AllowancesService allowancesService = new AllowancesService();

    public Double findBonusFund(List<Employee> employees) {
        double total = salaryService.getSalaryByLoadOfAllTeachers(employees);
        double rate = allowancesService.getProfActivityAllowance();
        return Normalizer.normalizeItem(total * rate);
    }

    public Double findBalance(List<Employee> employees,
                               Double firstRate, Double secondRate, Double thirdRate) {
        Double fund = findBonusFund(employees);
        Double balance = fund;
        for (Employee employee : employees) {
            int q = 0;
            if (employee.getWorkQualityGrade() == null) {
                q = 3;
                employee.setWorkQualityGrade(3);
            } else {
                q = employee.getWorkQualityGrade();
                double value = 0;
                switch (q) {
                    case 1:
                        value = employee.getSalary().getLoadSalary() * firstRate;
                        break;
                    case 2:
                        value = employee.getSalary().getLoadSalary() * secondRate;
                        break;
                    case 3:
                        value = employee.getSalary().getLoadSalary() * thirdRate;
                        break;
                }
                balance -= value;
                employee.getSalary().setProfActivitiesAllowance(Normalizer.normalizeItem(value));
            }
        }
        return Normalizer.roundValue(balance);
    }
}
