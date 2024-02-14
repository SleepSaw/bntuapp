package bntu.accounting.application.controllers.windows;

import bntu.accounting.application.controllers.VisualComponentsInitializer;
import bntu.accounting.application.controllers.pages.VacanciesPageController;
import bntu.accounting.application.controllers.templates.VacancyItemController;
import bntu.accounting.application.dao.interfaces.VacancyDAO;
import bntu.accounting.application.iojson.FileLoader;
import bntu.accounting.application.models.Employee;
import bntu.accounting.application.models.Load;
import bntu.accounting.application.models.Vacancy;
import bntu.accounting.application.services.VacancyService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShowVacancyWindowController extends VisualComponentsInitializer implements Initializable {
    private Vacancy vacancy;
    private VacancyService vacancyService = new VacancyService();
    @FXML
    private TableColumn<Employee, String> academicHours;

    @FXML
    private TextField academicHoursField;

    @FXML
    private TableColumn<Employee, String> additionalHours;

    @FXML
    private TextField additionalHoursField;

    @FXML
    private TextArea commentTextArea;

    @FXML
    private Button createNewEmployeeButton;

    @FXML
    private ComboBox<String> employeeListComboBox;

    @FXML
    private TableView<Employee> employeeTable;

    @FXML
    private TableColumn<Employee, String> nameColumn;

    @FXML
    private TableColumn<Employee, String> organizationHours;

    @FXML
    private TextField organizationHoursField;

    @FXML
    private ComboBox<String> postComboBox;

    @FXML
    private Button saveChangesButton;

    @FXML
    private Label statusLabel;

    @FXML
    private ComboBox<String> subjectComboBox;

    public ShowVacancyWindowController(Vacancy vacancy) {
        this.vacancy = vacancy;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initComboBox(postComboBox, "posts", vacancy.getPost());
        initComboBox(subjectComboBox, "subjects", vacancy.getSubject());
        academicHoursField.setText(vacancy.getLoad().getAcademicHours().toString());
        organizationHoursField.setText(vacancy.getLoad().getOrganizationHours().toString());
        additionalHoursField.setText(vacancy.getLoad().getAdditionalHours().toString());
        commentTextArea.setText(vacancy.getComment());
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        academicHours.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getLoad().getAcademicHours().toString()));
        organizationHours.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getLoad().getOrganizationHours().toString()));
        additionalHours.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getLoad().getAdditionalHours().toString()));
        saveChangesButton.setOnAction(actionEvent -> {
            setActionToSaveChanges();
        });
        createNewEmployeeButton.setOnAction(actionEvent -> {
            setActionToSaveChanges();
            FXMLLoader fxmlLoader = new FXMLLoader(ShowVacancyWindowController.class
                    .getResource("/fxml/windows/add_performer_window.fxml"));
            fxmlLoader.setController(new AddingPerformerWindowController(vacancy));
            Stage dialog = new Stage();
            Scene scene;
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                System.out.println(e);
                throw new RuntimeException();
            }
            dialog.setScene(scene);
            dialog.show();
        });
        updateTable(employeeTable);
    }

    private void setActionToSaveChanges() {
        vacancy.setPost(postComboBox.getValue());
        vacancy.setSubject(subjectComboBox.getValue());
        vacancy.getLoad().setAcademicHours(Double.parseDouble(academicHoursField.getText()));
        vacancy.getLoad().setOrganizationHours(Double.parseDouble(organizationHoursField.getText()));
        vacancy.getLoad().setAdditionalHours(Double.parseDouble(additionalHoursField.getText()));
        vacancy.setComment(commentTextArea.getText());
        vacancyService.updateVacancy(vacancy);
        updateTable(employeeTable);
    }

    @Override
    protected List<Employee> updateTable(TableView<Employee> table) {
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        List<Employee> resultList = vacancyService.getAllPerformers(vacancy);
        employees.addAll(resultList);
        table.setItems(employees);
        Load load = vacancyService.findResidue(vacancy);
        return resultList;
    }
}

