package com.github.aclijpio.docuflow.services.process;

import com.github.aclijpio.docuflow.entities.Document;
import com.github.aclijpio.docuflow.services.PropertyType;
import com.github.aclijpio.docuflow.services.process.annotations.DocumentProperty;

import java.lang.reflect.Field;

public class DocumentField {

    DocumentProperty property;
    Field field;

    public DocumentField(DocumentProperty property, Field field) {
        this.property = property;
        this.field = field;
    }
    public String getName(){
        return property.value();
    }
    public PropertyType getType() {
        return property.type();
    }
    public Object getValue(Document document) throws IllegalAccessException {
        field.setAccessible(true);
        Object value = field.get(document);
        field.setAccessible(false);
        return value;
    }
    protected void setValue(Document document, Object value) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(document, value);
        field.setAccessible(false);
    }

}
