package bntu.accounting.application.controllers.windows;

import bntu.accounting.application.controllers.VisualComponentsInitializer;
import bntu.accounting.application.controllers.alerts.*;
import bntu.accounting.application.controllers.exceptions.EmptyEntityException;
import bntu.accounting.application.controllers.exceptions.SettingIncorrectValue;
import bntu.accounting.application.dao.exceptions.EmptyResultListException;
import bntu.accounting.application.models.Employee;
import bntu.accounting.application.models.Vacancy;
import bntu.accounting.application.models.builders.EmployeeBuilder;
import bntu.accounting.application.services.EmployeeService;
import bntu.accounting.application.services.LoadService;
import bntu.accounting.application.services.VacancyService;
import bntu.accounting.application.util.db.entityloaders.EmployeesInstance;
import bntu.accounting.application.util.db.entityloaders.Observer;
import bntu.accounting.application.util.db.entityloaders.VacancyInstance;
import bntu.accounting.application.util.enums.LoadTypes;
import bntu.accounting.application.util.enums.VacancyStatus;
import bntu.accounting.application.util.fxsupport.WindowCreator;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.hibernate.HibernateException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ShowVacancyWindowController extends VisualComponentsInitializer implements Initializable,
        Observer, AlertManager {
    private Vacancy vacancy;
    private VacancyStatus status;
    private VacancyService vacancyService = new VacancyService();
    private EmployeeService employeeService = new EmployeeService();
    private LoadService loadService = new LoadService();

    @FXML
    private BorderPane showVacancyWindow;
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
        super.getStage().setOnCloseRequest(event -> {
            setActionToSaveChanges();
        });
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
            Optional<ButtonType> result = showConfirmingAlert("Сохранить изменения?",
                    "Вы действительно хотите сохранить изменения?");
            if (result.get().getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                setActionToSaveChanges();
            } else if (result.get().getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE) {
                return;
            }

        });
        createPerformerButton.setOnAction(actionEvent -> {
            try {
                if (status != VacancyStatus.CLOSED) {
                    List<Employee> employees = employeeService.getAllEmployees();
                    Employee e = findEmployeeByName(employees, employeeListComboBox.getValue());
                    WindowCreator.createWindow("/fxml/windows/add_performer_window.fxml",
                            this, new AddingPerformerWindowController(e, vacancy));

                } else {
                    showInformationAlert("Вакансия закрыта!",
                            "Исполнители заняли всю вакантную нагрузку");
                }
            }
            catch (HibernateException e){
                showErrorAlert("Ошибка базы данных",
                        "Не удалось получить данные из базы");
            }
            catch (EmptyEntityException e) {
                try {
                    WindowCreator.createWindow("/fxml/windows/add_performer_window.fxml",
                            this, new AddingPerformerWindowController(vacancy));
                } catch (LoadException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (LoadException e) {
                showErrorAlert("Неизвестная ошибка",
                        "Что-то пошло не так");
            }
        });
    }

    private Employee findEmployeeByName(List<Employee> employees, String name) throws EmptyEntityException {
        if (name != null && !name.equals("Нет")) {
            for (Employee e : employees) {
                if (e.getName().equals(name)) return e;
            }
        }
        throw new EmptyEntityException();
    }

    private void updatePerformers(List<Employee> performers) {
        for (Employee e : performers) {
            EmployeeBuilder employeeBuilder = new EmployeeBuilder();
            Employee updatedEmployee = employeeBuilder
                    .setName(e.getName())
                    .setPost(vacancy.getPost())
                    .setSubject(vacancy.getSubject())
                    .setCategory(e.getCategory())
                    .setExperience(e.getExperience())
                    .setQualification(e.getQualification())
                    .setYoungSpecialist(e.getYoungSpecialist())
                    .setContractValue(e.getContractValue())
                    .build();
            employeeService.updateEmployee(e, updatedEmployee);
        }
    }

    private void checkLoadInFields(List<Employee> performers) throws SettingIncorrectValue {
        loadService.checkLoadOfPerformers(LoadTypes.ACADEMIC,
                Double.parseDouble(academicHoursField.getText()),performers);
        loadService.checkLoadOfPerformers(LoadTypes.ORGANIZATION,
                Double.parseDouble(organizationHoursField.getText()),performers);
        loadService.checkLoadOfPerformers(LoadTypes.ADDITIONAL,
                Double.parseDouble(additionalHoursField.getText()),performers);
    }

    private void setActionToSaveChanges() {
        try {
            List<Employee> performers = vacancy.getEmployeeList();
            try {
                checkLoadInFields(performers);
            } catch (SettingIncorrectValue e) {
                showErrorAlert("Некорректное значение нагрузки",
                        "Убедитесь, что вы указали значение нагрузки не меньше той, что уже" +
                                " назначена исполнителям");
                academicHoursField.setText(vacancy.getLoad().getAcademicHours().toString());
                organizationHoursField.setText(vacancy.getLoad().getOrganizationHours().toString());
                additionalHoursField.setText(vacancy.getLoad().getAdditionalHours().toString());
                return;
            }
            vacancy.setPost(postComboBox.getValue());
            vacancy.setSubject(subjectComboBox.getValue());
            vacancy.getLoad().setAcademicHours(Double.parseDouble(academicHoursField.getText()));
            vacancy.getLoad().setOrganizationHours(Double.parseDouble(organizationHoursField.getText()));
            vacancy.getLoad().setAdditionalHours(Double.parseDouble(additionalHoursField.getText()));
            vacancy.getLoad().setTotalHours(loadService.findTotalHours(vacancy.getLoad()));
            vacancy.setComment(commentTextArea.getText());
            status = vacancyService.getStatus(vacancy);
            vacancy.setStatus(status);
            updatePerformers(performers);
            vacancyService.updateVacancy(vacancy);
            loadService.updateLoad(vacancy.getLoad().getId(), vacancy.getLoad());
            showStatus(status);
            updateTable(performersTable);
        } catch (HibernateException e) {
            System.out.println(e);
        }

    }

    @Override
    protected List<Employee> updateTable(TableView<Employee> table) {
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        List<Employee> resultList = vacancyService.getAllPerformers(vacancy);
        employees.addAll(resultList);
        table.setItems(employees);
        return resultList;
    }

    private void addEmployeesToComboBox() {
        try {
            List<String> names = new ArrayList<>();
            names.add("Нет");
            names.addAll(employeeService.getAllEmployees().stream().map(e -> e.getName()).toList());
            ObservableList<String> items = FXCollections.observableList(names);
            employeeListComboBox.setItems(items);
        }
        catch (RuntimeException w){

        }

    }

    private void showStatus(VacancyStatus status) {
        switch (status) {
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
    }

    @Override
    public Optional<ButtonType> showInformationAlert(String header, String message) {
        Alerts informationAlert = new InformationAlert();
        return informationAlert.showAlert(header, message, showVacancyWindow);
    }

    @Override
    public Optional<ButtonType> showWarningAlert(String header, String message) {
        Alerts warningAlert = new WarningAlert();
        return warningAlert.showAlert(header, message, showVacancyWindow);
    }

    @Override
    public Optional<ButtonType> showErrorAlert(String header, String message) {
        Alerts errorAlert = new ErrorAlert();
        return errorAlert.showAlert(header, message, showVacancyWindow);
    }

    @Override
    public Optional<ButtonType> showConfirmingAlert(String header, String message) {
        ConfirmingAlert confirmingAlert = new ConfirmingAlert();
        return confirmingAlert.showAlert(header, message, showVacancyWindow);
    }
}

