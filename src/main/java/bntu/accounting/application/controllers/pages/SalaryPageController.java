package bntu.accounting.application.controllers.pages;

import bntu.accounting.application.controllers.VisualComponentsInitializer;
import bntu.accounting.application.controllers.windows.BonusWindowController;
import bntu.accounting.application.controllers.windows.SalaryOptionsWindowController;
import bntu.accounting.application.excel.SalaryFileCreator;
import bntu.accounting.application.models.fordb.Employee;
import bntu.accounting.application.models.Item;
import bntu.accounting.application.services.EmployeeService;
import bntu.accounting.application.services.SalaryService;
import bntu.accounting.application.util.db.entityloaders.EmployeesInstance;
import bntu.accounting.application.util.db.entityloaders.Observer;
import bntu.accounting.application.util.fxsupport.RowIndexer;
import bntu.accounting.application.util.fxsupport.WindowCreator;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SalaryPageController extends VisualComponentsInitializer implements Initializable, Observer {

    private SalaryService salaryService = new SalaryService();
    private EmployeeService employeeService = new EmployeeService();
    @FXML
    private TableColumn<Employee, String> categoryColumn;

    @FXML
    private TableColumn<Employee, String> coefColumn;

    @FXML
    private TableColumn<Employee, Integer> indexColumn;

    @FXML
    private TableColumn<Employee, String> loadColumn;
    @FXML
    private TableColumn<Employee, String> contractAllowanceColumn;

    @FXML
    private TableColumn<Employee, String> expAllowanceColumn;
    @FXML
    private TableColumn<Employee, String> profActivityAllowanceColumn;

    @FXML
    private TableColumn<Employee, String> qualAllowanceColumn;
    @FXML
    private TableColumn<Employee, String> workInIndustryAllowanceColumn;
    @FXML
    private TableColumn<Employee, String> youngSpecAllowanceColumn;
    @FXML
    private Button bonusButton;
    @FXML
    private Button optionsButton;
    @FXML
    private Button moreButton;

    @FXML
    private TableColumn<Employee, String> nameColumn;

    @FXML
    private Button saveToFileButton;

    @FXML
    private ImageView saveIcon;
    @FXML
    private TableView<Employee> salaryTable;

    @FXML
    private TableColumn<Employee, String> totalAllowancesColumn;

    @FXML
    private TableColumn<Employee, String> totalSalaryColumn;

    @FXML
    private TableColumn<Employee, String> withLoadSalaryColumn;

    @FXML
    private TableColumn<Employee, String> withoutLoadSalaryColumn;

    @FXML
    void moreButtonAction(ActionEvent event) {
        try {
            WindowCreator.createWindow("/fxml/windows/salary_window.fxml", this);
        } catch (LoadException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void optionsButtonAction(ActionEvent event) {
        try {
            WindowCreator.createWindow("/fxml/windows/salary_options_window.fxml", this,
                    new SalaryOptionsWindowController(super.getStage()));
        } catch (LoadException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void bonusButtonAction(ActionEvent event) {
        try {
            WindowCreator.createWindow("/fxml/windows/bonus_window.fxml", this,
                    new BonusWindowController(super.getStage()));
        } catch (LoadException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EmployeesInstance.getInstance().attach(this);
        RowIndexer.index(indexColumn);
        findActualSalary(updateTable(salaryTable));
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        loadColumn.setCellValueFactory(data -> new SimpleStringProperty(Double.toString(data.getValue()
                .getLoad().getTotalHours())));
        categoryColumn.setCellValueFactory(data -> new SimpleStringProperty(Integer.toString(data.getValue()
                .getCategory())));
        coefColumn.setCellValueFactory(data -> new SimpleStringProperty(Double.toString(salaryService
                .getTariffByCategory(data.getValue().getCategory()))));
        withoutLoadSalaryColumn.setCellValueFactory(data -> new SimpleStringProperty(Double.toString
                (data.getValue().getSalary().getRateSalary())));
        withLoadSalaryColumn.setCellValueFactory(data -> new SimpleStringProperty
                (Double.toString(data.getValue().getSalary().getLoadSalary())));
        contractAllowanceColumn.setCellValueFactory(data -> new SimpleStringProperty(
                Double.toString(data.getValue().getSalary().getContractAllowance())));
        expAllowanceColumn.setCellValueFactory(data -> new SimpleStringProperty(
                Double.toString(data.getValue().getSalary().getExpAllowance())));
        qualAllowanceColumn.setCellValueFactory(data -> new SimpleStringProperty(
                Double.toString(data.getValue().getSalary().getQualAllowance())));
        youngSpecAllowanceColumn.setCellValueFactory(data -> new SimpleStringProperty(
                Double.toString(data.getValue().getSalary().getYSAllowance())));
        workInIndustryAllowanceColumn.setCellValueFactory(data -> new SimpleStringProperty(
                Double.toString(data.getValue().getSalary().getIndustryWorkAllowance())));
        profActivityAllowanceColumn.setCellValueFactory(data -> new SimpleStringProperty(
                Double.toString(data.getValue().getSalary().getProfActivitiesAllowance())));
        totalAllowancesColumn.setCellValueFactory(data -> new SimpleStringProperty(
                Double.toString(salaryService.getTotalAllowances(data.getValue()))));
        totalSalaryColumn.setCellValueFactory(data -> new SimpleStringProperty(
                Double.toString(data.getValue().getSalary().getTotalSalary())));
        saveToFileButton.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
            File file = fileChooser.showSaveDialog(null);
            if (file != null) {
                SalaryFileCreator salaryFileCreator = new SalaryFileCreator();
                List<Item> items = employeeService.getAllEmployees().stream().map(e -> e.getParent()).toList();
                salaryFileCreator.createFile(file.getPath(), items);
            }
        });
    }

    private void findActualSalary(List<Employee> employees) {
        for (Employee e : employees) {
            salaryService.getTotalSalary(e);
        }
    }

    @Override
    public void update() {
        updateTable(salaryTable);
        salaryTable.refresh();
    }
}
