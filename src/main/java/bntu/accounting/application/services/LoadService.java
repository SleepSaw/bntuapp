package bntu.accounting.application.services;

import bntu.accounting.application.controllers.exceptions.SettingIncorrectValue;
import bntu.accounting.application.dao.impl.LoadDAOImpl;
import bntu.accounting.application.dao.interfaces.LoadDAO;
import bntu.accounting.application.models.Employee;
import bntu.accounting.application.models.Load;
import bntu.accounting.application.models.Vacancy;
import bntu.accounting.application.util.db.entityloaders.EmployeesInstance;
import bntu.accounting.application.util.enums.LoadTypes;
import bntu.accounting.application.util.normalization.Normalizer;

import java.util.List;

public class LoadService {
    private LoadDAO loadDAO = new LoadDAOImpl();
    public Double findTotalHours(Load load){
        Double res = load.getAcademicHours() +
                load.getAdditionalHours() + load.getOrganizationHours();
        load.setTotalHours(res);
        return res;
    }
    public void updateLoad(Integer id,Load load){
        Normalizer.normalizeLoad(load);
        loadDAO.updateLoad(id,load);
        EmployeesInstance.getInstance().notifyObservers();
    }
    public void checkLoadCapacity(Double maxCapacity, Double newSum) throws SettingIncorrectValue {

    }
    public void checkLoadOfPerformers(LoadTypes type, Double value, List<Employee> performers) throws SettingIncorrectValue {
        double sum;
        switch (type) {
            case ACADEMIC:
                sum = 0;
                for (Employee e : performers) {
                    sum += e.getLoad().getAcademicHours();
                }
                if (value < sum) throw new SettingIncorrectValue("Неправильная уч нагрузка", "Нагрузка");
                break;
            case ORGANIZATION:
                sum = 0;
                for (Employee e : performers) {
                    sum += e.getLoad().getOrganizationHours();
                }
                if (value < sum) throw new SettingIncorrectValue("Неправильная орг нагрузка", "Нагрузка");
                break;
            case ADDITIONAL:
                sum = 0;
                for (Employee e : performers) {
                    sum += e.getLoad().getAdditionalHours();
                }
                if (value < sum) throw new SettingIncorrectValue("Неправильная доп нагрузка", "Нагрузка");
                break;
        }
    }
}
