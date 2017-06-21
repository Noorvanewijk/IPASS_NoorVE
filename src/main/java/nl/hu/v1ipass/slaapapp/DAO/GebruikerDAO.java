package nl.hu.v1ipass.slaapapp.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import nl.hu.v1ipass.slaapapp.entity.Gebruiker;
import nl.hu.v1ipass.slaapapp.entity.Gegevens;

public class GebruikerDAO extends BaseDAO { //extentie van BaseDAO
	
	//Functie om een gebruiker toe te voegen in de database
	//GEBRUIKT BIJ HET REGISTREREN
	public boolean maakGebruiker(Gebruiker gebruiker) {
		//EntityManagerFactory maakt een persistence unit aan
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");

		EntityManager entitymanager = emfactory.createEntityManager(); //maak een entitymanager aan met de persistence unit
		entitymanager.getTransaction().begin(); //entitymanager wordt gestart

		entitymanager.persist(gebruiker); //haal alle gegevens voor de nieuwe gebruiker op
		entitymanager.getTransaction().commit(); //commit de gegevens (ze worden toegevoegd)

		entitymanager.close(); //sluit de entitymanager
		emfactory.close(); //sluit de entitymanagerfactory
		return true;
	}
	
	//Voor het zoeken van een bepaalde username in de database
	//GEBRUIKT BIJ DE INLOG FUNCTIE EN HET TOEVOEGEN VAN GEGEVENS
	public Gebruiker vindUsername(String username) {
		//EntityManagerFactory maakt een persistence unit aan
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
		
		EntityManager entitymanager = emfactory.createEntityManager();//maak een entitymanager aan met de persistence unit
		Gebruiker gebruiker = entitymanager.find(Gebruiker.class, username); //zoek naar de username
		
		return gebruiker;
	}
	
	//Functie voor het checken of een username wel bestaat in de database
	//GEBRUIKT IN DE REGISTREER FUNCTIE
	public boolean checkUsername(String username) {
		//EntityManagerFactory maakt een persistence unit aan
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
		
		EntityManager entitymanager = emfactory.createEntityManager();//maak een entitymanager aan met de persistence unit
		Gebruiker gebruiker = entitymanager.find(Gebruiker.class, username); //zoek de username op
		
		if (gebruiker != null) { //check of de gebruiker bestaat
			return false; //als hij bestaat (dus is niet null) return false
		}
		return true; //als hij niet bestaat return true
	}
	
	//Functie om gegevens op de halen onder een specifieke username
	//GEBRUIKT IN DE DELETE FUNCTIE
	public Gebruiker zoekUsername(String username) {
		String query = "SELECT username, wachtwoord, voornaam, achternaam FROM gebruiker WHERE username='"
				+ username + "'"; //maak een query voor het selecteren, deze wordt later doorgestuurd naar de database
		Gebruiker gebruiker = null; //gebruiker is eerst null, omdat hij wellicht niet eens bestaat
		
		try (Connection con = super.getConnection()) { //maak connectie met JDBC
			Statement stmt = con.createStatement(); //interface die sql statement representeert
			ResultSet dbResultSet = stmt.executeQuery(query); //verleent toegang tot data in database en voert het statement uit

			while (dbResultSet.next()) { //voeg de waardes toe aan de query
				gebruiker = new Gebruiker(); //indien gebruiker bestaat wordt hij nu gevuld met benodigde gegevens
				String wachtwoord = dbResultSet.getString("wachtwoord");
				String voornaam = dbResultSet.getString("voornaam");
				String achternaam = dbResultSet.getString("achternaam");
				
				gebruiker.setUsername(username);
				gebruiker.setWachtwoord(wachtwoord);
				gebruiker.setVoornaam(voornaam);
				gebruiker.setAchternaam(achternaam);
			}
		} catch (SQLException sqle) { //in geval van error
			sqle.printStackTrace();
		}
		return gebruiker; //return null of een gebruiker, dit wordt later gecheckt in de webservice
	}
	
	//Functie om gebruiker te deleten uit de database onder specifieke username
	//GEBRUIKT IN DELETE FUNCTIE
	public boolean deleteGebruiker(Gebruiker gebruiker) {
		boolean result = false; //result is eerst false
		boolean usernameExists = zoekUsername(gebruiker.getUsername()) != null; //check of de username überhaupt bestaat

		if (usernameExists) { //als de username bestaat kan hij verwijderd worden
			String query = "DELETE FROM gebruiker WHERE username= '" + gebruiker.getUsername() + "'";
				System.out.println(query);
			try (Connection con = super.getConnection()) {

				Statement stmt = con.createStatement();
				if (stmt.executeUpdate(query) == 1) { //query wordt uitgevoerd
					result = true; //result is true, delete functie ziet dit aan als succes
				}
			} catch (SQLException sqle) { //indien error
				sqle.printStackTrace();
			}
		}
	
		return result; //result is null (geen succes) of true (wel succes)
	}
	
	/* ------- OUDE FUNCTIES : DEZE FUNCTIES WERDEN GEBRUIKT IN OUDERE VERSIES VAN DE APPLICATIE -----*/
	
	//Functie voor het zoeken van een specifieke gebruiker
	private List<Gebruiker> selectGebruiker(String query) {
		List<Gebruiker> results = new ArrayList<Gebruiker>();

		try (Connection con = super.getConnection()) {
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);

			while (dbResultSet.next()) {
				String username = dbResultSet.getString("username");
				String wachtwoord = dbResultSet.getString("wachtwoord");
				String voornaam = dbResultSet.getString("voornaam");
				String achternaam = dbResultSet.getString("achternaam");

				results.add(new Gebruiker(username, wachtwoord, voornaam, achternaam));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return results;
	}
	
	//Functie om alle gegevens uit de database op te halen
	public List<Gebruiker> GeefAlleGebruikers() {
		return selectGebruiker("SELECT username,wachtwoord,voornaam,achternaam FROM gebruiker");
	}
	
	//Functie voor het zoeken van een wachtwoord
	public Gebruiker zoekWachtwoord(String wachtwoord) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
		EntityManager entitymanager = emfactory.createEntityManager();
		Gebruiker gebruiker = entitymanager.find(Gebruiker.class, wachtwoord);
		return gebruiker;
	}
}