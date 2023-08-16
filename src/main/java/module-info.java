module com.vicoub.triovisiongame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.vicoub.triovisiongame to javafx.fxml;
    exports com.vicoub.triovisiongame;
}