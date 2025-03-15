package com.github.aclijpio.docuflow.controllers;

import com.github.aclijpio.docuflow.config.source.Files;
import com.github.aclijpio.docuflow.entities.Document;
import com.github.aclijpio.docuflow.services.FinancialMenuService;
import com.github.aclijpio.docuflow.services.impls.FinancialMenuServiceImpl;
import com.github.aclijpio.docuflow.utils.database.FinancialDatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class FinancialMenuController {
    private final FinancialDatabaseManager databaseManager;
    public ListView<Document> documentList;
    public VBox documentContainer;

    private FinancialMenuService service;

    @FXML
    private CheckBox mainCheckboxControl;

    public FinancialMenuController(FinancialDatabaseManager databaseManager, Files files) {
        this.databaseManager = databaseManager;
        service = new FinancialMenuServiceImpl(files);
    }


    public void initializeDocuments(Document ... documents) {
        documentContainer.getChildren().addAll(
                service.createDocumentActionButtons(
                        documents
                )
        );
    }
    @FXML
    public void checkbox() {
        boolean mainCheckbox = mainCheckboxControl.isSelected();
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