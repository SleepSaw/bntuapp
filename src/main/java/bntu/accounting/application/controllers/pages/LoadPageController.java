package bntu.accounting.application.controllers.pages;

import bntu.accounting.application.controllers.VisualComponentsInitializer;
import bntu.accounting.application.dao.impl.EmployeeDAOImpl;
import bntu.accounting.application.dao.impl.LoadDAOImpl;
import bntu.accounting.application.dao.interfaces.EmployeeDAO;
import bntu.accounting.application.dao.interfaces.LoadDAO;
import bntu.accounting.application.models.Employee;
import bntu.accounting.application.models.Load;
import bntu.accounting.application.services.LoadService;
import bntu.accounting.application.util.LoadTypes;
import bntu.accounting.application.util.RowIndexer;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static bntu.accounting.application.util.LoadTypes.ACADEMIC;

public class LoadPageController extends VisualComponentsInitializer implements Initializable {
    private LoadService loadService = new LoadService();
    private EmployeeDAO employeeDAO = new EmployeeDAOImpl();
    private LoadDAO loadDAO = new LoadDAOImpl();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

    @FXML
    public void updateTableButtonAction(ActionEvent actionEvent) {
        updateTable(loadTable);
    }

    // предосталвение возможности редактировая ячеек колонок типов нагрузки
    private void provideEditingOfColumn(TableColumn<Employee, String> column, LoadTypes type) {
        // Установка фабрики ячеек для редактирования
        column.setOnEditCommit(event -> {
            Employee employee = event.getRowValue();
            Load load = employee.getLoad();
            switch (type) {
                case ACADEMIC:
                    load.setAcademicHours(Double.parseDouble(event.getNewValue()));
                    break;
                case ADDITIONAL:
                    load.setAdditionalHours(Double.parseDouble(event.getNewValue()));
                    break;
                case ORGANIZATION:
                    load.setOrganizationHours(Double.parseDouble(event.getNewValue()));
                    break;
            }
            loadService.findTotalHours(load);
            loadService.updateLoad(employee.getLoad().getId(),load);
            loadTable.refresh();
        });
    }
}
