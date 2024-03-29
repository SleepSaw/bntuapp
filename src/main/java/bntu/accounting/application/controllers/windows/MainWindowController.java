package bntu.accounting.application.controllers.windows;

import bntu.accounting.application.util.enums.PagesNames;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;;import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
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
    private BorderPane homePage;
    @FXML
    private BorderPane vacancyPage;
    @FXML
    private BorderPane loadPage;
    @FXML
    private BorderPane salaryPage;
    @FXML
    private BorderPane employeePage;

    @FXML
    void employeeButtonAction(ActionEvent event) {
        showPage(PagesNames.EMPLOYEE);
        drawPageSelector(PagesNames.EMPLOYEE);
    }
    @FXML
    void homeButtonAction(ActionEvent event) {
        showPage(PagesNames.HOME);
        drawPageSelector(PagesNames.HOME);
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
        showPage(PagesNames.HOME);
        drawPageSelector(PagesNames.HOME);
    }
    //Демонстрация страницы в зависимости от переданного имени страницы
    private void showPage(PagesNames name) {
        switch (name){
            case HOME:
                homePage.setVisible(true);
                employeePage.setVisible(false);
                loadPage.setVisible(false);
                salaryPage.setVisible(false);
                vacancyPage.setVisible(false);
                break;
            case EMPLOYEE:
                homePage.setVisible(true);
                employeePage.setVisible(true);
                loadPage.setVisible(false);
                salaryPage.setVisible(false);
                vacancyPage.setVisible(false);
                break;
            case LOAD:
                homePage.setVisible(false);
                employeePage.setVisible(false);
                loadPage.setVisible(true);
                salaryPage.setVisible(false);
                vacancyPage.setVisible(false);
                break;
            case SALARY:
                homePage.setVisible(false);
                employeePage.setVisible(false);
                loadPage.setVisible(false);
                salaryPage.setVisible(true);
                vacancyPage.setVisible(false);
                break;
            case VACANCY:
                homePage.setVisible(false);
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
            case HOME:
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
}
