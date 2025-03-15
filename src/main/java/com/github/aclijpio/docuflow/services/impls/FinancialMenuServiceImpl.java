package com.github.aclijpio.docuflow.services.impls;

import com.github.aclijpio.docuflow.controllers.DocumentController;
import com.github.aclijpio.docuflow.controllers.FinancialMenuController;
import com.github.aclijpio.docuflow.entities.Document;
import com.github.aclijpio.docuflow.services.FinancialMenuService;
import com.github.aclijpio.docuflow.services.process.DocumentForward;
import com.github.aclijpio.docuflow.services.process.DocumentProcessor;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class FinancialMenuServiceImpl implements FinancialMenuService {
    private static FinancialMenuController controller;

    public FinancialMenuServiceImpl(FinancialMenuController controller) {
        FinancialMenuServiceImpl.controller = controller;
    }
    /**
     * Создает список кнопок для каждого документа.
     * @param documents список документов
     * @return Список кнопок
     */
    public List<Button> createDocumentActionButtons(URL resourcePath, Document ... documents){
        return Arrays.stream(documents)
                .map(DocumentProcessor::forwardProcess)
                .map(forward -> createActionButton(forward, resourcePath))
                .toList();
    }
    /**
     * Создает кнопку, которая открывать форму документа.
     * @param documentForward документ с метаданными
     * @return кнопку
     */
    private Button createActionButton(DocumentForward documentForward, URL resourcePath){
        Button button = new Button();
        button.setText(documentForward.getDocumentName());
        button.setOnAction(event -> {
            Stage stage = createDocumentStage(documentForward, resourcePath);
            stage.show();
        });

        return button;
    }

    private Stage createDocumentStage(DocumentForward documentForward, URL resourcePath) {

        FXMLLoader fxmlLoader = new FXMLLoader(resourcePath);
        DocumentController documentController = new DocumentController(documentForward);



        fxmlLoader.setController(documentController);
        try {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();

            documentController.complete.setOnAction(event -> {
                DocumentForward forward = documentController.getDocument();
                controller.documentList.getItems().add(new FinancialMenuController.DocumentItem(forward));
                stage.close();
            });
            stage.setMinWidth(scene.getWidth());
            stage.setMinHeight(scene.getHeight());
            stage.setTitle(documentForward.getDocumentName());
            stage.setScene(scene);
            return stage;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load document form " , e);
        }



    }

}
