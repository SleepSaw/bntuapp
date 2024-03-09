package bntu.accounting.application.controllers.pages;

import bntu.accounting.application.controllers.VisualComponentsInitializer;
import bntu.accounting.application.controllers.exceptions.SettingIncorrectValue;
import bntu.accounting.application.models.Employee;
import bntu.accounting.application.models.Load;
import bntu.accounting.application.services.LoadService;
import bntu.accounting.application.services.VacancyService;
import bntu.accounting.application.util.db.entityloaders.EmployeesInstance;
import bntu.accounting.application.util.db.entityloaders.Observer;
import bntu.accounting.application.util.db.entityloaders.VacancyInstance;
import bntu.accounting.application.util.enums.LoadTypes;
import bntu.accounting.application.util.fxsupport.RowIndexer;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static bntu.accounting.application.util.enums.LoadTypes.ACADEMIC;

public class LoadPageController extends VisualComponentsInitializer implements Initializable, Observer {
    private VacancyService vacancyService = new VacancyService();
    private LoadService loadService = new LoadService();
    @FXML
    private TableColumn<Employee, String> academicLoadColumn;
    @FXML
    private TableColumn<Employee, String> addLoadColumn;
    @FXML
    private TableColumn<Employee, Integer> indexColumn;
    @FXML
    private TableColumn<Employee, String> nameColumn;
    @FXML
    private TableColumn<Employee, String> orgLoadColumn;
    @FXML
    private TableColumn<Employee, String> postColumn;
    @FXML
    private TableColumn<Employee, String> subjectColumn;
    @FXML
    private TableColumn<Employee, String> totalLoadColumn;
    @FXML
    private TableView<Employee> loadTable;
    @FXML
    private BorderPane loadWindow;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EmployeesInstance.getInstance().attach(this);
        RowIndexer.index(indexColumn);
        nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
        postColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPost()));
        subjectColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSubject()));

        academicLoadColumn.setCellValueFactory(data -> new SimpleStringProperty(Double.toString(data.getValue().getLoad().getAcademicHours())));
        addLoadColumn.setCellValueFactory(data -> new SimpleStringProperty(Double.toString(data.getValue().getLoad().getAdditionalHours())));
        orgLoadColumn.setCellValueFactory(data -> new SimpleStringProperty(Double.toString(data.getValue().getLoad().getOrganizationHours())));
        totalLoadColumn.setCellValueFactory(data -> new SimpleStringProperty(Double.toString(data.getValue().getLoad().getTotalHours())));
        updateTable(loadTable);
        academicLoadColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        addLoadColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        orgLoadColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        provideEditingOfColumn(academicLoadColumn, ACADEMIC);
        provideEditingOfColumn(orgLoadColumn, LoadTypes.ORGANIZATION);
        provideEditingOfColumn(addLoadColumn, LoadTypes.ADDITIONAL);
        // Установка таблицы редактируемой
        loadTable.setEditable(true);

    }

    private Double findSum(List<Double> loads) {
        double sum = 0;
        for (Double v : loads) {
            sum += v;
        }
        return sum;
    }

    // предосталвение возможности редактировая ячеек колонок типов нагрузки
    private void provideEditingOfColumn(TableColumn<Employee, String> column, LoadTypes type) {
        // Установка фабрики ячеек для редактирования
        column.setOnEditCommit(event -> {
            Pattern p = Pattern.compile("(\\d+\\.?\\d{0,2})?");
            Employee employee = event.getRowValue();
            Load load = employee.getLoad();
            double value = 0;
            double newSum = 0;
            double maxCapacity = 0;
            switch (type) {
                case ACADEMIC:
                    try {
                        if(!p.matcher(event.getNewValue()).matches()){
                            load.setAcademicHours(Double.parseDouble(event.getOldValue()));
                            throw new NumberFormatException();
                        }
                        if(employee.getVacancy() != null){
                            value = employee.getLoad().getAcademicHours();
                            newSum = findSum(employee.getVacancy().getEmployeeList()
                                    .stream().map(e -> e.getLoad().getAcademicHours()).toList()) - value
                                    + Double.parseDouble(event.getNewValue());
                            maxCapacity = employee.getVacancy().getLoad().getAcademicHours();
                            if (newSum > maxCapacity ) throw new SettingIncorrectValue(
                                    "Неправильная уч нагрузка", "Нагрузка");
                            load.setAcademicHours(Double.parseDouble(event.getNewValue()));
                            vacancyService.updateVacancy(employee.getVacancy());
                            VacancyInstance.getInstance().notifyObservers();
                        }
                        else {
                            load.setAcademicHours(Double.parseDouble(event.getNewValue()));
                        }

                    }
                    catch (NumberFormatException e){
                        showErrorAlert("Ошибка ввода нагрузки",
                                "Указанно некорректное значение. При вводе допускаются только цирф и точка" +
                                        " по шаблону XX.XX");
                    }
                    catch (NullPointerException e){
                        showErrorAlert("Ну это ваще пизда","");
                    }
                    catch (SettingIncorrectValue e ) {
                        showErrorAlert("Ошибка ввода нагрузки",
                                "Указанное значение превышает значение необходимой нагрузки вакансии");
                    }
                    break;
                case ADDITIONAL:
                    try {
                        if(!p.matcher(event.getNewValue()).matches()){
                            load.setAdditionalHours(Double.parseDouble(event.getOldValue()));
                            throw new NumberFormatException();
                        }
                        if(employee.getVacancy() != null){
                            value = employee.getLoad().getAdditionalHours();
                            newSum = findSum(employee.getVacancy().getEmployeeList()
                                    .stream().map(e -> e.getLoad().getAdditionalHours()).toList()) - value
                                    + Double.parseDouble(event.getNewValue());
                            maxCapacity = employee.getVacancy().getLoad().getAdditionalHours();
                            if (newSum > maxCapacity ) throw new SettingIncorrectValue(
                                    "Неправильная доп нагрузка", "Нагрузка");
                            load.setAdditionalHours(Double.parseDouble(event.getNewValue()));
                            vacancyService.updateVacancy(employee.getVacancy());
                            VacancyInstance.getInstance().notifyObservers();
                        }
                        else {
                            load.setAdditionalHours(Double.parseDouble(event.getNewValue()));
                        }
                    }
                    catch (NumberFormatException e){
                        showErrorAlert("Ошибка ввода нагрузки",
                                "Указанно некорректное значение. При вводе допускаются только цирф и точка" +
                                        " по шаблону XX.XX");
                    }
                    catch (NullPointerException e){
                        showErrorAlert("Ну это ваще пизда","");
                    }
                    catch (SettingIncorrectValue e ) {
                        showErrorAlert("Ошибка ввода нагрузки",
                                "Указанное значение превышает значение необходимой нагрузки вакансии");
                    }
                    break;
                case ORGANIZATION:
                    try {
                        if(!p.matcher(event.getNewValue()).matches()){
                            load.setOrganizationHours(Double.parseDouble(event.getOldValue()));
                            throw new NumberFormatException();
                        }
                        if(employee.getVacancy() != null){
                            value = employee.getLoad().getOrganizationHours();
                            newSum = findSum(employee.getVacancy().getEmployeeList()
                                    .stream().map(e -> e.getLoad().getOrganizationHours()).toList()) - value
                                    + Double.parseDouble(event.getNewValue());
                            maxCapacity = employee.getVacancy().getLoad().getOrganizationHours();
                            if (newSum > maxCapacity ) throw new SettingIncorrectValue(
                                    "Неправильная орг нагрузка", "Нагрузка");
                            load.setOrganizationHours(Double.parseDouble(event.getNewValue()));
                            vacancyService.updateVacancy(employee.getVacancy());
                            VacancyInstance.getInstance().notifyObservers();
                        }
                        else {
                            load.setOrganizationHours(Double.parseDouble(event.getNewValue()));
                        }
                    }
                    catch (NumberFormatException e){
                        showErrorAlert("Ошибка ввода нагрузки",
                                "Указанно некорректное значение. При вводе допускаются только цирф и точка" +
                                        " по шаблону XX.XX");
                    }
                    catch (NullPointerException e){
                        showErrorAlert("Ну это ваще пизда","");
                    }
                    catch (SettingIncorrectValue e ) {
                        showErrorAlert("Ошибка ввода нагрузки",
                                "Указанное значение превышает значение необходимой нагрузки вакансии");
                    }
                    break;
            }
            loadService.findTotalHours(load);
            loadService.updateLoad(employee.getLoad().getId(), load);
            loadTable.refresh();
        });
    }

    @Override
    public void update() {
        updateTable(loadTable);
    }
}
