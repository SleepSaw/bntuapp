package bntu.accounting.application.util.db;

import bntu.accounting.application.dao.impl.EmployeeDAOImpl;
import bntu.accounting.application.dao.interfaces.EmployeeDAO;
import bntu.accounting.application.models.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeesLoader {
    private static final EmployeesLoader INSTANCE = new EmployeesLoader();
    private static final EmployeeDAO employeeDAO = new EmployeeDAOImpl();
    private List<Observer> observers = new ArrayList<>();
    private List<Employee> employees;

    private EmployeesLoader(){
    }

    public static EmployeesLoader getInstance(){
        return INSTANCE;
    }

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void detach(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

}
