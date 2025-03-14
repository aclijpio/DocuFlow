package com.github.aclijpio.docuflow.entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.aclijpio.docuflow.services.process.annotations.DocumentId;
import com.github.aclijpio.docuflow.services.process.annotations.DocumentProperty;
import com.github.aclijpio.docuflow.services.process.annotations.PropertyType;
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

/*    @Override
    public NodeRegistry toNodeTree(ParentDocumentHelper helper) {

        NodeRegistry nodeRegistry = new NodeRegistry();

        return helper.createNodeRegistryBuilder(nodeRegistry,
                documentNode -> documentNode
                        .createNumberLabel(this.number)
                        .createDateLabel(this.date)
                        .createUserLabel(this.user)
                        .createAmountLabel(this.amountOfMoney)
        );
    }


    @Override
    public Document fromNodeTree(ParentDocumentHelper helper, NodeRegistry nodeRegistry) {

        TextField textField = nodeRegistry.getNode(TextField.class);
        this.number = textField.getText();
        this.date = nodeRegistry.getNode(DatePicker.class).getValue();
        String username = nodeRegistry.getNode(TextField.class).getText();
        Optional<User> user = helper.getHelper().getDatabaseManager().findByName(User.class, username);

        if (user.isEmpty()){
            this.user = new User(username);
            helper.getHelper().getDatabaseManager().save(this.user);
        } else {
            this.user = user.get();
        }
        this.amountOfMoney = Double.parseDouble(nodeRegistry.getNode(TextField.class).getText());
        return this;
    }*/



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
