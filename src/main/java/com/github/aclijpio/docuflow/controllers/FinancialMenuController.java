package com.github.aclijpio.docuflow.controllers;

import com.github.aclijpio.docuflow.FinancialApplication;
import com.github.aclijpio.docuflow.config.source.Files;
import com.github.aclijpio.docuflow.entities.Document;
import com.github.aclijpio.docuflow.entities.Payment;
import com.github.aclijpio.docuflow.services.process.DocumentForward;
import com.github.aclijpio.docuflow.services.process.DocumentProcessor;
import com.github.aclijpio.docuflow.utils.database.FinancialDatabaseManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class FinancialMenuController implements Initializable {
    private final FinancialDatabaseManager databaseManager;
    private final Files files;
    public ListView<Document> documentList;
    private boolean mainCheckbox = false;


    @FXML
    private CheckBox mainCheckboxControl;

    public FinancialMenuController(FinancialDatabaseManager databaseManager, Files files) {
        this.databaseManager = databaseManager;
        this.files = files;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Инициализация контроллера
    }

    @FXML
    public void checkbox() {
        mainCheckbox = mainCheckboxControl.isSelected();
        System.out.println("Checkbox state: " + mainCheckbox);
    }

    @FXML
    public void showInvoiceForm() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FinancialApplication.class.getResource(files.getEntity()));

        Payment payment = new Payment();
        payment.setId(1L);
        payment.setUser("User");
        payment.setDate(LocalDate.now());
        payment.setNumber("123131");
        payment.setEmployee("Employee");
        DocumentForward documentForward = DocumentProcessor.forwardProcess(payment);

        fxmlLoader.setController(new DocumentController(documentForward));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle(documentForward.getDocumentName());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void showPaymentForm() {
        // Логика для отображения формы платежки
    }

    @FXML
    public void showPaymentRequest() {
        // Логика для отображения заявки на оплату
    }

    @FXML
    public void save() {
        // Логика для сохранения
    }

    @FXML
    public void load() {
        // Логика для загрузки
    }

    @FXML
    public void showDocument() {
        // Логика для просмотра документа
    }

    @FXML
    public void delete() {
        // Логика для удаления
    }

    @FXML
    public void close() {
        // Логика для закрытия приложения
    }
}