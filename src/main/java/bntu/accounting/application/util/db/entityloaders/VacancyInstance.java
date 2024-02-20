package bntu.accounting.application.util.db.entityloaders;

import java.util.ArrayList;
import java.util.List;

public class VacancyInstance implements EntityInstance {
    private static final VacancyInstance INSTANCE = new VacancyInstance();
    private List<Observer> observers = new ArrayList<>();
    private VacancyInstance(){

    }
    public static VacancyInstance getInstance(){
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
