package bntu.accounting.application.dao.impl;

import bntu.accounting.application.dao.interfaces.SalaryDAO;
import bntu.accounting.application.models.fordb.Employee;
import bntu.accounting.application.models.fordb.Load;
import bntu.accounting.application.models.fordb.Rating;
import bntu.accounting.application.models.fordb.Salary;
import bntu.accounting.application.util.db.DBManager;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class SalaryDAOImpl implements SalaryDAO {
    @Override
    public List<Salary> getAllSalaries() {
        try (Session session = DBManager.getSession()) {
            session.beginTransaction();
            List<Salary> salaries = session.createQuery("select s from Salary s", Salary.class).getResultList();
            session.getTransaction().commit();
            return salaries;
        } catch (HibernateException e) {
            System.out.println("find all salaries exception");
        }
        return null;
    }

    @Override
    public Integer saveSalary(Salary salary) {
        try (Session session = DBManager.getSession()) {
            session.beginTransaction();
            session.persist(salary);
            session.getTransaction().commit();
            return salary.getEmployee().getLoad().getId();
        } catch (HibernateException e) {
            System.out.println("________________________________________________________________________");
            System.out.println(e);
            return 0;
        }
    }

    @Override
    public Load findSalaryById(Integer id) {
        return null;
    }

    @Override
    public void updateSalary(Integer id, Salary updatedSalary) {
        try (Session session = DBManager.getSession()) {
            session.beginTransaction();
            Salary salary = session.get(Salary.class, updatedSalary.getEmployee());
            List<Rating> ratings = new ArrayList<>();
            ratings.addAll(salary.getEmployee().getRatings());
            salary.getEmployee().setRatings(ratings);
            salary.setTotalSalary(updatedSalary.getTotalSalary());
            salary.setRateSalary(updatedSalary.getRateSalary());
            salary.setLoadSalary(updatedSalary.getLoadSalary());
            salary.setExpAllowance(updatedSalary.getExpAllowance());
            salary.setQualAllowance(updatedSalary.getQualAllowance());
            salary.setContractAllowance(updatedSalary.getContractAllowance());
            salary.setIndustryWorkAllowance(updatedSalary.getIndustryWorkAllowance());
            salary.setProfActivitiesAllowance(updatedSalary.getProfActivitiesAllowance());
            salary.setYSAllowance(updatedSalary.getYSAllowance());
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println(e);
        }
    }
    @Override
    public void updateSalaryOPD(Employee key, Double OPD) {
        try (Session session = DBManager.getSession()) {
            session.beginTransaction();
            Salary salary = session.get(Salary.class, key);
            salary.setProfActivitiesAllowance(OPD);
            List<Rating> ratings = new ArrayList<>();
            ratings.addAll(salary.getEmployee().getRatings());
            salary.getEmployee().setRatings(ratings);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("_______________________________________________________________________________");
            System.out.println(e);
        }
    }

    @Override
    public void removeSalary(Salary salary) {

    }

    @Override
    public void removeSalary(Integer id) {

    }
}
