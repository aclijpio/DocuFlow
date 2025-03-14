module com.github.aclijpio.docuflow {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    requires static lombok;

    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.dataformat.yaml;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires jakarta.cdi;
    requires org.postgresql.jdbc;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    opens com.github.aclijpio.docuflow to javafx.fxml;
    exports com.github.aclijpio.docuflow;
    exports com.github.aclijpio.docuflow.entities;

    opens com.github.aclijpio.docuflow.controllers to javafx.fxml, com.fasterxml.jackson.databind, com.fasterxml.jackson.datatype.jsr310;
    exports com.github.aclijpio.docuflow.config.source;

    opens com.github.aclijpio.docuflow.entities.money to org.hibernate.orm.core;
    opens com.github.aclijpio.docuflow.config.source to com.fasterxml.jackson.databind;
    opens com.github.aclijpio.docuflow.entities.clients to org.hibernate.orm.core;
    opens com.github.aclijpio.docuflow.entities to org.hibernate.orm.core;

}