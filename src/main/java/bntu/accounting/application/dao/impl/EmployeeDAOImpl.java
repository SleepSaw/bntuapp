package bntu.accounting.application.dao.impl;

import bntu.accounting.application.controllers.exceptions.EmptyResultListException;
import bntu.accounting.application.dao.interfaces.EmployeeDAO;
import bntu.accounting.application.dao.interfaces.LoadDAO;
import bntu.accounting.application.models.Employee;
import bntu.accounting.application.models.Load;
import bntu.accounting.application.models.Salary;
import bntu.accounting.application.models.Vacancy;
import bntu.accounting.application.util.DBManager;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public List<Employee> getAllEmployees(){
        List<Employee> employees = null;
        try (Session session = DBManager.getSession()) {
            session.beginTransaction();
            employees = session.createQuery("select e from Employee e order by e.name", Employee.class).getResultList();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            System.out.println("find all exception");
        }
        return employees;
    }


    @Override
    public Employee findById(Integer id) {
        return null;
    }

    @Override
    public void removeEmployee(Employee employee) {
        try (Session session = DBManager.getSession()) {
            session.beginTransaction();
            // Удаляем связные сущности в БД, чтобы не оставалось мусора в таблицах
            session.remove(employee.getLoad());
            // Затем удаляем саму запись.
            session.remove(employee);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Didn't removed employee from DB");
        }
    }

    @Override
    public void updateEmployee(Integer id, Employee updatedEmployee) {
        try (Session session = DBManager.getSession()) {
            session.beginTransaction();
            Employee employee = session.get(Employee.class, id);
            employee.setName(updatedEmployee.getName());
            employee.setSubject(updatedEmployee.getSubject());
            employee.setPost(updatedEmployee.getPost());
            employee.setQualification(updatedEmployee.getQualification());
            employee.setYoungSpecialist(updatedEmployee.getYoungSpecialist());
            employee.setExperience(updatedEmployee.getExperience());
            employee.setCategory(updatedEmployee.getCategory());
            employee.setContractValue(updatedEmployee.getContractValue());
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Create HIBERNATE EXCEPTION");
        }
    }

    @Override
    public Integer saveEmployee(Employee employee) {
        try (Session session = DBManager.getSession()) {
            session.beginTransaction();
            // Сначала создаём основную сущность и привязываем к побочной
            Load load = new Load(0D,0D,0D);
            employee.setLoad(load);
            // здесь же создаём помочную и к ней привязываем основную
            Salary salary = new Salary();
            salary.setEmployee(employee);
            // сохраняем в правильном порядке
            session.persist(load);
            session.persist(employee);
            session.persist(salary);
            session.getTransaction().commit();
            return employee.getLoad().getId();
        } catch (HibernateException e) {
            System.out.println("Create HIBERNATE EXCEPTION");
            return 0;
        }
    }

    public Integer savePerformer(Employee employee) {
        try (Session session = DBManager.getSession()) {
            session.beginTransaction();
            // здесь же создаём помочную и к ней привязываем основную
            Salary salary = new Salary();
            salary.setEmployee(employee);
            Load load = new Load();
            load.setTotalHours(employee.getLoad().getTotalHours());
            load.setAdditionalHours(employee.getLoad().getAdditionalHours());
            load.setOrganizationHours(employee.getLoad().getOrganizationHours());
            load.setAcademicHours(employee.getLoad().getAcademicHours());
            employee.setLoad(load);
            // сохраняем в правильном порядке
            session.persist(load);
            session.persist(employee);
            session.persist(salary);
            session.getTransaction().commit();
            return employee.getLoad().getId();
        } catch (HibernateException e) {
            System.out.println(e);
            return 0;
        }
    }

    @Override
    public Load getEmployeeLoad(Employee employee) {
        return null;
    }

    @Override
    public Load getEmployeeLoadById(Integer id) {
        return null;
    }
}
