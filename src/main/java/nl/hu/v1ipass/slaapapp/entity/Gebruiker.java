package nl.hu.v1ipass.slaapapp.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table (name = "Gebruiker")
@NamedQuery(query = "Select e from Gebruiker e where e.username = :id", name = "find gebruiker by id")

public class Gebruiker {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO) 	
   
   private String username;
   private String voornaam;
   private String achternaam;
   private String wachtwoord;
   
   public Gebruiker(String username, String wachtwoord, String voornaam, String achternaam) {
	      super( );
	      this.username = username;
	      this.wachtwoord = wachtwoord;
	      this.voornaam = voornaam;
	      this.achternaam = achternaam;
   }
   
   public Gebruiker( ) {
      super();
   }
   
   public String getUsername( ) {
	   return username;
   }
   
   public void setUsername(String username) {
	   this.username = username;
   }
   
   public String getWachtwoord() {
	   return wachtwoord;
   }
   
   public void setWachtwoord(String wachtwoord) {
	   this.wachtwoord = wachtwoord;
   }
   
   public String getVoornaam( ) {
      return voornaam;
   }
   
   public void setVoornaam(String voornaam) {
      this.voornaam = voornaam;
   }
   
   public String getAchternaam() {
	   return achternaam;
   }
   
   public void setAchternaam(String achternaam) {
	   this.achternaam = achternaam;
   }
   
   @Override
   public String toString() {
      return "Gebruiker [username=" + username + ", voornaam=" + voornaam + achternaam + "]";
   }
}