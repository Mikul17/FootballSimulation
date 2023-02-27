module com.mikul17.footballsimulatiojn {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.mikul17.footballsimulation to javafx.fxml;
    exports com.mikul17.footballsimulation;
}