package bntu.accounting.application.util.db.entityloaders;

import bntu.accounting.application.models.fordb.Salary;

import java.util.ArrayList;
import java.util.List;

public class SalaryInstance implements EntityInstance {
    private static final SalaryInstance INSTANCE = new SalaryInstance();
    private List<Observer> observers = new ArrayList<>();
    private List<Salary> salaries;

    private SalaryInstance(){
    }

    public static SalaryInstance getInstance(){
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
