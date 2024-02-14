package bntu.accounting.application.controllers.windows;

import bntu.accounting.application.controllers.VisualComponentsInitializer;
import bntu.accounting.application.dao.impl.LoadDAOImpl;
import bntu.accounting.application.dao.interfaces.LoadDAO;
import bntu.accounting.application.models.Employee;
import bntu.accounting.application.models.Load;
import bntu.accounting.application.models.Vacancy;
import bntu.accounting.application.services.LoadService;
import bntu.accounting.application.services.VacancyService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class AddingPerformerWindowController extends VisualComponentsInitializer implements Initializable {
    private Vacancy vacancy;
    private VacancyService vacancyService = new VacancyService();
    private LoadService loadService = new LoadService();
    @FXML
    private Slider academicHoursSlider;

    @FXML
    private Button addPerformerButton;

    @FXML
    private Slider additionalHoursSlider;

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private Button clearAllFields;

    @FXML
    private CheckBox contractCheckBox;

    @FXML
    private TextField contractValueField;

    @FXML
    private ImageView employeeIcon;

    @FXML
    private ComboBox<String> expComboBox;

    @FXML
    private TextField fioTextField;

    @FXML
    private TextField fioTextField1;

    @FXML
    private TextField fioTextField11;

    @FXML
    private TextField fioTextField111;

    @FXML
    private TextField fioTextField112;

    @FXML
    private Slider organizationHoursSlider;

    @FXML
    private ComboBox<String> postComboBox;

    @FXML
    private ComboBox<String> qualificationComboBox;

    @FXML
    private CheckBox specCheckBox;

    @FXML
    private ComboBox<String> subjectComboBox;
    private boolean flag;

    public AddingPerformerWindowController(Vacancy vacancy) {
        this.vacancy = vacancy;
    }


    private void addEmployeeButtonAction() {
        Employee employee = new Employee();
        employee.setName(fioTextField.getText());
        employee.setPost(postComboBox.getValue());
        employee.setSubject(subjectComboBox.getValue());
        employee.setCategory(Integer.parseInt(categoryComboBox.getValue()));
        employee.setExperience(expComboBox.getValue());
        employee.setQualification(qualificationComboBox.getValue());
        employee.setYoungSpecialist(specCheckBox.isSelected());
        employee.setContractValue(Double.parseDouble(contractValueField.getText()));
        employee.setLoad(new Load());
        employee.getLoad().setAcademicHours(academicHoursSlider.getValue());
        employee.getLoad().setOrganizationHours(organizationHoursSlider.getValue());
        employee.getLoad().setAdditionalHours(additionalHoursSlider.getValue());
        employee.getLoad().setTotalHours(loadService.findTotalHours(employee.getLoad()));
        vacancyService.addPerformer(vacancy,employee);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            addPerformerButton.setOnAction(actionEvent -> {
                addEmployeeButtonAction();
            });
            initComboBox(postComboBox,"posts", vacancy.getPost());
            initComboBox(subjectComboBox,"subjects", vacancy.getSubject());
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
            academicHoursSlider.setMax(vacancy.getLoad().getAcademicHours());
            organizationHoursSlider.setMax(vacancy.getLoad().getOrganizationHours());
            additionalHoursSlider.setMax(vacancy.getLoad().getAdditionalHours());
        }
        catch (Exception e){
            System.out.printf(e.toString());
        }

    }
}
