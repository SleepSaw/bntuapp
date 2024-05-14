package bntu.accounting.application.controllers.windows;

import bntu.accounting.application.controllers.VisualComponentsInitializer;
import bntu.accounting.application.dao.impl.EmployeeDAOImpl;
import bntu.accounting.application.dao.interfaces.EmployeeDAO;
import bntu.accounting.application.models.fordb.Employee;
import bntu.accounting.application.models.builders.EmployeeBuilder;
import bntu.accounting.application.services.EmployeeService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import org.hibernate.HibernateException;

import java.net.URL;
import java.util.Optional;
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
        // обработчик на сек бокс контракта
        contractCheckBox.setOnAction(actionEvent -> {
            flag = !flag;
            contractValueField.setEditable(flag);
        });
        // обработчик кнопки "Редактировать"
        editEmployeeButton.setOnAction(actionEvent -> {
            try {
                employeeService.updateEmployee(employee,buildEmployee());
                Optional<ButtonType> result = showInformationAlert("Работник успешно изменен","");
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
        postComboBox.setValue(employee.getPost());
        subjectComboBox.setValue(employee.getSubject());
        if (employee.getVacancy() == null){
            initComboBox(postComboBox, "posts", employee.getPost());
            initComboBox(subjectComboBox, "subjects", employee.getSubject());
        }
        initComboBox(qualificationComboBox, "qualifications", employee.getQualification());
        initComboBox(categoryComboBox, "categories", employee.getCategory().toString());
        initComboBox(expComboBox, "experiences", employee.getExperience());
        specCheckBox.setSelected(employee.getYoungSpecialist());
        fioTextField.setText(employee.getName());
        contractValueField.setText(employee.getContractValue().toString());
        selectContractCheckBox();
    }
    private Employee buildEmployee(){
        EmployeeBuilder employeeBuilder = new EmployeeBuilder();
        return employeeBuilder
                .setName(fioTextField.getText())
                .setPost(postComboBox.getValue())
                .setSubject(subjectComboBox.getValue())
                .setCategory(Integer.parseInt(categoryComboBox.getValue()))
                .setExperience(expComboBox.getValue())
                .setQualification(qualificationComboBox.getValue())
                .setYoungSpecialist(specCheckBox.isSelected())
                .setContractValue(Double.parseDouble(contractValueField.getText()))
                .build();
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
