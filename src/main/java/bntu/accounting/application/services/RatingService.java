package bntu.accounting.application.services;

import bntu.accounting.application.bonus.Result;
import bntu.accounting.application.dao.impl.RatingDAOImpl;
import bntu.accounting.application.dao.interfaces.RatingDAO;
import bntu.accounting.application.iojson.RatingJsonHelper;
import bntu.accounting.application.models.fordb.Employee;
import bntu.accounting.application.models.fordb.Expert;
import bntu.accounting.application.models.fordb.Rating;
import bntu.accounting.application.models.serializable.RatingOptions;
import bntu.accounting.application.util.normalization.Normalizer;

import java.util.List;

public class RatingService {
    private RatingOptions ratingOptions = new RatingOptions();
    private RatingDAO ratingDAO = new RatingDAOImpl();
    private RatingJsonHelper ratingJsonHelper = new RatingJsonHelper();
    private SalaryService salaryService = new SalaryService();
    private AllowancesService allowancesService = new AllowancesService();

    public RatingService() {
        ratingOptions = ratingJsonHelper.readFromJson();
    }

    public void updateRating(Employee employee, Expert expert, Integer score) {
        ratingDAO.updateRating(employee,expert,score);
    }
    public RatingOptions getRatingOptions() {
        return ratingOptions;
    }
    public void saveRates(Result result){
        ratingOptions.setActualFirstRate(result.getFirstRate());
        ratingOptions.setActualSecondRate(result.getSecondRate());
        ratingOptions.setActualThirdRate(result.getThirdRate());
        ratingJsonHelper.writeToJson(ratingOptions);
    }
    public Double findBonusFund(List<Employee> employees){
        double total = salaryService.getSalaryByLoadOfAllTeachers(employees);
        double rate = allowancesService.getProfActivityAllowance();
        return Normalizer.normalizeItem(total * rate);
    }
    public Double findBonusRateByGrade(Integer grade){
        Double result = 0d;
        if (grade == 3) result = ratingOptions.getActualThirdRate();
        else if (grade == 2) result =  ratingOptions.getActualSecondRate();
        else result = ratingOptions.getActualFirstRate();
        return Normalizer.normalizeItem(result);
    }
    public Double findSumOfScores(List<Rating> ratings){
        double sum = 0;
        for (Rating rating : ratings){
            sum += rating.getScore();
        }
        return sum;
    }
    public Double findBonusValue(Employee employee){
        double rate = findBonusRateByGrade(employee.getWorkQualityGrade());
        return Normalizer.normalizeItem(employee.getSalary().getLoadSalary() * rate);
    }
    public void saveRating(Rating rating){
        ratingDAO.saveRating(rating);
    }
    public Rating findRatingByKey(Employee employee, Expert expert){
        return ratingDAO.findAllRatingByKey(employee,expert);
    }
}
