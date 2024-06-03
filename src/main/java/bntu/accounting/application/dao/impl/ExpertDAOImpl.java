package bntu.accounting.application.dao.impl;

import bntu.accounting.application.dao.interfaces.ExpertDAO;
import bntu.accounting.application.models.fordb.Expert;
import bntu.accounting.application.util.db.DBManager;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

public class ExpertDAOImpl implements ExpertDAO {
    @Override
    public List<Expert> getAllExperts() {
        List<Expert> experts = null;
        try (Session session = DBManager.getSession()) {
            session.beginTransaction();
            experts = session.createQuery("select e from Expert e order by e.name", Expert.class).getResultList();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            throw new HibernateException("Не удалось получить данные из базы данных");
        }
        return experts;
    }

    @Override
    public Expert findById(Integer id) {
        return null;
    }

    @Override
    public void removeExpert(Expert expert) {
        try (Session session = DBManager.getSession()) {
            session.beginTransaction();
            // Удаляем связные сущности в БД, чтобы не оставалось мусора в таблицах
            session.remove(expert.getId());
            // Затем удаляем саму запись.
            session.remove(expert);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Didn't removed employee from DB");
        }
    }

    @Override
    public void updateExpert(Integer id, Expert updatedExpert) {
        try (Session session = DBManager.getSession()) {
            session.beginTransaction();
            Expert expert = session.get(Expert.class, id);
            expert.setPost(updatedExpert.getPost());
            expert.setName(updatedExpert.getName());
            expert.setExperience(updatedExpert.getExperience());
            expert.setCompetence(updatedExpert.getCompetence());
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Create HIBERNATE EXCEPTION");
        }
    }

    @Override
    public Integer saveExpert(Expert expert) {
        try (Session session = DBManager.getSession()) {
            session.beginTransaction();
            session.persist(expert);
            session.getTransaction().commit();
            return expert.getId();
        } catch (HibernateException e) {
            System.out.println("Create HIBERNATE EXCEPTION");
            return 0;
        }
    }
}
