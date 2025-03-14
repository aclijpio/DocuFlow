package com.github.aclijpio.docuflow.services.process;


import com.github.aclijpio.docuflow.entities.Document;
import com.github.aclijpio.docuflow.services.process.annotations.DocumentProperty;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Getter
public class DocumentForward {

    private final Document document;
    @Setter
    private  String documentName;
    private final List<DocumentField> fields = new ArrayList<>();
    public DocumentForward(Document document) {
        this.document = document;
    }
    public void addField(DocumentProperty property, Field value) {
        fields.add(new DocumentField(property, value));
    }

    public void setValueByIndex(int index, Object value) throws IllegalAccessException {
        fields.get(index).setValue(document, value);
    }
    public Object getValueByIndex(int index) throws IllegalAccessException {
        return fields.get(index).getValue(document);
    }

}
