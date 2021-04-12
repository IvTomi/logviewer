module org.logmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens org.logmanager to javafx.fxml;
    opens org.logmanager.model to javafx.base;
    exports org.logmanager;
}