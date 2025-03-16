package com.github.aclijpio.docuflow.services.process;


import com.github.aclijpio.docuflow.entities.Document;
import com.github.aclijpio.docuflow.services.process.annotations.DocumentForm;
import com.github.aclijpio.docuflow.services.process.annotations.DocumentProperty;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class DocumentProcessor {

    /**
     * Создается обертка над объектом документа с указанными метаданными в аннотациях класса.
     * @param document реализация документа
     * @return обертку над документом с указанными метаданными
     */

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

    /**
     * Создается обертка над классом документа с указанными метаданными в аннотациях класса.
     * Класс должен иметь public & пустой конструктор.
     * @param clazz объект класса
     * @return  обертку над классом документа с указанными метаданными
     */
    public static DocumentForward forwardProcess(Class<? extends Document> clazz) {
        try {
            if (clazz.isAnnotationPresent(DocumentForm.class)) {
                Constructor<?> constructor = clazz.getDeclaredConstructor();
                return forwardProcess(clazz.cast(constructor.newInstance()));
            } else
                throw new IllegalArgumentException("Class " + clazz.getName() + " must be annotated with @DocumentForm");
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new IllegalStateException("Class " + clazz.getSimpleName() + " must have a public no-args constructor.");
        } catch (InvocationTargetException | InstantiationException e) {
            throw new RuntimeException(e);
        }
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
