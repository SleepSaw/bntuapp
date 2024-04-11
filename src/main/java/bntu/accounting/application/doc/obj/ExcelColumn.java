package bntu.accounting.application.doc.obj;

import bntu.accounting.application.doc.RotateType;
import org.apache.poi.ss.usermodel.CellStyle;

public class ExcelColumn {
    private Integer index;
    private String headerValue;
    private CellStyle style;
    private Integer numberOfRows;
    private Integer numberOfColumns;
    private Double pixelHeight;
    private Double PixelWidth;
    private RotateType rotate;

    public ExcelColumn(Integer index, String headerValue, CellStyle style, Integer numberOfRows,
                       Integer numberOfColumns, Double pixelHeight, Double pixelWidth, RotateType rotate) {
        this.index = index;
        this.headerValue = headerValue;
        this.style = style;
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.pixelHeight = pixelHeight;
        PixelWidth = pixelWidth;
        this.rotate = rotate;
        if(this.rotate == RotateType.VERTICAL) this.style.setRotation((short) 90);
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getHeaderValue() {
        return headerValue;
    }

    public void setHeaderValue(String headerValue) {
        this.headerValue = headerValue;
    }

    public CellStyle getStyle() {
        return style;
    }

    public void setStyle(CellStyle style) {
        this.style = style;
    }

    public Integer getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(Integer numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public Integer getNumberOfColumns() {
        return numberOfColumns;
    }

    public void setNumberOfColumns(Integer numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    public Double getPixelHeight() {
        return pixelHeight;
    }

    public void setPixelHeight(Double pixelHeight) {
        this.pixelHeight = pixelHeight;
    }

    public Double getPixelWidth() {
        return PixelWidth;
    }

    public void setPixelWidth(Double pixelWidth) {
        PixelWidth = pixelWidth;
    }

    public RotateType getRotate() {
        return rotate;
    }

    public void setRotate(RotateType rotate) {
        if(this.rotate == RotateType.HORIZONTAL) this.style.setRotation((short) 0);
        else this.style.setRotation((short) 90);
        this.rotate = rotate;
    }
}
