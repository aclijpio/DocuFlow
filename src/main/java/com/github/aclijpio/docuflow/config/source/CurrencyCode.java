package com.github.aclijpio.docuflow.config.source;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum CurrencyCode {
    USD,
    EUR,
    JPY,
    GBP,
    AUD,
    RUB;

    public static ObservableList<CurrencyCode> getCurrencies(){
        return FXCollections.observableArrayList(
                USD,
                EUR,
                JPY,
                GBP,
                AUD,
                RUB
        );
    }
}
