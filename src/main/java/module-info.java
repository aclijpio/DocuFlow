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

    opens com.github.aclijpio.docuflow to
            javafx.fxml,
            com.fasterxml.jackson.databind;

    exports com.github.aclijpio.docuflow;
}