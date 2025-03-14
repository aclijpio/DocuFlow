package com.github.aclijpio.docuflow.controllers;

import com.github.aclijpio.docuflow.entities.Document;
import com.github.aclijpio.docuflow.services.DocumentService;
import com.github.aclijpio.docuflow.services.impls.DocumentServiceImpl;
import com.github.aclijpio.docuflow.services.process.DocumentForward;
import javafx.event.ActionEvent;
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


    public DocumentForward getDocument()  {

        try {
            return service.formToDocument(form);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void onComplete(ActionEvent actionEvent) throws IllegalAccessException {
    }


/*    @Getter
    private ValidationButton save;
    @Getter
    private ValidationButton update;*/

/*
    public DocumentController(DocumentHelper helper, DocumentStage stage) {
        this.service = new DocumentService(helper.getDatabaseManager());
        this.helper = helper;
        this.parentDocumentHelper = new ParentDocumentHelper(helper, this);
        this.stage = stage;
    }*/


/*    @FXML
    private void close(){
        this.service.closeForm(stage);
    }*/
/*
    @FXML
    private void save(){
        LabelTree labelTree = this.service.getSavedTree(this.stage.getDocument(), this.parentDocumentHelper, this.nodeRegistry);
        this.nodeRegistry.setIdNode(
                labelTree
        );
        getForm().getChildren().add(0,
                    labelTree.getHBox()
                );
        fillDocumentMode();
        stage.addAction(DocumentActionCode.ADD);
    }
    @FXML
    private void update(){
        this.service.getUpdatedTree(this.stage.getDocument(), this.parentDocumentHelper, this.nodeRegistry);
        stage.addAction(DocumentActionCode.UPDATE);
        new Alert(Alert.AlertType.INFORMATION, "Документ был обновлен").show();
    }
    @FXML
    private void delete(){
        Optional<Node> nodeOptional = nodeRegistry.getIdNode();

        if (nodeOptional.isEmpty())
            throw new TextFieldNotFoundException("Id not found on the form.");

        TextField textField = (TextField) nodeOptional.get();
        long id = Long.parseLong(textField.getText());
        this.service.delete(id);
        this.stage.close();
        stage.addAction(DocumentActionCode.DELETE);
    }


    private NodeRegistry loadEntity(){
        NodeRegistry nodeRegistry = this.stage.getDocument().toNodeTree(parentDocumentHelper);

        getForm().getChildren().addAll(
                nodeRegistry.getNodes()
        );

        return nodeRegistry;
    }
    @FXML
    public void initialize() {
        this.save = new SaveButton("Сохранить", this);
        this.update = new UpdateButton("Обновить", this);

        buttonsForm.getChildren().addAll(
                this.save,
                this.update
        );
        this.nodeRegistry = loadEntity();


        this.nodeRegistry = setDocumentIdToNodeRegistry();


        Optional<Node> node = this.nodeRegistry.getIdNode();
        if (node.isPresent())
            fillDocumentMode();
        else
            emptyDocumentMode();


    }

    private boolean availableDocumentId(){
        return this.stage.getDocument().getId() != null;
    }
    private NodeRegistry setDocumentIdToNodeRegistry(){
        if (availableDocumentId()){
            LabelTree labelTree = parentDocumentHelper.createIdNode(this.stage.getDocument().getId());
            this.nodeRegistry.setIdNode(
                    labelTree
            );
            getForm().getChildren().add(0,
                    labelTree.getHBox()
            );
            fillDocumentMode();

            getForm().autosize();
        }
        return this.nodeRegistry;
    }

    static private class SaveButton extends ValidationButton {
        private final DocumentController documentController;
        public SaveButton(String name, DocumentController controller) {
            super(name);
            this.documentController = controller;
        }
        @Override
        public void executeEvent() {
            documentController.save();
        }
    }
    static private class UpdateButton extends ValidationButton {
        private final DocumentController documentController;
        public UpdateButton(String name, DocumentController controller) {
            super(name);
            this.documentController = controller;
        }
        @Override
        public void executeEvent() {
            documentController.update();
        }
    }
    private void fillDocumentMode(){
        this.save.setVisible(false);
        this.update.setVisible(true);
        this.deleteButton.setVisible(true);
    }
    private  void emptyDocumentMode(){
        this.save.setVisible(true);
        this.update.setVisible(false);
        this.deleteButton.setVisible(false);
    }*/
}
