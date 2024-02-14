package bntu.accounting.application.controllers.windows;

import bntu.accounting.application.controllers.VisualComponentsInitializer;
import bntu.accounting.application.dao.impl.EmployeeDAOImpl;
import bntu.accounting.application.dao.impl.LoadDAOImpl;
import bntu.accounting.application.dao.interfaces.EmployeeDAO;
import bntu.accounting.application.dao.interfaces.LoadDAO;
import bntu.accounting.application.models.Employee;
import bntu.accounting.application.models.Load;
import bntu.accounting.application.models.Salary;
import bntu.accounting.application.services.EmployeeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddingEmployeeWindowController extends VisualComponentsInitializer implements Initializable {
   private EmployeeService employeeService = new EmployeeService();

    @FXML
    private CheckBox contractCheckBox;
    @FXML
    private CheckBox specCheckBox;

    @FXML
    private Button addEmployeeButton;
    @FXML
    private Button clearAllFields;

    @FXML
    private TextField fioTextField;
    @FXML
    private TextField contractValueField;

    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private ComboBox<String> expComboBox;
    @FXML
    private ComboBox<String> postComboBox;
    @FXML
    private ComboBox<String> qualificationComboBox;
    @FXML
    private ComboBox<String> subjectComboBox;
    private boolean flag = false;

    @FXML
    void addEmployeeButtonAction(ActionEvent event) {
        Employee employee = new Employee();
        employee.setName(fioTextField.getText());
        employee.setPost(postComboBox.getValue());
        employee.setSubject(subjectComboBox.getValue());
        employee.setCategory(Integer.parseInt(categoryComboBox.getValue()));
        employee.setExperience(expComboBox.getValue());
        employee.setQualification(qualificationComboBox.getValue());
        employee.setYoungSpecialist(specCheckBox.isSelected());
        employee.setContractValue(Double.parseDouble(contractValueField.getText()));
        employeeService.saveEmployee(employee);
    }

    @FXML
    void clearAllFieldsAction(ActionEvent event) {
        fioTextField.setText(null);
        contractValueField.setText("0.0");
        postComboBox.setValue(null);
        subjectComboBox.setValue(null);
        expComboBox.setValue(null);
        qualificationComboBox.setValue(null);
        categoryComboBox.setValue(null);
        specCheckBox.setSelected(false);
        contractCheckBox.setSelected(false);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initComboBox(postComboBox,"posts","Учитель");
        initComboBox(subjectComboBox,"subjects","Математика");
        initComboBox(qualificationComboBox,"qualifications","в.к.к.");
        initComboBox(categoryComboBox,"categories","7");
        initComboBox(expComboBox,"experiences","до 5 лет");
        contractValueField.setText("0.0");
        contractValueField.setEditable(false);
        // обработчик на сек бокс контракта
        contractCheckBox.setOnAction(actionEvent -> {
            flag = !flag;
            contractValueField.setEditable(flag);
        });
    }

}
