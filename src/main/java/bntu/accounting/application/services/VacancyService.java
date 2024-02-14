package bntu.accounting.application.services;

import bntu.accounting.application.dao.impl.EmployeeDAOImpl;
import bntu.accounting.application.dao.impl.LoadDAOImpl;
import bntu.accounting.application.dao.impl.VacancyDAOImpl;
import bntu.accounting.application.dao.interfaces.EmployeeDAO;
import bntu.accounting.application.dao.interfaces.LoadDAO;
import bntu.accounting.application.dao.interfaces.VacancyDAO;
import bntu.accounting.application.models.Employee;
import bntu.accounting.application.models.Load;
import bntu.accounting.application.models.Vacancy;

import java.util.List;

public class VacancyService {
    private VacancyDAO vacancyDAO = new VacancyDAOImpl();
    private EmployeeDAO employeeDAO = new EmployeeDAOImpl();
    private LoadDAO loadDAO = new LoadDAOImpl();

    public Integer saveVacancy(Vacancy vacancy) {
        return vacancyDAO.saveVacancy(vacancy);
    }

    public List<Vacancy> getAllVacancies(){
        return vacancyDAO.getAllVacancies();
    }
    public void updateVacancy(Vacancy vacancy){
        vacancyDAO.updateVacancy(vacancy.getLoad().getId(),vacancy);
    }

    public Integer addPerformer(Vacancy vacancy, Employee employee){
        List<Employee> performers = vacancyDAO.getAllPerformers(vacancy.getLoad().getId());
        performers.add(employee);
        employee.setVacancy(vacancy);
        vacancy.setEmployeeList(performers);
        int id = employeeDAO.savePerformer(employee);
        return id;
    }
    public List<Employee> getAllPerformers(Vacancy vacancy) {
        return vacancyDAO.getAllPerformers(vacancy.getLoad().getId());
    }
    public Load findResidue(Vacancy vacancy){
        List<Load> performersLoads = getAllPerformers(vacancy)
                .stream().map(e -> e.getLoad()).toList();
        Load necessaryLoad = vacancy.getLoad();
        Load residueLoad = new Load();
        residueLoad.clone(necessaryLoad);
        for (Load l : performersLoads){
            residueLoad.setAcademicHours(residueLoad.getAcademicHours() - l.getAcademicHours());
            residueLoad.setOrganizationHours(residueLoad.getOrganizationHours() - l.getOrganizationHours());
            residueLoad.setAdditionalHours(residueLoad.getAdditionalHours() - l.getAdditionalHours());
            residueLoad.setTotalHours(residueLoad.getTotalHours() - l.getTotalHours());
        }
        return residueLoad;
    }
}
