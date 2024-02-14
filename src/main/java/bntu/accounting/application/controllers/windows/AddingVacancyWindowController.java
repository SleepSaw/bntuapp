package bntu.accounting.application.controllers.windows;

import bntu.accounting.application.controllers.VisualComponentsInitializer;
import bntu.accounting.application.models.Load;
import bntu.accounting.application.models.Vacancy;
import bntu.accounting.application.services.LoadService;
import bntu.accounting.application.services.VacancyService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddingVacancyWindowController extends VisualComponentsInitializer implements Initializable {
    private LoadService loadService = new LoadService();
    private VacancyService vacancyService = new VacancyService();
    @FXML
    private TextField academicLoadField;

    @FXML
    private TextField additionalLoadField;

    @FXML
    private Button clearAllFieldsButton;

    @FXML
    private TextArea commentTextArea;

    @FXML
    private TextField organizationLoadField;

    @FXML
    private ComboBox<String> postComboBox;

    @FXML
    private Button saveVacancyButton;

    @FXML
    private ComboBox<String> subjectComboBox;

    @FXML
    void clearAllFieldsButtonAction(ActionEvent event) {

    }

    @FXML
    void saveVacancyButtonAction(ActionEvent event) {
        Vacancy vacancy = new Vacancy();
        Load load = new Load();
        load.setAcademicHours(Double.parseDouble(academicLoadField.getText()));
        load.setOrganizationHours(Double.parseDouble(organizationLoadField.getText()));
        load.setAdditionalHours(Double.parseDouble(additionalLoadField.getText()));
        load.setTotalHours(loadService.findTotalHours(load));
        vacancy.setLoad(load);
        vacancy.setPost(postComboBox.getValue());
        vacancy.setSubject(subjectComboBox.getValue());
        vacancy.setComment(commentTextArea.getText());
        vacancyService.saveVacancy(vacancy);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initComboBox(postComboBox,"posts","Учитель");
        initComboBox(subjectComboBox,"subjects","Математика");
    }
}
