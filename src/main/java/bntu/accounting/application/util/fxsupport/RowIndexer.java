package bntu.accounting.application.util.fxsupport;

import bntu.accounting.application.models.Employee;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class RowIndexer {
    public static void index(TableColumn<Employee, Integer> column) {
        column.setCellFactory(new Callback<TableColumn<Employee, Integer>, TableCell<Employee, Integer>>() {
            @Override
            public TableCell<Employee, Integer> call(TableColumn<Employee, Integer> param) {
                return new TableCell<Employee, Integer>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            int rowIndex = getIndex() + 1;
                            setText(String.valueOf(rowIndex));
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
    }
}
