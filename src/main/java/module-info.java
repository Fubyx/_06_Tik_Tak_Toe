module com.example._06_tik_tak_toe {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;


    opens com.example._06_tik_tak_toe to javafx.fxml;
    exports com.example._06_tik_tak_toe;
}