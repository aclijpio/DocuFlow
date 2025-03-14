package com.github.aclijpio.docuflow.entities;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.github.aclijpio.docuflow.entities.money.Currency;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@JsonTypeName("invoice")
public class Invoice extends Document {

    @ManyToOne()
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;
    private String product;

    public Invoice(String number, LocalDate date, String user, double amountOfMoney, Currency currency, String product) {
        super(number, date, user, amountOfMoney);
        this.currency = currency;
        this.product = product;
    }

    public Invoice(Long id, String number, LocalDate date, String user, double amountOfMoney, Currency currency, String product) {
        super(id, number, date, user, amountOfMoney);
        this.currency = currency;
        this.product = product;
    }

    public Invoice() {
    }
/*
    @Override
    public NodeRegistry toNodeTree(ParentDocumentHelper helper) {


        NodeRegistry nodeRegistry = super.toNodeTree(helper);

        return helper.createNodeRegistryBuilder(nodeRegistry
                , documentNode -> documentNode
                        .createCurrencyLabel(this.currency)
                        .createProductLabel(this.product)
        );
    }

    @Override
    public Document fromNodeTree(ParentDocumentHelper helper, NodeRegistry nodeRegistry) {


        super.fromNodeTree(helper, nodeRegistry);

        CurrencyCode stringCurrency = (CurrencyCode) nodeRegistry.getNode(ComboBox.class).getSelectionModel().getSelectedItem();

        Optional<Currency> currency = helper.getHelper().getDatabaseManager().findCurrencyByCurrencyCode(stringCurrency);
        if (currency.isEmpty())
            throw new NodeUnavailableException("Currency not found");
        this.currency = currency.get();
        nodeRegistry.skip();
        String productName =  nodeRegistry.getNode(TextField.class).getText();
        Optional<Product> product = helper.getHelper().getDatabaseManager().findByName(Product.class, productName);
        double quantity = Double.parseDouble(nodeRegistry.getNode(TextField.class).getText());
        if (product.isEmpty()) {
            this.product = new Product(productName, quantity);
            helper.getHelper().getDatabaseManager().save(this.product);
        }
        else {
            this.product = product.get();
        }

        return this;*/

}
