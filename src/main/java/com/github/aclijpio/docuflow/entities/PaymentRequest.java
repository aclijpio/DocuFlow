package com.github.aclijpio.docuflow.entities;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.github.aclijpio.docuflow.entities.money.Currency;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class PaymentRequest extends Document {

    private String counterparty;

    @ManyToOne()
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    private double commission;

    public PaymentRequest(String number, LocalDate date, String user, double amountOfMoney, String counterparty, Currency currency, double commission) {
        super(number, date, user, amountOfMoney);
        this.counterparty = counterparty;
        this.currency = currency;
        this.commission = commission;
    }

    public PaymentRequest(Long id, String number, LocalDate date, String user, double amountOfMoney, String counterparty, Currency currency, double commission) {
        super(id, number, date, user, amountOfMoney);
        this.counterparty = counterparty;
        this.currency = currency;
        this.commission = commission;
    }

    public PaymentRequest() {
    }
/*
    @Override
    public NodeRegistry toNodeTree(ParentDocumentHelper helper) {
        NodeRegistry nodeRegistry =  super.toNodeTree(helper);


        this.commission = helper.getHelper().getConfig().getCommission();

        nodeRegistry.add(
                helper.createStringComboBox(
                        Employee.class,
                        new TextField(this.getCounterparty() == null ? "" : this.getCounterparty().getName()),
                        "Контрагент: "
                )
        );
        if (this.getCurrency() == null) {
            Optional<Currency> currentCurrency = helper.getDefaultCurrencyByCurrencyCode();

            this.setCurrency(currentCurrency.orElseThrow(CurrencyDefaultNotSetException::new));
        }
        TextField rateTextField = FinancialControlsFactory.createUneditableTextField(String.valueOf(this.getCurrency().getRate()));


        nodeRegistry.createHBoxTrees(
                helper.findCurrencyCodeComboBox(
                        this.getCurrency().getCurrencyCode(),
                        rateTextField),
                labelTree -> labelTree
                        .createLabelWithBox("Валюта: ")
        );
        nodeRegistry.createHBoxTrees(
                rateTextField,
                labelTree -> labelTree
                        .createLabelWithBox("Курс валюты: ")
        );
        nodeRegistry.createHBoxTrees(
                FinancialControlsFactory.createUneditableTextField(String.valueOf(commission)),
                labelTree -> labelTree
                        .createLabelWithBox("Комиссия: ")
        );

        return nodeRegistry;
    }

    @Override
    public Document fromNodeTree(ParentDocumentHelper helper, NodeRegistry nodeRegistry) {

        nodeRegistry.clear();

        super.fromNodeTree(helper, nodeRegistry);

        String employeeName = nodeRegistry.getNode(TextField.class).getText();
        Optional<Counterparty> employeeOptional = helper.getHelper().getDatabaseManager().findByName(Counterparty.class, employeeName);
        if (employeeOptional.isEmpty()){
            this.counterparty = new Counterparty(employeeName);
            helper.getHelper().getDatabaseManager().save(this.counterparty);
        } else {
            this.counterparty = employeeOptional.get();
        }


        CurrencyCode stringCurrency = (CurrencyCode) nodeRegistry.getNode(ComboBox.class).getSelectionModel().getSelectedItem();

        Optional<Currency> currency = helper.getHelper().getDatabaseManager().findCurrencyByCurrencyCode(stringCurrency);
        if (currency.isEmpty())
            throw new NodeUnavailableException("Currency not found");
        this.currency = currency.get();


        return this;
    }*/
}
