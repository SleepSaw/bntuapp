package bntu.accounting.application.util.db.entityloaders;

public interface EntityInstance {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers();
}
