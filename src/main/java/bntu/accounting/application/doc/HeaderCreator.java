package bntu.accounting.application.doc;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;

public class HeaderCreator extends ExcelUtil {
    private Sheet sheet;
    private CellStyle headerStyle = setFontForCell(createFont("Times New Roman", 20, false));
    private CellStyle headerStyleBold = setFontForCell(createFont("Times New Roman", 20, true));
    private CellStyle headerStyleBoldRight = setFontForCell(createFont("Times New Roman", 24, true));

    // На основе объекта документа создаёт первый лист
    public HeaderCreator(Workbook workbook, Sheet sheet) {
        super(workbook);
        this.sheet = sheet;

    }
    public void drawHeader(String fileName, JSONObject jsonData, boolean extend){
        writeDataToCell(sheet.createRow(0), 0, jsonData.getString("governing_institution_name"), headerStyle);
        writeDataToCell(sheet.createRow(1), 0, jsonData.getString("educational_institution_name"), headerStyle);
        writeDataToCell(sheet.createRow(2), 0, jsonData.getString("branch"), headerStyleBold);
        writeDataToCell(sheet.createRow(5), 0, jsonData.getString("table_description_part1"), headerStyle);
        writeDataToCell(sheet.createRow(6), 0, jsonData.getString("table_description_part2"), headerStyle);
        if (extend) createRightBlock(jsonData);
        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            workbook.write(outputStream);
            System.out.println("Данные успешно записаны в файл " + fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // Добавление правого блока
    private void createRightBlock(JSONObject jsonData){
        writeDataToCell(sheet.getRow(0), 5, "УТВЕРЖДАЮ", headerStyleBoldRight);
        writeDataToCell(sheet.getRow(1), 5, jsonData.getString("main_person_post"), headerStyleBoldRight);
        writeDataToCell(sheet.getRow(2), 5, "__________________   " +
                (String) jsonData.get("main_person_name"), headerStyleBoldRight);
        writeDataToCell(sheet.createRow(3), 5, "\"_____ \"_______________2023год", headerStyleBoldRight);
    }

}
