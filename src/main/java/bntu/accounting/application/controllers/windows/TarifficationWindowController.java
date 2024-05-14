package bntu.accounting.application.controllers.windows;

import bntu.accounting.application.controllers.VisualComponentsInitializer;
import bntu.accounting.application.excel.TarifficationFileCreator;
import bntu.accounting.application.iojson.FileLoader;
import bntu.accounting.application.models.serializable.ReportData;
import bntu.accounting.application.iojson.ReportJsonHelper;
import bntu.accounting.application.models.Item;
import bntu.accounting.application.services.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;


public class TarifficationWindowController extends VisualComponentsInitializer implements Initializable {

    private ReportData reportData;
    private LocalDate date;

    private final ObjectMapper mapper = new ObjectMapper();
    private Stage parent;
    private FileLoader loader = new FileLoader();
    @FXML
    private TextField departmentHeadNameField;
    @FXML
    private TextField academicYearField;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField approverNameField;
    @FXML
    private TextField approverPostField;
    @FXML
    private TextArea notesArea;
    @FXML
    private CheckBox firstHalfOfYear;
    @FXML
    private CheckBox secondHalfOfYear;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button saveButton;
    private EmployeeService employeeService = new EmployeeService();
    private  ReportJsonHelper reportJsonHelper = new ReportJsonHelper();

    public TarifficationWindowController(Stage parent) {
        this.parent = parent;
    }

    private void initializeForm() {
        Integer monthNumber = date.getMonth().getValue();
//        reportData.setMonth(reportData.getPairs().get(monthNumber).toString());
        findAcademicYear(monthNumber);
        approverNameField.setText(reportData.getApproverName());
        approverPostField.setText(reportData.getApproverPost());
        departmentHeadNameField.setText(reportData.getDepartmentHeadName());
        datePicker.setValue(date);
    }

    private void findAcademicYear(int monthNumber) {
        Integer yearCurrent = date.getYear();
        reportData.setYear(yearCurrent);
        reportData.setDay(date.getDayOfMonth());
        Integer yearPrev = yearCurrent - 1;
        Integer yearNext = yearCurrent + 1;
        if (monthNumber > 0 && monthNumber <= 6) {
            firstHalfOfYear.setSelected(false);
            secondHalfOfYear.setSelected(true);
            reportData.setAcademicYear(yearPrev + "/" + yearCurrent);
            reportData.setHalfOfYear("первое");
        } else {
            firstHalfOfYear.setSelected(true);
            secondHalfOfYear.setSelected(false);
            reportData.setAcademicYear(yearCurrent + "/" + yearNext);
            reportData.setHalfOfYear("второе");
        }
        academicYearField.setText(reportData.getAcademicYear());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.getStage().initModality(Modality.APPLICATION_MODAL);
        reportData = reportJsonHelper.readFromJson();
        date = LocalDate.now();
        initializeForm();
        datePicker.setOnAction(event -> {
            date = datePicker.getValue();
            Integer monthNumber = date.getMonth().getValue();
            findAcademicYear(monthNumber);
        });
        saveButton.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
            File file;
            fileChooser.setInitialDirectory(new File("C://"));
            if (reportData.getLastFileSavePath() != null || !reportData.getLastFileSavePath().equals("")) {
                file = new File(reportData.getLastFileSavePath());
                if(file.exists()) fileChooser.setInitialDirectory(file);
            }
            file = fileChooser.showSaveDialog(null);
            if (file != null) {
                reportData.setLastFileSavePath(file.getPath().substring(0, file.getPath().length() - file.getName().length()));
                saveAllData();
                TarifficationFileCreator tarifficationFileCreator = new TarifficationFileCreator();
                List<Item> items = employeeService.getAllEmployees().stream().map(e -> e.getParent()).toList();
                tarifficationFileCreator.createFile(file.getPath(), items);
            }
        });
    }
    private void saveAllData(){
        reportData.setApproverName(approverNameField.getText());
        reportData.setApproverPost(approverPostField.getText());
        reportData.setDepartmentHeadName(departmentHeadNameField.getText());
        reportJsonHelper.writeToJson(reportData);
    }

}
