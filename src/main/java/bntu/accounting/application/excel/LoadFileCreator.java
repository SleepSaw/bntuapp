package bntu.accounting.application.excel;

import bntu.accounting.application.iojson.FileLoader;
import bntu.accounting.application.models.Employee;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class LoadFileCreator {
    private final static String headerFilePath = "excel_header.json";
    private final static String loadTableFilePath = "load_table.json";
    private FileLoader jsonFileLoader;
    private ExcelFileHeaderCreator headerCreator;
    private ExcelLoadTableCreator loadTableCreator;
    private Workbook workbook;

    public void createFile(String filePath, List<Employee> employees){
        try(Workbook workbook = new XSSFWorkbook()){
            this.workbook = workbook;
            Sheet sheet = workbook.createSheet("Педагогическая нагрузка");
            init();
            JSONObject headersData = jsonFileLoader.loadJsonFile(headerFilePath);
            JSONObject loadTableData = jsonFileLoader.loadJsonFile(loadTableFilePath);
            headerCreator.writeDataToExcel(filePath,5,headersData);
            headerCreator.createRightBlock(5,headersData);
            loadTableCreator.createLoadTableColumns(filePath,loadTableData);
            int endRow = loadTableCreator.addAllTeacherToTable(14,employees);
            loadTableCreator.addCommonData(endRow, employees);
            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void init(){
        headerCreator = new ExcelFileHeaderCreator(workbook);
        loadTableCreator = new ExcelLoadTableCreator(workbook);
        jsonFileLoader = new FileLoader();
    }

}
