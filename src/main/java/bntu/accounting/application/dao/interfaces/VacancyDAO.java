package bntu.accounting.application.dao.interfaces;

import bntu.accounting.application.models.Employee;
import bntu.accounting.application.models.Load;
import bntu.accounting.application.models.Vacancy;

import java.util.List;

public interface VacancyDAO {
    List<Vacancy> getAllVacancies();
    Integer saveVacancy(Vacancy vacancy);

    Load findVacancyById(Integer id);

    void updateVacancy(Integer id, Vacancy updatedVacancy);

    void removeVacancy(Vacancy vacancy);

    void removeVacancy(Integer id);
    List<Employee> getAllPerformers(Integer id);

}
