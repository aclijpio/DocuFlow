package com.github.aclijpio.docuflow;

import com.github.aclijpio.docuflow.config.ConfigLoader;
import com.github.aclijpio.docuflow.config.source.AppConfig;
import com.github.aclijpio.docuflow.controllers.FinancialMenuController;
import com.github.aclijpio.docuflow.entities.Invoice;
import com.github.aclijpio.docuflow.entities.Payment;
import com.github.aclijpio.docuflow.entities.PaymentRequest;
import com.github.aclijpio.docuflow.utils.database.FinancialDatabaseManager;
import com.github.aclijpio.docuflow.utils.database.JpaUtil;
import jakarta.persistence.EntityManagerFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class FinancialApplication extends Application{


    @Override
    public void start(Stage stage) throws Exception {

        AppConfig appConfig = ConfigLoader.loadConfig();

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory(appConfig.getDatasource());


        FinancialDatabaseManager databaseManager = new FinancialDatabaseManager(entityManagerFactory);

        FXMLLoader fxmlLoader = new FXMLLoader(FinancialApplication.class.getResource(appConfig.getFiles().getMenu()));

        FinancialMenuController controller = new FinancialMenuController(databaseManager, appConfig.getFiles());

        fxmlLoader.setController(controller);
        HBox root = fxmlLoader.load();
        controller.initializeDocuments(
                new Invoice(),
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
