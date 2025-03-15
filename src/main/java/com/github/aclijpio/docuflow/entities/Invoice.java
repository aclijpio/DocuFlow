package com.github.aclijpio.docuflow.entities;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.github.aclijpio.docuflow.services.PropertyType;
import com.github.aclijpio.docuflow.services.process.annotations.DocumentForm;
import com.github.aclijpio.docuflow.services.process.annotations.DocumentProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@JsonTypeName("invoice")
@DocumentForm("Накладная")
public class Invoice extends Document {
    @Enumerated(EnumType.STRING)
    @DocumentProperty(type = PropertyType.ENUM, value = "Валюта")
    private CurrencyCode currencyType;
    @DocumentProperty(type = PropertyType.DOUBLE, value = "Курс валюты")
    private Double exchangeCurrency;
    @DocumentProperty("Продукт")
    private String product;


    public Invoice(String number, LocalDate date, String user, double amountOfMoney, CurrencyCode currencyType, Double exchangeCurrency, String product) {
        super(number, date, user, amountOfMoney);
        this.currencyType = currencyType;
        this.exchangeCurrency = exchangeCurrency;
        this.product = product;
    }

    public Invoice(CurrencyCode currencyType, Double exchangeCurrency, String product) {
        this.currencyType = currencyType;
        this.exchangeCurrency = exchangeCurrency;
        this.product = product;
    }

    public Invoice() {
    }
}
