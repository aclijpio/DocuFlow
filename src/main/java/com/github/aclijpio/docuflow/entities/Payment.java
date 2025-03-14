package com.github.aclijpio.docuflow.entities;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.github.aclijpio.docuflow.services.process.annotations.DocumentForm;
import com.github.aclijpio.docuflow.services.process.annotations.DocumentProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "payments")
@JsonTypeName("payment")
@DocumentForm("Платёжка")
public class Payment extends Document {
    @DocumentProperty("Сотрудник")
    private String employee;

    public Payment(String number, LocalDate date, String user, double amountOfMoney, String employee) {
        super(number, date, user, amountOfMoney);
        this.employee = employee;
    }

    public Payment(Long id, String number, LocalDate date, String user, double amountOfMoney, String employee) {
        super(id, number, date, user, amountOfMoney);
        this.employee = employee;
    }

    public Payment() {
    }
/*
    @Override
    public NodeRegistry toNodeTree(ParentDocumentHelper helper) {
        NodeRegistry nodeRegistry = super.toNodeTree(helper);
        nodeRegistry.add(
                helper.createStringComboBox(
                        Employee.class,
                        new TextField(this.getEmployee() == null ? "" : this.getEmployee().getName()),
                        "Сотрудник: "
                )
        );
        return nodeRegistry;
    }

    @Override
    public Document fromNodeTree(ParentDocumentHelper helper, NodeRegistry nodeRegistry) {
        super.fromNodeTree(helper, nodeRegistry);

        String employeeName = nodeRegistry.getNode(TextField.class).getText();
        Optional<Employee> employeeOptional = helper.getHelper().getDatabaseManager().findByName(Employee.class, employeeName);
        if (employeeOptional.isEmpty()){
            this.employee = new Employee(employeeName);
            helper.getHelper().getDatabaseManager().save(this.employee);
        } else {
            this.employee = employeeOptional.get();
        }
        return this;
    }*/
}
