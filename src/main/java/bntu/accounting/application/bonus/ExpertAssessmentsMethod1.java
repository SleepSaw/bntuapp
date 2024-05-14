package bntu.accounting.application.bonus;

import bntu.accounting.application.models.fordb.Employee;
import bntu.accounting.application.models.fordb.Expert;
import bntu.accounting.application.services.RatingService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpertAssessmentsMethod1 {
    // Утилитные поля
    private RatingService ratingService = new RatingService();
    private Competence competence = new Competence();

    private Map<Employee, AssessmentResult> results = new HashMap<>();

    // Основной метод
    public Map<Employee, AssessmentResult> performExpertAssessmentsMethod(Double balance, List<Employee> bestEmployees,
                                                                          List<Expert> experts){
        // Определение показателей компетентности экспертов
        findExpertsCompetences(experts);
        for(Employee employee : bestEmployees){
            double sum = findSumScoresOneEmployee(employee,experts);
            double weightSum = findWeightSumScoresOneEmployee(employee,experts);
            results.put(employee,new AssessmentResult(sum, weightSum));
        }
        double generalSum = findGeneralWeightSum(bestEmployees,experts);
        for (Employee employee : bestEmployees){
            AssessmentResult ar = results.get(employee);
            double piece = findPieceOfBalance(generalSum, ar.getWeightSumOfScores());
            double value = balance * piece;
            ar.setPieceOfTheBalance(piece);
            ar.setBonusValue(value);
        }
        return results;
    }


    public void findExpertsCompetences(List<Expert> experts){
        for (Expert e : experts){
            double c = competence.getCompetenceDegree(e); // Пока не записаны данные в БД рабоать не будет
            e.setCompetence(c);
        }
    }
    // Сумма оценок без учёта компетенции
    private Double findSumScoresOneEmployee(Employee employee, List<Expert>experts){
        double sum = 0d;
        for(Expert e : experts){
            sum += employee.getScoreByExpert(e).getScore();
        }
        return sum;
    }
    // Взвешенная сумма оценок для одного учителя
    private Double findWeightSumScoresOneEmployee(Employee employee, List<Expert>experts){
        double weightSum = 0d;
        double k_competence;
        for(Expert e : experts){
            k_competence = e.getCompetence();
            weightSum += employee.getScoreByExpert(e).getScore() * k_competence;
        }
        return weightSum;
    }
    // Доля от баланса на одного учителя
    private Double findPieceOfBalance(Double generalWeightSum, Double employeesWeightSum){
        return employeesWeightSum / generalWeightSum;
    }

    private Double findGeneralWeightSum(List<Employee> employees, List<Expert> experts){
        double sum = 0;
        for(Employee employee : employees){
            sum += findWeightSumScoresOneEmployee(employee, experts);
        }
        return sum;
    }
}
