package bntu.accounting.application.services;

import bntu.accounting.application.dao.impl.EmployeeDAOImpl;
import bntu.accounting.application.dao.impl.VacancyDAOImpl;
import bntu.accounting.application.dao.interfaces.EmployeeDAO;
import bntu.accounting.application.dao.interfaces.VacancyDAO;
import bntu.accounting.application.models.Employee;
import bntu.accounting.application.models.Load;
import bntu.accounting.application.models.Vacancy;
import bntu.accounting.application.util.db.entityloaders.EmployeesInstance;
import bntu.accounting.application.util.db.entityloaders.VacancyInstance;
import bntu.accounting.application.util.enums.VacancyStatus;
import bntu.accounting.application.util.normalization.Normalizer;

import java.util.List;

public class VacancyService {

    private VacancyDAO vacancyDAO = new VacancyDAOImpl();
    private EmployeeDAO employeeDAO = new EmployeeDAOImpl();
    private LoadService loadService = new LoadService();

    public Integer saveVacancy(Vacancy vacancy) {
        int id = vacancyDAO.saveVacancy(vacancy);
        VacancyInstance.getInstance().notifyObservers();
        return id;
    }
    public void removeVacancy(Vacancy vacancy){
        vacancyDAO.removeVacancy(vacancy);
        VacancyInstance.getInstance().notifyObservers();
        EmployeesInstance.getInstance().notifyObservers();
    }

    public List<Vacancy> getAllVacancies() {
        return vacancyDAO.getAllVacancies();
    }

    public void updateVacancy(Vacancy vacancy) {
        vacancyDAO.updateVacancy(vacancy.getLoad().getId(), vacancy);
        VacancyInstance.getInstance().notifyObservers();
    }

    public Integer addPerformer(Vacancy vacancy, Employee employee) {
        List<Employee> performers = vacancyDAO.getAllPerformers(vacancy.getLoad().getId());
        performers.add(employee);
        employee.setVacancy(vacancy);
        vacancy.setEmployeeList(performers);
        int id = employeeDAO.savePerformer(employee);
        // Оповестить окна о изменении состояния
        EmployeesInstance.getInstance().notifyObservers();
        return id;
    }

    public List<Employee> getAllPerformers(Vacancy vacancy) {
        return vacancyDAO.getAllPerformers(vacancy.getLoad().getId());
    }

    public Load findResidue(Vacancy vacancy) {
        List<Load> performersLoads = getAllPerformers(vacancy)
                .stream().map(e -> e.getLoad()).toList();
        Load necessaryLoad = vacancy.getLoad();
        Load residueLoad = new Load();
        residueLoad.clone(necessaryLoad);
            for (Load l : performersLoads) {
            residueLoad.setAcademicHours(residueLoad.getAcademicHours() - l.getAcademicHours());
            residueLoad.setOrganizationHours(residueLoad.getOrganizationHours() - l.getOrganizationHours());
            residueLoad.setAdditionalHours(residueLoad.getAdditionalHours() - l.getAdditionalHours());
            residueLoad.setTotalHours(loadService.findTotalHours(residueLoad));
        }
        return residueLoad;
    }

    public VacancyStatus getStatus(Vacancy vacancy) {
        // Остаток нагрузки
        Load residue = findResidue(vacancy);
        Normalizer.normalizeLoad(residue);
        VacancyStatus status;
        // Необходимая нагрузка и остаток равны
        if (residue.getTotalHours().equals(vacancy.getLoad().getTotalHours())) {
            status = VacancyStatus.OPENED;
        } else if (residue.getTotalHours() == 0) {
            status = VacancyStatus.CLOSED;
        } else {
            status = VacancyStatus.PARTIALLY_CLOSED;
        }
        return status;
    }
}
