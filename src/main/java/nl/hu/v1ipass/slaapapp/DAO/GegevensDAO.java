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

public class GegevensDAO extends BaseDAO {
	private List<Gegevens> selectGegevens(String query) {
		List<Gegevens> results = new ArrayList<Gegevens>();

		try (Connection con = super.getConnection()) {
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);

			while (dbResultSet.next()) {
				String username = dbResultSet.getString("username");
				String datum = dbResultSet.getString("datum");
				String begintijd = dbResultSet.getString("begintijd");
				String eindtijd = dbResultSet.getString("eindtijd");

				results.add(new Gegevens(username, datum, begintijd, eindtijd));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return results;
	}

	public boolean VoegGegevensToe(Gegevens gegevens) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");

		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();

		entitymanager.persist(gegevens);
		entitymanager.getTransaction().commit();

		entitymanager.close();
		emfactory.close();
		return true;
	}

	public List<Gegevens> geefAlleGegevens() {
		return selectGegevens("SELECT username,datum,begintijd,eindtijd FROM gegevens");
	}
	
	public List<Gegevens> geefAllesPerUsername(String username) {
		return selectGegevens("SELECT username,datum,begintijd,eindtijd FROM gegevens"
				+ " WHERE username='" + username + "'");
	}

	public Gegevens zoekUsername(String username) {
		String query = "SELECT username, datum, begintijd, eindtijd FROM gegevens WHERE username='"
				+ username + "'";
		Gegevens gegevens = null;
		try (Connection con = super.getConnection()) {
			Statement stmt = con.createStatement();
			ResultSet dbResultSet = stmt.executeQuery(query);

			while (dbResultSet.next()) {
				gegevens = new Gegevens();
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
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return gegevens;
	}

	public Gegevens updateGegevens(Gegevens gegevens) throws SQLException {
		boolean gegevensExists = zoekUsername(gegevens.getUsername()) != null;

		if (gegevensExists) {
			String query = "Update gegevens SET begintijd= '" + gegevens.getBegintijd() + "', eindtijd= '"
					+ gegevens.getEindtijd() + "' WHERE datum = '" + gegevens.getDatum() + "' AND username = '"
					+ gegevens.getUsername() + "'";
			System.out.println(query);
			try (Connection con = super.getConnection()) {

				Statement stmt = con.createStatement();
				if (stmt.executeUpdate(query) == 1) { // 1 row updated!

				}
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		return gegevens;
	}
	
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