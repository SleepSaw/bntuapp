package bntu.accounting.application.controllers.pages;

import bntu.accounting.application.controllers.templates.VacancyItemController;
import bntu.accounting.application.controllers.windows.EditingEmployeeWindowController;
import bntu.accounting.application.models.Employee;
import bntu.accounting.application.models.Vacancy;
import bntu.accounting.application.services.VacancyService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class VacanciesPageController implements Initializable {
    private VacancyService vacancyService = new VacancyService();
    @FXML
    private Button addVacancyButton;
    @FXML
    private VBox vBoxMy;

    @FXML
    private void addVacancyButtonAction(ActionEvent event) throws IOException {
        FXMLLoader l = new FXMLLoader(VacanciesPageController.class.getResource("/fxml/windows/add_vacancy_window.fxml"));
        Stage dialog = new Stage();
        Scene scene = new Scene(l.load());
        dialog.setScene(scene);

        dialog.show();
    }

    private List<BorderPane> vacancyItems;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            createVacanciesItems();
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    private void createVacanciesItems() throws IOException {
        List<Vacancy> vacancies = vacancyService.getAllVacancies();
        for (Vacancy v : vacancies) {
            FXMLLoader fxmlLoader = new FXMLLoader(VacanciesPageController
                    .class.getResource("/fxml/templates/vacancy_item.fxml"));
            fxmlLoader.setController(new VacancyItemController(v));
            vBoxMy.getChildren().add(fxmlLoader.load());
        }

    }
}
