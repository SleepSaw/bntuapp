module bntu.accounting.application {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires org.apache.commons.io;
    requires java.naming;


    opens bntu.accounting.application.controllers to javafx.fxml;
    opens  bntu.accounting.application.models to org.hibernate.orm.core, javafx.base,
            hibernate.entitymanager,jakarta.persistence;
    opens bntu.accounting.application.controllers.pages to javafx.fxml;
    opens bntu.accounting.application.controllers.windows to javafx.fxml;
    opens bntu.accounting.application.controllers.templates to javafx.fxml;
    opens bntu.accounting.application.util to hibernate.entitymanager, jakarta.persistence, javafx.base;
    exports bntu.accounting.application;
    opens bntu.accounting.application.iojson to hibernate.entitymanager, jakarta.persistence, javafx.base;
    opens bntu.accounting.application.util.db to hibernate.entitymanager, jakarta.persistence, javafx.base;
    opens bntu.accounting.application.util.enums to hibernate.entitymanager, jakarta.persistence, javafx.base;
    opens bntu.accounting.application.util.normalization to hibernate.entitymanager, jakarta.persistence, javafx.base;
    opens bntu.accounting.application.util.fxsupport to hibernate.entitymanager, jakarta.persistence, javafx.base;
    opens bntu.accounting.application.util.db.entityloaders to hibernate.entitymanager, jakarta.persistence, javafx.base;
    opens bntu.accounting.application.controllers.alerts to javafx.fxml;

}