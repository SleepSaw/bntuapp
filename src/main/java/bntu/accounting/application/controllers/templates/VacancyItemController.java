package bntu.accounting.application.controllers.templates;

import bntu.accounting.application.controllers.VisualComponentsInitializer;
import bntu.accounting.application.controllers.pages.VacanciesPageController;
import bntu.accounting.application.controllers.windows.AddingEmployeeWindowController;
import bntu.accounting.application.controllers.windows.EditingEmployeeWindowController;
import bntu.accounting.application.controllers.windows.ShowVacancyWindowController;
import bntu.accounting.application.models.Employee;
import bntu.accounting.application.models.Load;
import bntu.accounting.application.models.Vacancy;
import bntu.accounting.application.services.EmployeeService;
import bntu.accounting.application.services.VacancyService;
import bntu.accounting.application.util.db.entityloaders.Observer;
import bntu.accounting.application.util.db.entityloaders.VacancyInstance;
import bntu.accounting.application.util.enums.VacancyStatus;
import bntu.accounting.application.util.fxsupport.WindowCreator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.hibernate.HibernateException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class VacancyItemController extends VisualComponentsInitializer implements Initializable, Observer {
    private Vacancy vacancy;
    private VacancyService vacancyService = new VacancyService();
    private EmployeeService employeeService = new EmployeeService();
    @FXML
    private Label postLabel;
    @FXML
    private Label subjectLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Button showVacancyButton;
    @FXML
    private Button removeVacancyButton;
    @FXML
    private FlowPane performersBox;

    @FXML
    private Pane vacancyItem;

    @FXML
    private Label necessaryAcademicLoadLabel;

    @FXML
    private Label necessaryAdditionalLoadLabel;

    @FXML
    private Label necessaryOrganizationLoadLabel;

    @FXML
    private Label necessaryTotalLoadLabel;
    @FXML
    private Label residueAcademicLoadLabel;

    @FXML
    private Label residueAdditionalLoadLabel;

    @FXML
    private Label residueOrganizationLoadLabel;

    @FXML
    private Label residueTotalLoadLabel;

    private List<Button> performers = new ArrayList<>();

    public VacancyItemController(Vacancy vacancy) {
        this.vacancy = vacancy;
    }
    private void showLoad(Load residue){
        necessaryAcademicLoadLabel.setText(String.valueOf(vacancy.getLoad().getAcademicHours()));
        necessaryOrganizationLoadLabel.setText(String.valueOf(vacancy.getLoad().getOrganizationHours()));
        necessaryAdditionalLoadLabel.setText(String.valueOf(vacancy.getLoad().getAdditionalHours()));
        necessaryTotalLoadLabel.setText(String.valueOf(vacancy.getLoad().getTotalHours()));
        residueAcademicLoadLabel.setText(String.valueOf(residue.getAcademicHours()));
        residueOrganizationLoadLabel.setText(String.valueOf(residue.getOrganizationHours()));
        residueAdditionalLoadLabel.setText(String.valueOf(residue.getAdditionalHours()));
        residueTotalLoadLabel.setText(String.valueOf(residue.getTotalHours()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        update();
        showVacancyButton.setOnAction(actionEvent -> {
            try {
                WindowCreator.createWindow("/fxml/windows/show_vacancy_window.fxml",this,
                        new ShowVacancyWindowController(super.getStage(),vacancy));
            } catch (LoadException e) {
                System.out.println(e);
                throw new RuntimeException(e);
            }
        });
        for (Button b : performers){
            b.setOnAction(actionEvent -> {
                try {
                    WindowCreator.createWindow("/fxml/windows/edit_employee_window.fxml", this,
                            new EditingEmployeeWindowController(
                                    vacancyService.findPerformerByName(vacancy,b.getText())));
                } catch (LoadException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        removeVacancyButton.setOnAction(event ->{
            if (vacancy.getEmployeeList() != null && !vacancy.getEmployeeList().isEmpty()){
                Optional<ButtonType> result = showWarningAlert("На вакансию назначены исполнители",
                        "Вы можете удалить вакансию с привязанными к ней исполнителями или же без них");
                if (result.get().getButtonData() == ButtonBar.ButtonData.OK_DONE){
                    try {
                        vacancyService.removeVacancy(vacancy);
                        showInformationAlert("Вакансия успешно удалена","");
                    }
                    catch (HibernateException e){
                        showErrorAlert("Ошибка базы данных","Не удалось удалить вакансию из базы данных");
                    }
                    catch (RuntimeException e){
                        showErrorAlert("Возникла неизвестная ошибка","");
                    }

                }
            }
            else {
                Optional<ButtonType> result = showConfirmingAlert("Удаление вакансии",
                        "Вы действительно хотите удалить вакансию?");
                if (result.get().getButtonData() == ButtonBar.ButtonData.OK_DONE){
                    try {
                        vacancyService.removeVacancy(vacancy);
                        showInformationAlert("Вакансия успешно удалена","");
                    }
                    catch (HibernateException e){
                        showErrorAlert("Ошибка базы данных","Не удалось удалить вакансию из базы данных");
                    }
                    catch (RuntimeException e){
                        showErrorAlert("Возникла неизвестная ошибка","");
                    }
                }
            }

        });
        showLoad(vacancyService.findResidue(vacancy));
    }
    private void showStatus(String status){
        switch (status){
            case "OPENED":
                statusLabel.setText("Открыта");
                statusLabel.setStyle("-fx-background-color: #d6ffe8");
                break;
            case "PARTIALLY_CLOSED":
                statusLabel.setText("Частично закрыта");
                statusLabel.setStyle("-fx-background-color:  #fdf8e7");
                statusLabel.setPrefWidth(130);
                break;
            case "CLOSED":
                statusLabel.setText("Закрыта");
                statusLabel.setStyle("-fx-background-color: #f89c9c");
                break;
        }
    }
    private void addPerformersToBox(){
        performersBox.getChildren().clear();
        for (Employee e: vacancy.getEmployeeList()) {
            Button b = new Button(e.getName());
            b.setStyle("-fx-text-fill: #468ffd;" +
                    "-fx-background-color: transparent;" +
                    "-fx-underline: true;");
            performers.add(b);
            performersBox.getChildren().add(b);
        }
    }
    @Override
    public void update() {
        postLabel.setText(vacancy.getPost());
        subjectLabel.setText(vacancy.getSubject());
        showStatus(vacancy.getStatus());
        addPerformersToBox();
    }
}
