package com.github.aclijpio.docuflow.services;

import com.github.aclijpio.docuflow.services.process.DocumentField;
import com.github.aclijpio.docuflow.services.process.DocumentForward;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.time.LocalDate;
import java.util.List;

public class DocumentService {
    private static DocumentForward documentForward;

    public DocumentService(DocumentForward documentForward) {
        DocumentService.documentForward = documentForward;
    }

    public List<Node> createFields() {
        List<DocumentField> fields = documentForward.getFields();
        return fields.stream().map(field -> field.getType().createField(field)).toList();
    }
    public class FieldCreator {
        public static Node createTextField(DocumentField documentField) throws IllegalAccessException {
            Label label = new Label(documentField.getName());
            TextField textField = new TextField((String) documentField.getValue(documentForward.getDocument()));
            return hCombine(label, textField);
        }

        public static Node createDoubleField(DocumentField documentField) throws IllegalAccessException {
            Label label = new Label(documentField.getName());
            TextField textField = new TextField((String) documentField.getValue(documentForward.getDocument()));
            textField.setTextFormatter(TextFormatters.doubleTextFormatter);
            return hCombine(label, textField);
        }

        public static Node createIntegerField(DocumentField documentField) throws IllegalAccessException {
            Label label = new Label(documentField.getName());
            TextField textField = new TextField((String) documentField.getValue(documentForward.getDocument()));
            textField.setTextFormatter(TextFormatters.integerTextFormatter);
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

        public static class TextFormatters{
            private final static TextFormatter<Integer> integerTextFormatter = new TextFormatter<>(
                    new IntegerStringConverter(),
                    0,
                    change -> {
                        String newText = change.getControlNewText();
                        if (newText.matches("\\d*"))
                            return change;

                        return null;
                    }
            );
            private final static TextFormatter<Double> doubleTextFormatter = new TextFormatter<>(
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
