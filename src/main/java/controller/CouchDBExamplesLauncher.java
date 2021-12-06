package controller;

import com.google.gson.JsonObject;
import javacouchdb.CouchDBaccess;
import javacouchdb.VerbruikerCouchDBDAO;
import model.Verbruiker;

import java.util.List;

public class CouchDBExamplesLauncher {

    private CouchDBaccess db;
    private VerbruikerCouchDBDAO cdbf;

    public CouchDBExamplesLauncher() {
        super();
        db = new CouchDBaccess();
        cdbf = new VerbruikerCouchDBDAO(db);
    }

    public static void main(String[] args) {
        CouchDBExamplesLauncher myself = new CouchDBExamplesLauncher();
        myself.run();
    }

    public void run() {
//		Maak een connectie met CouchDB, gebruik het CouchDbaccess object.
        try {
            db.setupConnection();
            System.out.println("Connection open");
        } catch (Exception e) {
            System.out.println("\nEr is iets fout gegaan\n");
            e.printStackTrace();
        }

        //		Hierna volgen stappen die gegevens ophalen uit de CouchDb database. Maak eerst de CouchDb opdrachten.

//		Uitleg methodes in CouchDB DAO, gebruik gson library.
//        Verbruiker verbruikerId = cdbf.getVerbruikerByDocId("08ecd4cf7e4d4c32af182c28f9d07858");
//        System.out.println(verbruikerId);
//
        Verbruiker verbruikerPcNr = cdbf.getVerbruiker("1053CA" , 176);
        System.out.println(verbruikerPcNr);
//
        System.out.println();
        System.out.println("----------  Alle documenten ------------");
        List<JsonObject> allDocs = db.getClient().view("_all_docs").includeDocs(true).query(JsonObject.class);
        for (JsonObject d : allDocs) {
            System.out.println(d.getAsJsonObject());
        }
        System.out.println("------------------------------------------");

//		Gebruik van views, hoe haal je met ligthcouch library gegevens uit een view in CouchDB
        System.out.println("----------  Document uit View ------------");
		List<JsonObject> test = db.getClient().view("opdrachten/postcode-huisnr").key("1050BB")
                .query(JsonObject.class);
		for (JsonObject i :test) {
            System.out.println(i);
        }
        System.out.println("----------------------------------------");

        //Voorbeeld met reduce functie
        System.out.println("----------  View met reduce ------------");
        List<JsonObject> reducer = db.getClient().view("opdrachten/stroom-per-soort").reduce(true).groupLevel(1).query(JsonObject.class);

		for (JsonObject js : reducer) {
			System.out.println(js);
		}
        System.out.println("----------------------------------------");
    }
}


