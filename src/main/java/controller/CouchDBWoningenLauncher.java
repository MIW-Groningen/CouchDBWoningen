package controller;

import com.google.gson.Gson;
import javacouchdb.VerbruikerCouchDBDAO;
import javacouchdb.CouchDBaccess;
import model.Verbruiker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CouchDBWoningenLauncher {

	private CouchDBaccess db;
	private VerbruikerCouchDBDAO cdbf;
	
	public CouchDBWoningenLauncher() {
		super();
		db = new CouchDBaccess();
		cdbf = new VerbruikerCouchDBDAO(db);
	}
	
	public static void main(String[] args) {
		CouchDBWoningenLauncher myself = new CouchDBWoningenLauncher();
		myself.run();		
	}
		
	public void run() {
//		Maak een connectie met CouchDB, gebruik het CouchDbaccess object.
		try {
			db.setupConnection();
			System.out.println("Connection open");
		} 
		catch (Exception e) {
			System.out.println("\nEr is iets fout gegaan\n");
			e.printStackTrace();
		}

//		Lees de Json-string in het tekstbestand, maak er Verbruiker objecten van en sla die op in CouchDB
//		Sla alle Verbruikers op in de CouchDb database.
		Gson gson = new Gson();
		List<Verbruiker> verbruikerList = new ArrayList<>();
		try (Scanner bestandLezer = new Scanner(new File("src/main/resources/woningenJson.txt"))) {
			while (bestandLezer.hasNext()) {
				verbruikerList.add(gson.fromJson(bestandLezer.nextLine(), Verbruiker.class));
			}
		} catch (FileNotFoundException schrijfFout) {
			System.out.println("Het bestand lezen is mislukt.");
		}

		for (Verbruiker v : verbruikerList) {
			cdbf.saveSingleVerbruiker(v);
		}
	}
}
