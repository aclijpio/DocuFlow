package com.github.aclijpio.docuflow.controllers;

import com.github.aclijpio.docuflow.entities.Document;
import com.github.aclijpio.docuflow.services.FinancialMenuService;
import com.github.aclijpio.docuflow.services.impls.FinancialMenuServiceImpl;
import com.github.aclijpio.docuflow.services.process.DocumentForward;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;

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


    public void initializeDocuments(URL resourcePath, Document ... documents) {


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

                    public String getString(DocumentItem forward){
                        Document document = forward.getForward().getDocument();
                        return String.format(
                                "%s от %s номер %s",
                                forward.getForward().getDocumentName(),
                                document.getDate(), document.getNumber()
                        );
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

    @Getter
    @Setter
    public static class DocumentItem{

        private final DocumentForward forward;
        private final BooleanProperty property = new SimpleBooleanProperty(false);
        public DocumentItem(DocumentForward forward) {
            this.forward = forward;
        }
        public void unselect(){
            property.setValue(false);
        }
        public void select(){
            property.setValue(true);
        }

    }
}