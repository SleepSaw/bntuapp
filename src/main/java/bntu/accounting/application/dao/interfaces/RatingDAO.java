package bntu.accounting.application.dao.interfaces;

import bntu.accounting.application.models.fordb.Employee;
import bntu.accounting.application.models.fordb.Expert;
import bntu.accounting.application.models.fordb.Rating;

import java.util.List;

public interface RatingDAO {
    List<Rating> getAllRatings();
    List<Rating> findAllRatingByEmployee(Employee employee);
    List<Rating> findAllRatingByExpert(Expert expert);
    void removeRating(Rating rating);
    void updateRating(Employee employee, Expert expert, Rating updatedRating);
    void saveRating(Rating rating);
    Rating findAllRatingByKey(Employee employee, Expert expert);
}
