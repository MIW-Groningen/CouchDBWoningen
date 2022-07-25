module CouchDBWoningen {
    requires lightcouch;
    requires java.sql;
    requires gson;

    opens javacouchdb to lightcouch, java.sql;
    opens model to gson;
}