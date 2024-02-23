package bntu.accounting.application.dao.interfaces;

import bntu.accounting.application.models.Employee;
import bntu.accounting.application.models.Load;

import java.util.List;

public interface EmployeeDAO {

    List<Employee> getAllEmployees();

    Employee findById(Integer id);

    void removeEmployee(Employee employee);

    void updateEmployee(Integer id, Employee updatedEmployee);

    /**
     * @param employee объект работника
     * @return идентификатор работника в базе данных
     * */
    Integer saveEmployee(Employee employee);

    Load getEmployeeLoad(Employee employee);

    Load getEmployeeLoadById(Integer id);
    Integer savePerformer(Employee employee);


}
