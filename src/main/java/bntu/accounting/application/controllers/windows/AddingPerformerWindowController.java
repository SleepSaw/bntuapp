package bntu.accounting.application.controllers.windows;

import bntu.accounting.application.controllers.VisualComponentsInitializer;
import bntu.accounting.application.models.Employee;
import bntu.accounting.application.models.Load;
import bntu.accounting.application.models.Vacancy;
import bntu.accounting.application.models.builders.EmployeeBuilder;
import bntu.accounting.application.services.LoadService;
import bntu.accounting.application.services.VacancyService;
import bntu.accounting.application.util.normalization.Normalizer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class AddingPerformerWindowController extends VisualComponentsInitializer implements Initializable {
    private Employee performer;
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

    public AddingPerformerWindowController(Employee performer, Vacancy vacancy) {
        this.performer = performer;
        this.vacancy = vacancy;
    }

    private void addEmployeeButtonAction() {
        EmployeeBuilder builder = new EmployeeBuilder();
        Employee employee = builder
                .setName(fioTextField.getText())
                .setPost(postComboBox.getValue())
                .setSubject(subjectComboBox.getValue())
                .setCategory(Integer.parseInt(categoryComboBox.getValue()))
                .setExperience(expComboBox.getValue())
                .setQualification(qualificationComboBox.getValue())
                .setYoungSpecialist(specCheckBox.isSelected())
                .setContractValue(Double.parseDouble(contractValueField.getText()))
                .build();
        Load load = new Load(
                academicHoursSlider.getValue(),
                organizationHoursSlider.getValue(),
                additionalHoursSlider.getValue()
        );
        load.setTotalHours(loadService.findTotalHours(employee.getLoad()));
        Normalizer.normalizeLoad(load);
        employee.setLoad(load);
        vacancyService.addPerformer(vacancy, employee);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            addPerformerButton.setOnAction(actionEvent -> {
                addEmployeeButtonAction();
            });
            if (performer != null) {
                fioTextField.setText(performer.getName());
                initComboBox(postComboBox, "posts", performer.getPost());
                initComboBox(subjectComboBox, "subjects", performer.getSubject());
                initComboBox(qualificationComboBox, "qualifications", performer.getQualification());
                initComboBox(categoryComboBox, "categories", performer.getCategory().toString());
                initComboBox(expComboBox, "experiences", performer.getExperience());
                contractValueField.setText(performer.getContractValue().toString());
                specCheckBox.setSelected(performer.getYoungSpecialist());
            } else {
                initComboBox(postComboBox, "posts", vacancy.getPost());
                initComboBox(subjectComboBox, "subjects", vacancy.getSubject());
                initComboBox(qualificationComboBox, "qualifications", "в.к.к.");
                initComboBox(categoryComboBox, "categories", "7");
                initComboBox(expComboBox, "experiences", "до 5 лет");
                contractValueField.setText("0.0");
                contractValueField.setEditable(false);
            }
            // обработчик на сек бокс контракта
            contractCheckBox.setOnAction(actionEvent -> {
                flag = !flag;
                contractValueField.setEditable(flag);
            });
        } catch (Exception e) {
            System.out.printf(e.toString());
        }

    }
}
