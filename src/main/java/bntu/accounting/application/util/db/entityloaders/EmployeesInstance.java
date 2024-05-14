package bntu.accounting.application.util.db.entityloaders;

import bntu.accounting.application.models.fordb.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeesInstance implements EntityInstance {
    private static final EmployeesInstance INSTANCE = new EmployeesInstance();
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
