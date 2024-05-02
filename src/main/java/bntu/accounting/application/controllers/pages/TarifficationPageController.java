package bntu.accounting.application.controllers.pages;

import bntu.accounting.application.controllers.VisualComponentsInitializer;
import bntu.accounting.application.excel.TarifficationFileCreator;
import bntu.accounting.application.models.Item;
import bntu.accounting.application.services.EmployeeService;
import bntu.accounting.application.services.SalaryService;
import bntu.accounting.application.util.db.entityloaders.Observer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TarifficationPageController extends VisualComponentsInitializer implements Initializable {
    private EmployeeService employeeService = new EmployeeService();
    @FXML
    private Button saveButton;
    @FXML
    void saveButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            TarifficationFileCreator tarifficationFileCreator = new TarifficationFileCreator();
            List<Item> items = employeeService.getAllEmployees().stream().map(e -> e.getParent()).toList();
            tarifficationFileCreator.createFile(file.getPath(), items);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
