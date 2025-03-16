package com.github.aclijpio.docuflow.controllers;

import com.github.aclijpio.docuflow.entities.Document;
import com.github.aclijpio.docuflow.services.DocumentService;
import com.github.aclijpio.docuflow.services.exceptions.InvalidInputException;
import com.github.aclijpio.docuflow.services.impls.DocumentServiceImpl;
import com.github.aclijpio.docuflow.services.process.DocumentForward;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class DocumentController implements Initializable {

    private final DocumentService service;
    @FXML
    public Button complete;

    @Getter
    @FXML
    private VBox form;

    @FXML
    private HBox buttonsForm;
    @FXML
    @Setter
    private Consumer<Document> completeProcess;

    public DocumentController(DocumentForward documentForward) {
        this.service = new DocumentServiceImpl(documentForward);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Node> nodes = service.createFields();
        form.getChildren().addAll(nodes);
    }

    public DocumentForward getDocument() throws InvalidInputException, IllegalAccessException {
        return service.formToDocument(form);
    }


}
