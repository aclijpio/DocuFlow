module com.github.aclijpio.docuflow {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.github.aclijpio.docuflow to javafx.fxml;
    exports com.github.aclijpio.docuflow;
}