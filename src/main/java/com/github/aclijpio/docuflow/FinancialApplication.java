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


public class FinancialApplication extends Application{


    @Override
    public void start(Stage stage) throws Exception {

        AppConfig appConfig = ConfigLoader.loadConfig();

        FXMLLoader fxmlLoader = new FXMLLoader(FinancialApplication.class.getResource(appConfig.getFiles().getMenu()));
        FinancialMenuController controller = new FinancialMenuController( appConfig.getFiles());

        fxmlLoader.setController(controller);
        HBox root = fxmlLoader.load();

        Invoice invoice = new Invoice();
        invoice.setId(1313L);

        controller.initializeDocuments(
                invoice,
                new Payment(),
                new PaymentRequest()
        );
        Scene scene = new Scene(root);
        stage.setTitle("Financial");
        stage.setMinHeight(root.getMinHeight());
        stage.setMinWidth(root.getMinWidth());
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args) {
        launch();
    }

}
