package com.github.aclijpio.docuflow.entities;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.github.aclijpio.docuflow.config.source.CurrencyCode;
import com.github.aclijpio.docuflow.services.PropertyType;
import com.github.aclijpio.docuflow.services.process.annotations.DocumentForm;
import com.github.aclijpio.docuflow.services.process.annotations.DocumentProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "payment_requests")
@JsonTypeName("paymentRequest")
@DocumentForm("Заявка на оплату")
public class PaymentRequest extends Document {

    @Enumerated(EnumType.STRING)
    @DocumentProperty(type = PropertyType.ENUM, value = "Валюта")
    private CurrencyCode currencyType;
    @DocumentProperty(type = PropertyType.DOUBLE, value = "Курс валюты")
    private Double exchangeCurrency;
    @DocumentProperty(type = PropertyType.DOUBLE, value = "Комиссия")
    private Double commission;

    public PaymentRequest(Long id, String number, LocalDate date, String user, Double amountOfMoney, CurrencyCode currencyType, Double exchangeCurrency, double commission) {
        super(id, number, date, user, amountOfMoney);
        this.currencyType = currencyType;
        this.exchangeCurrency = exchangeCurrency;
        this.commission = commission;
    }

    public PaymentRequest(String number, LocalDate date, String user, double amountOfMoney, CurrencyCode currencyType, Double exchangeCurrency, double commission) {
        super(number, date, user, amountOfMoney);
        this.currencyType = currencyType;
        this.exchangeCurrency = exchangeCurrency;
        this.commission = commission;
    }

    public PaymentRequest(CurrencyCode currencyType, Double exchangeCurrency, double commission) {
        this.currencyType = currencyType;
        this.exchangeCurrency = exchangeCurrency;
        this.commission = commission;
    }

    public PaymentRequest() {
    }
}
