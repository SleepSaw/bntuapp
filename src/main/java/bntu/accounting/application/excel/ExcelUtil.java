package bntu.accounting.application.excel;

import org.apache.poi.ss.usermodel.*;

public abstract class ExcelUtil {
    protected Workbook workbook;
    protected Sheet sheet;

    public ExcelUtil(Workbook workbook) {
        this.workbook = workbook;
        if (workbook!=null) {
            this.sheet = workbook.getSheetAt(0);
        }

    }

    protected Font createFont(String fontFamily, int fontSize){
        Font font = workbook.createFont();
        font.setFontName(fontFamily);
        font.setFontHeightInPoints((short) fontSize);
        return font;
    }
    protected Font createFont(String fontFamily, int fontSize, boolean isBold){
        Font font = workbook.createFont();
        font.setFontName(fontFamily);
        font.setFontHeightInPoints((short) fontSize);
        font.setBold(isBold);
        return font;
    }
    protected Font createFont(String fontFamily, int fontSize, boolean isBold, boolean isItalic){
        Font font = workbook.createFont();
        font.setFontName(fontFamily);
        font.setFontHeightInPoints((short) fontSize);
        font.setBold(isBold);
        font.setItalic(isItalic);
        return font;
    }
    protected CellStyle createCellStyle(Font font){
        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }
    protected void setColumnWidth(int columnIndex, int width, Sheet sheet){
        sheet.setColumnWidth(columnIndex, (width / 8)*256);
    }
    protected void writeDataToCell(Row row, int columnIndex, String value, CellStyle style){
        Cell cell = row.createCell(columnIndex);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }
}
