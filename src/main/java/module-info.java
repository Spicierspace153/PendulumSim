module org.example.pendulumsim {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens org.example.pendulumsim to javafx.fxml;
    exports org.example.pendulumsim;
}