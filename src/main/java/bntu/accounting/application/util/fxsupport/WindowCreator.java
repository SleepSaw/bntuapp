package bntu.accounting.application.util.fxsupport;

import bntu.accounting.application.controllers.VisualComponentsInitializer;
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
                                    VisualComponentsInitializer controller) throws LoadException {
        FXMLLoader loader = createLoader(resourcePath, parentController);
        initWindow(loader,controller);
    }

    public static void createWindow(String resourcePath, Initializable parentController) throws LoadException {
        FXMLLoader loader = new FXMLLoader(parentController.getClass()
                .getResource(resourcePath));
        initWindow(loader);
    }
    private static FXMLLoader createLoader(String resourcePath, Initializable parentController) {
        return new FXMLLoader(parentController.getClass().getResource(resourcePath));
    }
    private static void  initWindow(FXMLLoader loader, VisualComponentsInitializer controller) throws LoadException {
        Stage stage = new Stage();
        controller.setStage(stage);
        loader.setController(controller);
        loadWindow(stage,loader);
    }
    private static void  initWindow(FXMLLoader loader) throws LoadException {
        Stage stage = new Stage();
        loadWindow(stage,loader);
    }
    private static void loadWindow(Stage stage, FXMLLoader loader){
        Scene scene;
        try {
            scene = new Scene(loader.load());
        }
        catch (NullPointerException e) {
            throw new NullPointerException();
        } catch (IOException e) {
            System.out.println(e);
            throw new RuntimeException();
        }
        stage.setScene(scene);
        stage.show();
    }
}
