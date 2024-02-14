package bntu.accounting.application.dao.interfaces;

import bntu.accounting.application.models.Load;
import bntu.accounting.application.models.Salary;

import java.util.List;

public interface SalaryDAO {
    List<Salary> getAllSalaries();
    Integer saveSalary(Salary salary);
    Load findSalaryById(Integer id);
    void updateSalary(Integer id, Salary updatedSalary);
    void removeSalary(Salary salary);
    void removeSalary(Integer id);
}
