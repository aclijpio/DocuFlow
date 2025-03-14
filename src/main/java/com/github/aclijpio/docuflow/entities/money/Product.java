package com.github.aclijpio.docuflow.entities.money;

import com.github.aclijpio.docuflow.entities.clients.Client;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "products")
public class Product implements Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double quantity;

    public Product(String name, double quantity) {
        this.name = name;
        this.quantity = quantity;
    }
}
