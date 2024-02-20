package bntu.accounting.application.controllers.templates;

import bntu.accounting.application.controllers.VisualComponentsInitializer;
import bntu.accounting.application.controllers.pages.VacanciesPageController;
import bntu.accounting.application.controllers.windows.ShowVacancyWindowController;
import bntu.accounting.application.models.Vacancy;
import bntu.accounting.application.services.VacancyService;
import bntu.accounting.application.util.fxsupport.WindowCreator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VacancyItemController extends VisualComponentsInitializer implements Initializable {
    private Vacancy vacancy;
    private VacancyService vacancyService = new VacancyService();
    @FXML
    private Label titleLabel;
    @FXML
    private Button showVacancyButton;
    @FXML
    private Button removeVacancyButton;

    @FXML
    private BorderPane vacancyItem;
    public VacancyItemController(Vacancy vacancy) {
        this.vacancy = vacancy;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleLabel.setText(vacancy.getPost() + ", " + vacancy.getSubject());
        showVacancyButton.setOnAction(actionEvent -> {
            try {
                WindowCreator.createWindow("/fxml/windows/show_vacancy_window.fxml",this,
                        new ShowVacancyWindowController(vacancy));
            } catch (LoadException e) {
                System.out.println(e);
                throw new RuntimeException(e);
            }
        });
        removeVacancyButton.setOnAction(event ->{
            vacancyService.removeVacancy(vacancy);
        });

    }
}
