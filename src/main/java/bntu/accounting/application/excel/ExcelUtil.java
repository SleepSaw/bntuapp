package bntu.accounting.application.excel;

import org.apache.poi.ss.usermodel.*;

public abstract class ExcelUtil {
    protected Workbook workbook;
    protected Sheet sheet;
    /**
     * На основе объекта документа создаёт первый лист
     * */
    public ExcelUtil(Workbook workbook) {
        this.workbook = workbook;
        if (workbook!=null) {
            this.sheet = workbook.getSheetAt(0);
            sheet.setDefaultRowHeight((short)15);
        }

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
    /**
     * Установка ширины колонки
     * */
    protected void setColumnWidth(int columnIndex, int width, Sheet sheet){
        sheet.setColumnWidth(columnIndex, (width / 8) * 256);
    }
    /**
     * Запись данных в ячейку и кастомизация
     * */
    protected void writeDataToCell(Row row, int columnIndex, String value, CellStyle style){
        Cell cell = row.createCell(columnIndex);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }
}
