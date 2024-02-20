package bntu.accounting.application.util.db.entityloaders;

import bntu.accounting.application.dao.impl.EmployeeDAOImpl;
import bntu.accounting.application.dao.interfaces.EmployeeDAO;
import bntu.accounting.application.models.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeesInstance implements EntityInstance {
    private static final EmployeesInstance INSTANCE = new EmployeesInstance();
//    private static final EmployeeDAO employeeDAO = new EmployeeDAOImpl();
    private List<Observer> observers = new ArrayList<>();
    private List<Employee> employees;

    private EmployeesInstance(){
    }

    public static EmployeesInstance getInstance(){
        return INSTANCE;
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
