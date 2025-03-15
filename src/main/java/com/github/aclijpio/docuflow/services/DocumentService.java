package com.github.aclijpio.docuflow.services;

import com.github.aclijpio.docuflow.entities.Document;
import javafx.scene.Node;

import java.util.List;

public interface DocumentService {
    List<Node> createFields();
    Document formToDocument(Node node) throws IllegalAccessException;
}
