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

public class SalaryFileCreator {
    private final static String headerFilePath = "excel_header.json";
    private final static String salaryTableFilePath = "salary_table.json";
    private FileLoader jsonFileLoader;
    private ExcelFileHeaderCreator headerCreator;
    private ExcelSalaryTableCreator SalaryTableCreator;
    private Workbook workbook;

    public void createFile(String filePath, List<Employee> employees){
        try(Workbook workbook = new XSSFWorkbook()){
            this.workbook = workbook;
            Sheet sheet = workbook.createSheet("Педагогическая нагрузка");
            init();
            JSONObject headersData = jsonFileLoader.loadJsonFile(headerFilePath);
            JSONObject salaryTableData = jsonFileLoader.loadJsonFile(salaryTableFilePath);
            headerCreator.writeDataToExcel(filePath,12,headersData);
            SalaryTableCreator.createLoadTableColumns(filePath,salaryTableData);
            int endRow = SalaryTableCreator.addAllTeacherToTable(14,employees);
            SalaryTableCreator.addCommonData(endRow, employees);
            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void init(){
        headerCreator = new ExcelFileHeaderCreator(workbook);
        SalaryTableCreator = new ExcelSalaryTableCreator(workbook);
        jsonFileLoader = new FileLoader();
    }
}
