package com.github.aclijpio.docuflow;

import com.github.aclijpio.docuflow.config.ConfigLoader;
import com.github.aclijpio.docuflow.config.source.AppConfig;
import com.github.aclijpio.docuflow.controllers.FinancialMenuController;
import com.github.aclijpio.docuflow.entities.Invoice;
import com.github.aclijpio.docuflow.entities.Payment;
import com.github.aclijpio.docuflow.entities.PaymentRequest;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;


public class FinancialApplication extends Application{


    @Override
    public void start(Stage stage) throws Exception {

        AppConfig appConfig = ConfigLoader.loadConfig();

        FXMLLoader fxmlLoader = new FXMLLoader(FinancialApplication.class.getResource(appConfig.getFiles().getMenu()));
        FinancialMenuController controller = new FinancialMenuController();

        fxmlLoader.setController(controller);
        HBox root = fxmlLoader.load();

        URL documentPath  = FinancialApplication.class.getResource(appConfig.getFiles().getEntity());

        controller.initializeDocuments(
                documentPath,
                Invoice.class,
                Payment.class,
                PaymentRequest.class
        );
        Scene scene = new Scene(root);
        calculateMinSize(stage, scene);
        stage.setTitle("Financial");
        stage.setScene(scene);
        stage.show();

    }

    public static void calculateMinSize(Stage stage, Scene scene) {
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (oldVal.doubleValue() == 0) {
                stage.setMinWidth(newVal.doubleValue() + 30);
            }
        });

        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            if (oldVal.doubleValue() == 0) {
                stage.setMinHeight(newVal.doubleValue() + 30);
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }

}
