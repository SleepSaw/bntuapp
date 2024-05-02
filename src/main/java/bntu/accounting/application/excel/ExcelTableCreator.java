package bntu.accounting.application.excel;

import bntu.accounting.application.models.Item;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

public abstract class ExcelTableCreator extends ExcelUtil implements ExcelTable {
    private Integer counter =0;

    public ExcelTableCreator(Workbook workbook) {
        super(workbook);
    }


    @Override
    public void createColumn(int startRow, int endRow, int startColumn, int endColumn, String title, CellStyle style,
                             boolean rotate) {
        Sheet sheet = workbook.getSheetAt(0);
        CellStyle ownStyle = workbook.createCellStyle();
        ownStyle.cloneStyleFrom(columnStyle);
        if (rotate){
            ownStyle.setRotation((short) 90); // Поворот на 90 градусов
        }
        Cell cell;
        Row row = sheet.getRow(startRow);
        for(int i = startRow;i<=endRow;i++){
            row = sheet.getRow(i);
            for(int j = startColumn;j<=endColumn;j++){
                cell = row.createCell(j);
                cell.setCellValue(title);
                cell.setCellStyle(ownStyle);
            }
        }

        if(startRow != endRow || startColumn != endColumn){
            CellRangeAddress mergedRegion = new CellRangeAddress(startRow, endRow, startColumn, endColumn);
            sheet.addMergedRegion(mergedRegion);
        }
            if(startColumn == endColumn){
                row = sheet.getRow(endRow+1);
                counter++;
                indexingColumn(row,startColumn,counter.toString());
            }
    }
    private void indexingColumn(Row row, int columnIndex,String value){
        Cell cell = row.createCell(columnIndex);
        Font font = createFont("Times New Roman",12,false,true);
        CellStyle style = setFontForCell(font);
        style.cloneStyleFrom(columnStyle);
        style.setFont(font);
        style.setRotation((short) 0);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    @Override
    public abstract void setAllColumnsWidth();

    // Реализация добавления учителей в таблицу
    @Override
    public int addAllItemsToTable(int startRow, List<Item> items) {
        int i = startRow;
        int count = 1;
        for (Item item: items) {
            Row row = sheet.createRow(i);
            addEmployeeToTable(count,item,row);
            i++;
            count++;
        }
        return i;
    }
    public abstract void addCommonData(int rowIndex, List<Item> items);

    // Асбтрактный метод, который необходимо переопределить для добавления одного учителя
    @Override
    public abstract void addEmployeeToTable(Integer number, Item item, Row row);
}
