package bntu.accounting.application.controllers.windows;

import bntu.accounting.application.controllers.VisualComponentsInitializer;
import bntu.accounting.application.dao.impl.EmployeeDAOImpl;
import bntu.accounting.application.dao.interfaces.EmployeeDAO;
import bntu.accounting.application.models.Employee;
import bntu.accounting.application.models.builders.EmployeeBuilder;
import bntu.accounting.application.services.EmployeeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class EditingEmployeeWindowController extends VisualComponentsInitializer implements Initializable {
    private EmployeeDAO employeeDAO = new EmployeeDAOImpl();
    private EmployeeService employeeService = new EmployeeService();
    private Employee employee;
    private boolean flag;
    @FXML
    private CheckBox contractCheckBox;
    @FXML
    private CheckBox specCheckBox;

    @FXML
    private Button clearAllFieldsButton;
    @FXML
    private Button editEmployeeButton;

    @FXML
    private ImageView employeeIcon;

    @FXML
    private TextField fioTextField;

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
    @FXML
    private TextField contractValueField;

    public EditingEmployeeWindowController(Employee employee) {
        this.employee = employee;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EmployeeBuilder employeeBuilder = new EmployeeBuilder();
        // обработчик на сек бокс контракта
        contractCheckBox.setOnAction(actionEvent -> {
            flag = !flag;
            contractValueField.setEditable(flag);
        });
        // обработчик кнопки "Редактировать"
        editEmployeeButton.setOnAction(actionEvent -> {
            Employee updatedEmployee = employeeBuilder
                    .setName(fioTextField.getText())
                    .setPost(postComboBox.getValue())
                    .setSubject(subjectComboBox.getValue())
                    .setCategory(Integer.parseInt(categoryComboBox.getValue()))
                    .setExperience(expComboBox.getValue())
                    .setQualification(qualificationComboBox.getValue())
                    .setYoungSpecialist(specCheckBox.isSelected())
                    .setContractValue(Double.parseDouble(contractValueField.getText()))
                    .build();
            employeeService.updateEmployee(employee,updatedEmployee);
        });
        // Очистка полей
        clearAllFieldsButton.setOnAction(actionEvent -> {
            fioTextField.setText(null);
            contractValueField.setText("0.0");
            postComboBox.setValue(null);
            subjectComboBox.setValue(null);
            expComboBox.setValue(null);
            qualificationComboBox.setValue(null);
            categoryComboBox.setValue(null);
            specCheckBox.setSelected(false);
            contractCheckBox.setSelected(false);
        });
        initComboBox(postComboBox, "posts", employee.getPost());
        initComboBox(subjectComboBox, "subjects", employee.getSubject());
        initComboBox(qualificationComboBox, "qualifications", employee.getQualification());
        initComboBox(categoryComboBox, "categories", employee.getCategory().toString());
        initComboBox(expComboBox, "experiences", employee.getExperience());
        specCheckBox.setSelected(employee.getYoungSpecialist());
        fioTextField.setText(employee.getName());
        contractValueField.setText(employee.getContractValue().toString());
        selectContractCheckBox();
    }
    // Автоматическое выставление значения чек-бокса при появлении окна
    private void selectContractCheckBox(){
        if (employee.getContractValue() != 0D){
            contractValueField.setEditable(true);
            contractCheckBox.setSelected(true);
            flag = true;
        }
        else {
            contractValueField.setEditable(false);
            contractCheckBox.setSelected(false);
            flag = false;
        }
    }
}
