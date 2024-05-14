package bntu.accounting.application.bonus;

import bntu.accounting.application.models.fordb.Employee;
import bntu.accounting.application.models.fordb.Expert;
import bntu.accounting.application.models.fordb.Rating;
import bntu.accounting.application.services.EmployeeService;
import bntu.accounting.application.services.ExpertService;
import bntu.accounting.application.services.RatingService;

import java.util.ArrayList;
import java.util.List;

public class RatingsInitializer {
    private static RatingService ratingService = new RatingService();
    private static ExpertService expertService = new ExpertService();
    private static EmployeeService employeeService = new EmployeeService();

    public static void Initialize(){
        List<Employee> employees = employeeService.getAllEmployees();
        List<Expert> experts = expertService.getAllExperts();
        for (Expert expert: experts) {
            for (Employee employee: employees) {
                Rating rating = ratingService.findRatingByKey(employee,expert);
                if (rating == null) ratingService.saveRating(new Rating(employee,expert,0));
            }
        }
    }
    
}
