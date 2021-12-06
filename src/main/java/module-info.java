module CouchDBWoningen {
    requires com.google.gson;
    requires lightcouch;
    requires java.sql;

    opens javacouchdb to com.google.gson, lightcouch, java.sql;
    opens model to com.google.gson;
}