package bntu.accounting.application.controllers;

import bntu.accounting.application.dao.impl.EmployeeDAOImpl;
import bntu.accounting.application.dao.interfaces.EmployeeDAO;
import bntu.accounting.application.models.Employee;
import bntu.accounting.application.iojson.FileLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VisualComponentsInitializer {
    private EmployeeDAO employeeDAO = new EmployeeDAOImpl();
    // название файла с конфигурацией приложения
    private final String filePath = "options.json";
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

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
