package com.github.aclijpio.docuflow.services.impls;

import com.github.aclijpio.docuflow.FinancialApplication;
import com.github.aclijpio.docuflow.config.source.Files;
import com.github.aclijpio.docuflow.controllers.DocumentController;
import com.github.aclijpio.docuflow.entities.Document;
import com.github.aclijpio.docuflow.services.FinancialMenuService;
import com.github.aclijpio.docuflow.services.process.DocumentForward;
import com.github.aclijpio.docuflow.services.process.DocumentProcessor;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FinancialMenuServiceImpl implements FinancialMenuService {
    private final Files files;

    public FinancialMenuServiceImpl(Files files) {
        this.files = files;
    }
    /**
     * Создает список кнопок для каждого документа.
     * @param documents список документов
     * @return Список кнопок
     */
    public List<Button> createDocumentActionButtons(Document ... documents){
        return Arrays.stream(documents)
                .map(DocumentProcessor::forwardProcess)
                .map(this::createActionButton)
                .toList();
    }
    /**
     * Создает кнопку, которая открывать форму документа.
     * @param documentForward документ с метаданными
     * @return кнопку
     */
    private Button createActionButton(DocumentForward documentForward){
        Button button = new Button();
        button.setText(documentForward.getDocumentName());
        button.setOnAction(event -> {
            Stage stage = createDocumentStage(documentForward);
            stage.show();
        });

        return button;
    }

    private static Parent loadDocument(String resourcePath, DocumentForward documentForward) {
        FXMLLoader fxmlLoader = new FXMLLoader(FinancialApplication.class.getResource(resourcePath));
        DocumentController documentController = new DocumentController(documentForward);
        fxmlLoader.setController(documentController);
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load document form " , e);
        }
    }
    private void processCompleteDocumentStage(){

    }
    private Stage createDocumentStage(DocumentForward documentForward) {
        Scene scene = new Scene(loadDocument(files.getEntity(), documentForward));
        Stage stage = new Stage();
        stage.setMinWidth(scene.getWidth());
        stage.setMinHeight(scene.getHeight());
        stage.setTitle(documentForward.getDocumentName());
        stage.setScene(scene);
        return stage;
    }

}
