package com.github.aclijpio.docuflow.services;

import com.github.aclijpio.docuflow.services.exceptions.InvalidInputException;
import com.github.aclijpio.docuflow.services.process.DocumentForward;
import javafx.scene.Node;

import java.util.List;

public interface DocumentService {
    List<Node> createFields();
    DocumentForward formToDocument(Node node) throws IllegalAccessException, InvalidInputException;
}
