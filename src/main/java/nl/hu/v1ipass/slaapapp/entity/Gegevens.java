package nl.hu.v1ipass.slaapapp.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "gegevens")

public class Gegevens {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) 	
	
	private int id;
	private String username;
	private String datum;
	private String begintijd;
	private String eindtijd;
	
	public Gegevens(int id, String username, String datum, String begintijd, String eindtijd) {
		super( );
		this.id = id;
		this.datum = datum;
		this.username = username;
		this.begintijd = begintijd;
		this.eindtijd = eindtijd;
	}
	
	public Gegevens(String username, String datum, String begintijd, String eindtijd) {
		super( );
		this.username = username;
		this.datum = datum;
		this.begintijd = begintijd;
		this.eindtijd = eindtijd;
	}
	
	public Gegevens( ) {
		super();
	}
	
	public int getID() {
		return id;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getDatum() {
		return datum;
	}
	
	public void setDatum(String datum) {
		this.datum = datum;
	}
	
	public String getBegintijd() {
		return begintijd;
	}
	
	public void setBegintijd(String begintijd) {
		this.begintijd = begintijd;
	}
	
	public String getEindtijd() {
		return eindtijd;
	}
	
	public void setEindtijd(String eindtijd) {
		this.eindtijd = eindtijd;
	}
	
	@Override
	public String toString() {
		return "Op " + datum + " is geslapen van: " + begintijd + " tot " + eindtijd;
	}
}
