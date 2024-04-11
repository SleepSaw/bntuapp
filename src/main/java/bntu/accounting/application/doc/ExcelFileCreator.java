package bntu.accounting.application.doc;

import bntu.accounting.application.doc.obj.ExcelColumn;
import bntu.accounting.application.doc.obj.ExcelTable;
import bntu.accounting.application.doc.obj.TableCreator;
import bntu.accounting.application.iojson.FileLoader;
import bntu.accounting.application.models.Item;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelFileCreator{
    private final static String headerFileName = "excel_header.json";
    private boolean extend = false;
    private HeaderCreator headerCreator;
    private FileLoader jsonFileLoader = new FileLoader();
    // На основе объекта документа создаёт первый лист
    public void createFile(String filePath, TableType type, List<Item> items){
        try(Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = createSheetWithName(workbook,type);
            headerCreator = new HeaderCreator(workbook,sheet);
            JSONObject headersData = jsonFileLoader.loadJsonFile(headerFileName);
            headerCreator.drawHeader(filePath, headersData, extend);
            TableCreator tc = new TableCreator(workbook);
            ExcelTable table = new ExcelTable();
            table.setTitle("Таблица");
            List <ExcelColumn> columns = List.of(
                    new ExcelColumn(0,"Колонка 1", workbook.createCellStyle(),
                            2,1,100D, 50D, RotateType.HORIZONTAL),
                    new ExcelColumn(1,"Колонка 1", workbook.createCellStyle(),
                            2,4,100D, 50D, RotateType.HORIZONTAL),
                    new ExcelColumn(2,"Колонка 1", workbook.createCellStyle(),
                            2,1,100D, 50D, RotateType.HORIZONTAL)

            );
            tc.create(table,0,10);
            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private Sheet createSheetWithName(Workbook workbook, TableType type){
        Sheet sheet;
        switch (type){
            case EMPLOYEE_LIST_TABLE:
                sheet = workbook.createSheet("Список работников");
                break;
            case LOAD_TABLE:
                sheet = workbook.createSheet("Педагогичесская нагрузка");
                break;
            case SALARY_TABLE:
                sheet = workbook.createSheet("Оклады");
                break;
            case TARIFFICATION_TABLE:
                sheet = workbook.createSheet("Тарификация");
                extend = true;
                break;
            default:
                sheet = workbook.createSheet("Неизвестно");
                break;
        }
        return sheet;
    }
}
