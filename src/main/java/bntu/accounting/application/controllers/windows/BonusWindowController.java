package bntu.accounting.application.controllers.windows;

import bntu.accounting.application.bonus.*;
import bntu.accounting.application.controllers.VisualComponentsInitializer;
import bntu.accounting.application.models.fordb.*;
import bntu.accounting.application.models.serializable.RatingOptions;
import bntu.accounting.application.services.*;
import bntu.accounting.application.util.db.entityloaders.EmployeesInstance;
import bntu.accounting.application.util.db.entityloaders.Observer;
import bntu.accounting.application.util.normalization.Normalizer;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

public class BonusWindowController extends VisualComponentsInitializer implements Initializable, Observer {
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
    // После корректировки не сходятся обнуленые значения

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
    private List<Button> expertButtons = new ArrayList<>();

    public BonusWindowController(Stage stage) {
        super(stage);
    }

    // Заебись вроде
    private void initializeContext() {
        employees = employeeService.getAllEmployees();
        experts = expertService.getAllExperts();
        fund = bonusService.findBonusFund(employees);
        options = ratingService.getRatingOptions();
//        DefaultDivider defaultDivider = new DefaultDivider(options);
//        defaultDivider.divideFund(employees);
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

        provideEditingOfGradeColumn(gradeOfQualityColumn);
        gradeOfQualityColumn.setEditable(true);
        mainTable.setEditable(true);
        rateColumn.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(Normalizer.normalizeItem((ratingService.findBonusRateByGrade
                (data.getValue().getWorkQualityGrade()) * 100)))));
        valueColumn.setCellValueFactory(data -> new SimpleStringProperty(ratingService.findBonusValue(data.getValue()).toString()));
        additionalSalaryColumn.setCellValueFactory(data -> new SimpleStringProperty(
                bonusHandler.getBonusValue(data.getValue()).toString()));
        totalBonus.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(
                Normalizer.normalizeItem(bonusHandler.getBonusValue(data.getValue())
                        + data.getValue().getSalary().getProfActivitiesAllowance()))
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
                String.valueOf(Normalizer.normalizeItem(bonusHandler.getPiece(data.getValue()) * 100))));
        totalAdditionalBonus.setCellValueFactory(data -> new SimpleStringProperty(
                Normalizer.normalizeItem(bonusHandler.getBonusValue(data.getValue())).toString()));
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
        updateBalanceStatus(result);
        updateRates(result);
        ratingService.saveRates(result);
        updateTable(mainTable,employees);
        mainTable.refresh();
        updateTable(bonusTable,employees.stream().filter(e -> e.getWorkQualityGrade() == 1).toList());
        bonusTable.refresh();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EmployeesInstance.getInstance().attach(this);
        initializeContext();
        createRatings();
        initializeHeader();
        initializeMainTable();
        initializeBonusTable();
        initializeExpertBar();
        mainTable.refresh();
        correctButton.setOnAction(actionEvent -> {
            resolve();
        });
        super.getStage().setOnCloseRequest(event -> {
            EmployeesInstance.getInstance().notifyObservers();
            EmployeesInstance.getInstance().detach(this);
        });
    }
    private void createRatings(){
        for(Employee e : employees){
            if(e.getRatings() == null || e.getRatings().isEmpty()){
                List<Rating> ratings = new ArrayList<>();
                for(Expert expert : experts){
                    Rating rating = new Rating(e,expert,0);
                    ratingService.saveRating(rating);
                    ratings.add(rating);
                }
            }
        }
    }

    private void addExpertsColumns() {
        ObservableList<TableColumn<Employee, ?>> columns = bonusTable.getColumns();
        List<Expert> list = expertService.getAllExperts();
        list.stream().sorted();
        int col_i = 1;
        for (Expert e : list) {
            TableColumn<Employee, String> column = new TableColumn<>();
            column.setText("Э" + col_i);
            column.setMaxWidth(1000);
            column.setPrefWidth(50);
            column.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getScoreByExpert(e).getScore().toString()));
            column.setCellFactory(TextFieldTableCell.forTableColumn());
            provideEditingOfRatingColumn(column,list.get(col_i-1));
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
        firstRateField.setText(String.valueOf(Normalizer.normalizeItem(options.getDefaultFirstRate() * 100)));
        secondRateField.setText(String.valueOf(Normalizer.normalizeItem(options.getDefaultSecondRate() * 100)));
        thirdRateField.setText(String.valueOf(Normalizer.normalizeItem(options.getDefaultThirdRate() * 100)));
        result.setBalance(bonusService.findBalance(employees, options.getDefaultFirstRate(),
                options.getDefaultSecondRate(), options.getDefaultThirdRate()));
        updateBalanceStatus(result);
    }

    private void updateBalanceStatus(Result result) {
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
        firstRateField.setText(String.valueOf(Normalizer.normalizeItem(result.getFirstRate() * 100)));
        secondRateField.setText(String.valueOf(Normalizer.normalizeItem(result.getSecondRate() * 100)));
        thirdRateField.setText(String.valueOf(Normalizer.normalizeItem(result.getThirdRate() * 100)));
    }
    private void provideEditingOfRatingColumn(TableColumn<Employee, String> column, Expert expert) {
        // Установка фабрики ячеек для редактирования
        column.setOnEditCommit(event -> {
            Pattern p = Pattern.compile("^(10|[1-9])$");
            Employee employee = event.getRowValue();
            try {
                if (!p.matcher(event.getNewValue()).matches()) {
                    throw new NumberFormatException();
                }
                Integer score = Integer.valueOf(event.getNewValue());
                employee.setScoreByExpert(score,expert);
                ratingService.updateRating(employee, expert, score);
            } catch (NumberFormatException e) {
                showErrorAlert("Некорректное значение",
                        "Ошибка ввода оценки. Допускаются числа от 1 до 10");
            }
            resolve();
            updateTable(bonusTable, employees.stream().filter(e -> e.getWorkQualityGrade() == 1).toList());
            bonusTable.refresh();
            mainTable.refresh();

        });
    }
    private void provideEditingOfGradeColumn(TableColumn<Employee, String> column) {
        // Установка фабрики ячеек для редактирования
        column.setOnEditCommit(event -> {
            Pattern p = Pattern.compile("^[123]$");
            Employee employee = event.getRowValue();
            try {
                if (!p.matcher(event.getNewValue()).matches()) {
                    throw new NumberFormatException();
                }
                employee.setWorkQualityGrade(Integer.valueOf(event.getNewValue()));
                employeeService.updateGradeOfEmployee(employee.getLoad().getId(),employee.getWorkQualityGrade());
            } catch (NumberFormatException e) {
                showErrorAlert("Некорректное значение",
                        "Ошибка ввода степени качества работы. При вводе допускаются только одна из цифр 1,2 и 3");
            }
            mainTable.refresh();
            updateTable(bonusTable,employees.stream().filter(e -> e.getWorkQualityGrade() ==1).toList());
            bonusTable.refresh();
            DefaultDivider defaultDivider = new DefaultDivider(options);
            updateBalanceStatus(defaultDivider.divideFund(employees));
        });
    }
    private void initializeExpertBar() {
        expertsPanel.getChildren().clear();
        int i = 1;
        for (Expert expert : experts) {
            Button b = new Button(expert.getName() + " (Э" + i + ")");
            b.setStyle("-fx-text-fill: #468ffd;" +
                    "-fx-background-color: transparent;" +
                    "-fx-underline: true;");
            expertButtons.add(b);
            expertsPanel.getChildren().add(b);
            i++;
        }

    }
    @Override
    public void update() {}
}
