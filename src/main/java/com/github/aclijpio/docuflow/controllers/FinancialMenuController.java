package com.github.aclijpio.docuflow.controllers;

import com.github.aclijpio.docuflow.entities.Document;
import com.github.aclijpio.docuflow.entities.DocumentItem;
import com.github.aclijpio.docuflow.services.FinancialMenuService;
import com.github.aclijpio.docuflow.services.impls.FinancialMenuServiceImpl;
import com.github.aclijpio.docuflow.services.process.DocumentForward;
import com.github.aclijpio.docuflow.services.process.DocumentProcessor;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class FinancialMenuController {
    @FXML
    public ListView<DocumentItem> documentList;
    @FXML
    public VBox documentContainer;

    private final FinancialMenuService service;

    @FXML
    private CheckBox mainCheckboxControl;

    public FinancialMenuController() {
        service = new FinancialMenuServiceImpl(this);
    }


    @SafeVarargs
    public final void initializeDocuments(URL resourcePath, Class<? extends Document>... documents) {
        documentList.setCellFactory(CheckBoxListCell.forListView(
                new Callback<DocumentItem, ObservableValue<Boolean>>() {
                    @Override
                    public ObservableValue<Boolean> call(DocumentItem document) {
                        BooleanProperty observable = document.getProperty();
                        observable.addListener((obs, wasSelected, isNowSelected) -> {
                            observable.setValue(isNowSelected);
                        });

                        return observable;
                    }
                }, new StringConverter<DocumentItem>() {
                    @Override
                    public String toString(DocumentItem documentForward) {
                        return getString(documentForward);
                    }
                    @Override
                    public DocumentItem fromString(String s) {
                        return null;
                    }
                }
        ));
        documentContainer.getChildren().addAll(
                service.createDocumentActionButtons(
                        resourcePath,
                        documents
                )
        );
    }

    public static String getString(DocumentItem forward){
        Document document = forward.getForward().getDocument();
        return String.format(
                "%s от %s номер %s",
                forward.getForward().getDocumentName(),
                document.getDate(), document.getNumber()
        );
    }
    @FXML
    public void checkbox() {
        boolean isSelected = mainCheckboxControl.isSelected();
        documentList.getItems().forEach(item -> {
            if (isSelected) {
                item.select();
            } else {
                item.unselect();
            }
        });
    }

    @FXML
    public void save() {
        this.service.saveToJsonFile(
                findSelectedDocumentItems(this.documentList)
        );
    }

    @FXML
    public void load() {
        List<DocumentItem> items = this.service.loadDocumentsFromJsonFile().stream()
                .map(DocumentProcessor::forwardProcess)
                .map(DocumentItem::new)
                .toList();
        service.offerSimilar(items);

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

    public List<Document> findSelectedDocumentItems(ListView<DocumentItem> documentList){
        return documentList.getItems().stream()
                .filter(DocumentItem::isSelected)
                .map(DocumentItem::getForward)
                .map(DocumentForward::getDocument)
                .collect(Collectors.toList());
    }


}