package bntu.accounting.application.dao.impl;

import bntu.accounting.application.dao.interfaces.VacancyDAO;
import bntu.accounting.application.models.Employee;
import bntu.accounting.application.models.Load;
import bntu.accounting.application.models.Vacancy;
import bntu.accounting.application.util.db.DBManager;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

public class VacancyDAOImpl implements VacancyDAO {
    @Override
    public List<Vacancy> getAllVacancies() {
        List<Vacancy> vacancies = null;
        try (Session session = DBManager.getSession()) {
            session.beginTransaction();
            vacancies = session.createQuery("select v from Vacancy v", Vacancy.class).getResultList();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            System.out.println("find all exception");
        }
        return vacancies;
    }

    @Override
    public Integer saveVacancy(Vacancy vacancy) {
        try (Session session = DBManager.getSession()) {
            session.beginTransaction();
            session.persist(vacancy.getLoad());
            vacancy.setEmployeeList(null);
            session.persist(vacancy);
            session.getTransaction().commit();
            return vacancy.getLoad().getId();
        } catch (HibernateException e) {
            System.out.println("Create HIBERNATE EXCEPTION");
            return 0;
        }
    }

    @Override
    public Load findVacancyById(Integer id) {
        return null;
    }

    @Override
    public void updateVacancy(Integer id, Vacancy updatedVacancy) {
        try (Session session = DBManager.getSession()) {
            session.beginTransaction();
            Vacancy vacancy = session.get(Vacancy.class, updatedVacancy.getLoad().getId());
            vacancy.setPost(updatedVacancy.getPost());
            vacancy.setSubject(updatedVacancy.getSubject());
            vacancy.setLoad(updatedVacancy.getLoad());
            vacancy.setComment(updatedVacancy.getComment());
            vacancy.setStatus(updatedVacancy.getStatus());
            vacancy.setEmployeeList(updatedVacancy.getEmployeeList());
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Create HIBERNATE EXCEPTION");
        }
    }

    @Override
    public void removeVacancy(Vacancy vacancy) {

    }

    @Override
    public void removeVacancy(Integer id) {

    }

    @Override
    public List<Employee> getAllPerformers(Integer id) {
        List<Employee> performers = null;
        try (Session session = DBManager.getSession()) {
            session.beginTransaction();
            Vacancy vacancy = session.get(Vacancy.class, id);
            performers = vacancy.getEmployeeList();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Create HIBERNATE EXCEPTION");
        }
        return performers;
    }
}
