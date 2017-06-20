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

/* --------------------------- GEBRUIKER CONNECTIE (CRUD INSERT) */
@Path("/")
public class Main {
	
	@POST
	@Path("/register")
	public String Registreer(@FormParam("username") String username, @FormParam("wachtwoord") String wachtwoord,
			@FormParam("voornaam") String voornaam, @FormParam("achternaam") String achternaam) {
		
		Gebruiker gebruiker = new Gebruiker(username, wachtwoord, voornaam, achternaam);

		GebruikerDAO dao = new GebruikerDAO();

		if (dao.CheckUsername(username)) {
			dao.MaakGebruiker(gebruiker);
			return "success";
		}

		return "error";
	}

	
	@POST
	@Path("/login")
	public String Login(@FormParam("username") String username, @FormParam("wachtwoord") String wachtwoord) {
		GebruikerDAO dao = new GebruikerDAO();
		Gebruiker g = dao.VindUsername(username);
		
		if (g.getWachtwoord().equals(wachtwoord)) {
			return "success";
		}

		return null;
	}

	/* ------------------------------------- GEGEVENS CONNECTIE (CRUD INSERT) */

	@POST
	@Path("/voegtoe")
	public String VoegTijdenToe(@FormParam("username") String username, @FormParam("h_datum") String datum,
			@FormParam("b_tijd") String begintijd, @FormParam("e_tijd") String eindtijd) {
		Gegevens gegevens = new Gegevens(username, datum, begintijd, eindtijd);

		GegevensDAO gdao = new GegevensDAO();
		GebruikerDAO dao = new GebruikerDAO();

		Gebruiker g = dao.VindUsername(username);

		if (g.getUsername().equals(username)) {
			gdao.VoegGegevensToe(gegevens);
			return "miep";
		}

		return "Er bestaat geen account met deze username!";
	}

	@GET
	@Path("/alles")
	@Produces("application/json")
	public String getGebruikers() {
		JsonArrayBuilder jab = Json.createArrayBuilder();
		GegevensDAO gdao = new GegevensDAO();
		List<Gegevens> gegevens = gdao.geefAlleGegevens();

		for (Gegevens g : gegevens) {
			JsonObjectBuilder job = Json.createObjectBuilder();

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
	@Path("/alles/{username}")
	@Produces("application/json")
	public String getGegevens(@PathParam("username") String username) {
		JsonArrayBuilder jab = Json.createArrayBuilder();
		GegevensDAO gdao = new GegevensDAO();
		List<Gegevens> lijst = gdao.geefAllesPerUsername(username);

		for (Gegevens naam : lijst) {
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("datum", naam.getDatum());
			job.add("begintijd", naam.getBegintijd());
			job.add("eindtijd", naam.getEindtijd());
			jab.add(job);
		}
		JsonArray array = jab.build();
		return array.toString();

	}

	/*------------------------------ CRUD */

	@DELETE
	@Path("/deletegebruiker/{username}")
	public Response deleteGebruiker(@PathParam("username") String username, @FormParam("wachtwoord") String wachtwoord) {
		GebruikerDAO rDAO = new GebruikerDAO();
		Gebruiker gebruiker = rDAO.zoekUsername(username);

		for (Gebruiker r : rDAO.GeefAlleGebruikers())
			if (r.getUsername() == username && r.getWachtwoord() == wachtwoord) {
				gebruiker = r;
			}
		if (gebruiker == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			rDAO.deleteGebruiker(gebruiker);
			return Response.ok().build();

		}
	}

	@PUT
	@Path("/update/{username}") /*update via username */
	@Produces("application/json")
	public String updateGegevens(@PathParam("username") String username, @FormParam("datum") String datum,
			@FormParam("begintijd") String begintijd, @FormParam("eindtijd") String eindtijd) throws SQLException {
		JsonObjectBuilder job = Json.createObjectBuilder();
		
		GegevensDAO dao = new GegevensDAO();
		Gegevens g = new Gegevens();
		
		g.setUsername(username);
		g.setDatum(datum);
		g.setBegintijd(begintijd);
		g.setEindtijd(eindtijd);
		dao.updateGegevens(g);

		job.add("username", username);
		job.add("datum", datum);
		job.add("begintijd", begintijd);
		job.add("eindtijd", eindtijd);

		return job.build().toString();
	}
	
	
}
