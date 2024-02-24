package bntu.accounting.application.controllers.windows;

import bntu.accounting.application.controllers.VisualComponentsInitializer;
import bntu.accounting.application.models.Employee;
import bntu.accounting.application.models.Load;
import bntu.accounting.application.models.Vacancy;
import bntu.accounting.application.models.builders.EmployeeBuilder;
import bntu.accounting.application.services.LoadService;
import bntu.accounting.application.services.VacancyService;
import bntu.accounting.application.util.fxsupport.TextFieldValidator;
import bntu.accounting.application.util.normalization.Normalizer;
import bntu.accounting.application.util.notifications.AlertInitializer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.HibernateException;

import java.net.URL;
import java.util.List;
import java.util.Optional;
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
    private TextField academicHoursField;

    @FXML
    private TextField organizationHoursField;

    @FXML
    private TextField additionalHoursField;

    @FXML
    private TextField totalHoursField;

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
    @FXML
    private BorderPane performerWindow;
    private boolean flag;
    private Double sum = 0D;

    public AddingPerformerWindowController(Vacancy vacancy) {
        this.vacancy = vacancy;

    }

    public AddingPerformerWindowController(Employee performer, Vacancy vacancy) {
        this.performer = performer;
        this.vacancy = vacancy;
    }



    private void addEmployeeButtonAction() {
        try {
            Employee employee = buildEmployee();
            Load load = new Load(
                    academicHoursSlider.getValue(),
                    organizationHoursSlider.getValue(),
                    additionalHoursSlider.getValue()
            );
            load.setTotalHours(loadService.findTotalHours(load));
            employee.setLoad(load);
            vacancyService.addPerformer(vacancy, employee);
            showGoodAlert();
        } catch (HibernateException e) {
            showErrorAlert("Ошибка базы данных","Попробуйте ещё раз");
        } catch (RuntimeException e) {
            return;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initVisualComponents();
            addPerformerButton.setOnAction(actionEvent -> {
                showAgreementAlert();
            });
            showLoadData();
            totalHoursField.setText(String.valueOf(findSum()));
            // обработчик на сек бокс контракта
            contractCheckBox.setOnAction(actionEvent -> {
                flag = !flag;
                contractValueField.setEditable(flag);
            });
            clearAllFields.setOnAction(actionEvent -> {
                // TODO
            });
        } catch (NumberFormatException e) {
            showErrorAlert("Ошибка ввода","Проверьте введенные данные");

        }
    }

    private Double findSum() {
        return academicHoursSlider.getValue() +
                organizationHoursSlider.getValue() +
                additionalHoursSlider.getValue();
    }

    private void showLoadData() {
        Load residue = (vacancyService.findResidue(vacancy));
        Normalizer.normalizeLoad(residue);
        academicHoursSlider.setMax(residue.getAcademicHours());
        organizationHoursSlider.setMax(residue.getOrganizationHours());
        additionalHoursSlider.setMax(residue.getAdditionalHours());
    }

    private void initVisualComponents() {
        TextFieldValidator.preventLettersInput(academicHoursField);
        TextFieldValidator.preventLettersInput(organizationHoursField);
        TextFieldValidator.preventLettersInput(additionalHoursField);
        TextFieldValidator.preventLettersInput(contractValueField);
        bindSliderToField(academicHoursSlider, academicHoursField);
        bindSliderToField(organizationHoursSlider, organizationHoursField);
        bindSliderToField(additionalHoursSlider, additionalHoursField);
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
            postComboBox.setValue(vacancy.getPost());
            subjectComboBox.setValue(vacancy.getSubject());
            initComboBox(qualificationComboBox, "qualifications", "в.к.к.");
            initComboBox(categoryComboBox, "categories", "7");
            initComboBox(expComboBox, "experiences", "до 5 лет");
            contractValueField.setText("0.0");
            contractValueField.setEditable(false);
        }
    }

    /**
     * Метод для двунаправленной привязки значений слайдеров и текстовых полей нагрузки.
     * Изменение значений того или иного объекта приведет к автоматическому изменению значения
     * другого.
     *
     * @param slider слайдер для привзяки
     * @param field  текстовое поле
     */
    private void bindSliderToField(Slider slider, TextField field) throws NumberFormatException {
        // Значение слайдера отражается в текстовом поле
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
                Double v = newValue.doubleValue();
                field.setText(String.valueOf(v));
                totalHoursField.setText(String.valueOf(findSum()));

            }
        });
        field.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    if (!field.getText().equals("")) {
                        slider.setValue(Double.parseDouble(field.getText()));
                        totalHoursField.setText(String.valueOf(findSum()));
                    } else {
                        try {
                            slider.setValue(Double.parseDouble(field.getText()));
                            totalHoursField.setText(String.valueOf(findSum()));
                        } catch (NumberFormatException e) {
                            slider.setValue(0);
                            field.setText("");
                            totalHoursField.setText(String.valueOf(findSum()));
                        }
                    }
                }
            }
        });
    }

    private Employee buildEmployee() {
        EmployeeBuilder builder = new EmployeeBuilder();
        builder
                .setName(fioTextField.getText())
                .setPost(postComboBox.getValue())
                .setSubject(subjectComboBox.getValue())
                .setExperience(expComboBox.getValue())
                .setQualification(qualificationComboBox.getValue())
                .setYoungSpecialist(specCheckBox.isSelected());
        try {
            builder.setContractValue(Double.parseDouble(contractValueField.getText()))
                    .setCategory(Integer.parseInt(categoryComboBox.getValue()));
        } catch (NumberFormatException e) {
            showErrorAlert("Ошибка ввода","Проверьте введенные данные");
            throw new RuntimeException();
        }
        return builder.build();
    }


    private void showAgreementAlert() {
        ButtonType saveButton = new ButtonType("Сохранить", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert agreementAlert = new Alert(Alert.AlertType.CONFIRMATION,"Подтверждение",
                saveButton,cancelButton);
        AlertInitializer alertInitializer = new AlertInitializer(performerWindow, agreementAlert,
                "Сохранить исполнителя?",
                "Подтвердите создание исполнителя нажав на кнопку \"ОК\"");
        alertInitializer.init();
        Optional<ButtonType> result = agreementAlert.showAndWait();
        if (result.get() == saveButton) {
            addEmployeeButtonAction();
        } else if (result.get() == cancelButton) {
            return;
        }
    }
    private void showGoodAlert() {
        ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        Alert successesAlert = new Alert(Alert.AlertType.INFORMATION,"",
                okButton);
        AlertInitializer alertInitializer = new AlertInitializer(performerWindow, successesAlert,
                "Испольнитель успешно сохранён",
                "Нажмите кнопку \"ОК\" для продолжения работы");
        alertInitializer.init();
        Optional<ButtonType> result = successesAlert.showAndWait();
        if (result.get() == okButton) {
           return;
        }
    }

    private void showErrorAlert(String header,String message) {
        ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        Alert errorAlert = new Alert(Alert.AlertType.ERROR,"Произошла ошибка",
                okButton);
        AlertInitializer alertInitializer = new AlertInitializer(performerWindow, errorAlert,
                header,
                message);
        alertInitializer.init();
        Optional<ButtonType> result = errorAlert.showAndWait();
        if (result.get() == okButton) {
            return;
        }
    }
}
