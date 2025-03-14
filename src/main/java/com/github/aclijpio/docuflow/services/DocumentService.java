package com.github.aclijpio.docuflow.services;

import com.github.aclijpio.docuflow.services.process.DocumentField;
import com.github.aclijpio.docuflow.services.process.DocumentForward;
import com.github.aclijpio.docuflow.services.process.annotations.PropertyType;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.util.converter.IntegerStringConverter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DocumentService {


    TextFormatter<Integer> formatter = new TextFormatter<>(
            new IntegerStringConverter(),
            0,
            change -> {
                String newText = change.getControlNewText();
                if (newText.matches("\\d*")) {
                    return change;
                }
                return null;
            }
    );

    private final DocumentForward documentForward;

    public DocumentService(DocumentForward documentForward) {
        this.documentForward = documentForward;
    }


    public List<Node> createFields() {
        List<Node> boxes = new ArrayList<>();

        List<DocumentField> fields = documentForward.getFields();
        return fields.stream().map(field -> {
            try {
                if (field.getType().equals(PropertyType.TEXT_FIELD)) {
                    return createTextField(field);
                }
                if (field.getType().equals(PropertyType.DATE)){
                    return createDateField(field);
                }
                return createTextField(field);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }).toList();


    }
    private Node createTextField(DocumentField documentField) throws IllegalAccessException {
        Label label = new Label(documentField.getName());
        TextField textField = new TextField((String) documentField.getValue(documentForward.getDocument()));
        return hCombine(label, textField);
    }
    private Node createDateField(DocumentField documentField) throws IllegalAccessException {
        Label label = new Label(documentField.getName());
        LocalDate date = (LocalDate) documentField.getValue(documentForward.getDocument());
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(date != null ? date : LocalDate.now());
        datePicker.setEditable(false);
        return hCombine(label, datePicker);
    }

    private HBox hCombine(Node... nodes){
        return new HBox(
                nodes
        );
    }

}
