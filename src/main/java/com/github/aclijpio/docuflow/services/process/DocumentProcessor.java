package com.github.aclijpio.docuflow.services.process;


import com.github.aclijpio.docuflow.entities.Document;
import com.github.aclijpio.docuflow.services.process.annotations.DocumentForm;
import com.github.aclijpio.docuflow.services.process.annotations.DocumentProperty;

import java.lang.reflect.Field;

public class DocumentProcessor {

    public static DocumentForward forwardProcess(Document document) {
        Class<?> clazz = document.getClass();
        DocumentForward forward = new DocumentForward(document);
        DocumentForm documentForm = clazz.getAnnotation(DocumentForm.class);
        if (documentForm != null) {
            forward.setDocumentName(documentForm.value());

            processFields(clazz, forward);
        }
        return forward;
    }
    private static void processFields(Class<?> clazz, DocumentForward forward) {
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null && superClass != Object.class) {
            processFields(superClass, forward);
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(DocumentProperty.class)) {
                DocumentProperty annotation = field.getAnnotation(DocumentProperty.class);
                forward.addField(annotation, field);
            }
        }
    }
}
