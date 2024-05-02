package bntu.accounting.application.excel;

import org.apache.poi.ss.usermodel.*;

public abstract class ExcelUtil {
    public DataFormat format;
    public CellStyle leftStyle;
    public CellStyle centerStyle;
    public CellStyle rightStyle;
    protected static CellStyle columnStyle;
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
        format = workbook.createDataFormat();
        initStyles();

    }

    private void initStyles() {
        Font usualFont =  createFont("Times New Roman", 16, false);
        columnStyle = workbook.createCellStyle();
        centerStyle = workbook.createCellStyle();
        leftStyle = workbook.createCellStyle();
        rightStyle = workbook.createCellStyle();
        columnStyle.setFont(usualFont);
        columnStyle.setAlignment(HorizontalAlignment.CENTER);
        columnStyle.setWrapText(true);
        columnStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        columnStyle.setBorderTop(BorderStyle.THIN);
        columnStyle.setBorderBottom(BorderStyle.THIN);
        columnStyle.setBorderLeft(BorderStyle.THIN);
        columnStyle.setBorderRight(BorderStyle.THIN);
        centerStyle.cloneStyleFrom(columnStyle);
        centerStyle.setAlignment(HorizontalAlignment.CENTER);
        leftStyle.cloneStyleFrom(columnStyle);
        leftStyle.setAlignment(HorizontalAlignment.LEFT);
        rightStyle.cloneStyleFrom(columnStyle);
        rightStyle.setAlignment(HorizontalAlignment.RIGHT);
        rightStyle.setDataFormat(format.getFormat("#0.00"));
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
