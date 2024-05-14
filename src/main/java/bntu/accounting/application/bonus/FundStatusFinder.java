package bntu.accounting.application.bonus;

import bntu.accounting.application.models.fordb.Employee;
import bntu.accounting.application.util.normalization.Normalizer;

import java.util.List;

public class FundStatusFinder
{
    public static Double findBalance(Result result, List<Employee> employees) {
        Double balance = result.getFund();
        for (Employee employee : employees) {
            int q = employee.getWorkQualityGrade();
            double value = 0;
            switch (q) {
                case 1:
                    value = employee.getSalary().getLoadSalary() * result.getFirstRate();
                    break;
                case 2:
                    value = employee.getSalary().getLoadSalary() * result.getSecondRate();
                    break;
                case 3:
                    value = employee.getSalary().getLoadSalary() * result.getThirdRate();
                    break;
            }
            balance -= value;
            employee.getSalary().setProfActivitiesAllowance(value);
        }
        return Normalizer.roundValue(balance);
    }
}
