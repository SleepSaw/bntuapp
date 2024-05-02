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
import javafx.scene.control.*;
import org.hibernate.HibernateException;

import java.net.URL;
import java.util.Optional;
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


    void addEmployeeButtonAction() {
        Employee employee = new Employee();
        employee.setName(fioTextField.getText());
        employee.setPost(postComboBox.getValue());
        employee.setSubject(subjectComboBox.getValue());
        employee.setCategory(Integer.parseInt(categoryComboBox.getValue()));
        employee.setExperience(expComboBox.getValue());
        employee.setQualification(qualificationComboBox.getValue());
        employee.setYoungSpecialist(specCheckBox.isSelected());
        if (flag) employee.setContractValue(Double.parseDouble(contractValueField.getText()));
        employeeService.saveEmployee(employee);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        flag = contractCheckBox.isSelected();
        addEmployeeButton.setOnAction(actionEvent -> {
            try{
                addEmployeeButtonAction();
                Optional<ButtonType> result = showInformationAlert("Работник успешно сохранён","");
                if (result.get().getButtonData() == ButtonBar.ButtonData.OK_DONE){
                    super.getStage().close();
                }
            }
            catch (HibernateException e){
                showErrorAlert("Ошибка базы данных","Не удалось удалить вакансию из базы данных");
            }
            catch (NullPointerException e){
                showErrorAlert("Ошибка данных","Похоже вы ввели пустое значение");
            }
            catch (RuntimeException e){
                showErrorAlert("Возникла неизвестная ошибка","");
            }
        });
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
