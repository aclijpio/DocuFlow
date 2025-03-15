package com.github.aclijpio.docuflow.services.impls;

import com.github.aclijpio.docuflow.entities.CurrencyCode;
import com.github.aclijpio.docuflow.entities.Document;
import com.github.aclijpio.docuflow.services.DocumentService;
import com.github.aclijpio.docuflow.services.process.DocumentField;
import com.github.aclijpio.docuflow.services.process.DocumentForward;
import com.github.aclijpio.docuflow.services.process.DocumentIdField;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DocumentServiceImpl implements DocumentService {
    private static DocumentForward documentForward;

    public DocumentServiceImpl(DocumentForward documentForward) {
        DocumentServiceImpl.documentForward = documentForward;
    }

    public Document formToDocument(Node box) throws IllegalAccessException {
        List<DocumentField> fields = documentForward.getFields();
        Document document = documentForward.getDocument();
        VBox form = (VBox) box;
        for (int i = 0; i < fields.size(); i++){
            DocumentField field = fields.get(i);
            HBox hBox = (HBox)form.getChildren().get(i + 1);
            Node node = hBox.getChildren().get(1);
            System.out.println(node);
            switch (field.getType()){
                case TEXT_FIELD -> {
                    TextField textField = (TextField) node;
                    String value = textField.getText();
                    documentForward.setValueByIndex(i, value);
                }
                case DATE -> {
                    DatePicker datePicker = (DatePicker) node;
                    LocalDate localDate = datePicker.getValue();
                    documentForward.setValueByIndex(i, localDate);
                }
                case DOUBLE -> {
                    TextField textField = (TextField) node;
                    Double value = Double.parseDouble(textField.getText());
                    documentForward.setValueByIndex(i, value);
                }
                case ENUM -> {
                    ComboBox<CurrencyCode> comboBox = (ComboBox<CurrencyCode>) node;
                    CurrencyCode currencyCode = comboBox.getValue();
                    documentForward.setValueByIndex(i, currencyCode);
                }
            };
        }

        for (DocumentField field : fields) {
            System.out.println(field.getValue(document));
        }

        return document;
    }

    public List<Node> createFields() {

        List<DocumentField> fields = documentForward.getFields();
        List<Node> result = new ArrayList<>();
        if (documentForward.hasId()){
            result.add(createIdField(documentForward.getDocumentIdField()));
        }
        result.addAll(fields.stream().map(field -> {
            return switch (field.getType()){
                case TEXT_FIELD -> createField(FieldCreator::createTextField, field);
                case DATE -> createField(FieldCreator::createDateField, field);
                case DOUBLE -> createField(FieldCreator::createDoubleField, field);
                case ENUM -> createField(FieldCreator::createCurrencyEnumField, field);
            };
        }).toList());

        return result;
    }
    private Node createField(FieldCreatorFunction creator, DocumentField field) {
        try {
            return creator.apply(field);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private Node createIdField(DocumentIdField field) {
        try {
            return FieldCreator.createIdField(field);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @FunctionalInterface
    private interface FieldCreatorFunction{
        Node apply(DocumentField field) throws IllegalAccessException;
    }


    private static class FieldCreator {
        public static Node createTextField(DocumentField documentField) throws IllegalAccessException {
            Label label = new Label(documentField.getName());
            TextField textField = new TextField((String) documentField.getValue(documentForward.getDocument()));
            return hCombine(label, textField);
        }

        public static Node createDoubleField(DocumentField documentField) throws IllegalAccessException {
            Label label = new Label(documentField.getName());
            TextField textField = new TextField(String.valueOf(documentField.getValue(documentForward.getDocument())));
            textField.setTextFormatter(TextFormatters.createDoubleFormatter());
            return hCombine(label, textField);
        }
        public static  Node  createCurrencyEnumField(DocumentField documentField) throws IllegalAccessException {
            Label label = new Label(documentField.getName());
            ComboBox<CurrencyCode> currencyCodeComboBox = new ComboBox<>(FXCollections.observableArrayList(CurrencyCode.values()));
            return hCombine(label, currencyCodeComboBox);
        }
        public static Node createIntegerField(DocumentField documentField) throws IllegalAccessException {
            Label label = new Label(documentField.getName());
            TextField textField = new TextField((String.valueOf(documentField.getValue(documentForward.getDocument()))));
            textField.setTextFormatter(TextFormatters.createIntegerFormatter());
            return hCombine(label, textField);
        }
        public static Node createIdField(DocumentIdField idField) throws IllegalAccessException {
            Label label = new Label(idField.getName());
            TextField textField = new TextField(String.valueOf(idField.getValue(documentForward.getDocument())));
            textField.setEditable(false);
            return hCombine(label, textField);
        }

        public static Node createDateField(DocumentField documentField) throws IllegalAccessException {
            Label label = new Label(documentField.getName());
            LocalDate date = (LocalDate) documentField.getValue(documentForward.getDocument());
            DatePicker datePicker = new DatePicker();
            datePicker.setValue(date != null ? date : LocalDate.now());
            datePicker.setEditable(false);
            return hCombine(label, datePicker);
        }

        private static HBox hCombine(Node... nodes){
            return new HBox(
                    nodes
            );
        }

        private static class TextFormatters{
            private static TextFormatter<Integer> createIntegerFormatter() {
                return  new TextFormatter<>(
                        new IntegerStringConverter(),
                        0,
                        change -> {
                            String newText = change.getControlNewText();
                            if (newText.matches("\\d*"))
                                return change;

                            return null;
                        }
                );
            }
            private static TextFormatter<Double> createDoubleFormatter() {
                return new TextFormatter<>(
                        new DoubleStringConverter(),
                        0.0,
                        change -> {
                            String newText = change.getControlNewText();
                            if (newText.matches("-?\\d*(\\.\\d*)?")) {
                                try {
                                    Double.parseDouble(newText);
                                    return change;
                                } catch (NumberFormatException e) {
                                    return null;
                                }
                            }
                            return null;
                        }
                );
            }
        }
    }

}
