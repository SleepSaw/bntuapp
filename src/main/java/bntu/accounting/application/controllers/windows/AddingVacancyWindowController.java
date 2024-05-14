package bntu.accounting.application.controllers.windows;

import bntu.accounting.application.controllers.VisualComponentsInitializer;
import bntu.accounting.application.models.fordb.Load;
import bntu.accounting.application.models.fordb.Vacancy;
import bntu.accounting.application.services.LoadService;
import bntu.accounting.application.services.VacancyService;
import bntu.accounting.application.util.enums.VacancyStatus;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.hibernate.HibernateException;

import java.net.URL;
import java.util.Optional;
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



   private void saveVacancyButtonAction() {
        try{
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
            vacancy.setStatus(VacancyStatus.OPENED);
            vacancyService.saveVacancy(vacancy);
            Optional<ButtonType> result = showInformationAlert("Вакансия успешно сохранена","");
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

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       saveVacancyButton.setOnAction(e -> saveVacancyButtonAction());
        initComboBox(postComboBox,"posts","Учитель");
        initComboBox(subjectComboBox,"subjects","Математика");
    }
}
