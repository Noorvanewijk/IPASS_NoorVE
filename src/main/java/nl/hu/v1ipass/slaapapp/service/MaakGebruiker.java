package nl.hu.v1ipass.slaapapp.service;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import nl.hu.v1ipass.slaapapp.DAO.GebruikerDAO;
import nl.hu.v1ipass.slaapapp.entity.Gebruiker;



public class MaakGebruiker {
	public static void main(String[] args) {

		/*
		 * EntityManagerFactory emfactory =
		 * Persistence.createEntityManagerFactory( "Eclipselink_JPA" );
		 * 
		 * EntityManager entitymanager = emfactory.createEntityManager( );
		 * entitymanager.getTransaction( ).begin( );
		 */
		
		GebruikerDAO dao = new GebruikerDAO();
		
		Gebruiker gebruiker = new Gebruiker();
		gebruiker.setVoornaam("Thomas");
		gebruiker.setAchternaam("Korevaar");
		
		dao.MaakGebruiker(gebruiker);

		/* om dingen te vinden
		Gebruiker g = dao.VindGebruiker(2);
		System.out.println(g.getVoornaam());
		*/
		
		/*
		 * entitymanager.persist( gebruiker ); entitymanager.getTransaction(
		 * ).commit( );
		 * 
		 * entitymanager.close( ); emfactory.close( );
		 */
	}
}
