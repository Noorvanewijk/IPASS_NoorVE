package nl.hu.v1ipass.slaapapp.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import nl.hu.v1ipass.slaapapp.entity.Gebruiker;

public class VerwijderGebruiker {
   public static void main( String[ ] args ) {
   
      EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "Eclipselink_JPA" );
      EntityManager entitymanager = emfactory.createEntityManager( );
      entitymanager.getTransaction( ).begin( );
      
      Gebruiker gebruiker = entitymanager.find( Gebruiker.class, 6 );
      entitymanager.remove( gebruiker );
      entitymanager.getTransaction( ).commit( );
      entitymanager.close( );
      emfactory.close( );
   }
}
