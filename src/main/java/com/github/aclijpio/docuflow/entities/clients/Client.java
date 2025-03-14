package com.github.aclijpio.docuflow.entities.clients;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public interface Client {

    String getName();

}
