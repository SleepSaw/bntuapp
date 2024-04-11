package bntu.accounting.application.services;

import bntu.accounting.application.dao.impl.EmployeeDAOImpl;
import bntu.accounting.application.dao.impl.VacancyDAOImpl;
import bntu.accounting.application.dao.interfaces.EmployeeDAO;
import bntu.accounting.application.dao.interfaces.VacancyDAO;
import bntu.accounting.application.models.Employee;
import bntu.accounting.application.models.Load;
import bntu.accounting.application.models.Salary;
import bntu.accounting.application.models.Vacancy;
import bntu.accounting.application.util.db.entityloaders.EmployeesInstance;
import bntu.accounting.application.util.db.entityloaders.VacancyInstance;
import bntu.accounting.application.util.enums.VacancyStatus;
import bntu.accounting.application.util.normalization.Normalizer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        vacancy.setStatus(getStatus(vacancy));
        vacancyDAO.updateVacancy(vacancy.getLoad().getId(), vacancy);
        VacancyInstance.getInstance().notifyObservers();
    }

    public Integer addPerformer(Vacancy vacancy, Employee employee) {
        List<Employee> performers = vacancyDAO.getAllPerformers(vacancy.getLoad().getId());
        performers.add(employee);
        employee.setVacancy(vacancy);
        vacancy.setEmployeeList(performers);
        vacancy.setStatus(getStatus(vacancy));
        updateVacancy(vacancy);
        int id = employeeDAO.savePerformer(employee);
        // Оповестить окна о изменении состояния
        EmployeesInstance.getInstance().notifyObservers();
        return id;
    }
    public void removePerformer(Vacancy vacancy, Employee employee) {
        List<Employee> performers = vacancyDAO.getAllPerformers(vacancy.getLoad().getId());
        performers.remove(employee);
        vacancy.setEmployeeList(performers);
        vacancy.setStatus(getStatus(vacancy));
        updateVacancy(vacancy);
        employeeDAO.removeEmployee(employee);
        EmployeesInstance.getInstance().notifyObservers();
    }

    public List<Employee> getAllPerformers(Vacancy vacancy) {
        return vacancyDAO.getAllPerformers(vacancy.getLoad().getId());
    }
    public Employee findPerformerByName(Vacancy vacancy,String name) {
        Employee employee = null;
        for (Employee e:  vacancy.getEmployeeList()){
            if (e.getName().equals(name)) employee = e;
        }
        return employee;
    }

    public Load findResidue(Vacancy vacancy) {
        List<Load> performersLoads = vacancy.getEmployeeList().stream().map(e -> e.getLoad()).toList();
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

    // Плановые показатели
    public Map<Vacancy,Salary> getPlannedVacanciesSalary(List<Vacancy> vacancies,List<Employee> employees){
        SalaryService salaryService = new SalaryService();
        AllowancesService allowancesService = new AllowancesService();
        Map<Vacancy,Salary> pairs = new HashMap<>();
        List<Salary> salaries = employees.stream().map(e -> e.getSalary()).toList();
        for (Vacancy vacancy : vacancies){
            Salary salary = new Salary();
            Load residue = findResidue(vacancy);
            salary.setRateSalary(roundValue(findPlannedRate(salaries.stream().map(e -> e.getRateSalary()).toList())));
            salary.setLoadSalary(roundValue(residue.getTotalHours() * salary.getRateSalary() / 20));
            salary.setExpAllowance(roundValue(findPlannedRate(salaries.stream().map(e -> e.getExpAllowance()).toList())
                    * residue.getTotalHours() / 20));
            salary.setQualAllowance(roundValue(findPlannedRate(salaries.stream().map(e -> e.getQualAllowance()).toList())
                    * residue.getTotalHours() / 20));
            salary.setContractAllowance(roundValue(findPlannedRate(salaries.stream().map(e -> e.getContractAllowance()).toList())
                    * residue.getTotalHours() / 20));
            salary.setYSAllowance(roundValue(findPlannedRate(salaries.stream().map(e -> e.getYSAllowance()).toList())
                    * residue.getTotalHours() / 20));
            salary.setIndustryWorkAllowance(roundValue(salary.getLoadSalary() * allowancesService.getWorkInIndustryAllowance()));
            salary.setProfActivitiesAllowance(0.00D);
            salary.setTotalSalary(roundValue(salaryService.getTotalSalary(salary)));
            pairs.put(vacancy,salary);
        }
        return pairs;
    }
    private double roundValue(double value) {
        double result = Math.round(value * 100);
        result = result / 100;
        return result;
    }
    // Поиск среднего значения
    public double findPlannedRate(List<Double> list){
        double sum = 0;
        for (double v : list){
            sum += v;
        }
        return sum / list.size();
    }

}
