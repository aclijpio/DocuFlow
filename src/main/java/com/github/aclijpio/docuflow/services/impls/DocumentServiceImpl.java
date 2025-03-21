package com.github.aclijpio.docuflow.services.impls;

import com.github.aclijpio.docuflow.entities.CurrencyCode;
import com.github.aclijpio.docuflow.services.DocumentService;
import com.github.aclijpio.docuflow.services.exceptions.InvalidInputException;
import com.github.aclijpio.docuflow.services.process.DocumentField;
import com.github.aclijpio.docuflow.services.process.DocumentForward;
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

    /**
     * Меняет значения переменных документа на основе полей формы.
     * Типы полей указываются в аннотации DocumentProperty, чтобы правильно идентифицировать Node`ы
     * @param box форма (form)
     * @return документ
     */

    public DocumentForward formToDocument(Node box) throws IllegalAccessException, InvalidInputException {
        List<DocumentField> fields = documentForward.getFields();
        VBox form = (VBox) box;
        for (int i = 0; i < fields.size(); i++){
            DocumentField field = fields.get(i);
            HBox hBox = (HBox)form.getChildren().get(i);
            Node node = hBox.getChildren().get(1);


            Class<?> type = field.getType();

            if (type.isEnum())
            {
                @SuppressWarnings("unchecked")
                ComboBox<CurrencyCode> comboBox = (ComboBox<CurrencyCode>) node;
                CurrencyCode currencyCode = comboBox.getValue();
                documentForward.setValueByIndex(i, currencyCode);
            }
            if (type.equals(String.class))
            {
                assert node instanceof TextField;
                TextField textField = (TextField) node;
                String value = textField.getText();
                if (value == null || value.isEmpty())
                    throw new InvalidInputException(String.format("Поле '%s.%s' не может быть пустым.",
                            field.getName(), field.getType()));
                documentForward.setValueByIndex(i, value);
            }
            if (type.equals(Integer.class))
            {
                assert node instanceof TextField;
                TextField textField = (TextField) node;
                Integer value = Integer.parseInt(textField.getText());
                if (value  < 0)
                    throw new InvalidInputException(String.format("Значение поле '%s.%s' некорректна.",
                            field.getName(), field.getType()));
                documentForward.setValueByIndex(i, value);
            }
            if (type.equals(Double.class))
            {
                assert node instanceof TextField;
                TextField textField = (TextField) node;
                Double value = Double.parseDouble(textField.getText());
                if (value.isNaN() || value.isInfinite())
                    throw new InvalidInputException(String.format("Значение поле '%s.%s' некорректна.",
                            field.getName(), field.getType()));
                documentForward.setValueByIndex(i, value);
            }
            if (type.equals(LocalDate.class))
            {
                DatePicker datePicker = (DatePicker) node;
                LocalDate localDate = datePicker.getValue();
                if (localDate == null)
                    throw new InvalidInputException(String.format("Поле '%s.%s' не может быть пустым.",
                            field.getName(), field.getType()));
                documentForward.setValueByIndex(i, localDate);
            }


        }
        return documentForward;
    }

    /**
     * Создает поля формы на основе метаданных и переменных документа.
     * Типы полей указываются в аннотации DocumentProperty, чтобы создать соответствующие Node`ы
     * @return список полей в виде Node
     */
    public List<Node> createFields() {

        List<DocumentField> fields = documentForward.getFields();

        return new ArrayList<>(fields.stream().map(field -> {

            Class<?> type = field.getType();
            if (type.isEnum())
                return createField(FieldCreator::createCurrencyEnumField, field);
            if (type.equals(String.class))
                return createField(FieldCreator::createTextField, field);
            if (type.equals(Integer.class))
                return createField(FieldCreator::createIntegerField, field);
            if (type.equals(Double.class))
                return createField(FieldCreator::createDoubleField, field);
            if (type.equals(LocalDate.class))
                return createField(FieldCreator::createDateField, field);


            throw new RuntimeException("Invalid class type " + type.getName());
        }).toList());
    }
    private Node createField(FieldCreatorFunction creator, DocumentField field) {
        try {
            return creator.apply(field);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @FunctionalInterface
    private interface FieldCreatorFunction{
        Node apply(DocumentField field) throws IllegalAccessException;
    }

    /**
     * Готовые решения создания полей (Node элементов)
     */
    private static class FieldCreator {
        public static Node createTextField(DocumentField documentField) throws IllegalAccessException {
            Label label = new Label(documentField.getName());
            TextField textField = new TextField((String) documentField.getValue(documentForward.getDocument()));
            return hCombine(label, textField);
        }

        public static Node createDoubleField(DocumentField documentField) throws IllegalAccessException {
            Label label = new Label(documentField.getName());
            Double value = (Double) documentField.getValue(documentForward.getDocument());
            TextField textField = new TextField(String.valueOf(value));
            textField.setTextFormatter(TextFormatters.createDoubleFormatter(value));
            return hCombine(label, textField);
        }
        public static  Node  createCurrencyEnumField(DocumentField documentField) throws IllegalAccessException {
            Label label = new Label(documentField.getName());
            ComboBox<CurrencyCode> currencyCodeComboBox = new ComboBox<>(FXCollections.observableArrayList(CurrencyCode.values()));
            currencyCodeComboBox.setValue(CurrencyCode.values()[0]);
            return hCombine(label, currencyCodeComboBox);
        }
        public static Node createIntegerField(DocumentField documentField) throws IllegalAccessException {
            Label label = new Label(documentField.getName());
            Integer value = (Integer) documentField.getValue(documentForward.getDocument());
            TextField textField = new TextField((String.valueOf(value)));
            textField.setTextFormatter(TextFormatters.createIntegerFormatter(value));
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
            HBox hBox = new HBox(nodes);
            hBox.setId("hbox");
            return hBox;
        }

        private static class TextFormatters{
            private static TextFormatter<Integer> createIntegerFormatter(Integer value) {
                return  new TextFormatter<>(
                        new IntegerStringConverter(),
                        value,
                        change -> {
                            String newText = change.getControlNewText();
                            if (newText.matches("\\d*"))
                                return change;

                            return null;
                        }
                );
            }
            private static TextFormatter<Double> createDoubleFormatter(Double value) {
                return new TextFormatter<>(
                        new DoubleStringConverter(),
                        value,
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
