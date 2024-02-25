package bntu.accounting.application.controllers.windows;

import bntu.accounting.application.controllers.VisualComponentsInitializer;
import bntu.accounting.application.controllers.alerts.*;
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
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import org.hibernate.HibernateException;

import java.net.URL;
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
                    setActionToSaveChanges();
                    List<Employee> employees = employeeService.getAllEmployees();
                    if (employees.size() == 0) throw new EmptyResultListException();
                    for (Employee employee : employees) {
                        if (employeeListComboBox.getValue().equals(employee.getName())) {
                            WindowCreator.createWindow("/fxml/windows/add_performer_window.fxml",
                                    this, new AddingPerformerWindowController(employee, vacancy));
                            return;
                        }
                    }
                } else {
                    System.out.println("CLOSED");
                }
            } catch (EmptyResultListException e) {
                try {
                    WindowCreator.createWindow("/fxml/windows/add_performer_window.fxml",
                            this, new AddingPerformerWindowController(vacancy));
                } catch (LoadException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (NullPointerException e) {
                try {
                    WindowCreator.createWindow("/fxml/windows/add_performer_window.fxml",
                            this, new AddingPerformerWindowController(vacancy));
                } catch (LoadException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (LoadException e) {
                System.out.println(e);
                throw new RuntimeException(e);
            }
        });
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

    private void checkLoadInFields(LoadTypes type, List<Employee> performers) throws SettingIncorrectValue {
        double sum, v;
        switch (type) {
            case ACADEMIC:
                sum = 0;
                v = Double.parseDouble(academicHoursField.getText());
                for (Employee e : performers) {
                    sum += e.getLoad().getAcademicHours();
                }
                if (v < sum) throw new SettingIncorrectValue("Неправильная уч нагрузка", "Нагрузка");
                break;
            case ORGANIZATION:
                sum = 0;
                v = Double.parseDouble(organizationHoursField.getText());
                for (Employee e : performers) {
                    sum += e.getLoad().getOrganizationHours();
                }
                if (v < sum) throw new SettingIncorrectValue("Неправильная орг нагрузка", "Нагрузка");
                break;
            case ADDITIONAL:
                sum = 0;
                v = Double.parseDouble(additionalHoursField.getText());
                for (Employee e : performers) {
                    sum += e.getLoad().getAdditionalHours();
                }
                if (v < sum) throw new SettingIncorrectValue("Неправильная доп нагрузка", "Нагрузка");
                break;
        }
    }

    private void setActionToSaveChanges() {
        try {
            List<Employee> performers = vacancy.getEmployeeList();
            try {
                checkLoadInFields(LoadTypes.ACADEMIC, performers);
                checkLoadInFields(LoadTypes.ORGANIZATION, performers);
                checkLoadInFields(LoadTypes.ADDITIONAL, performers);
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
        List<Employee> employees =
                employeeService.getAllEmployees();
        ObservableList<String> items = FXCollections.observableList(employees.stream().map(e -> e.getName()).toList());
        employeeListComboBox.setItems(items);
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
        status = vacancyService.getStatus(vacancy);
        vacancy.setStatus(status);
        showStatus(status);
    }

    @Override
    public Optional<ButtonType> showInformationAlert(String header, String message) {
        Alerts informationAlert = new InformationAlert();
        return informationAlert.showAlert(header,message,showVacancyWindow);
    }

    @Override
    public Optional<ButtonType> showWarningAlert(String header, String message) {
        Alerts warningAlert = new WarningAlert();
        return  warningAlert.showAlert(header,message,showVacancyWindow);
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

