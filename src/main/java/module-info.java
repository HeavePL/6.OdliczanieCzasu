module com.example.odliczanieczasu {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.odliczanieczasu to javafx.fxml;
    exports com.example.odliczanieczasu;
}