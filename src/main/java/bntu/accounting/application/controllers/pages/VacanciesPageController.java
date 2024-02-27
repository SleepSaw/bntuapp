package bntu.accounting.application.controllers.pages;

import bntu.accounting.application.controllers.templates.VacancyItemController;
import bntu.accounting.application.controllers.windows.AddingVacancyWindowController;
import bntu.accounting.application.models.Vacancy;
import bntu.accounting.application.services.VacancyService;
import bntu.accounting.application.util.db.entityloaders.Observer;
import bntu.accounting.application.util.db.entityloaders.VacancyInstance;
import bntu.accounting.application.util.fxsupport.WindowCreator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class VacanciesPageController implements Initializable, Observer {
    private VacancyService vacancyService = new VacancyService();
    @FXML
    private Button addVacancyButton;
    @FXML
    private VBox vacanciesContainer;

    @FXML
    private void addVacancyButtonAction(ActionEvent event) throws IOException {
        WindowCreator.createWindow("/fxml/windows/add_vacancy_window.fxml", this,
                new AddingVacancyWindowController());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        VacancyInstance.getInstance().attach(this);
        try {
            createVacanciesItems();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void createVacanciesItems() throws IOException {
        vacanciesContainer.getChildren().clear();
        List<Vacancy> vacancies = vacancyService.getAllVacancies();
        for (Vacancy v : vacancies) {
            FXMLLoader fxmlLoader = new FXMLLoader(VacanciesPageController
                    .class.getResource("/fxml/templates/vacancy_item.fxml"));
            fxmlLoader.setController(new VacancyItemController(v));
            vacanciesContainer.getChildren().add(fxmlLoader.load());
        }

    }
    @Override
    public void update() {
        try {
            createVacanciesItems();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
