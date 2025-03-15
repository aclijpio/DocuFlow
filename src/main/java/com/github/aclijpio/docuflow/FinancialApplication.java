package com.github.aclijpio.docuflow;

import com.github.aclijpio.docuflow.config.ConfigLoader;
import com.github.aclijpio.docuflow.config.source.AppConfig;
import com.github.aclijpio.docuflow.controllers.FinancialMenuController;
import com.github.aclijpio.docuflow.utils.database.FinancialDatabaseManager;
import com.github.aclijpio.docuflow.utils.database.JpaUtil;
import jakarta.persistence.EntityManagerFactory;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;


public class FinancialApplication extends Application{

    @FXML
    private ListView<String> documentList;
    @Override
    public void start(Stage stage) throws Exception {

        AppConfig appConfig = ConfigLoader.loadConfig();

        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory(appConfig.getDatasource());


        FinancialDatabaseManager databaseManager = new FinancialDatabaseManager(entityManagerFactory);

        FXMLLoader fxmlLoader = new FXMLLoader(FinancialApplication.class.getResource(appConfig.getFiles().getMenu()));

        fxmlLoader.setController(new FinancialMenuController(databaseManager, appConfig.getFiles()));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Financial");
        stage.setScene(scene);
        stage.show();

    }
    public static void main(String[] args) {
        launch();
    }

}
