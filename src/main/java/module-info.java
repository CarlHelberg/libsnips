module greadings.com.greadings {
    requires javafx.controls;
    requires javafx.fxml;


    opens greadings.com.greadings to javafx.fxml;
    exports greadings.com.greadings;
}