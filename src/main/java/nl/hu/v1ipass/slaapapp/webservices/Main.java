package nl.hu.v1ipass.slaapapp.webservices;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.annotation.Resources;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import nl.hu.v1ipass.slaapapp.DAO.GebruikerDAO;
import nl.hu.v1ipass.slaapapp.DAO.GegevensDAO;
import nl.hu.v1ipass.slaapapp.entity.Gebruiker;
import nl.hu.v1ipass.slaapapp.entity.Gegevens;

@Path("/") //geen speciaal pad verder toegevoegd
public class Main {
	/* --- POST FUNCTIES : REGISTRATIE, INLOG EN TOEVOEGEN --- */
	@POST
	@Path("/register")
	//haal alle gegevens op
	public String registreer(@FormParam("username") String username, @FormParam("wachtwoord") String wachtwoord,
			@FormParam("voornaam") String voornaam, @FormParam("achternaam") String achternaam) {
		//maak nieuwe gebruiker met deze gegevens
		Gebruiker gebruiker = new Gebruiker(username, wachtwoord, voornaam, achternaam);
		//maak een GEBRUIKERdao zodat je functies hieruit kan gebruiken
		GebruikerDAO dao = new GebruikerDAO();
		//check of de username niet al bestaat
		if (dao.checkUsername(username)) {
			dao.maakGebruiker(gebruiker); //maak gebruiker
			return "success";
		}

		return "error";
	}

	@POST
	@Path("/login")
	//haal username en wachtwoord op
	public String login(@FormParam("username") String username, @FormParam("wachtwoord") String wachtwoord) {
		GebruikerDAO dao = new GebruikerDAO(); //roep dao aan zodat hij gebruikt kan worden
		Gebruiker g = dao.vindUsername(username); //gebruik de vindUsername functie om te checken of dat username bestaat
		
		if (g.getWachtwoord().equals(wachtwoord)) { //check of het wachtwoord van de username gelijk is aan het wachtwoord in de database
				return "success"; //is gelijk aan wachtwoord
		}

		return "Ongeldige wachtwoord!"; //is niet gelijk aan wachtwoord
	}

	@POST
	@Path("/voegtoe")
	//haal alle gegevens op: username, begintijd, eindtijd en datum
	public String voegTijdenToe(@FormParam("username") String username, @FormParam("h_datum") String datum,
			@FormParam("b_tijd") String begintijd, @FormParam("e_tijd") String eindtijd) {
		//maak een nieuwe klasse gegevens aan met deze waardes
		Gegevens gegevens = new Gegevens(username, datum, begintijd, eindtijd);

		//beide dao's zijn nodig dus die worden allebei aangeroepen!
		GegevensDAO gdao = new GegevensDAO();
		GebruikerDAO dao = new GebruikerDAO();
		
		//gebruiker dao wordt gebruikt om te kijken of de username uberhaupt wel bestaat
		Gebruiker g = dao.vindUsername(username);

		//vervolgens wordt vergeleken of de username overeenkomt
		if (g.getUsername().equals(username)) {
			gdao.voegGegevensToe(gegevens);
			return "success"; //username komt overeen dus worden de gegevens toegevoegd
		}

		return "error"; //er bestaat geen gebruiker met deze username
	}

	/* --- GET FUNCTIES : VEEL OP VEEL, EEN OP VEEL --- */
	
	@GET
	@Path("/alles") //veel op veel relatie
	@Produces("application/json")
	public String getGebruikers() {
		JsonArrayBuilder jab = Json.createArrayBuilder(); //array builder om een lijst aan te kunnen maken
		GegevensDAO gdao = new GegevensDAO(); //haal dao op zodat deze gebruikt kan worden
		List<Gegevens> gegevens = gdao.geefAlleGegevens(); //maak een lijst en zet hier alle gegevens in

		for (Gegevens g : gegevens) { //voor iedere waarde uit de lijst:
			JsonObjectBuilder job = Json.createObjectBuilder();
			//maak een object builder en voeg via deze object builder waardes toe aan de Array
			job.add("username", g.getUsername());
			job.add("datum", g.getDatum());
			job.add("begintijd", g.getBegintijd());
			job.add("eindtijd", g.getEindtijd());
			jab.add(job);
		}
		JsonArray array = jab.build();
		return array.toString();
	}

