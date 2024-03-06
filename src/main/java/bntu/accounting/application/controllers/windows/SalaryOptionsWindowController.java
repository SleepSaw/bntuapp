package bntu.accounting.application.controllers.windows;

import bntu.accounting.application.controllers.VisualComponentsInitializer;
import bntu.accounting.application.iojson.OptionsJsonHelper;
import bntu.accounting.application.models.Employee;
import bntu.accounting.application.models.SalaryOptions;
import bntu.accounting.application.models.Tariff;
import bntu.accounting.application.services.AllowancesService;
import bntu.accounting.application.util.db.entityloaders.EmployeesInstance;
import bntu.accounting.application.util.db.entityloaders.SalaryInstance;
import bntu.accounting.application.util.enums.AllowanceTypes;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SalaryOptionsWindowController extends VisualComponentsInitializer implements Initializable {

    private AllowancesService allowancesService = new AllowancesService();
    private List<TableColumn<SalaryOptions, String>> tariffColumns;
    private List<TableColumn<SalaryOptions, String>> experienceColumns;
    private List<TableColumn<SalaryOptions, String>> qualificationColumns;
    private SalaryOptions options;
    private OptionsJsonHelper helper = new OptionsJsonHelper();
    @FXML
    private TableView<SalaryOptions> tariffTable;

    @FXML
    private TableColumn<SalaryOptions, String> category7Column;

    @FXML
    private TableColumn<SalaryOptions, String> category8Column;

    @FXML
    private TableColumn<SalaryOptions, String> category9Column;
    @FXML
    private TableColumn<SalaryOptions, String> category10Column;
    @FXML
    private TableColumn<SalaryOptions, String> category11Column;
    @FXML
    private TableView<SalaryOptions> experienceTable;
    @FXML
    private TableColumn<SalaryOptions, String> exp1Column;

    @FXML
    private TableColumn<SalaryOptions, String> exp2Column;

    @FXML
    private TableColumn<SalaryOptions, String> exp3Column;

    @FXML
    private TableColumn<SalaryOptions, String> exp4Column;
    @FXML
    private TableView<SalaryOptions> qualificationTable;

    @FXML
    private TableColumn<SalaryOptions, String> qual1Column;

    @FXML
    private TableColumn<SalaryOptions, String> qual2Column;

    @FXML
    private TableColumn<SalaryOptions, String> qual3Column;

    @FXML
    private TableColumn<SalaryOptions, String> qual4Column;

    @FXML
    private TableColumn<SalaryOptions, String> qual5Column;
    @FXML
    private Label generalLabel;

    @FXML
    private TextField loadRateField;

    @FXML
    private TextField profActivityField;

    @FXML
    private Button saveButton;

    @FXML
    private TextField workInIndustryField;

    @FXML
    private TextField youngSpecialistField;
    @FXML
    private Label allowanceLabel;

    @FXML
    private TextField baseRateField;

    @FXML
    private Button cancelButton;

    public SalaryOptionsWindowController(Stage parent) {
        this.parent = parent;
    }

    private Stage parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saveButton.setOnAction(actionEvent -> {
            Optional<ButtonType> res = showConfirmingAlert("Сохранить изменения?","");
            if (res.get().getButtonData() == ButtonBar.ButtonData.OK_DONE){
                helper.writeToJson(options);
                SalaryInstance.getInstance().notifyObservers();
                EmployeesInstance.getInstance().notifyObservers();
                super.getStage().close();
            }
        });
        super.getStage().initModality(Modality.APPLICATION_MODAL);
        super.getStage().initOwner(parent);
        options = helper.readFromJson();
        initTables();
        try {
            baseRateField.setText(String.valueOf(options.getBaseRate()));
            loadRateField.setText(String.valueOf(options.getLoadRate()));
            workInIndustryField.setText(String.valueOf(options.getWorkInIndustryAllowance()* 100));
            youngSpecialistField.setText(String.valueOf(options.getYoungSpecialistAllowance() * 100));
            profActivityField.setText(String.valueOf(options.getProfActivityAllowance() * 100));
        } catch (RuntimeException e) {
            System.out.println("биля");
        }

    }
    private void initTables(){
        tariffColumns = List.of(category7Column, category8Column, category9Column,
                category9Column, category10Column, category11Column);
        experienceColumns = List.of(exp1Column,exp2Column,exp3Column,exp4Column);
        qualificationColumns = List.of(qual1Column,qual2Column,qual3Column,qual4Column,qual5Column);

        category7Column.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getTariffs().getCategory7())));
        category8Column.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getTariffs().getCategory8())));
        category9Column.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getTariffs().getCategory9())));
        category10Column.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getTariffs().getCategory10())));
        category11Column.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getTariffs().getCategory11())));

        exp1Column.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getExperienceAllowances().getStep1())));
        exp2Column.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getExperienceAllowances().getStep2())));
        exp3Column.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getExperienceAllowances().getStep3())));
        exp4Column.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getExperienceAllowances().getStep4())));

        qual1Column.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getQualificationAllowances().getStep1())));
        qual2Column.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getQualificationAllowances().getStep2())));
        qual3Column.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getQualificationAllowances().getStep3())));
        qual4Column.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getQualificationAllowances().getStep4())));
        qual5Column.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getQualificationAllowances().getStep5())));

        for (TableColumn<SalaryOptions, String> column : tariffColumns) {
            column.setCellFactory(TextFieldTableCell.forTableColumn());
            provideEditingOfColumn(column, AllowanceTypes.TARIFFS);
        }
        for (TableColumn<SalaryOptions, String> column : experienceColumns) {
            column.setCellFactory(TextFieldTableCell.forTableColumn());
            provideEditingOfColumn(column, AllowanceTypes.EXPERIENCE);
        }
        for (TableColumn<SalaryOptions, String> column : qualificationColumns) {
            column.setCellFactory(TextFieldTableCell.forTableColumn());
            provideEditingOfColumn(column, AllowanceTypes.QUALIFICATION);
        }
        updateTable(AllowanceTypes.TARIFFS);
        updateTable(AllowanceTypes.EXPERIENCE);
        updateTable(AllowanceTypes.QUALIFICATION);
    }
    private void provideEditingOfColumn(TableColumn<SalaryOptions, String> column, AllowanceTypes type) {
        column.setOnEditCommit(event -> {
            String key = column.getText();
            Double newValueFromCell = Double.valueOf(event.getNewValue());
            switch (type){
                case TARIFFS:
                    setTariffValue(key,newValueFromCell);
                    updateTable(AllowanceTypes.TARIFFS);
                    break;
                case EXPERIENCE:
                    setExpValue(key,newValueFromCell);
                    updateTable(AllowanceTypes.EXPERIENCE);
                    break;
                case QUALIFICATION:
                    setQualValue(key,newValueFromCell);
                    updateTable(AllowanceTypes.QUALIFICATION);
                    break;
            }
            tariffTable.refresh();
        });
    }
    private void setTariffValue(String key, Double newValueFromCell){
        switch (key) {
            case "7":
                options.getTariffs().setCategory7(newValueFromCell);
                break;
            case "8":
                options.getTariffs().setCategory8(newValueFromCell);
                break;
            case "9":
                options.getTariffs().setCategory9(newValueFromCell);
                break;
            case "10":
                options.getTariffs().setCategory10(newValueFromCell);
                break;
            case "11":
                options.getTariffs().setCategory11(newValueFromCell);
                break;
        }
    }
    private void setExpValue(String key, Double newValueFromCell){
        switch (key) {
            case "До 5 лет":
                options.getExperienceAllowances().setStep1(newValueFromCell);
                break;
            case "5-10 лет":
                options.getExperienceAllowances().setStep2(newValueFromCell);
                break;
            case "10-15 лет":
                options.getExperienceAllowances().setStep3(newValueFromCell);
                break;
            case "св. 15 лет":
                options.getExperienceAllowances().setStep4(newValueFromCell);
                break;
        }
    }
    private void setQualValue(String key, Double newValueFromCell){
        switch (key) {
            case "б.к.к.":
                options.getQualificationAllowances().setStep1(newValueFromCell);
                break;
            case "1-я к.к.":
                options.getQualificationAllowances().setStep2(newValueFromCell);
                break;
            case "2-я к.к.":
                options.getQualificationAllowances().setStep3(newValueFromCell);
                break;
            case "в.к.к.":
                options.getQualificationAllowances().setStep4(newValueFromCell);
                break;
            case "уч.-методист":
                options.getQualificationAllowances().setStep5(newValueFromCell);
                break;
        }
    }
    private void updateTable(AllowanceTypes type) {
        ObservableList<SalaryOptions> opt = FXCollections.observableArrayList(Collections.singleton(options));
        switch (type) {
            case TARIFFS:
                tariffTable.setItems(opt);
                break;
            case EXPERIENCE:
                experienceTable.setItems(opt);
                break;
            case QUALIFICATION:
                qualificationTable.setItems(opt);
                break;
        }
    }

}
