package com.github.aclijpio.docuflow.services;

import com.github.aclijpio.docuflow.entities.Document;
import com.github.aclijpio.docuflow.entities.DocumentItem;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.List;

public interface FinancialMenuService {

    List<Button> createDocumentActionButtons(URL resourcePath, Document ... documents);
    void saveToJsonFile(List<Document> documentList);
    List<Document> loadDocumentsFromJsonFile();
    void offerSimilar(List<DocumentItem> items);


}
