package bntu.accounting.application.services;

import bntu.accounting.application.controllers.exceptions.SettingIncorrectValue;
import bntu.accounting.application.dao.impl.LoadDAOImpl;
import bntu.accounting.application.dao.interfaces.LoadDAO;
import bntu.accounting.application.models.fordb.Employee;
import bntu.accounting.application.models.fordb.Load;
import bntu.accounting.application.util.db.entityloaders.EmployeesInstance;
import bntu.accounting.application.util.enums.LoadTypes;
import bntu.accounting.application.util.normalization.Normalizer;

import java.util.List;

public class LoadService {
    private LoadDAO loadDAO = new LoadDAOImpl();
    public Double getTotalLoadOfAllTeachers(List<Employee> employees){
        double result = 0;
        for (Employee employee: employees) {
            result += employee.getLoad().getTotalHours();
        }
        return roundValue(result);
    }

    public Double getAcademicLoadOfAllTeachers(List<Employee> employees){
        double result =0;
        for (Employee teacher: employees) {
            result += teacher.getLoad().getAcademicHours();
        }
        return result;
    }
    public Double getOrgLoadOfAllTeachers(List<Employee> employees){
        double result =0;
        for (Employee teacher: employees) {
            result += teacher.getLoad().getOrganizationHours();
        }
        return roundValue(result);
    }
    public Double getAddLoadOfAllTeachers(List<Employee> employees){
        double result =0;
        for (Employee teacher: employees) {
            result += teacher.getLoad().getAdditionalHours();
        }
        return roundValue(result);
    }
    public Double findTotalHours(Load load){
        Double result = load.getAcademicHours() +
                load.getAdditionalHours() + load.getOrganizationHours();
        load.setTotalHours(result);
        return roundValue(result);
    }
    public void updateLoad(Integer id,Load load){
        Normalizer.normalizeLoad(load);
        loadDAO.updateLoad(id,load);
        EmployeesInstance.getInstance().notifyObservers();
    }
    public void checkLoadCapacity(Double maxCapacity, Double newSum) throws SettingIncorrectValue {

    }
    private double roundValue(double value) {
        double result = Math.round(value * 100);
        result = result / 100;
        return result;
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
