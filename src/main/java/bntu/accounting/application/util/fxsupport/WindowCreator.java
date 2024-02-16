package bntu.accounting.application.util.fxsupport;

import bntu.accounting.application.controllers.windows.AddingPerformerWindowController;
import bntu.accounting.application.controllers.windows.ShowVacancyWindowController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowCreator {
    public static void createWindow(String resourcePath, Initializable parentController,
                                    Initializable controller) throws LoadException {
        FXMLLoader loader = createLoader(resourcePath,parentController);
        loader.setController(controller);
        initWindow(loader);
    }

    public static void createWindow(String resourcePath, Initializable parentController) throws LoadException {
        FXMLLoader loader = new FXMLLoader(parentController.getClass()
                .getResource(resourcePath));
        initWindow(loader);
    }
    private static FXMLLoader createLoader(String resourcePath, Initializable parentController) {
        return new FXMLLoader(parentController.getClass().getResource(resourcePath));
    }
    private static void initWindow(FXMLLoader loader) throws LoadException {
        Stage stage = new Stage();
        Scene scene;
        try {
            scene = new Scene(loader.load());
        }
        catch (NullPointerException e) {
            throw new NullPointerException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
        stage.setScene(scene);
        stage.show();
    }
}
