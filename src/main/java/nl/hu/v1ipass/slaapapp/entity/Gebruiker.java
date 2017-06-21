package nl.hu.v1ipass.slaapapp.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/* ------ POJO CLASS GEBRUIKER : VOOR DE TABEL GEBRUIKER ------- */

@Entity // declareert de klasse als een entiteit of tabel
@Table(name = "Gebruiker") // onder de naam van tabel genaamd gegevens
@NamedQuery(query = "Select e from Gebruiker e where e.username = :id", name = "find gebruiker by id")

public class Gebruiker {
	@Id // specificeert de aanwezigheid en het gebruik van de
		// identiteit/uniekheid (primary key) van de klasse
	@GeneratedValue(strategy = GenerationType.AUTO) //configuratie van increment van de specifieke column

	// Hieronder staan alle attributen. Deze komen overeen met de attributen in
	// de tabel, waarbij username gezien kan worden als de primary key
	private String username;
	private String voornaam;
	private String achternaam;
	private String wachtwoord;

	public Gebruiker(String username, String wachtwoord, String voornaam, String achternaam) {
		super(); //subset van alle gebruikers
		this.username = username;
		this.wachtwoord = wachtwoord;
		this.voornaam = voornaam;
		this.achternaam = achternaam;
	}

	public Gebruiker() {
		super(); //subset van alle gebruikers
	}

	/* ---- GETTERS : LEEST DE WAARDE VAN DE VARIABELEN ---- */
	public String getUsername() {
		return username;
	}
	
	public String getWachtwoord() {
		return wachtwoord;
	}
	
	public String getVoornaam() {
		return voornaam;
	}
	
	public String getAchternaam() {
		return achternaam;
	}
	
	/* ---- SETTERS : UPDATE DE WAARDEN VAN VARIABELEN ---- */
	public void setUsername(String username) {
		this.username = username;
	}

	public void setWachtwoord(String wachtwoord) {
		this.wachtwoord = wachtwoord;
	}

	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}

	public void setAchternaam(String achternaam) {
		this.achternaam = achternaam;
	}

	@Override //override een parent klasse -> voor een check indien er iets fout wordt gespeld
	public String toString() {
		return "Gebruiker [username=" + username + ", voornaam=" + voornaam + achternaam + "]";
	}
}