module TwojaOpinia {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    opens twojaOpinia.controller to javafx.fxml;
    exports twojaOpinia;
    opens twojaOpinia.controller.admin to javafx.fxml;
    opens twojaOpinia.controller.user to javafx.fxml;
    opens twojaOpinia.model to javafx.base;
    exports twojaOpinia.controller;

}