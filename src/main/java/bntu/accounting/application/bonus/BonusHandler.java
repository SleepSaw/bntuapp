package bntu.accounting.application.bonus;

import bntu.accounting.application.iojson.RatingJsonHelper;
import bntu.accounting.application.models.fordb.Employee;
import bntu.accounting.application.models.fordb.Expert;
import bntu.accounting.application.models.fordb.Salary;
import bntu.accounting.application.models.serializable.RatingOptions;
import bntu.accounting.application.services.BonusService;
import bntu.accounting.application.services.SalaryService;
import bntu.accounting.application.util.normalization.Normalizer;

import java.util.List;
import java.util.Map;

public class BonusHandler {
    private BonusService bonusService = new BonusService();
    private SalaryService salaryService = new SalaryService();
    private RatingJsonHelper jsonHelper = new RatingJsonHelper();
    private RatingOptions options;
    private DefaultDivider defaultDivider;
    private RatesRecalculator recalculator;
    private ExpertAssessmentsMethod1 expertAssessmentsMethod;
    private Map<Employee,AssessmentResult> resultMap;

    public Result bonusEmployees(List<Employee> employees, List<Expert> experts) {
        initialize();
        double eps = 10;
        Result result = new Result();
        double fund = bonusService.findBonusFund(employees);
        result.setFund(fund);
        double balance = defaultDivider.divideFund(employees);
        result.setBalance(balance);
        result.setFirstRate(options.getDefaultFirstRate());
        result.setSecondRate(options.getDefaultSecondRate());
        result.setThirdRate(options.getDefaultThirdRate());
        if (Math.abs(result.getBalance()) > eps) {
            resultMap = null;
            if (result.getBalance() > 0) {
                List<Employee> bestEmployees = employees.stream().filter(employee -> employee.getWorkQualityGrade() == 1).toList();
                resultMap = expertAssessmentsMethod.performExpertAssessmentsMethod(balance, bestEmployees, experts);
            }
            if (result.getBalance() < 0) {
                result = recalculator.recalculateBonusRates(eps, result.getBalance(), employees);
            }
        }
        result.setBalance(0d);
        updateContext(result);
        for (Employee employee : employees) {
            salaryService.updateSalaryOPD(employee, employee.getSalary().getProfActivitiesAllowance());
        }
        return result;
    }
    public AssessmentResult getAssessmentResult(Employee employee){
        if (resultMap != null){
            return resultMap.get(employee);
        }
        return new AssessmentResult(0d,0d,0d,0d);
    }
    public Double getSum(Employee employee){
        if (resultMap != null && resultMap.containsKey(employee)){
            return Normalizer.normalizeItem(resultMap.get(employee).getDefaultSumOfScores());
        }
        return 0d;
    }
    public Double getWeightSum(Employee employee){
        if (resultMap != null && resultMap.containsKey(employee)){
            return Normalizer.normalizeItem(resultMap.get(employee).getWeightSumOfScores());
        }
        return 0d;
    }
    public Double getPiece(Employee employee){
        if (resultMap != null && resultMap.containsKey(employee)){
            return Normalizer.normalizeItem(resultMap.get(employee).getPieceOfTheBalance());
        }
        return 0d;
    }
    public Double getBonusValue(Employee employee){
        if (resultMap != null && resultMap.containsKey(employee)){
            return Normalizer.normalizeItem(resultMap.get(employee).getBonusValue());
        }
        return 0d;
    }

    private void updateContext(Result result) {
        options.setActualFirstRate(result.getFirstRate());
        options.setActualSecondRate(result.getSecondRate());
        options.setActualThirdRate(result.getThirdRate());
        jsonHelper.writeToJson(options);
    }

    private void initialize() {
        options = jsonHelper.readFromJson();
        defaultDivider = new DefaultDivider(options);
        recalculator = new RatesRecalculator(options);
        expertAssessmentsMethod = new ExpertAssessmentsMethod1();
    }
}
