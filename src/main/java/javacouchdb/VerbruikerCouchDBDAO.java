package javacouchdb;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Verbruik;
import model.Verbruiker;

import java.util.ArrayList;
import java.util.List;

public class VerbruikerCouchDBDAO {
	
	private CouchDBaccess db;
	private Gson gson;
	
	public VerbruikerCouchDBDAO(CouchDBaccess db) {
		super();
		this.db = db;
		gson = new Gson();
	}
	
	public String saveSingleVerbruiker(Verbruiker verbruiker) {
		String jsonString = gson.toJson(verbruiker);
		System.out.println(jsonString);
		JsonParser parser = new JsonParser();
		JsonObject jsonobject = parser.parse(jsonString).getAsJsonObject();
		return db.saveDocument(jsonobject);
	}

	public Verbruiker getVerbruikerByDocId(String doc_Id) {
		JsonObject json = db.getClient().find(JsonObject.class, doc_Id);
		return gson.fromJson(json, Verbruiker.class);
	}

	public Verbruiker getVerbruiker(String postcode, int huisnr) {
		Verbruiker resultaat = null;
		List<JsonObject> alleVerbruikers = db.getClient().view("_all_docs").includeDocs(true).query(JsonObject.class);
		for (JsonObject json : alleVerbruikers) {
			resultaat = gson.fromJson(json, Verbruiker.class);
			if (resultaat.getPostcode().equals(postcode)  && (resultaat.getHuisnummer() == huisnr)) {
				return resultaat;
			}
		}
		return resultaat;
	}

}
