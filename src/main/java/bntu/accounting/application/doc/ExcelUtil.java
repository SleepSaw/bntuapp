package bntu.accounting.application.doc;

import org.apache.poi.ss.usermodel.*;

public class ExcelUtil {
    protected Workbook workbook;
    /**
     * На основе объекта документа создаёт первый лист
     * */
    public ExcelUtil(Workbook workbook) {
        this.workbook = workbook;
    }
    /**
     * Создаёт объект шрифта на основе аргументов
     * */
    protected Font createFont(String fontFamily, int fontSize){
        Font font = workbook.createFont();
        font.setFontName(fontFamily);
        font.setFontHeightInPoints((short) fontSize);
        return font;
    }
    /**
     * Создаёт объект шрифта на основе аргументов
     * */
    protected Font createFont(String fontFamily, int fontSize, boolean isBold){
        Font font = workbook.createFont();
        font.setFontName(fontFamily);
        font.setFontHeightInPoints((short) fontSize);
        font.setBold(isBold);
        return font;
    }
    /**
     * Создаёт объект шрифта на основе аргументов
     * */
    protected Font createFont(String fontFamily, int fontSize, boolean isBold, boolean isItalic){
        Font font = workbook.createFont();
        font.setFontName(fontFamily);
        font.setFontHeightInPoints((short) fontSize);
        font.setBold(isBold);
        font.setItalic(isItalic);
        return font;
    }
    /**
     * Создаём стиль с переданным шрифтом
     * */
    protected CellStyle setFontForCell(Font font){
        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }
    protected void writeDataToCell(Row row, int column, String value, CellStyle style){
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }
}
