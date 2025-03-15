package com.github.aclijpio.docuflow.entities;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.github.aclijpio.docuflow.services.process.annotations.DocumentForm;
import com.github.aclijpio.docuflow.services.process.annotations.DocumentProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@JsonTypeName("payment")
@DocumentForm("Платёжка")
public class Payment extends Document {
    @DocumentProperty("Сотрудник")
    private String employee;

    public Payment(String number, LocalDate date, String user, double amountOfMoney, String employee) {
        super(number, date, user, amountOfMoney);
        this.employee = employee;
    }

    public Payment() {
    }
}
