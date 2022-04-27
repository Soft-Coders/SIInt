package es.uma.softcoders.eburyApp.test;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import es.uma.softcoders.eburyApp.Cliente;
import es.uma.softcoders.eburyApp.PersonaAutorizada;

public class BaseDatosAutorizado {
	public static void inicializaBaseDatos(String nombreUnidadPersistencia) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(nombreUnidadPersistencia);
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		//------------------------------------------------------
		
		PersonaAutorizada pa1 = new PersonaAutorizada();
		PersonaAutorizada pa2 = new PersonaAutorizada("ABC123", "Marta", "Maleno", "Calle Patata, 37");
		@SuppressWarnings("deprecation")
		Date cumple = new Date(2002,4,30);
		PersonaAutorizada pa3 = new PersonaAutorizada("ABC456", "Pablo", "Huertas", "Calle Boniato, 8", "ACTIVO",  cumple, new Date(), null);
		PersonaAutorizada pa4 = new PersonaAutorizada();
		PersonaAutorizada pa5 = new PersonaAutorizada("ABC789", "Ignacio", "Lopezosa", "Calle Cebolla, 13");
		PersonaAutorizada pa6 = new PersonaAutorizada("DEF123", "Jesús", "Cestino", "Calle Puerro, 16");
		
		for (PersonaAutorizada personaAutorizada: new PersonaAutorizada [] {pa1, pa2, pa3, pa4, pa5, pa6}) {
			em.persist(personaAutorizada);
		}
		
		Cliente cl1 = new Cliente();
		Cliente cl2 = new Cliente();
		Cliente cl3 = new Cliente("ignacioLop", "Individual", "Activo", new Date(), "Calle dnjisak, 33", "Málaga", 29011, "España");
		Cliente cl4 = new Cliente("jesusCest", "Individual", "Activo", new Date(), "Calle ahnskc, 90", "Málaga", 29004, "España");
		
		for (Cliente cliente: new Cliente [] {cl1, cl2, cl3, cl4}) {
			em.persist(cliente);
		}
		
		
		//------------------------------------------------------
		em.getTransaction().commit();
		
		em.close();
		emf.close();
	}
}
