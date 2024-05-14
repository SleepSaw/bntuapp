package bntu.accounting.application.bonus;

import bntu.accounting.application.models.fordb.Employee;
import bntu.accounting.application.models.fordb.Expert;
import bntu.accounting.application.services.RatingService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpertAssessmentsMethod {
    private static RatingService ratingService = new RatingService();
    private static List<Double> competenceDegrees = new ArrayList<>();
    private static Competence competence = new Competence();
    public static void findExpertsCompetences(List<Expert> experts){
        for (Expert e : experts){
            double c = competence.getCompetenceDegree(e); // Пока не записаны данные в БД рабоать не будет
            competenceDegrees.add(c);
            e.setCompetence(c);
        }
    }
    public static Map<Employee,Double> findWeights(Expert expert,List<Employee> bestEmployees){
        double relativeKoef = expert.getCompetence();
        Map<Employee,Double> map = new HashMap<>();
        for (Employee employee : bestEmployees){
            map.put(employee,ratingService.findRatingByKey(employee,expert).getScore() * relativeKoef);
        }
        return map;
    }
    public static Double FindSumOfPointAEmployee(Employee employee, List<Expert>experts){
        double sum = 0;
        for(Expert e : experts){
            sum += employee.getScoreByExpert(e).getScore();
        }
        return sum;
    }

    public static Map<Employee,Double> performExpertAssessmentsMethod(List<Employee> bestEmployees,List<Expert> experts,
                                                                   Double balance){
        // Определение показателей компетентности экспертов
        findExpertsCompetences(experts);
        Map<Employee, Double> sumWeights = findWeightSum(bestEmployees,experts);
        // Коэффициенты пропорциональности
        for (Employee e: bestEmployees) {
            sumWeights.put(e, sumWeights.get(e) / experts.size());
        }
        double sum = 0;
        for (Employee e: bestEmployees) {
            sum += sumWeights.get(e);
        }
        double piece = balance / sum;
        HashMap<Employee,Double> results = new HashMap<>();
        for (Employee employee: bestEmployees){
            results.put(employee, sumWeights.get(employee)*piece);
        }
        return results;
    }
    public static Map<Employee, Double> FindPercent(List<Employee> employees, List<Expert> experts){
        Map<Employee, Double> map = findWeightSum(employees,experts);
        double sum = 0;
        for (Employee e: employees) {
            sum += map.get(e);
        }
        Map<Employee, Double> results = new HashMap<>();
        for (Employee e : employees){
            results.put(e, map.get(e) / sum);
        }
        return results;
    }

    public static Map<Employee, Double> findWeightSum(List<Employee> employees, List<Expert> experts) {
        // Массив сумм весовых коэффициентов
        Map<Employee,Double> sumWeights = new HashMap<>();
        for (Employee e: employees) {
            sumWeights.put(e,0d);
        }
        // Вычисление сумм оценок каждого учителя
        for (Expert expert : experts){
            Map<Employee,Double> scoresOfExpert = findWeights(expert,employees);
            for(Employee e: employees){
                sumWeights.put(e,sumWeights.get(e)+scoresOfExpert.get(e));
            }
        }
        return sumWeights;
    }
}
