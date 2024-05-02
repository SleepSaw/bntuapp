package bntu.accounting.application.excel;

import bntu.accounting.application.models.Employee;
import bntu.accounting.application.models.Item;
import bntu.accounting.application.services.LoadService;
import org.apache.poi.ss.usermodel.*;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelLoadTableCreator extends ExcelTableCreator {

    public ExcelLoadTableCreator(Workbook workbook) {
        super(workbook);
    }

    public void createLoadTableColumns(String fileName, JSONObject jsonData) {
        CellStyle columnsStyle = setFontForCell(createFont("Times New Roman", 16, false));
        columnsStyle.setAlignment(HorizontalAlignment.CENTER);
        columnsStyle.setWrapText(true);
        columnsStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        columnsStyle.setBorderTop(BorderStyle.THIN);
        columnsStyle.setBorderBottom(BorderStyle.THIN);
        columnsStyle.setBorderLeft(BorderStyle.THIN);
        columnsStyle.setBorderRight(BorderStyle.THIN);
        CellStyle styleBold = setFontForCell(createFont("Times New Roman", 20, true));

        setAllColumnsWidth();

        Row row9 = sheet.createRow(9);
        Row row10 = sheet.createRow(10);
        row10.setHeightInPoints(30);
        Row row11 = sheet.createRow(11);
        row11.setHeightInPoints(30);
        Row row12 = sheet.createRow(12);
        row12.setHeightInPoints(100);
        Row row13 = sheet.createRow(13);



        writeDataToCell(row9, 0,  jsonData.getString("chapter_name"), styleBold);

        createColumn(10,12,0,0,jsonData.getString("index_column"),columnsStyle,false);
        createColumn(10,12,1,1,jsonData.getString("fio_column"),columnsStyle,false);
        createColumn(10,12,2,2,jsonData.getString("post_column"),columnsStyle,false);
        createColumn(10,12,3,3,jsonData.getString("subject_column"),columnsStyle,false);

        createColumn(10,10,4,7,jsonData.getString("week_load_column"),columnsStyle,false);
        createColumn(11,11,5,7,jsonData.getString("parts_load_column"),columnsStyle,false);

        createColumn(11,12,4,4,jsonData.getString("total_load_column"),columnsStyle,false);
        createColumn(12,12,5,5,jsonData.getString("academic_load_column"),columnsStyle,false);
        createColumn(12,12,6,6,jsonData.getString("additional_load_column"),columnsStyle,false);
        createColumn(12,12,7,7,jsonData.getString("organization_load_column"),columnsStyle,false);

        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            workbook.write(outputStream);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    public void setAllColumnsWidth(){
        setColumnWidth(0,50,sheet);
        setColumnWidth(1,290,sheet);
        setColumnWidth(2,200,sheet);
        setColumnWidth(3,660,sheet);
        setColumnWidth(4,200,sheet);
        setColumnWidth(5,200,sheet);
        setColumnWidth(6,200,sheet);
        setColumnWidth(7,200,sheet);
    }

    @Override
    public void addCommonData(int rowIndex, List<Item> items) {
        List<Employee> employees = items.stream().map(e -> (Employee) e).toList();
        Row row = sheet.createRow(rowIndex);
        row.setHeightInPoints(20);

        LoadService service = new LoadService();
        addCell(0,null,leftStyle,row);
        addCell(1,"ИТОГО:",leftStyle,row);
        addCell(2,null,leftStyle,row);
        addCell(3,null,leftStyle,row);
        addCell(4,service.getTotalLoadOfAllTeachers(employees).toString(),rightStyle,row);
        addCell(5,service.getAcademicLoadOfAllTeachers(employees).toString(),rightStyle,row);
        addCell(6,service.getAddLoadOfAllTeachers(employees).toString(),rightStyle,row);
        addCell(7,service.getOrgLoadOfAllTeachers(employees).toString(),rightStyle,row);
    }
    private void addCell(int cellIndex, String value, CellStyle style, Row row){
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }


    @Override
    public void addEmployeeToTable(Integer number, Item item, Row row) {
        Employee employee = (Employee) item;
        row.setHeightInPoints(20);
        Cell numberCell = row.createCell(0);
        numberCell.setCellValue(number);
        numberCell.setCellStyle(rightStyle);

        Cell fioCell = row.createCell(1);
        fioCell.setCellValue(employee.getName());
        fioCell.setCellStyle(rightStyle);

        Cell postCell = row.createCell(2);
        postCell.setCellValue(employee.getPost());
        postCell.setCellStyle(rightStyle);

        Cell subjectCell = row.createCell(3);
        subjectCell.setCellValue(employee.getSubject());
        subjectCell.setCellStyle(rightStyle);

        Cell totalLoadCell = row.createCell(4);
        totalLoadCell.setCellValue(employee.getLoad().getTotalHours());
        totalLoadCell.setCellStyle(rightStyle);

        Cell academicLoadCell = row.createCell(5);
        academicLoadCell.setCellValue(employee.getLoad().getAcademicHours());
        academicLoadCell.setCellStyle(rightStyle);

        Cell addLoadCell = row.createCell(6);
        addLoadCell.setCellValue(employee.getLoad().getAdditionalHours());
        addLoadCell.setCellStyle(rightStyle);

        Cell orgLoadCell = row.createCell(7);
        orgLoadCell.setCellValue(employee.getLoad().getOrganizationHours());
        orgLoadCell.setCellStyle(rightStyle);
    }

}
