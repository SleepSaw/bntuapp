package bntu.accounting.application.controllers.pages;

import bntu.accounting.application.controllers.VisualComponentsInitializer;
import bntu.accounting.application.models.Employee;
import bntu.accounting.application.models.Salary;
import bntu.accounting.application.services.AllowancesService;
import bntu.accounting.application.services.SalaryService;
import bntu.accounting.application.util.RowIndexer;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SalaryPageController extends VisualComponentsInitializer implements Initializable {

    private SalaryService salaryService = new SalaryService();
    @FXML
    private TableColumn<Employee, String> categoryColumn;

    @FXML
    private TableColumn<Employee, String> coefColumn;

    @FXML
    private TableColumn<Employee, Integer> indexColumn;

    @FXML
    private TableColumn<Employee, String> loadColumn;

    @FXML
    private Button moreButton;

    @FXML
    private TableColumn<Employee, String> nameColumn;

    @FXML
    private Button saveButton;

    @FXML
    private ImageView saveIcon;
    @FXML
    private TableView<Employee> salaryTable;

    @FXML
    private TableColumn<Employee, String> totalAllowancesColumn;

    @FXML
    private TableColumn<Employee, String> totalSalaryColumn;

    @FXML
    private TableColumn<Employee, String> withLoadSalaryColumn;

    @FXML
    private TableColumn<Employee, String> withoutLoadSalaryColumn;
    @FXML
    void moreButtonAction(ActionEvent event) throws IOException {
        FXMLLoader l = new FXMLLoader(VacanciesPageController.class.getResource("/fxml/windows/salary_window.fxml"));
        Stage dialog = new Stage();
        Scene scene = new Scene(l.load());
        dialog.setScene(scene);
        dialog.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RowIndexer.index(indexColumn);
        findActualSalary(updateTable(salaryTable));
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        loadColumn.setCellValueFactory(data -> new SimpleStringProperty(Double.toString(data.getValue()
                .getLoad().getTotalHours())));
        categoryColumn.setCellValueFactory(data -> new SimpleStringProperty(Integer.toString(data.getValue()
                .getCategory())));
        coefColumn.setCellValueFactory(data -> new SimpleStringProperty(Double.toString(salaryService
                .getTariffByCategory(data.getValue().getCategory()))));
        withoutLoadSalaryColumn.setCellValueFactory(data -> new SimpleStringProperty(Double.toString
                (data.getValue().getSalary().getRateSalary())));
        withLoadSalaryColumn.setCellValueFactory(data -> new SimpleStringProperty
                (Double.toString(data.getValue().getSalary().getLoadSalary())));
        totalAllowancesColumn.setCellValueFactory(data -> new SimpleStringProperty(
                Double.toString(salaryService.getTotalAllowances(data.getValue()))));
        totalSalaryColumn.setCellValueFactory(data -> new SimpleStringProperty(
                Double.toString(data.getValue().getSalary().getTotalSalary())));
    }
    private void findActualSalary(List<Employee> employees){
        for (Employee e : employees) {
            salaryService.getTotalSalary(e);
        }
    }
}
