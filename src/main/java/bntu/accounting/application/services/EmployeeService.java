package bntu.accounting.application.services;

import bntu.accounting.application.dao.impl.EmployeeDAOImpl;
import bntu.accounting.application.dao.impl.LoadDAOImpl;
import bntu.accounting.application.dao.impl.SalaryDAOImpl;
import bntu.accounting.application.dao.interfaces.EmployeeDAO;
import bntu.accounting.application.dao.interfaces.LoadDAO;
import bntu.accounting.application.dao.interfaces.SalaryDAO;
import bntu.accounting.application.models.Employee;
import bntu.accounting.application.models.Vacancy;
import bntu.accounting.application.util.db.entityloaders.EmployeesInstance;
import org.hibernate.HibernateException;

import java.util.List;

public class EmployeeService {
    private EmployeeDAO employeeDAO = new EmployeeDAOImpl();
    private LoadDAO loadDAO = new LoadDAOImpl();
    private SalaryDAO salaryDAO = new SalaryDAOImpl();
    private VacancyService vacancyService = new VacancyService();

    public void updateEmployee(Employee employee, Employee updatedEmployee) {
        int id = employee.getLoad().getId(); // Получаем id объекта
        // Привзка связанных сущностей к новому объекту
        updatedEmployee.setLoad(employee.getLoad());
        updatedEmployee.setSalary(employee.getSalary());
        // + VACANCY
        employeeDAO.updateEmployee(id, updatedEmployee);
        EmployeesInstance.getInstance().notifyObservers();
    }

    public void saveEmployee(Employee employee) {
        employeeDAO.saveEmployee(employee);
        EmployeesInstance.getInstance().notifyObservers();
    }

    public void removeEmployee(Employee employee) {
        Vacancy vacancy = employee.getVacancy();
        if (vacancy != null) {
            vacancyService.removePerformer(vacancy, employee);
        } else employeeDAO.removeEmployee(employee);
        EmployeesInstance.getInstance().notifyObservers();
    }

    public List<Employee> getAllEmployees() throws HibernateException {
        return employeeDAO.getAllEmployees();
    }

}
