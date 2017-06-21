package nl.hu.v1ipass.slaapapp.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import nl.hu.v1ipass.slaapapp.entity.Gegevens;

public class GegevensDAO extends BaseDAO { //extentie van BaseDAO
	
	//Functie om alle gegevens op te vragen, specifiek op query
	//GEBRUIKT IN GEEFALLEGEGEVENS EN GEEFGEGEVENSPERUSERNAME (in deze dao)
	private List<Gegevens> selectGegevens(String query) { //maakt lijst met de query
		List<Gegevens> results = new ArrayList<Gegevens>(); //nieuwe arraylist

		try (Connection con = super.getConnection()) { //maak connectie met JDBC
			Statement stmt = con.createStatement(); //interface die sql statement representeert
			ResultSet dbResultSet = stmt.executeQuery(query); //verleent toegang tot data in database en voert het statement uit

			while (dbResultSet.next()) { //voeg de waardes toe aan de query
				String username = dbResultSet.getString("username");
				String datum = dbResultSet.getString("datum");
				String begintijd = dbResultSet.getString("begintijd");
				String eindtijd = dbResultSet.getString("eindtijd");

				results.add(new Gegevens(username, datum, begintijd, eindtijd)); //voeg gegevens toe aan de arraylist
			}
		} catch (SQLException sqle) { //indien error
			sqle.printStackTrace();
		}

		return results;
	}
	
	//Functie om gegevens toe te voegen
	//GEBRUIKT IN VOEG TOE FUNCTIE
	public boolean voegGegevensToe(Gegevens gegevens) {
		//EntityManagerFactory maakt een persistence unit aan
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");

		EntityManager entitymanager = emfactory.createEntityManager(); //maak een entitymanager aan met de persistence unit
		entitymanager.getTransaction().begin(); //entitymanager wordt gestart

		entitymanager.persist(gegevens); //haal alle gegevens op die toegevoegd moeten worden
		entitymanager.getTransaction().commit(); //commit de gegevens (ze worden toegevoegd)

		entitymanager.close(); //sluit de entitymanager
		emfactory.close(); //sluit de entitymanagerfactory
		return true;
	}

	//Functie om alle gegevens op te vragen
	//GEBRUIKT IN VEEL OP VEEL RELATIE EN GETGEGEVENSBYUSERNAME(in deze dao)
	public List<Gegevens> geefAlleGegevens() { //gebruik selectGegevens functie en stuurt deze query ernaar door
		return selectGegevens("SELECT username,datum,begintijd,eindtijd FROM gegevens");
	}
	
	//Functie om gegevens op te vragen, geselecteerd op username
	//GEBRUIKT IN EEN OP VEEL RELATIE
	public List<Gegevens> geefAllesPerUsername(String username) { //maakt gebruik van de selectGegevens functie, stuurt een query
		return selectGegevens("SELECT username,datum,begintijd,eindtijd FROM gegevens"
				+ " WHERE username='" + username + "'"); //query specificeert op username
	}
	
	//Functie om gegevens op te halen onder specifieke username en eventueel te updaten
	//GEBRUIKT IN DELETE FUNCTIE
	public Gegevens zoekUsername(String username) {
		String query = "SELECT username, datum, begintijd, eindtijd FROM gegevens WHERE username='"
				+ username + "'"; //maak een query die specificeert op username
		Gegevens gegevens = null; //gebruiker is eerst null, omdat hij wellicht niet eens bestaat

		try (Connection con = super.getConnection()) { //maak connectie met JDBC
			Statement stmt = con.createStatement(); //interface die sql statement representeert
			ResultSet dbResultSet = stmt.executeQuery(query); //verleent toegang tot data in database en voert het statement uit

			while (dbResultSet.next()) { //voeg de waardes toe aan de query
				gegevens = new Gegevens(); //indien de username bestaat wordt het nu aangepast
				int id = dbResultSet.getInt("id");
				String datum = dbResultSet.getString("datum");
				String begintijd = dbResultSet.getString("begintijd");
				String eindtijd = dbResultSet.getString("eindtijd");
				
				gegevens.setUsername(username);
				gegevens.setID(id);
				gegevens.setDatum(datum);
				gegevens.setBegintijd(begintijd);
				gegevens.setEindtijd(eindtijd);
			}
		} catch (SQLException sqle) { //in geval van error
			sqle.printStackTrace();
		}
		return gegevens; //return null of gegevens, dit wordt later gecheckt in de webservice
	}
	
	//Functie om gegevens up te daten in de database
	//GEBRUIKT IN UPDATE FUNCTIE
	public Gegevens updateGegevens(Gegevens gegevens) throws SQLException {
		boolean gegevensExists = zoekUsername(gegevens.getUsername()) != null; //check of de username wel bestaat

		if (gegevensExists) { //indien de username bestaat
			String query = "Update gegevens SET begintijd= '" + gegevens.getBegintijd() + "', eindtijd= '"
					+ gegevens.getEindtijd() + "' WHERE datum = '" + gegevens.getDatum() + "' AND username = '"
					+ gegevens.getUsername() + "'"; //maak een query die update, checkend op username en datum
			try (Connection con = super.getConnection()) { //maak connectie met JDBC

				Statement stmt = con.createStatement(); //interface die sql statement representeert
				if (stmt.executeUpdate(query) == 1) { //query wordt doorgevoerd en rij wordt geupdate

				}
			} catch (SQLException sqle) { //in geval van error
				sqle.printStackTrace();
			}
		}
		return gegevens;
	}
	
	/* ------- OUDE FUNCTIES : DEZE FUNCTIES WERDEN GEBRUIKT IN OUDERE VERSIES VAN DE APPLICATIE -----*/
	
	//Functie om gegevens op te vragen specifiek op username
	public Gegevens getGegevensbyUsername(String username) {
		Gegevens result = null;
		
		for (Gegevens g : geefAlleGegevens()) {
			if (g.getUsername().equals(username)) {
				result = g;
				break;
			}
		}
		
		return result;
	}

}