	@GET
	@Path("/alles/{username}") //veel op veel, aan de hand van username
	@Produces("application/json")
	public String getGegevens(@PathParam("username") String username) { //gebruik de username om specifieke waardes te krijgen
		JsonArrayBuilder jab = Json.createArrayBuilder(); //array builder om een lijst aan te kunnen maken
		GegevensDAO gdao = new GegevensDAO(); //haal dao op zodat deze gebruikt kan worden
		List<Gegevens> lijst = gdao.geefAllesPerUsername(username); //in tegenstelling tot de vorige code selecteert deze alle gegevens met een bepaalde username

		for (Gegevens naam : lijst) { //voor iedere waarde uit de lijst:
			JsonObjectBuilder job = Json.createObjectBuilder();
			//maak een object builder en voeg via deze object builder waardes toe aan de Array
			job.add("datum", naam.getDatum());
			job.add("begintijd", naam.getBegintijd());
			job.add("eindtijd", naam.getEindtijd());
			jab.add(job);
		}
		JsonArray array = jab.build();
		return array.toString();

	}

	/* --- DELETE FUNCTIE : DELETE GEBRUIKER --- */

	@DELETE
	@Path("/deletegebruiker/{username}") //specifiek op username
	public Response deleteGebruiker(@PathParam("username") String username,
			@FormParam("wachtwoord") String wachtwoord) { //wachtwoord en username zijn nodig voor de check, dus die worden opgehaald
		GebruikerDAO rDAO = new GebruikerDAO(); //haal de gebruikerdao op zodat deze gebruikt kan worden
		Gebruiker gebruiker = rDAO.zoekUsername(username); //zoek met de dao de username op

		for (Gebruiker r : rDAO.GeefAlleGebruikers()) //voor iedere gebruiker in de lijst:
			if (r.getUsername() == username && r.getWachtwoord() == wachtwoord) { //als wachtwoord en username gelijk zijn
				gebruiker = r;
			}
		if (gebruiker == null) { //als de gebruiker niet bestaat
			return Response.status(Response.Status.NOT_FOUND).build(); //error
		} else {
			rDAO.deleteGebruiker(gebruiker); //delete gebruiker
			return Response.ok().build();
		}
	}
	
	/* --- PUT FUNCTIES : UPDATE GEGEVENS --- */
	
	@PUT
	@Path("/update/{username}") //update aan de hand van username (en datum, maar deze check staat in de dao)
	@Produces("application/json")
	public String updateGegevens(@PathParam("username") String username, @FormParam("datum") String datum,
			@FormParam("begintijd") String begintijd, @FormParam("eindtijd") String eindtijd) throws SQLException { //haal alle gegevens op, SQLException omdat er in de dao met sql gewerkt wordt
		JsonObjectBuilder job = Json.createObjectBuilder();

		GegevensDAO dao = new GegevensDAO(); //haal gegevens dao op
		Gegevens g = new Gegevens(); //maak nieuwe gegevens

		//zet alle opgehaalde waardes in nieuwe gegevens
		g.setUsername(username);
		g.setDatum(datum);
		g.setBegintijd(begintijd);
		g.setEindtijd(eindtijd);
		dao.updateGegevens(g); //stuur ze door om up te daten

		//na update pas je de waarden aan in het json object zodat ze ook correct op de html pagina weergegeven kunnen worden
		job.add("username", username);
		job.add("datum", datum);
		job.add("begintijd", begintijd);
		job.add("eindtijd", eindtijd);

		return job.build().toString();
	}

}
