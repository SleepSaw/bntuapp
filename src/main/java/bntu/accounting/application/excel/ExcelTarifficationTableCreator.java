package bntu.accounting.application.excel;

import bntu.accounting.application.models.Employee;
import bntu.accounting.application.models.Item;
import bntu.accounting.application.models.Salary;
import bntu.accounting.application.models.Vacancy;
import bntu.accounting.application.services.*;
import bntu.accounting.application.util.enums.VacancyStatus;
import org.apache.poi.ss.usermodel.*;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ExcelTarifficationTableCreator extends ExcelTableCreator

{
    private VacancyService vacancyService = new VacancyService();
    private LoadService loadService = new LoadService();
    private EmployeeService employeeService = new EmployeeService();
    private SalaryService salaryService = new SalaryService();
    public ExcelTarifficationTableCreator(Workbook workbook) {
        super(workbook);
    }

    public void createLoadTableColumns(String fileName, JSONObject jsonData) {
        AllowancesService allowancesService = new AllowancesService();

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

        Row row4 = sheet.createRow(4);
        row4.setHeightInPoints(30);
        Row row5 = sheet.createRow(5);
        row5.setHeightInPoints(30);
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
        writeDataToCell(row4, 8, jsonData.getString("chapter_name_part1"), styleBold);
        writeDataToCell(row5, 8, jsonData.getString("chapter_name_part2"), styleBold);
        writeDataToCell(row9, 8, "Базовая ставка: " + allowancesService.getBaseRate(), styleBold);
        // Нагрузка
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
        // Оклады
        createColumn(10,12,8,8,jsonData.getString("index_column"),columnsStyle,false);
        createColumn(10,12,9,9,jsonData.getString("fio_column"),columnsStyle,false);
        createColumn(10,12,10,10,jsonData.getString("post_column"),columnsStyle,false);
        createColumn(10,12,11,11,jsonData.getString("salary_total_load_column"),columnsStyle,true);
        createColumn(10,12,12,12,jsonData.getString("qualification_column"),columnsStyle,true);
        createColumn(10,12,13,13,jsonData.getString("exp_column"),columnsStyle,true);
        createColumn(10,12,14,14,jsonData.getString("category_column"),columnsStyle,true);
        createColumn(10,12,15,15,jsonData.getString("salary_per_rate_column"),columnsStyle,true);
        createColumn(10,12,16,16,jsonData.getString("salary_by_load_column"),columnsStyle,true);
        createColumn(10,10,17,23,jsonData.getString("allowances_by_load_column"),columnsStyle,false);
        createColumn(11,12,17,17,jsonData.getString("exp_allowance_column"),columnsStyle,true);
        createColumn(11,12,18,18,jsonData.getString("contract_allowance_column"),columnsStyle,true);
        createColumn(11,12,19,19,jsonData.getString("village_allowance_column"),columnsStyle,true);
        createColumn(11,12,20,20,jsonData.getString("qual_allowance_column"),columnsStyle,true);
        createColumn(11,12,21,21,jsonData.getString("young_specialist_allowance_column"),columnsStyle,true);
        createColumn(11,12,22,22,jsonData.getString("six_percent_allowance_column"),columnsStyle,true);
        createColumn(11,12,23,23,jsonData.getString("osobennosti_allowance_column"),columnsStyle,true);
        createColumn(10,12,24,24,jsonData.getString("osobennosti_per_rate_allowance_column"),columnsStyle,true);
        createColumn(10,12,25,25,jsonData.getString("also_allowance_column"),columnsStyle,true);
        createColumn(10,12,26,26,jsonData.getString("total_salary_column"),columnsStyle,true);

        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            workbook.write(outputStream);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void setAllColumnsWidth() {
        setColumnWidth(0,50,sheet);
        setColumnWidth(1,290,sheet);
        setColumnWidth(2,200,sheet);
        setColumnWidth(3,660,sheet);
        setColumnWidth(4,200,sheet);
        setColumnWidth(5,200,sheet);
        setColumnWidth(6,200,sheet);
        setColumnWidth(7,200,sheet);
        setColumnWidth(8,50,sheet);
        setColumnWidth(9,290,sheet);
        setColumnWidth(10,200,sheet);
        setColumnWidth(11,80,sheet);
        setColumnWidth(12,150,sheet);
        setColumnWidth(13,110,sheet);
        setColumnWidth(14,60,sheet);
        setColumnWidth(15,120,sheet);
        setColumnWidth(16,120,sheet);
        setColumnWidth(17,80,sheet);
        setColumnWidth(18,100,sheet);
        setColumnWidth(19,80,sheet);
        setColumnWidth(20,100,sheet);
        setColumnWidth(21,80,sheet);
        setColumnWidth(22,80,sheet);
        setColumnWidth(23,80,sheet);
        setColumnWidth(24,90,sheet);
        setColumnWidth(25,90,sheet);
        setColumnWidth(26,120,sheet);
    }

    @Override
    public void addCommonData(int rowIndex, List<Item> items) {
        List<Employee> employees = items.stream().map(e -> (Employee) e).toList();
        Row row = sheet.createRow(rowIndex);
        row.setHeightInPoints(20);
        CellStyle style = workbook.createCellStyle();
        style.cloneStyleFrom(columnStyle);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setDataFormat(format.getFormat("#0.00"));

        Font font = createFont("Times New Roman",16,true,false);
        style.setFont(font);
        CellStyle leftStyle = workbook.createCellStyle();
        leftStyle.cloneStyleFrom(columnStyle);
        leftStyle.setFont(font);
        leftStyle.setAlignment(HorizontalAlignment.LEFT);

        // Нагрузка
        addCell(0,null,style,row);
        addCell(1,"ИТОГО:",leftStyle,row);
        addCell(9,"ИТОГО:",leftStyle,row);

        addCell(2,null,style,row);
        addCell(3,null,style,row);


        addCell(4,loadService.getTotalLoadOfAllTeachers(employees).toString(),style,row);
        addCell(5,loadService.getAcademicLoadOfAllTeachers(employees).toString(),style,row);
        addCell(6,loadService.getAddLoadOfAllTeachers(employees).toString(),style,row);
        addCell(7,loadService.getOrgLoadOfAllTeachers(employees).toString(),style,row);
        // Оклады
        LoadService loadService = new LoadService();
        addCell(8,null,style,row);
        addCell(10,null,style,row);
        addCell(11,loadService.getTotalLoadOfAllTeachers(employees).toString(),style,row);
        addCell(12,null,style,row);
        addCell(13,null,style,row);
        addCell(14,null,style,row);
        addCell(15,salaryService.getSalaryPerRateOfAllTeachers(employees).toString(),style,row);
        addCell(16,salaryService.getSalaryByLoadOfAllTeachers(employees).toString(),style,row);
        addCell(17,salaryService.getExpAllowanceOfAllTeachers(employees).toString(),style,row);
        addCell(18,salaryService.getContractAllowanceOfAllTeachers(employees).toString(),style,row);
        addCell(19,null,style,row);
        addCell(20,salaryService.getQualAllowanceOfAllTeachers(employees).toString(),style,row);
        addCell(21,salaryService.getYoungSpecAllowanceOfAllTeachers(employees).toString(),style,row);
        addCell(22,salaryService.getProfActivitiesAllowanceOfAllTeachers(employees).toString(),style,row);
        addCell(23,salaryService.getWorkInIndustryAllowanceOfAllTeachers(employees).toString(),style,row);
        addCell(24,null,style,row);
        addCell(25,null,style,row);
        addCell(26,salaryService.getTotalSalaryOfAllTeachers(employees).toString(),style,row);
    }

    @Override
    public void addEmployeeToTable(Integer number, Item item, Row row) {
        Employee employee = (Employee) item;
        CellStyle styleAlignCenter = workbook.createCellStyle();
        styleAlignCenter.cloneStyleFrom(columnStyle);
        styleAlignCenter.setAlignment(HorizontalAlignment.CENTER);

        CellStyle styleAlignLeft = workbook.createCellStyle();
        styleAlignLeft.cloneStyleFrom(columnStyle);
        styleAlignLeft.setAlignment(HorizontalAlignment.LEFT);

        CellStyle styleAlignRight = workbook.createCellStyle();
        styleAlignRight.cloneStyleFrom(columnStyle);
        styleAlignRight.setAlignment(HorizontalAlignment.RIGHT);
        styleAlignRight.setDataFormat(format.getFormat("#0.00"));

        row.setHeightInPoints(20);

        Cell numberCell = row.createCell(0);
        numberCell.setCellValue(number);
        numberCell.setCellStyle(styleAlignCenter);

        Cell fioCell = row.createCell(1);
        fioCell.setCellValue(employee.getName());
        fioCell.setCellStyle(styleAlignLeft);

        Cell postCell = row.createCell(2);
        postCell.setCellValue(employee.getPost());
        postCell.setCellStyle(styleAlignLeft);

        Cell subjectCell = row.createCell(3);
        subjectCell.setCellValue(employee.getSubject());
        subjectCell.setCellStyle(styleAlignLeft);

        Cell totalLoadCell = row.createCell(4);
        totalLoadCell.setCellValue(employee.getLoad().getTotalHours());
        totalLoadCell.setCellStyle(styleAlignRight);

        Cell academicLoadCell = row.createCell(5);
        academicLoadCell.setCellValue(employee.getLoad().getAcademicHours());
        academicLoadCell.setCellStyle(styleAlignRight);

        Cell addLoadCell = row.createCell(6);
        addLoadCell.setCellValue(employee.getLoad().getAdditionalHours());
        addLoadCell.setCellStyle(styleAlignRight);

        Cell orgLoadCell = row.createCell(7);
        orgLoadCell.setCellValue(employee.getLoad().getOrganizationHours());
        orgLoadCell.setCellStyle(styleAlignRight);

        addCell(row,8,number.toString(),styleAlignLeft);
        addCell(row,9,employee.getName(),styleAlignLeft);
        addCell(row,10,employee.getPost(),styleAlignLeft);
        addCell(row,11,employee.getLoad().getTotalHours(),styleAlignRight);
        addCell(row,12,employee.getQualification(),styleAlignCenter);
        addCell(row,13,employee.getExperience(),styleAlignLeft);
        addCell(row,14,employee.getCategory(),styleAlignCenter);
        addCell(row,15,employee.getSalary().getRateSalary(),styleAlignRight);
        addCell(row,16,employee.getSalary().getLoadSalary(),styleAlignRight);
        addCell(row,17,employee.getSalary().getExpAllowance(),styleAlignRight);
        addCell(row,18,employee.getSalary().getContractAllowance(),styleAlignRight);
        addCell(row,19,employee.getSalary().getContractAllowance(),styleAlignRight);
        addCell(row,19,"-",styleAlignCenter);
        addCell(row,20,employee.getSalary().getQualAllowance(),styleAlignRight  );
        addCell(row,21,employee.getSalary().getYSAllowance(),styleAlignRight);
        addCell(row,22,employee.getSalary().getIndustryWorkAllowance(),styleAlignRight);
        addCell(row,23,employee.getSalary().getProfActivitiesAllowance(),styleAlignRight);
        addCell(row,24,"-",styleAlignCenter);
        addCell(row,25,"-",styleAlignCenter);
        addCell(row,26,employee.getSalary().getTotalSalary(),styleAlignRight);
    }
    public void writeVacanciesData(int startRow){
        CellStyle styleAlignCenter = workbook.createCellStyle();
        styleAlignCenter.cloneStyleFrom(columnStyle);
        styleAlignCenter.setAlignment(HorizontalAlignment.CENTER);

        CellStyle styleAlignLeft = workbook.createCellStyle();
        styleAlignLeft.cloneStyleFrom(columnStyle);
        styleAlignLeft.setAlignment(HorizontalAlignment.LEFT);

        CellStyle styleAlignRight = workbook.createCellStyle();
        styleAlignRight.cloneStyleFrom(columnStyle);
        styleAlignRight.setAlignment(HorizontalAlignment.RIGHT);
        List<Vacancy> vacancies = vacancyService.getAllVacancies().stream().
                filter(e -> !(e.getStatus().equals(VacancyStatus.CLOSED.toString()))).toList();
        int counter = startRow;
        Map<Vacancy, Salary> pairs = vacancyService.getPlannedVacanciesSalary(vacancies, employeeService.getAllEmployees());
        for (Vacancy v : vacancies) {
            Row row = sheet.createRow(counter);
            row.setHeightInPoints(20);
            addCell(0,"",styleAlignLeft,row);
            addCell(1,"Вакансия",styleAlignLeft,row);
            addCell(2,v.getPost(),styleAlignLeft,row);
            addCell(3,v.getSubject(),styleAlignLeft,row);
            addCell(4,vacancyService.findResidue(v).getTotalHours().toString(),styleAlignRight,row);
            addCell(5,vacancyService.findResidue(v).getAdditionalHours().toString(),styleAlignRight,row);
            addCell(6,vacancyService.findResidue(v).getAdditionalHours().toString(),styleAlignRight,row);
            addCell(7,vacancyService.findResidue(v).getOrganizationHours().toString(),styleAlignRight,row);
            addCell(8,"",styleAlignLeft,row);
            addCell(9,"Вакансия",styleAlignLeft,row);
            addCell(10,v.getPost(),styleAlignLeft,row);
            addCell(11,"",styleAlignLeft,row);
            addCell(12,"",styleAlignLeft,row);
            addCell(13,"",styleAlignLeft,row);
            addCell(14,"",styleAlignLeft,row);
            addCell(15,pairs.get(v).getRateSalary().toString(),styleAlignRight,row);
            addCell(16,pairs.get(v).getLoadSalary().toString(),styleAlignRight,row);
            addCell(17,pairs.get(v).getExpAllowance().toString(),styleAlignRight,row);
            addCell(18,pairs.get(v).getContractAllowance().toString(),styleAlignRight,row);
            addCell(19,"-",styleAlignCenter,row);
            addCell(20,pairs.get(v).getQualAllowance().toString(),styleAlignRight,row);
            addCell(21,pairs.get(v).getYSAllowance().toString(),styleAlignRight,row);
            addCell(22,pairs.get(v).getIndustryWorkAllowance().toString(),styleAlignRight,row);
            addCell(23,pairs.get(v).getProfActivitiesAllowance().toString(),styleAlignRight,row);
            addCell(24,"-",styleAlignCenter,row);
            addCell(25,"-",styleAlignCenter,row);
            addCell(26,pairs.get(v).getTotalSalary().toString(),styleAlignRight,row);
            counter++;
        }
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
    private void addCell(int cellIndex, String value, CellStyle style, Row row){
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }
}
