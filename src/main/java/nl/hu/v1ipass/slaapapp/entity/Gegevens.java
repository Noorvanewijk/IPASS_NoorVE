package nl.hu.v1ipass.slaapapp.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/* ------ POJO CLASS GEGEVENS : VOOR DE TABEL GEGEVENS ------- */

@Entity // declareert de klasse als een entiteit of tabel
@Table(name = "gegevens") // onder de naam van tabel genaamd gegevens

public class Gegevens {
	@Id // specificeert de aanwezigheid en het gebruik van de
		// identiteit/uniekheid (primary key) van de klasse
	@GeneratedValue(strategy = GenerationType.AUTO)//configuratie van increment van de specifieke column

	// Hieronder staan alle attributen. Deze komen overeen met de attributen in
	// de tabel, waarbij ID gezien kan worden als de primary key
	private int id;
	private String username;
	private String datum;
	private String begintijd;
	private String eindtijd;

	public Gegevens(int id, String username, String datum, String begintijd, String eindtijd) {
		super(); // subset van alle gegevens
		this.id = id;
		this.datum = datum;
		this.username = username;
		this.begintijd = begintijd;
		this.eindtijd = eindtijd;
	}

	// Deze public Gegevens geeft de optie om ook gegevens toe te voegen zonder
	// het gebruik van een ID
	public Gegevens(String username, String datum, String begintijd, String eindtijd) {
		super(); // subset van alle gegevens
		this.username = username;
		this.datum = datum;
		this.begintijd = begintijd;
		this.eindtijd = eindtijd;
	}

	public Gegevens() {
		super(); // subset van alle gegevens
	}

	/* ---- GETTERS : LEEST DE WAARDE VAN DE VARIABELEN ---- */
	public int getID() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getDatum() {
		return datum;
	}

	public String getBegintijd() {
		return begintijd;
	}

	public String getEindtijd() {
		return eindtijd;
	}

	/* ---- SETTERS : UPDATE DE WAARDEN VAN VARIABELEN ---- */
	public void setID(int id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	public void setBegintijd(String begintijd) {
		this.begintijd = begintijd;
	}

	public void setEindtijd(String eindtijd) {
		this.eindtijd = eindtijd;
	}

	@Override // override een parent klasse -> voor een check indien er iets
				// fout wordt gespeld
	public String toString() {
		return "Op " + datum + " is geslapen van: " + begintijd + " tot " + eindtijd;
	}
}
