package bntu.accounting.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationStarter extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStarter.class.getResource("/fxml/windows/main_window.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Учёт оплаты труда и педагогической нагрузки работников Лицея БНТУ");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}