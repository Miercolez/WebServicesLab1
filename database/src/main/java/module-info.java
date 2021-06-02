module database {
    exports functions;
    exports entity;
    opens entity;
    requires java.sql;
    requires mysql.connector.java;
    requires eclipselink;
    requires com.google.gson;
    requires jakarta.persistence;
}