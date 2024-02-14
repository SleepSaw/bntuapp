package bntu.accounting.application.services;

import bntu.accounting.application.controllers.exceptions.EmptyResultListException;
import bntu.accounting.application.dao.impl.EmployeeDAOImpl;
import bntu.accounting.application.dao.impl.LoadDAOImpl;
import bntu.accounting.application.dao.impl.SalaryDAOImpl;
import bntu.accounting.application.dao.interfaces.EmployeeDAO;
import bntu.accounting.application.dao.interfaces.LoadDAO;
import bntu.accounting.application.dao.interfaces.SalaryDAO;
import bntu.accounting.application.models.Employee;
import bntu.accounting.application.models.Load;
import bntu.accounting.application.models.Salary;

import java.util.List;
import java.util.Optional;

public class EmployeeService {
    private EmployeeDAO employeeDAO = new EmployeeDAOImpl();
    private LoadDAO loadDAO = new LoadDAOImpl();
    private SalaryDAO salaryDAO = new SalaryDAOImpl();

    public void updateEmployee(Employee employee, Employee updatedEmployee){
        int id = employee.getLoad().getId(); // Получаем id объекта
        // Привзка связанных сущностей к новому объекту
        updatedEmployee.setLoad(employee.getLoad());
        updatedEmployee.setSalary(employee.getSalary());
        // + VACANCY
        employeeDAO.updateEmployee(id,updatedEmployee);
    }
    public void saveEmployee(Employee employee){
        employeeDAO.saveEmployee(employee);
    }
    public void removeEmployee(Employee employee){
        employeeDAO.removeEmployee(employee);
    }
    public List<Employee> getAllEmployees() throws NullPointerException {
        return  employeeDAO.getAllEmployees();
    }

}
