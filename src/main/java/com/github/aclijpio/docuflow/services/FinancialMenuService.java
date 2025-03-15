package com.github.aclijpio.docuflow.services;

import com.github.aclijpio.docuflow.entities.Document;
import javafx.scene.control.Button;

import java.util.List;

public interface FinancialMenuService {

    List<Button> createDocumentActionButtons(Document ... documents);

}
