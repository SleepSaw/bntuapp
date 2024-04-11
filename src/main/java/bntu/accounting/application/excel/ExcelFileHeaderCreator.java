package bntu.accounting.application.excel;

import org.apache.poi.ss.usermodel.*;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelFileHeaderCreator extends ExcelUtil {
    private Sheet sheet = workbook.getSheetAt(0);
    private Row row0 = sheet.createRow(0);
    private Row row1 = sheet.createRow(1);
    private Row row2 = sheet.createRow(2);
    private Row row3 = sheet.createRow(3);
    private Row row5 = sheet.createRow(5);
    private Row row6 = sheet.createRow(6);
    private CellStyle headerStyle = setFontForCell(createFont("Times New Roman", 20, false));
    private CellStyle headerStyleBold = setFontForCell(createFont("Times New Roman", 20, true));
    private CellStyle headerStyleBoldRight = setFontForCell(createFont("Times New Roman", 24, true));

    public ExcelFileHeaderCreator(Workbook workbook) {
        super(workbook);
    }

    public void writeDataToExcel(String fileName, int marginLeftIndexColumn, JSONObject jsonData) {


        writeDataToCell(row0, 0, jsonData.getString("governing_institution_name"), headerStyle);
        writeDataToCell(row1, 0, jsonData.getString("educational_institution_name"), headerStyle);
        writeDataToCell(row2, 0, jsonData.getString("branch"), headerStyleBold);
        writeDataToCell(row5, 0, jsonData.getString("table_description_part1"), headerStyle);
        writeDataToCell(row6, 0, jsonData.getString("table_description_part2"), headerStyle);
        // Сохранение в файл
        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            workbook.write(outputStream);
            System.out.println("Данные успешно записаны в файл " + fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void createRightBlock(int marginLeftIndexColumn, JSONObject jsonData ){
        writeDataToCell(row1, marginLeftIndexColumn, jsonData.getString("main_person_post"), headerStyleBoldRight);
        writeDataToCell(row0, marginLeftIndexColumn, "УТВЕРЖДАЮ", headerStyleBoldRight);
        writeDataToCell(row2, marginLeftIndexColumn, "__________________   " +
                (String) jsonData.get("main_person_name"), headerStyleBoldRight);
        writeDataToCell(row3, marginLeftIndexColumn, "\"_____ \"_______________2023год", headerStyleBoldRight);
    }

}
