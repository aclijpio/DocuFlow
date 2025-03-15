package com.github.aclijpio.docuflow.services;

import com.github.aclijpio.docuflow.entities.Document;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.List;

public interface FinancialMenuService {

    List<Button> createDocumentActionButtons(URL resourcePath, Document ... documents);

}
