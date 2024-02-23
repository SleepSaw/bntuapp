package bntu.accounting.application.controllers.templates;

import bntu.accounting.application.controllers.VisualComponentsInitializer;
import bntu.accounting.application.controllers.pages.VacanciesPageController;
import bntu.accounting.application.controllers.windows.ShowVacancyWindowController;
import bntu.accounting.application.models.Employee;
import bntu.accounting.application.models.Vacancy;
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
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VacancyItemController extends VisualComponentsInitializer implements Initializable, Observer {
    private Vacancy vacancy;
    private VacancyService vacancyService = new VacancyService();
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
    private BorderPane vacancyItem;
    public VacancyItemController(Vacancy vacancy) {
        this.vacancy = vacancy;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        VacancyInstance.getInstance().attach(this);
        update();
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
