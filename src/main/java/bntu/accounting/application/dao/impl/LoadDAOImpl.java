package bntu.accounting.application.dao.impl;

import bntu.accounting.application.dao.interfaces.LoadDAO;
import bntu.accounting.application.models.Load;
import bntu.accounting.application.util.db.DBManager;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

public class LoadDAOImpl implements LoadDAO {
    @Override
    public List<Load> getAllLoads() {
        try (Session session = DBManager.getSession()) {
            session.beginTransaction();
            List<Load> loads = session.createQuery("select l from Load l", Load.class).getResultList();
            session.getTransaction().commit();
            return loads;
        } catch (HibernateException e) {
            System.out.println("find all loads exception");
        }
        return null;
    }

    @Override
    public Integer saveLoad(Load load) {
        try (Session session = DBManager.getSession()) {
            session.beginTransaction();
            session.persist(load);
            session.getTransaction().commit();
            return load.getId();
        } catch (HibernateException e) {
            System.out.println("Create HIBERNATE EXCEPTION");
            return 0;
        }
    }

    @Override
    public Load findLoadById(Integer id) {
        Load load = null;
        try (Session session = DBManager.getSession()) {
            session.beginTransaction();
            load = session.get(Load.class, id);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Create HIBERNATE EXCEPTION");
        }
        return load;
    }

    @Override
    public void updateLoad(Integer id, Load updatedLoad) {
        try (Session session = DBManager.getSession()) {
            session.beginTransaction();
            Load load = session.get(Load.class, updatedLoad.getId());
            load.setAcademicHours(updatedLoad.getAcademicHours());
            load.setOrganizationHours(updatedLoad.getOrganizationHours());
            load.setAdditionalHours(updatedLoad.getAdditionalHours());
            load.setTotalHours(updatedLoad.getTotalHours());
            load.setEmployee(updatedLoad.getEmployee());
            //  load.getEmployee().setLoad(load);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Create HIBERNATE EXCEPTION");
        }
    }

    @Override
    public void removeLoad(Load load) {
        try (Session session = DBManager.getSession()) {
            session.beginTransaction();

            // Удаляем связные сущности в БД, чтобы не оставалось мусора в таблицах
            session.remove(load.getEmployee());
            session.remove(load.getEmployee().getSalary());

            // Затем удаляем саму запись.
            session.remove(load);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Didn't removed load from DB");
        }
    }

    @Override
    public void removeLoad(Integer id) {

    }
}
