package com.github.aclijpio.docuflow.entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.aclijpio.docuflow.services.PropertyType;
import com.github.aclijpio.docuflow.services.process.annotations.DocumentProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Invoice.class, name = "invoice"),
        @JsonSubTypes.Type(value = Payment.class, name = "payment"),
        @JsonSubTypes.Type(value = PaymentRequest.class, name = "paymentRequest")
})

public abstract class Document {

    @DocumentProperty("Номер")
    private String number;
    @DocumentProperty(type = PropertyType.DATE, value = "Дата")
    private LocalDate date;
    @DocumentProperty("Пользователь")
    private String user;

    @DocumentProperty(type = PropertyType.DOUBLE, value = "Сумма")
    private Double amountOfMoney;

    public Document(String number, LocalDate date, String user, double amountOfMoney) {
        this.number = number;
        this.date = date;
        this.user = user;
        this.amountOfMoney = amountOfMoney;
    }

    public Document() {
    }

}
