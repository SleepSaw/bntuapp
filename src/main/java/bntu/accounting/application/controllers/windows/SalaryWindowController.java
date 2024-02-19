package bntu.accounting.application.controllers.windows;

import bntu.accounting.application.controllers.VisualComponentsInitializer;
import bntu.accounting.application.models.Employee;
import bntu.accounting.application.services.AllowancesService;
import bntu.accounting.application.services.SalaryService;
import bntu.accounting.application.util.enums.AllowanceTypes;
import bntu.accounting.application.util.fxsupport.RowIndexer;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class SalaryWindowController extends VisualComponentsInitializer implements Initializable {

    private AllowancesService allowancesService = new AllowancesService();
    private SalaryService salaryService = new SalaryService();
    @FXML
    private TableColumn<Employee, String> contractPercColumn;

    @FXML
    private TableColumn<Employee, String> contractRubColumn;

    @FXML
    private TableColumn<Employee, String> expPercColumn;

    @FXML
    private TableColumn<Employee, String> expRubColumn;

    @FXML
    private TableColumn<Employee, Integer> indexColumn;

    @FXML
    private TableColumn<Employee, String> loadColumn;

    @FXML
    private TableColumn<Employee, String> nameColumn;

    @FXML
    private TableColumn<Employee, String> profActivityPercColumn;

    @FXML
    private TableColumn<Employee, String> profActivityRubColumn;

    @FXML
    private TableColumn<Employee, String> qualificationAllowancePercColumn;

    @FXML
    private TableColumn<Employee, String> qualificationAllowanceRubColumn;

    @FXML
    private TableColumn<Employee, String> tariffColumn;

    @FXML
    private TableColumn<Employee, String> totalSalaryColumn;

    @FXML
    private TableColumn<Employee, String> withLoadSalaryColumn;

    @FXML
    private TableColumn<Employee, String> withoutLoadSalaryColumn;

    @FXML
    private TableColumn<Employee, String> workInIndustryAllowancePercColumn;

    @FXML
    private TableColumn<Employee, String> workInIndustryAllowanceRubColumn;

    @FXML
    private TableColumn<Employee, String> youngSpecialistPercColumn;

    @FXML
    private TableColumn<Employee, String> youngSpecialistRubColumn;
    @FXML
    private TableView<Employee> salaryTable;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RowIndexer.index(indexColumn);
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        loadColumn.setCellValueFactory(data -> new SimpleStringProperty(
                Double.toString(data.getValue().getLoad().getTotalHours())));
        tariffColumn.setCellValueFactory(data -> new SimpleStringProperty(
                Double.toString(allowancesService.getTariffByCategory(data.getValue().getCategory().toString()))));
        withoutLoadSalaryColumn.setCellValueFactory(data -> new SimpleStringProperty(
                Double.toString(data.getValue().getSalary().getRateSalary())));
        withLoadSalaryColumn.setCellValueFactory(data -> new SimpleStringProperty(
                Double.toString(data.getValue().getSalary().getLoadSalary())));
        expPercColumn.setCellValueFactory(data -> new SimpleStringProperty(
                showAllowance(AllowanceTypes.EXPERIENCE,data.getValue())));
        expRubColumn.setCellValueFactory(data -> new SimpleStringProperty(
                Double.toString(data.getValue().getSalary().getExpAllowance())));
        contractPercColumn.setCellValueFactory(data -> new SimpleStringProperty(
                showAllowance(AllowanceTypes.CONTRACT,data.getValue())));
        contractRubColumn.setCellValueFactory(data -> new SimpleStringProperty(
                Double.toString(data.getValue().getSalary().getContractAllowance())));
        qualificationAllowancePercColumn.setCellValueFactory(data -> new SimpleStringProperty(
                showAllowance(AllowanceTypes.QUALIFICATION,data.getValue())));
        qualificationAllowanceRubColumn.setCellValueFactory(data -> new SimpleStringProperty(
                Double.toString(data.getValue().getSalary().getQualAllowance())));
        youngSpecialistPercColumn.setCellValueFactory(data -> new SimpleStringProperty(
                showAllowance(AllowanceTypes.YOUNG_SPECIALIST,data.getValue())));
        youngSpecialistRubColumn.setCellValueFactory(data -> new SimpleStringProperty(
                Double.toString(data.getValue().getSalary().getYSAllowance())));
        workInIndustryAllowancePercColumn.setCellValueFactory(data -> new SimpleStringProperty(
                showAllowance(AllowanceTypes.WORK_IN_INDUSTRY,data.getValue())));
        workInIndustryAllowanceRubColumn.setCellValueFactory(data -> new SimpleStringProperty(
                Double.toString(data.getValue().getSalary().getIndustryWorkAllowance())));
        profActivityPercColumn.setCellValueFactory(data -> new SimpleStringProperty(
                showAllowance(AllowanceTypes.PROFESSIONAL_ACTIVITY,data.getValue())));
        profActivityRubColumn.setCellValueFactory(data -> new SimpleStringProperty(
                Double.toString(data.getValue().getSalary().getSpecialActivitiesAllowance())));
        totalSalaryColumn.setCellValueFactory(data -> new SimpleStringProperty(
                Double.toString(data.getValue().getSalary().getTotalSalary())));
        updateTable(salaryTable);
    }
    private String showAllowance(AllowanceTypes type, Employee employee) {
        String result;
        switch (type){
            case EXPERIENCE:
                result = allowancesService.getExpAllowance(employee.getExperience()) * 100 + "%";
                break;
            case QUALIFICATION:
                result = allowancesService.getQualAllowance(employee.getQualification()) * 100 + "%";
                break;
            case WORK_IN_INDUSTRY:
                result = allowancesService.getWorkInIndustryAllowance() * 100 + "%";
                break;
            case YOUNG_SPECIALIST:
                result = allowancesService.getYoungSpecialistAllowance() * 100 + "%";
                break;
            case CONTRACT:
                result = employee.getContractValue() + "%";
                break;
            case PROFESSIONAL_ACTIVITY:
                result = allowancesService.getProfActivityAllowance() * 100 + "%";
                break;
            default: result = "ERROR";
        }
        return result;
    }
}
