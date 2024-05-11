package bntu.accounting.application.controllers.windows;

import bntu.accounting.application.controllers.VisualComponentsInitializer;
import bntu.accounting.application.controllers.pages.TarifficationPageController;
import bntu.accounting.application.util.enums.PagesNames;
import bntu.accounting.application.util.fxsupport.WindowCreator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;;import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController extends VisualComponentsInitializer implements Initializable {
    // Текст
    @FXML
    private Label homeLabel;
    @FXML
    private Label employeeLabel;
    @FXML
    private Label loadLabel;
    @FXML
    private Label salaryLabel;
    @FXML
    private Label vacancyLabel;
    // Кнопки
    @FXML
    private Button employeeButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button helpButton;
    @FXML
    private Button homeButton;
    @FXML
    private Button loadButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button salaryButton;
    @FXML
    private Button vacancyButton;
    // Иконки
    @FXML
    private ImageView employeeIcon;
    @FXML
    private ImageView exitIcon;
    @FXML
    private ImageView helpIcon;
    @FXML
    private ImageView homeIcon;
    @FXML
    private ImageView loadIcon;
    @FXML
    private ImageView settingsIcon;
    @FXML
    private ImageView salaryIcon;
    @FXML
    private ImageView vacancyIcon;
    // Страницы
    @FXML
    private BorderPane tarifficationPage;
    @FXML
    private BorderPane vacancyPage;
    @FXML
    private BorderPane loadPage;
    @FXML
    private BorderPane salaryPage;
    @FXML
    private BorderPane employeePage;
    @FXML
    private MenuItem saveMenuItem;
    @FXML
    private MenuItem salaryOptionsMenuItem;


    @FXML
    void employeeButtonAction(ActionEvent event) {
        showPage(PagesNames.EMPLOYEE);
        drawPageSelector(PagesNames.EMPLOYEE);
    }
    @FXML
    void homeButtonAction(ActionEvent event) {
        showPage(PagesNames.TARIFFICATION);
        drawPageSelector(PagesNames.TARIFFICATION);
    }

    @FXML
    void loadButtonAction(ActionEvent event) {
        showPage(PagesNames.LOAD);
        drawPageSelector(PagesNames.LOAD);
    }

    @FXML
    void salaryButtonAction(ActionEvent event) {
        showPage(PagesNames.SALARY);
        drawPageSelector(PagesNames.SALARY);
    }

    @FXML
    void vacancyButtonAction(ActionEvent event) {
        showPage(PagesNames.VACANCY);
        drawPageSelector(PagesNames.VACANCY);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showPage(PagesNames.TARIFFICATION);
        drawPageSelector(PagesNames.TARIFFICATION);
    }
    //Демонстрация страницы в зависимости от переданного имени страницы
    private void showPage(PagesNames name) {
        switch (name){
            case TARIFFICATION:
                tarifficationPage.setVisible(true);
                employeePage.setVisible(false);
                loadPage.setVisible(false);
                salaryPage.setVisible(false);
                vacancyPage.setVisible(false);
                break;
            case EMPLOYEE:
                tarifficationPage.setVisible(true);
                employeePage.setVisible(true);
                loadPage.setVisible(false);
                salaryPage.setVisible(false);
                vacancyPage.setVisible(false);
                break;
            case LOAD:
                tarifficationPage.setVisible(false);
                employeePage.setVisible(false);
                loadPage.setVisible(true);
                salaryPage.setVisible(false);
                vacancyPage.setVisible(false);
                break;
            case SALARY:
                tarifficationPage.setVisible(false);
                employeePage.setVisible(false);
                loadPage.setVisible(false);
                salaryPage.setVisible(true);
                vacancyPage.setVisible(false);
                break;
            case VACANCY:
                tarifficationPage.setVisible(false);
                employeePage.setVisible(false);
                loadPage.setVisible(false);
                salaryPage.setVisible(false);
                vacancyPage.setVisible(true);
                break;

        }
    }
    // Очищает указатели со всех страниц
    private void clearSelection(){
        homeLabel.setStyle("-fx-border-width: 0 0 0 0;");
        employeeLabel.setStyle("-fx-border-width: 0 0 0 0;");
        loadLabel.setStyle("-fx-border-width: 0 0 0 0;");
        salaryLabel.setStyle("-fx-border-width: 0 0 0 0;");
        vacancyLabel.setStyle("-fx-border-width: 0 0 0 0;");
    }
    // Отрисовка указателя страницы в виде нижнего подчеркивания
    private void drawPageSelector(PagesNames name){

        switch (name){
            case TARIFFICATION:
                clearSelection();
                homeLabel.setStyle("-fx-border-width: 0 0 2 0;" +
                        "-fx-border-color: #217346;");
                break;
            case EMPLOYEE:
                clearSelection();
                employeeLabel.setStyle("-fx-border-width: 0 0 2 0;" +
                        "-fx-border-color: #217346;");
                break;
            case LOAD:
                clearSelection();
                loadLabel.setStyle("-fx-border-width: 0 0 2 0;" +
                        "-fx-border-color: #217346;");
                break;
            case SALARY:
                clearSelection();
                salaryLabel.setStyle("-fx-border-width: 0 0 2 0;" +
                        "-fx-border-color: #217346;");
                break;
            case VACANCY:
                clearSelection();
                vacancyLabel.setStyle("-fx-border-width: 0 0 2 0;" +
                        "-fx-border-color: #217346;");
                break;
        }
    }

    public void saveMenuItemAction(ActionEvent actionEvent) {
        try {
            WindowCreator.createWindow("/fxml/windows/tariffication_window.fxml", this,
                    new TarifficationWindowController(super.getStage()));
        } catch (LoadException e) {
            throw new RuntimeException(e);
        }
    }

    public void salaryOptionsMenuItemAction(ActionEvent actionEvent) {
    }
}
