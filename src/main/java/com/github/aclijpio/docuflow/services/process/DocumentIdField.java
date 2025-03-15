package com.github.aclijpio.docuflow.services.process;

import com.github.aclijpio.docuflow.entities.Document;
import com.github.aclijpio.docuflow.services.process.annotations.DocumentId;

import java.lang.reflect.Field;

public class DocumentIdField{
    private final DocumentId idProperty;
    private final Field field;

    public DocumentIdField(DocumentId idProperty, Field field) {
        this.idProperty = idProperty;
        this.field = field;
    }

    public String getName(){
        return idProperty.value();
    }
    public Object getValue(Document document) throws IllegalAccessException {
        field.setAccessible(true);
        Object value = field.get(document);
        field.setAccessible(false);
        return value;
    }

    public DocumentId getIdProperty() {
        return idProperty;
    }

    public Field getField() {
        return field;
    }
}