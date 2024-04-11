package bntu.accounting.application.excel;

import bntu.accounting.application.iojson.FileLoader;
import bntu.accounting.application.models.Item;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class TarifficationFileCreator  {
    private final static String headerFilePath = "excel_header.json";
    private final static String loadTableFilePath = "tariffication.json";
    private FileLoader jsonFileLoader;
    private ExcelFileHeaderCreator headerCreator;
    private ExcelTarifficationTableCreator tarifficationTableCreator;
    private Workbook workbook;

    public void createFile(String filePath, List<Item> items){
        try(Workbook workbook = new XSSFWorkbook()){
            this.workbook = workbook;
            Sheet sheet = workbook.createSheet("Тарификация");
            init();
            JSONObject headersData = jsonFileLoader.loadJsonFile(headerFilePath);
            JSONObject loadTableData = jsonFileLoader.loadJsonFile(loadTableFilePath);
            headerCreator.writeDataToExcel(filePath,5,headersData);
            headerCreator.createRightBlock(5,headersData);
            tarifficationTableCreator.createLoadTableColumns(filePath,loadTableData);
            int endRow = tarifficationTableCreator.addAllItemsToTable(14,items);
            tarifficationTableCreator.addCommonData(endRow, items);
            tarifficationTableCreator.writeVacanciesData(endRow+1);
            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void init(){
        headerCreator = new ExcelFileHeaderCreator(workbook);
        tarifficationTableCreator = new ExcelTarifficationTableCreator(workbook);
        jsonFileLoader = new FileLoader();
    }
}
