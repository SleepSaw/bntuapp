package bntu.accounting.application.dao.interfaces;

import bntu.accounting.application.models.fordb.Employee;
import bntu.accounting.application.models.fordb.Load;
import bntu.accounting.application.models.fordb.Salary;

import java.util.List;

public interface SalaryDAO {
    List<Salary> getAllSalaries();
    Integer saveSalary(Salary salary);
    Load findSalaryById(Integer id);
    void updateSalary(Integer id, Salary updatedSalary);
    void updateSalaryOPD(Employee key, Double OPD);
    void removeSalary(Salary salary);
    void removeSalary(Integer id);
}
