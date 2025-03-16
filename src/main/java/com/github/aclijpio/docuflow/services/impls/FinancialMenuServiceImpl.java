package com.github.aclijpio.docuflow.services.impls;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.aclijpio.docuflow.controllers.DocumentController;
import com.github.aclijpio.docuflow.controllers.FinancialMenuController;
import com.github.aclijpio.docuflow.entities.Document;
import com.github.aclijpio.docuflow.entities.DocumentItem;
import com.github.aclijpio.docuflow.services.FinancialMenuService;
import com.github.aclijpio.docuflow.services.exceptions.InvalidDocumentFormatException;
import com.github.aclijpio.docuflow.services.exceptions.InvalidInputException;
import com.github.aclijpio.docuflow.services.process.DocumentForward;
import com.github.aclijpio.docuflow.services.process.DocumentProcessor;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FinancialMenuServiceImpl implements FinancialMenuService {
    private static FinancialMenuController controller;
    private ObjectMapper objectMapper;



    public FinancialMenuServiceImpl(FinancialMenuController controller) {
        FinancialMenuServiceImpl.controller = controller;
    }
    /**
     * Создает список кнопок для каждого документа.
     * @param documents список документов
     * @return Список кнопок
     */
    public List<Button> createDocumentActionButtons(URL resourcePath, Class<? extends Document> ... documents){
        this.objectMapper = createStandartObjectMapper(documents);
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
            DocumentForward newForward = DocumentProcessor.forwardProcess(documentForward.getDocument().getClass());
            Stage stage = createDocumentStage(newForward, resourcePath);
            stage.show();
        });

        return button;
    }
    @SafeVarargs
    private ObjectMapper createStandartObjectMapper(Class<? extends Document> ... documents){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerSubtypes(documents);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_ARRAY);
        return objectMapper;
    }

    private void handleDocumentCompletion(DocumentController documentController, Stage stage) {
        try {
            DocumentForward forward = documentController.getDocument();
            offerSimilar(List.of(new DocumentItem(forward)));
            stage.close();
        } catch (IllegalAccessException e) {
            callErrorAlert("Document field type is mismatch.\n\t" + e.getMessage());
        } catch (InvalidInputException e) {
            callErrorAlert(e.getMessage());
        }
    }
    private Stage createDocumentStage(Scene scene, String title) {
        Stage stage = new Stage();
        stage.setMinWidth(scene.getWidth());
        stage.setMinHeight(scene.getHeight());
        stage.setTitle(title);
        stage.setScene(scene);
        return stage;
    }

    private Stage createDocumentStage(DocumentForward documentForward, URL resourcePath) {
        FXMLLoader fxmlLoader = new FXMLLoader(resourcePath);
        DocumentController documentController = new DocumentController(documentForward);
        fxmlLoader.setController(documentController);
        try {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = createDocumentStage(scene, documentForward.getDocumentName());
            documentController.complete.setOnAction(event -> handleDocumentCompletion(documentController, stage));
            return stage;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load document form " , e);
        }
    }


    @Override
    public void saveToJsonFile(List<Document> documentList){
        if (!documentList.isEmpty()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialFileName("documents.json");

            File createdFile = fileChooser.showSaveDialog(null);

            if (createdFile != null) {
                try (FileWriter fileWriter = new FileWriter(createdFile)) {

                    this.objectMapper.writeValue(fileWriter, documentList);
                } catch (IOException e) {
                    callErrorAlert("Не удалось сохранить в файл.");
                }
            }
        }
    }
    @Override
    public List<Document> loadDocumentsFromJsonFile(){

        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null && selectedFile.exists()){
            try (FileReader fileReader = new FileReader(selectedFile)){
                List<Object> objects = objectMapper.readValue(fileReader, new TypeReference<List<Object>>() {});
                List<Document> documents = new ArrayList<>();
                for (Object obj : objects) {
                    if (obj instanceof Document) {
                        documents.add((Document) obj);
                    } else {
                        throw new InvalidDocumentFormatException(
                                "Invalid document format in JSON file: expected Document, but got " + obj.getClass().getSimpleName()
                        );
                    }
                }
                return documents;
            } catch (StreamReadException e) {
                callErrorAlert(String.format("Failed to read JSON file: %s\n\t%s",
                        selectedFile.getAbsolutePath(),
                        e.getMessage()));
            } catch (DatabindException e) {
                callErrorAlert(String.format("Invalid Document format: %s\n\t%s",
                        selectedFile.getAbsolutePath(),
                        e.getMessage()));
            }catch (InvalidDocumentFormatException e){
                callErrorAlert(e.getMessage());
            }
            catch (IOException e) {
                callErrorAlert(String.format("Failed to read JSON file: %s\n\t%s",
                        selectedFile.getAbsolutePath(),
                        e.getMessage()));
            }

        }
        return List.of();
    }

    public void offerSimilar(List<DocumentItem> items){
        ListView<DocumentItem> listView = controller.documentList;
        ObservableList<DocumentItem> observableList =  listView.getItems();
        boolean replaceAllFlag = false;
        for (DocumentItem item : items) {
            try {
                Optional<DocumentItem> current = isEquals(item, observableList);
                if (current.isPresent()) {
                    DocumentItem document = current.get();
                    ConfirmationCode code;
                    if (replaceAllFlag)
                        code = ConfirmationCode.REPLACE;
                    else
                        code = callConfirmationAlert(document, item);
                    if (code == ConfirmationCode.REPLACE) {
                        int index = observableList.indexOf(document);
                        observableList.set(index, item);
                    } else if (code == ConfirmationCode.REPLACE_ALL) {
                        replaceAllFlag = true;
                        int index = observableList.indexOf(document);
                        observableList.set(index, item);
                    } else if (code == ConfirmationCode.SKIP) continue;
                    else if (code == ConfirmationCode.CLOSE) break;
                } else
                    observableList.add(item);
            } catch (NullPointerException _) {
                continue;
            }
        }
    }
    private Optional<DocumentItem> isEquals(DocumentItem item, ObservableList<DocumentItem> observableList){
        if (item.getForward().getDocument().getNumber() == null) {
            callErrorAlert(String.format("Загружаем объект не имеет номера документа! \n\t%s",
                    FinancialMenuController.getString(item)
            ));
            throw new NullPointerException("Document number is null");
        }
        for (DocumentItem document: observableList) {

            Document current = document.getForward().getDocument();
            Document forReplace = item.getForward().getDocument();

            if(current.getClass().equals(forReplace.getClass()) && forReplace.getNumber().equals(current.getNumber()))
                return Optional.of(document);
        }
        return Optional.empty();
    }
    private static void callErrorAlert(String s){
        Alert alert =  new Alert(Alert.AlertType.ERROR, s);
        alert.show();
    }
    private static ConfirmationCode callConfirmationAlert(DocumentItem current, DocumentItem forReplace){
        Alert alert =  new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Документ уже существует");
        alert.setHeaderText(String.format("Такой документ уже есть в списке:\n\t%s\n\t%s",
                FinancialMenuController.getString(current),
                FinancialMenuController.getString(forReplace)
        ));
        alert.setContentText("Выберите действия");

        ButtonType replaceButton =  new ButtonType("Заменить");
        ButtonType replaceAllButton =  new ButtonType("Заменить все");
        ButtonType skipButton =  new ButtonType("Пропустить");
        alert.getButtonTypes().setAll(replaceButton, replaceAllButton, skipButton);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            if (result.get() == replaceButton) return ConfirmationCode.REPLACE;
            if (result.get() == replaceAllButton) return ConfirmationCode.REPLACE_ALL;
            if (result.get() == skipButton) return ConfirmationCode.SKIP;
        }
        return ConfirmationCode.CLOSE;
    }
    private enum ConfirmationCode{
        REPLACE,
        REPLACE_ALL,
        SKIP,
        CLOSE;
    }

}
