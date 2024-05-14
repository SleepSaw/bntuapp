package bntu.accounting.application.controllers;

import bntu.accounting.application.controllers.alerts.*;
import bntu.accounting.application.dao.impl.EmployeeDAOImpl;
import bntu.accounting.application.dao.interfaces.EmployeeDAO;
import bntu.accounting.application.models.fordb.Employee;
import bntu.accounting.application.iojson.FileLoader;
import bntu.accounting.application.services.EmployeeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VisualComponentsInitializer implements AlertManager {
    private EmployeeDAO employeeDAO = new EmployeeDAOImpl();
    private EmployeeService employeeService = new EmployeeService();
    // название файла с конфигурацией приложения
    private final String filePath = "employee_data.json";
    private final FileLoader loader = new FileLoader();
    private Stage stage;
    public VisualComponentsInitializer() {
    }
    public VisualComponentsInitializer(Stage stage) {
        this.stage = stage;
    }

    protected void initComboBox(ComboBox<String> comboBox, String jsonKey, String defaultValue){
        try {
            JSONObject json = loader.loadJsonFile(filePath);
            JSONArray jsonArray = (JSONArray) json.get(jsonKey);
            List<String> itemsList = new ArrayList<>();
            for (Object item: jsonArray) {
                itemsList.add((String)item);
            }
            ObservableList<String> items = FXCollections.observableList(itemsList);
            comboBox.setItems(items);
            comboBox.setValue(defaultValue);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    protected List<Employee> updateTable(TableView<Employee> table) {
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        List<Employee> resultList = employeeDAO.getAllEmployees();
        employees.addAll(resultList);
        table.setItems(employees);
        return resultList;
    }
    protected List<Employee> specialUpdateTable(TableView<Employee> table) {
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        List<Employee> resultList = employeeService.getBestEmployees();
        employees.addAll(resultList);
        table.setItems(employees);
        return resultList;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @Override
    public Optional<ButtonType> showInformationAlert(String header, String message) {
        Alerts informationAlert = new InformationAlert();
        return informationAlert.showAlert(header, message, stage);
    }

    @Override
    public Optional<ButtonType> showWarningAlert(String header, String message) {
        Alerts warningAlert = new WarningAlert();
        return warningAlert.showAlert(header, message, stage);
    }

    @Override
    public Optional<ButtonType> showErrorAlert(String header, String message) {
        Alerts errorAlert = new ErrorAlert();
        return errorAlert.showAlert(header, message, stage);
    }

    @Override
    public Optional<ButtonType> showConfirmingAlert(String header, String message) {
        ConfirmingAlert confirmingAlert = new ConfirmingAlert();
        return confirmingAlert.showAlert(header, message, stage);
    }
}
