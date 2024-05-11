    module bntu.accounting.application {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires org.apache.commons.io;
    requires java.naming;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;


    opens bntu.accounting.application.controllers to javafx.fxml;
    opens bntu.accounting.application to com.fasterxml.jackson.core;
    opens  bntu.accounting.application.models to org.hibernate.orm.core, javafx.base,
            hibernate.entitymanager,jakarta.persistence;
    opens bntu.accounting.application.controllers.pages to javafx.fxml;
    opens bntu.accounting.application.controllers.windows to javafx.fxml;
    opens bntu.accounting.application.controllers.templates to javafx.fxml;
    opens bntu.accounting.application.util to hibernate.entitymanager, jakarta.persistence, javafx.base;
    exports bntu.accounting.application;
    exports bntu.accounting.application.models to com.fasterxml.jackson.databind;
    exports  bntu.accounting.application.iojson to com.fasterxml.jackson.databind;
    opens bntu.accounting.application.iojson to hibernate.entitymanager, jakarta.persistence, javafx.base;
    opens bntu.accounting.application.util.db to hibernate.entitymanager, jakarta.persistence, javafx.base;
    opens bntu.accounting.application.util.enums to hibernate.entitymanager, jakarta.persistence, javafx.base;
    opens bntu.accounting.application.util.normalization to hibernate.entitymanager, jakarta.persistence, javafx.base;
    opens bntu.accounting.application.util.fxsupport to hibernate.entitymanager, jakarta.persistence, javafx.base;
    opens bntu.accounting.application.util.db.entityloaders to hibernate.entitymanager, jakarta.persistence, javafx.base;
    opens bntu.accounting.application.controllers.alerts to javafx.fxml;
    exports bntu.accounting.application.services;
    exports bntu.accounting.application.doc;
    opens bntu.accounting.application.doc to com.fasterxml.jackson.core;
    exports bntu.accounting.application.doc.strategies;
    opens bntu.accounting.application.doc.strategies to com.fasterxml.jackson.core;
    exports bntu.accounting.application.doc.obj;
    opens bntu.accounting.application.doc.obj to com.fasterxml.jackson.core;
    exports bntu.accounting.application.excel;
    opens bntu.accounting.application.excel to com.fasterxml.jackson.core;

}