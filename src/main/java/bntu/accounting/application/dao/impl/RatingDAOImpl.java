package bntu.accounting.application.dao.impl;

import bntu.accounting.application.dao.interfaces.RatingDAO;
import bntu.accounting.application.models.fordb.*;
import bntu.accounting.application.services.RatingService;
import bntu.accounting.application.util.db.DBManager;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

public class RatingDAOImpl implements RatingDAO {
    @Override
    public List<Rating> getAllRatings() {
        List<Rating> ratings = null;
        try (Session session = DBManager.getSession()) {
            session.beginTransaction();
            ratings = session.createQuery("select r from Rating r order by r.score", Rating.class).getResultList();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            throw new HibernateException("Не удалось получить данные из базы данных");
        }
        return ratings;
    }
    @Override
    public Rating findAllRatingByKey(Employee employee, Expert expert) {
        Rating rating = null;
        try (Session session = DBManager.getSession()) {
            session.beginTransaction();
            rating = session.get(Rating.class, new ConstructKey(employee,expert));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Create HIBERNATE EXCEPTION");
        }
        return rating;
    }
    @Override
    public List<Rating> findAllRatingByEmployee(Employee employee) {
        return null;
    }

    @Override
    public List<Rating> findAllRatingByExpert(Expert expert) {
        return null;
    }

    @Override
    public void removeRating(Rating rating) {
        try (Session session = DBManager.getSession()) {
            session.beginTransaction();
            // Удаляем связные сущности в БД, чтобы не оставалось мусора в таблицах
            session.remove(rating.getKey());
            // Затем удаляем саму запись.
            session.remove(rating);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Didn't removed employee from DB");
        }
    }

    @Override
    public void updateRating(Employee employee, Expert expert, Integer score) {
        try (Session session = DBManager.getSession()) {
            session.beginTransaction();
            ConstructKey key = new ConstructKey(employee,expert);
            Rating rating = session.get(Rating.class, key);
            rating.setScore(score);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Create HIBERNATE EXCEPTION");
        }
    }

    @Override
    public void saveRating(Rating rating) {
        try (Session session = DBManager.getSession()) {
            session.beginTransaction();
            session.persist(rating);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            System.out.println("Create HIBERNATE EXCEPTION");
        }
    }
}
