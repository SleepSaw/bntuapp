package bntu.accounting.application.excel;

import bntu.accounting.application.iojson.FileLoader;
import bntu.accounting.application.models.Employee;
import bntu.accounting.application.models.Item;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class SalaryFileCreator {
    private final static String headerFilePath = "excel_header.json";
    private final static String salaryTableFilePath = "tariffication_table.json";
    private FileLoader jsonFileLoader;
    private ExcelFileHeaderCreator headerCreator;
    private ExcelSalaryTableCreator SalaryTableCreator;
    private Workbook workbook;

    public void createFile(String filePath, List<Item> items){
        try(Workbook workbook = new XSSFWorkbook()){
            this.workbook = workbook;
            Sheet sheet = workbook.createSheet("Педагогическая нагрузка");
            init();
            JSONObject headersData = jsonFileLoader.loadJsonFile(headerFilePath);
            JSONObject salaryTableData = jsonFileLoader.loadJsonFile(salaryTableFilePath);
            headerCreator.writeDataToExcel(filePath,12,headersData);
            SalaryTableCreator.createLoadTableColumns(filePath,salaryTableData);
            int endRow = SalaryTableCreator.addAllItemsToTable(14,items);
            SalaryTableCreator.addCommonData(endRow, items);
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
