module com.dastrix.testjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;


    opens com.dastrix.testjavafx to javafx.fxml;
    exports com.dastrix.testjavafx;
}