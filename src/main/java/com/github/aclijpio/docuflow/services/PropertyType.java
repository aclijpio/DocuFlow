package com.github.aclijpio.docuflow.services;

import com.github.aclijpio.docuflow.services.process.DocumentField;
import javafx.scene.Node;

public enum PropertyType {


    TEXT_FIELD(DocumentService.FieldCreator::createTextField),
    DATE(DocumentService.FieldCreator::createDateField),
    ENUM(DocumentService.FieldCreator::createTextField),
    DOUBLE(DocumentService.FieldCreator::createDoubleField),
    ID(DocumentService.FieldCreator::createIntegerField);


    private final FieldCreatorFunction creator;

    PropertyType(FieldCreatorFunction creator) {
        this.creator = creator;
    }
    public Node createField(DocumentField field) {
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
}
