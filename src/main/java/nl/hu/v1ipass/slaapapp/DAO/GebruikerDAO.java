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

public class GebruikerDAO extends BaseDAO {
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

	public boolean MaakGebruiker(Gebruiker gebruiker) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");

		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();

		entitymanager.persist(gebruiker);
		entitymanager.getTransaction().commit();

		entitymanager.close();
		emfactory.close();
		return true;
	}

	public Gebruiker VindUsername(String username) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
		EntityManager entitymanager = emfactory.createEntityManager();
		Gebruiker gebruiker = entitymanager.find(Gebruiker.class, username);
		return gebruiker;
	}
	
	public Gebruiker zoekWachtwoord(String wachtwoord) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
		EntityManager entitymanager = emfactory.createEntityManager();
		Gebruiker gebruiker = entitymanager.find(Gebruiker.class, wachtwoord);
		return gebruiker;
	}
	
	public boolean CheckUsername(String username) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
		EntityManager entitymanager = emfactory.createEntityManager();
		Gebruiker gebruiker = entitymanager.find(Gebruiker.class, username);
		
		if (gebruiker != null) {
			return false;
		}
		return true;
	}

	public List<Gebruiker> GeefAlleGebruikers() {
		return selectGebruiker("SELECT username,wachtwoord,voornaam,achternaam FROM gebruiker");
	}
	
	public Gebruiker zoekUsername(String username) {
		String query = "SELECT username, wachtwoord, voornaam, achternaam FROM gebruiker WHERE username='"
				+ username + "'";
		Gebruiker gebruiker = null;
		try (Connection con = super.getConnection()) {
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);

			while (dbResultSet.next()) {
				gebruiker = new Gebruiker();
				String wachtwoord = dbResultSet.getString("wachtwoord");
				String voornaam = dbResultSet.getString("voornaam");
				String achternaam = dbResultSet.getString("achternaam");
				
				gebruiker.setUsername(username);
				gebruiker.setWachtwoord(wachtwoord);
				gebruiker.setVoornaam(voornaam);
				gebruiker.setAchternaam(achternaam);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return gebruiker;
	}
	
	public boolean deleteGebruiker(Gebruiker gebruiker) {
		boolean result = false;
		boolean usernameExists = zoekUsername(gebruiker.getUsername()) != null;

		if (usernameExists) {
			String query = "DELETE FROM gebruiker WHERE username= '" + gebruiker.getUsername() + "'";
				System.out.println(query);
			try (Connection con = super.getConnection()) {

				Statement stmt = con.createStatement();
				if (stmt.executeUpdate(query) == 1) { // 1 row updated!
					result = true;
				}
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
	
		return result;
	}
}