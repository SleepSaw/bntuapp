package bntu.accounting.application.doc.obj;

import bntu.accounting.application.doc.ExcelUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

public class TableCreator extends ExcelUtil {
    private CellStyle columnStyle;
    private CellStyle styleBold;
    /**
     * На основе объекта документа создаёт первый лист
     * @param workbook
     */
    public TableCreator(Workbook workbook) {
        super(workbook);
        initCellStyles();
    }

    public void create(ExcelTable table, int startRow, int startColumn){
        Integer rowPointer = startRow;
        Integer columnPointer = startColumn;
        writeTitle(table.getTitle(), workbook.getSheetAt(0).createRow(rowPointer++),columnPointer);

        createColumns(table.getColumns(),startColumn,startColumn);
    }
    private void writeTitle(String title, Row row, int column){
        Cell cell = row.createCell(column);
        cell.setCellStyle(styleBold);
        cell.setCellValue(title);
    }
    private void createColumns(List<ExcelColumn> columns, Integer rowIndex, Integer colIndex){
        Sheet sheet = workbook.getSheetAt(0);
        int startRow = rowIndex;
        int startCol = colIndex;
        int endRow = rowIndex;
        int endCol = colIndex;
        for(ExcelColumn column : columns) {
            Cell cell;
            Row row = sheet.getRow(rowIndex);
            startCol = endCol;
            endRow = rowIndex + column.getNumberOfRows();
            endCol = colIndex + column.getNumberOfColumns();
            for (int i = startRow; i <= endRow-1; i++) {
                row = sheet.getRow(i);
                for (int j = startCol; j <= endCol-1; j++) {
                    cell = row.createCell(j);
                    cell.setCellValue(column.getHeaderValue());
                    cell.setCellStyle(columnStyle);
                }
            }
            if (startRow != endRow || startCol != endCol) {
                CellRangeAddress mergedRegion = new CellRangeAddress(startRow, endRow, startCol, endCol);
                sheet.addMergedRegion(mergedRegion);
            }
        }
    }
    private void initCellStyles(){
        columnStyle = setFontForCell(createFont("Times New Roman", 16, false));
        columnStyle.setAlignment(HorizontalAlignment.CENTER);
        columnStyle.setWrapText(true);
        columnStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        columnStyle.setBorderTop(BorderStyle.THIN);
        columnStyle.setBorderBottom(BorderStyle.THIN);
        columnStyle.setBorderLeft(BorderStyle.THIN);
        columnStyle.setBorderRight(BorderStyle.THIN);
        styleBold = setFontForCell(createFont("Times New Roman", 20, true));
    }
}
