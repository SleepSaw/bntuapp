package bntu.accounting.application.controllers.pages;

import bntu.accounting.application.controllers.windows.AddingEmployeeWindowController;
import bntu.accounting.application.controllers.windows.EditingEmployeeWindowController;
import bntu.accounting.application.models.fordb.Employee;
import bntu.accounting.application.services.EmployeeService;
import bntu.accounting.application.util.db.entityloaders.EmployeesInstance;
import bntu.accounting.application.util.db.entityloaders.Observer;
import bntu.accounting.application.util.fxsupport.RowIndexer;
import bntu.accounting.application.util.fxsupport.WindowCreator;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeePageController implements Initializable, Observer {

    private EmployeeService employeeService = new EmployeeService();
    @FXML
    private Button addEmployeeButton;
    @FXML
    private Button updateTableButton;
    @FXML
    private ImageView saveIcon;
    @FXML
    private TableColumn<Employee, Double> categoryColumn;
    @FXML
    private TableColumn<Employee, Double> contractColumn;
    @FXML
    private TableView<Employee> employeeListTable;
    @FXML
    private TableColumn<Employee, String> expColumn;
    @FXML
    private TableColumn<Employee, Integer> indexColumn;
    @FXML
    private TableColumn<Employee, String> nameColumn;
    @FXML
    private TableColumn<Employee, String> postColumn;
    @FXML
    private TableColumn<Employee, String> qualColumn;
    @FXML
    private TableColumn<Employee, String> subjectColumn;
    @FXML
    private TableColumn<Employee, String> youngSpecialistColumn;
    @FXML
    private ContextMenu tableContextMenu;
    @FXML
    private MenuItem removeItem;
    @FXML
    private MenuItem editItem;

    @FXML
    void editItemAction(ActionEvent event) throws IOException {
        Employee selectedEmployee = getEmployeesFromTable();
        if (selectedEmployee != null) {
            WindowCreator.createWindow("/fxml/windows/edit_employee_window.fxml",this,
                    new EditingEmployeeWindowController(selectedEmployee));
        }
    }
    @FXML
    void removeItemAction(ActionEvent event) throws IOException {
        Employee selectedEmployee = getEmployeesFromTable();
        if (selectedEmployee != null) {
            employeeService.removeEmployee(selectedEmployee);
        }
        updateTable();
    }
    @FXML
    void addEmployeeButtonAction(ActionEvent event) throws IOException {
        WindowCreator.createWindow("/fxml/windows/add_employee_window.fxml", this,
                new AddingEmployeeWindowController());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EmployeesInstance.getInstance().attach(this);
        // Индексация строк
        RowIndexer.index(indexColumn);
        // Связываение полей объекта и колонок таблицы
        nameColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
        postColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("post"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("subject"));
        qualColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("qualification"));
        expColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("experience"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<Employee, Double>("category"));
        contractColumn.setCellValueFactory(new PropertyValueFactory<Employee, Double>("contractValue"));
        youngSpecialistColumn.setCellValueFactory(data -> new SimpleStringProperty(getYSWord(data.getValue().getYoungSpecialist())));
        updateTable();
    }
    private String getYSWord(boolean isYS){
        if(isYS) return "Да";
        return "Нет";
    }
    private void updateTable () {
            ObservableList<Employee> employees = FXCollections.observableArrayList();
            List<Employee> list = employeeService.getAllEmployees();
            employees.addAll(list);
            employeeListTable.setItems(employees);
    }
    private Employee getEmployeesFromTable(){
        return employeeListTable.getSelectionModel().getSelectedItem();
    }

    @Override
    public void update() {
        updateTable();
    }
}
