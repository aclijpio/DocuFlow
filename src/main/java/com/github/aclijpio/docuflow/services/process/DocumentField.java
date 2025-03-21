package com.github.aclijpio.docuflow.services.process;

import com.github.aclijpio.docuflow.entities.Document;
import com.github.aclijpio.docuflow.services.process.annotations.DocumentProperty;

import java.lang.reflect.Field;

public class DocumentField {

    private final DocumentProperty property;
    private final Field field;

    public DocumentField(DocumentProperty property, Field field) {
        this.property = property;
        this.field = field;
    }
    public String getName(){
        return property.value();
    }
    public Class<?> getType() {
        return field.getType();
    }
    public Object getValue(Document document) throws IllegalAccessException {
        field.setAccessible(true);
        Object value = field.get(document);
        field.setAccessible(false);
        return value;
    }

    void setValue(Document document, Object value) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(document, value);
        field.setAccessible(false);
    }

}
