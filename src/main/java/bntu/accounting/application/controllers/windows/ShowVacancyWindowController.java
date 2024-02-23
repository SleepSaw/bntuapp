package bntu.accounting.application.controllers.windows;

import bntu.accounting.application.controllers.VisualComponentsInitializer;
import bntu.accounting.application.models.Employee;
import bntu.accounting.application.models.Load;
import bntu.accounting.application.models.Vacancy;
import bntu.accounting.application.services.EmployeeService;
import bntu.accounting.application.services.LoadService;
import bntu.accounting.application.services.VacancyService;
import bntu.accounting.application.util.db.entityloaders.EmployeesInstance;
import bntu.accounting.application.util.db.entityloaders.Observer;
import bntu.accounting.application.util.db.entityloaders.VacancyInstance;
import bntu.accounting.application.util.enums.VacancyStatus;
import bntu.accounting.application.util.fxsupport.WindowCreator;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShowVacancyWindowController extends VisualComponentsInitializer implements Initializable, Observer {
    private Vacancy vacancy;
    private VacancyStatus status;
    private VacancyService vacancyService = new VacancyService();
    private EmployeeService employeeService = new EmployeeService();
    private LoadService loadService = new LoadService();

    // КомбоБоксы
    @FXML
    private ComboBox<String> postComboBox;
    @FXML
    private ComboBox<String> subjectComboBox;
    @FXML
    private ComboBox<String> employeeListComboBox;

    // Таблица исполнителей
    @FXML
    private TableView<Employee> performersTable;
    @FXML
    private TableColumn<Employee, String> nameColumn;
    @FXML
    private TableColumn<Employee, String> organizationHoursColumn;
    @FXML
    private TableColumn<Employee, String> academicHoursColumn;
    @FXML
    private TableColumn<Employee, String> additionalHoursColumn;

    // Текстовые поля
    @FXML
    private TextField academicHoursField;
    @FXML
    private TextField additionalHoursField;
    @FXML
    private TextField organizationHoursField;
    @FXML
    private TextArea commentTextArea;

    // Кнопки
    @FXML
    private Button createPerformerButton;
    @FXML
    private Button saveChangesButton;

    // Текст
    @FXML
    private Label statusLabel;

    public ShowVacancyWindowController(Vacancy vacancy) {
        this.vacancy = vacancy;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        VacancyInstance.getInstance().attach(this);
        EmployeesInstance.getInstance().attach(this);
        status = vacancyService.getStatus(vacancy);
        updateTable(performersTable);
        addEmployeesToComboBox();
        showStatus(vacancyService.getStatus(vacancy));
        initComboBox(postComboBox, "posts", vacancy.getPost());
        initComboBox(subjectComboBox, "subjects", vacancy.getSubject());
        academicHoursField.setText(vacancy.getLoad().getAcademicHours().toString());
        organizationHoursField.setText(vacancy.getLoad().getOrganizationHours().toString());
        additionalHoursField.setText(vacancy.getLoad().getAdditionalHours().toString());
        commentTextArea.setText(vacancy.getComment());
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        academicHoursColumn.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getLoad().getAcademicHours().toString()));
        organizationHoursColumn.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getLoad().getOrganizationHours().toString()));
        additionalHoursColumn.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getLoad().getAdditionalHours().toString()));
        saveChangesButton.setOnAction(actionEvent -> {
            setActionToSaveChanges();
        });
        createPerformerButton.setOnAction(actionEvent -> {
            try {
                if (status != VacancyStatus.CLOSED){
                    setActionToSaveChanges();
                    List<Employee> employees = employeeService.getAllEmployees();
                    for (Employee employee: employees){
                        if (employeeListComboBox.getValue().equals(employee.getName())){
                            WindowCreator.createWindow("/fxml/windows/add_performer_window.fxml",
                                    this, new AddingPerformerWindowController(employee,vacancy));
                            return;
                        }
                    }
                }
                else {
                    System.out.println("CLOSED");
                }
            }
            catch (NullPointerException e){
                try {
                    WindowCreator.createWindow("/fxml/windows/add_performer_window.fxml",
                            this, new AddingPerformerWindowController(vacancy));
                } catch (LoadException ex) {
                    throw new RuntimeException(ex);
                }
            }
            catch (LoadException e) {
                System.out.println(e);
                throw new RuntimeException(e);
            }
        });
    }

    private void setActionToSaveChanges() {
        vacancy.setPost(postComboBox.getValue());
        vacancy.setSubject(subjectComboBox.getValue());
        vacancy.getLoad().setAcademicHours(Double.parseDouble(academicHoursField.getText()));
        vacancy.getLoad().setOrganizationHours(Double.parseDouble(organizationHoursField.getText()));
        vacancy.getLoad().setAdditionalHours(Double.parseDouble(additionalHoursField.getText()));
        vacancy.getLoad().setTotalHours(loadService.findTotalHours(vacancy.getLoad()));
        vacancy.setComment(commentTextArea.getText());
        status = vacancyService.getStatus(vacancy);
        vacancy.setStatus(status);
        vacancyService.updateVacancy(vacancy);
        loadService.updateLoad(vacancy.getLoad().getId(),vacancy.getLoad());
        showStatus(status);
        updateTable(performersTable);
    }

    @Override
    protected List<Employee> updateTable(TableView<Employee> table) {
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        List<Employee> resultList = vacancyService.getAllPerformers(vacancy);
        employees.addAll(resultList);
        table.setItems(employees);
        return resultList;
    }
    private void addEmployeesToComboBox(){
        List<Employee> employees =
                employeeService.getAllEmployees();
        ObservableList<String> items = FXCollections.observableList(employees.stream().map(e -> e.getName()).toList());
        employeeListComboBox.setItems(items);
    }
    private void showStatus(VacancyStatus status){
        switch (status){
            case OPENED:
                statusLabel.setText("Открыта");
                statusLabel.setStyle("-fx-text-fill: #217346 ");
                break;
            case PARTIALLY_CLOSED:
                statusLabel.setText("Частично закрыта");
                statusLabel.setStyle("-fx-text-fill: orange ");
                break;
            case CLOSED:
                statusLabel.setText("Закрыта");
                statusLabel.setStyle("-fx-text-fill: red ");
                break;
        }
    }

    @Override
    public void update() {
        updateTable(performersTable);
        addEmployeesToComboBox();
        status = vacancyService.getStatus(vacancy);
        vacancy.setStatus(status);
        showStatus(status);
    }
}

