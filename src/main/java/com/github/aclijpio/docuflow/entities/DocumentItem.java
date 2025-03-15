package com.github.aclijpio.docuflow.entities;

import com.github.aclijpio.docuflow.services.process.DocumentForward;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class DocumentItem{

    private final DocumentForward forward;
    private final BooleanProperty property = new SimpleBooleanProperty(false);
    public DocumentItem(DocumentForward forward) {
        this.forward = forward;
    }
    public boolean isSelected() {
        return property.get();
    }
    public void unselect(){
        property.setValue(false);
    }
    public void select(){
        property.setValue(true);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentItem that = (DocumentItem) o;
        return Objects.equals(forward, that.forward) && Objects.equals(property, that.property);
    }

    @Override
    public int hashCode() {
        return Objects.hash(forward, property);
    }
}