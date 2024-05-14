package bntu.accounting.application.controllers.windows;

import bntu.accounting.application.bonus.*;
import bntu.accounting.application.controllers.VisualComponentsInitializer;
import bntu.accounting.application.controllers.exceptions.SettingIncorrectValue;
import bntu.accounting.application.dao.impl.ExpertDAOImpl;
import bntu.accounting.application.dao.interfaces.ExpertDAO;
import bntu.accounting.application.dao.interfaces.RatingDAO;
import bntu.accounting.application.models.fordb.*;
import bntu.accounting.application.models.serializable.RatingOptions;
import bntu.accounting.application.services.*;
import bntu.accounting.application.util.db.entityloaders.VacancyInstance;
import bntu.accounting.application.util.enums.LoadTypes;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class BonusWindowController extends VisualComponentsInitializer implements Initializable {
    @FXML
    private TableView<Employee> bonusTable;

    @FXML
    private TableColumn<Employee, String> totalBonus;

    @FXML
    private TextField statusValueField;

    @FXML
    private TableColumn<Employee, String> InPointsColumn;

    @FXML
    private TextField thirdRateField;

    @FXML
    private TextField secondRateField;

    @FXML
    private FlowPane expertsPanel;

    @FXML
    private Label statusLabel;

    @FXML
    private TableColumn<Employee, String> additionalSalaryColumn;

    @FXML
    private TableColumn<Employee, String> totalPointsColumn;

    @FXML
    private TableView<Employee> mainTable;

    @FXML
    private TableColumn<Employee, String> loadColumn;

    @FXML
    private TableColumn<Employee, String> gradeOfQualityColumn;

    @FXML
    private TableColumn<Employee, String> totalAdditionalBonus;

    @FXML
    private TableColumn<Employee, String> nameColumn;

    @FXML
    private TableColumn<Employee, String> rateColumn;

    @FXML
    private Button performButton;

    @FXML
    private TableColumn<Employee, String> valueColumn;

    @FXML
    private Button optionsButton;
    @FXML
    private Button correctButton;

    @FXML
    private TableColumn<Employee, String> salaryColumn;

    @FXML
    private TextField firstRateField;

    @FXML
    private TableColumn<Employee, String> employeeNameColumn;

    @FXML
    private TextField fundField;

    @FXML
    private TableColumn<Employee, String> inPercentColumn;
    private RatingService ratingService = new RatingService();
    private EmployeeService employeeService = new EmployeeService();
    private List<Employee> employees;
    private List<Expert> experts;
    private Double fund;
    private RatingOptions options = new RatingOptions();
    private ExpertService expertService = new ExpertService();
    private BonusService bonusService = new BonusService();
    private BonusHandler bonusHandler = new BonusHandler();
    private Employee employee1;

    public BonusWindowController(Stage stage) {
        super(stage);
    }

    // Заебись вроде
    private void initializeContext() {
        employees = employeeService.getAllEmployees();
        experts = expertService.getAllExperts();
        fund = bonusService.findBonusFund(employees);
        options = ratingService.getRatingOptions();
        DefaultDivider defaultDivider = new DefaultDivider(options);
        defaultDivider.divideFund(employees);
    }

    // Доделать после второй таблицы
    private void initializeMainTable() {
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        loadColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLoad().getTotalHours().toString()));
        salaryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSalary().getLoadSalary().toString()));
        gradeOfQualityColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getWorkQualityGrade().toString()));
        gradeOfQualityColumn.setCellFactory(employeeStringTableColumn -> {
            return new javafx.scene.control.TableCell<Employee, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item);
                        setStyle("-fx-background-color: " + getColor(Integer.valueOf(item)) + ";"); // Установка цвета фона
                    }
                }
            };
        });
        gradeOfQualityColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        provideEditingOfColumn(gradeOfQualityColumn);
        gradeOfQualityColumn.setEditable(true);
        mainTable.setEditable(true);
        rateColumn.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf((ratingService.findBonusRateByGrade(data.getValue().getWorkQualityGrade()) * 100))));
        valueColumn.setCellValueFactory(data -> new SimpleStringProperty(ratingService.findBonusValue(data.getValue()).toString()));
        additionalSalaryColumn.setCellValueFactory(data -> new SimpleStringProperty(
                bonusHandler.getBonusValue(data.getValue()).toString()));
        totalBonus.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(
                bonusHandler.getBonusValue(data.getValue())
                        + data.getValue().getSalary().getProfActivitiesAllowance())
        ));
        updateTable(mainTable,employees);
    }

    private void initializeBonusTable() {
        employeeNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        addExpertsColumns();
        totalPointsColumn.setCellValueFactory(data -> new SimpleStringProperty(
                bonusHandler.getSum(data.getValue()).toString()));
        InPointsColumn.setCellValueFactory(data -> new SimpleStringProperty(
                bonusHandler.getWeightSum(data.getValue()).toString()));
        inPercentColumn.setCellValueFactory(data -> new SimpleStringProperty(
                String.valueOf(bonusHandler.getPiece(data.getValue()) * 100)));
        totalAdditionalBonus.setCellValueFactory(data -> new SimpleStringProperty(
                bonusHandler.getBonusValue(data.getValue()).toString()));
        updateTable(bonusTable,employees.stream().filter(e -> e.getWorkQualityGrade() == 1).toList());
        bonusTable.refresh();
    }
    protected void updateTable(TableView<Employee> table, List<Employee> employeesList) {
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        employees.addAll(employeesList);
        table.setItems(employees);
    }

    private void resolve() {
        Result result = bonusHandler.bonusEmployees(employees, experts);
        updateFundStatus(result);
        updateRates(result);
        ratingService.saveRates(result);
        updateTable(mainTable,employees);
        mainTable.refresh();
        updateTable(bonusTable,employees.stream().filter(e -> e.getWorkQualityGrade() == 1).toList());
        bonusTable.refresh();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeContext();
        initializeHeader();
        initializeMainTable();
        initializeBonusTable();
        mainTable.refresh();
        correctButton.setOnAction(actionEvent -> {
            resolve();
        });


    }

    private void addExpertsColumns() {
        ObservableList<TableColumn<Employee, ?>> columns = bonusTable.getColumns();
        List<Expert> list = expertService.getAllExperts();
        int col_i = 1;
        for (Expert e : list) {
            TableColumn<Employee, String> column = new TableColumn<>();
            column.setText("Э-i");
            column.setMaxWidth(1000);
            column.setPrefWidth(50);
            column.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getScoreByExpert(e).getScore().toString()));
            columns.add(col_i, column);
            col_i++;
        }
    }

    private String getColor(Integer grade) {
        if (grade == 3) return "#f89c9c";
        if (grade == 2) return "#fdf8e7";
        else return "#d6ffe8";
    }

    private void initializeHeader() {
        Result result = new Result(fund, options.getDefaultFirstRate(), options.getDefaultSecondRate(), options.getDefaultThirdRate());
        fundField.setText(fund.toString());
        firstRateField.setText(String.valueOf(options.getDefaultFirstRate() * 100));
        secondRateField.setText(String.valueOf(options.getDefaultSecondRate() * 100));
        thirdRateField.setText(String.valueOf(options.getDefaultThirdRate() * 100));
        result.setBalance(bonusService.findBalance(employees, options.getDefaultFirstRate(),
                options.getDefaultSecondRate(), options.getDefaultThirdRate()));
        updateFundStatus(result);
    }

    private void updateFundStatus(Result result) {
        updateRates(result);
        Double balance = result.getBalance();
        String status;
        String colorHex;
        if (balance > 0d) {
            status = "Избыток";
            colorHex = "#217346";
        } else if (balance < 0d) {
            status = "Недостаток";
            colorHex = "#ff0000";
        } else {
            status = "Оптимум";
            colorHex = "#ff9800";
        }
        statusLabel.setText(status);
        statusLabel.setStyle("-fx-text-fill: " + colorHex + " ;");
        statusValueField.setText(balance.toString());
    }

    private void updateRates(Result result) {
        firstRateField.setText(result.getFirstRate().toString());
        secondRateField.setText(result.getSecondRate().toString());
        thirdRateField.setText(result.getThirdRate().toString());
    }

    private void provideEditingOfColumn(TableColumn<Employee, String> column) {
        // Установка фабрики ячеек для редактирования
        column.setOnEditCommit(event -> {
            Pattern p = Pattern.compile("^[123]$");
            Employee employee = event.getRowValue();
            employee1 = employee;
            try {
                if (!p.matcher(event.getNewValue()).matches()) {
                    throw new NumberFormatException();
                }
                employee.setWorkQualityGrade(Integer.valueOf(event.getNewValue()));
            } catch (NumberFormatException e) {
                showErrorAlert("Некорректное значение",
                        "Ошибка ввода степени качества работы. При вводе допускаются только одна из цифр 1,2 и 3");
            }
            mainTable.refresh();
        });
    }
}
