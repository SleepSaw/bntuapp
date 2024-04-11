package bntu.accounting.application.doc.obj;

import bntu.accounting.application.doc.ExcelUtil;
import bntu.accounting.application.models.Item;
import org.apache.poi.ss.usermodel.*;

import java.util.List;

public class ExcelTable {
    private String title;
    private List<ExcelColumn> columns;
    private List<Item> items;


    public List<ExcelColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<ExcelColumn> columns) {
        this.columns = columns;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
