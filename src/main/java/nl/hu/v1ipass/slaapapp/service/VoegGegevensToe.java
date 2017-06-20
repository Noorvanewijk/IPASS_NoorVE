package nl.hu.v1ipass.slaapapp.service;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import nl.hu.v1ipass.slaapapp.DAO.GegevensDAO;
import nl.hu.v1ipass.slaapapp.entity.Gegevens;

public class VoegGegevensToe {
	public static void main(String[] args) {
		
		GegevensDAO dao = new GegevensDAO();
		Gegevens gegevens = new Gegevens();
		
		dao.VoegGegevensToe(gegevens);
	}
}
