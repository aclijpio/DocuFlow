package com.github.aclijpio.docuflow.entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.aclijpio.docuflow.services.PropertyType;
import com.github.aclijpio.docuflow.services.process.annotations.DocumentId;
import com.github.aclijpio.docuflow.services.process.annotations.DocumentProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Invoice.class, name = "invoice"),
        @JsonSubTypes.Type(value = Payment.class, name = "payment"),
        @JsonSubTypes.Type(value = PaymentRequest.class, name = "paymentRequest")
})

public abstract class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @DocumentId
    private Long id;

    @DocumentProperty("Номер")
    @Column(unique = true, nullable = false, updatable = false)
    private String number;
    @DocumentProperty(type = PropertyType.DATE, value = "Дата")
    private LocalDate date;
    @DocumentProperty("Пользователь")
    private String user;

    @Column(nullable = false)
    @DocumentProperty("Сумма")
    private Double amountOfMoney;

    public Document(String number, LocalDate date, String user, double amountOfMoney) {
        this.number = number;
        this.date = date;
        this.user = user;
        this.amountOfMoney = amountOfMoney;
    }

    public Document() {
    }


    @Override
    public String toString() {
        String name = switch (this.getClass().getSimpleName()){
            case "Invoice" -> "Накладная";
            case "Payment" -> "Платёжка";
            case "PaymentRequest" -> "Заявка на оплату";

            default -> throw new IllegalStateException("Unexpected value: " + this.getClass().getSimpleName());
        };

        return String.format(
                "%s от %s номер %s",
                name, date, number
        );
    }
}
