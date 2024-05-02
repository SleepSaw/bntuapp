package bntu.accounting.application.excel;

import bntu.accounting.application.models.Employee;
import bntu.accounting.application.models.Item;
import bntu.accounting.application.services.LoadService;
import bntu.accounting.application.services.SalaryService;
import org.apache.poi.ss.usermodel.*;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelSalaryTableCreator extends ExcelTableCreator  {

    public ExcelSalaryTableCreator(Workbook workbook) {
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

        Row row8 = sheet.createRow(8);
        row8.setHeightInPoints(30);
        Row row9 = sheet.createRow(9);
        row9.setHeightInPoints(30);
        Row row10 = sheet.createRow(10);
        row10.setHeightInPoints(40);
        Row row11 = sheet.createRow(11);
        row11.setHeightInPoints(30);
        Row row12 = sheet.createRow(12);
        row12.setHeightInPoints(270);
        Row row13 = sheet.createRow(13);



        writeDataToCell(row8, 0, jsonData.getString("chapter_name_part1"), styleBold);
        writeDataToCell(row9, 0, jsonData.getString("chapter_name_part2"), styleBold);

        createColumn(10,12,0,0,jsonData.getString("index_column"),columnsStyle,false);
        createColumn(10,12,1,1,jsonData.getString("fio_column"),columnsStyle,false);
        createColumn(10,12,2,2,jsonData.getString("post_column"),columnsStyle,false);
        createColumn(10,12,3,3,jsonData.getString("salary_total_load_column"),columnsStyle,true);
        createColumn(10,12,4,4,jsonData.getString("qualification_column"),columnsStyle,true);
        createColumn(10,12,5,5,jsonData.getString("exp_column"),columnsStyle,true);
        createColumn(10,12,6,6,jsonData.getString("category_column"),columnsStyle,true);


        createColumn(10,12,7,7,jsonData.getString("salary_per_rate_column"),columnsStyle,true);
        createColumn(10,12,8,8,jsonData.getString("salary_by_load_column"),columnsStyle,true);

        createColumn(10,10,9,15,jsonData.getString("allowances_by_load_column"),columnsStyle,false);

        createColumn(11,12,9,9,jsonData.getString("exp_allowance_column"),columnsStyle,true);
        createColumn(11,12,10,10,jsonData.getString("contract_allowance_column"),columnsStyle,true);
        createColumn(11,12,11,11,jsonData.getString("village_allowance_column"),columnsStyle,true);
        createColumn(11,12,12,12,jsonData.getString("qual_allowance_column"),columnsStyle,true);
        createColumn(11,12,13,13,jsonData.getString("young_specialist_allowance_column"),columnsStyle,true);
        createColumn(11,12,14,14,jsonData.getString("six_percent_allowance_column"),columnsStyle,true);
        createColumn(11,12,15,15,jsonData.getString("osobennosti_allowance_column"),columnsStyle,true);

        createColumn(10,12,16,16,jsonData.getString("osobennosti_per_rate_allowance_column"),columnsStyle,true);
        createColumn(10,12,17,17,jsonData.getString("also_allowance_column"),columnsStyle,true);
        createColumn(10,12,18,18,jsonData.getString("total_salary_column"),columnsStyle,true);



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
        setColumnWidth(3,80,sheet);
        setColumnWidth(4,150,sheet);
        setColumnWidth(5,110,sheet);
        setColumnWidth(6,60,sheet);
        setColumnWidth(7,120,sheet);
        setColumnWidth(8,120,sheet);
        setColumnWidth(9,80,sheet);
        setColumnWidth(10,100,sheet);
        setColumnWidth(11,80,sheet);
        setColumnWidth(12,100,sheet);
        setColumnWidth(13,80,sheet);
        setColumnWidth(14,80,sheet);
        setColumnWidth(15,80,sheet);
        setColumnWidth(16,90,sheet);
        setColumnWidth(17,90,sheet);
        setColumnWidth(18,120,sheet);
    }

    @Override
    public void addCommonData(int rowIndex, List<Item> items) {
        List<Employee> employees = items.stream().map(e -> (Employee) e).toList();
        Row row = sheet.createRow(rowIndex);
        row.setHeightInPoints(20);
        CellStyle style = workbook.createCellStyle();
        style.cloneStyleFrom(columnStyle);
        Font font = createFont("Times New Roman",16,true,false);
        style.setFont(font);
        CellStyle leftStyle = workbook.createCellStyle();
        leftStyle.cloneStyleFrom(style);
        leftStyle.setAlignment(HorizontalAlignment.LEFT);
        style.setDataFormat(format.getFormat("#0.00"));
        SalaryService salaryService = new SalaryService();
        LoadService loadService = new LoadService();
        addCell(0,null,style,row);
        addCell(1,"ИТОГО:",leftStyle,row);
        addCell(2,null,style,row);
        addCell(3,loadService.getTotalLoadOfAllTeachers(employees).toString(),style,row);
        addCell(4,null,style,row);
        addCell(5,null,style,row);
        addCell(6,null,style,row);
        style.setAlignment(HorizontalAlignment.RIGHT);
        addCell(7,salaryService.getSalaryPerRateOfAllTeachers(employees).toString(),style,row);
        addCell(8,salaryService.getSalaryByLoadOfAllTeachers(employees).toString(),style,row);
        addCell(9,salaryService.getExpAllowanceOfAllTeachers(employees).toString(),style,row);
        addCell(10,salaryService.getContractAllowanceOfAllTeachers(employees).toString(),style,row);
        addCell(11,null,style,row);
        addCell(12,salaryService.getQualAllowanceOfAllTeachers(employees).toString(),style,row);
        addCell(13,salaryService.getYoungSpecAllowanceOfAllTeachers(employees).toString(),style,row);
        addCell(14,salaryService.getProfActivitiesAllowanceOfAllTeachers(employees).toString(),style,row);
        addCell(15,salaryService.getWorkInIndustryAllowanceOfAllTeachers(employees).toString(),style,row);
        addCell(16,null,style,row);
        addCell(17,null,style,row);
        addCell(18,salaryService.getTotalSalaryOfAllTeachers(employees).toString(),style,row);
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
        CellStyle columnsStyle = setFontForCell(createFont("Times New Roman", 16, false));
        columnsStyle.setAlignment(HorizontalAlignment.CENTER);
        columnsStyle.setWrapText(true);
        columnsStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        columnsStyle.setBorderTop(BorderStyle.THIN);
        columnsStyle.setBorderBottom(BorderStyle.THIN);
        columnsStyle.setBorderLeft(BorderStyle.THIN);
        columnsStyle.setBorderRight(BorderStyle.THIN);
        columnStyle.cloneStyleFrom(columnsStyle);

        CellStyle styleAlignCenter = workbook.createCellStyle();
        styleAlignCenter.cloneStyleFrom(columnsStyle);
        styleAlignCenter.setAlignment(HorizontalAlignment.CENTER);

        CellStyle styleAlignLeft = workbook.createCellStyle();
        styleAlignLeft.cloneStyleFrom(columnsStyle);
        styleAlignLeft.setAlignment(HorizontalAlignment.LEFT);

        CellStyle styleAlignRight = workbook.createCellStyle();
        styleAlignRight.cloneStyleFrom(columnsStyle);
        styleAlignRight.setAlignment(HorizontalAlignment.RIGHT);
        styleAlignRight.setDataFormat(format.getFormat("#0.00"));
        addCell(row,0,number.toString(),styleAlignLeft);
        addCell(row,1,employee.getName(),styleAlignLeft);
        addCell(row,2,employee.getPost(),styleAlignLeft);
        addCell(row,3,employee.getLoad().getTotalHours(),styleAlignRight);
        addCell(row,4,employee.getQualification(),styleAlignCenter);
        addCell(row,5,employee.getExperience(),styleAlignLeft);
        addCell(row,6,employee.getCategory(),styleAlignCenter);
        addCell(row,7,employee.getSalary().getRateSalary(),styleAlignRight);
        addCell(row,8,employee.getSalary().getLoadSalary(),styleAlignRight);
        addCell(row,9,employee.getSalary().getExpAllowance(),styleAlignRight);
        addCell(row,10,employee.getSalary().getContractAllowance(),styleAlignRight);
        addCell(row,11,"-",styleAlignCenter);
        addCell(row,12,employee.getSalary().getQualAllowance(),styleAlignRight  );
        addCell(row,13,employee.getSalary().getYSAllowance(),styleAlignRight);
        addCell(row,14,employee.getSalary().getIndustryWorkAllowance(),styleAlignRight);
        addCell(row,15,employee.getSalary().getProfActivitiesAllowance(),styleAlignRight);
        addCell(row,16,"-",styleAlignCenter);
        addCell(row,17,"-",styleAlignCenter);
        addCell(row,18,employee.getSalary().getTotalSalary(),styleAlignRight);
    }
    private void addCell(Row row, int cellIndex,String value,CellStyle style){
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }
    private void addCell(Row row, int cellIndex,double value,CellStyle style){
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }
}
