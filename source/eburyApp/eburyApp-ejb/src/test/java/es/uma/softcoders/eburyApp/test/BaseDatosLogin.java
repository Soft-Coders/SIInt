package es.uma.softcoders.eburyApp.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import es.uma.softcoders.eburyApp.Usuario;

public class BaseDatosLogin {
	public static void inicializaBaseDatos(String nombreUnidadPersistencia) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(nombreUnidadPersistencia);
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		//----LoginEJB
		Usuario uNoAdmin = new Usuario("noadmin-0022", "noadmin", false);
		uNoAdmin.setId(Long.valueOf(22));
		Usuario uAdmin = new Usuario("admin-0122", "admin", true);
		uAdmin.setId(Long.valueOf(122));
		
		
		for(Usuario u : new Usuario [] {uNoAdmin, uAdmin})
			em.persist(u);
		
		em.getTransaction().commit();
		
		em.close();
		emf.close();
	}
	
//	public static void dropBaseDatos(String nombreUnidadPersistencia) {
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory(nombreUnidadPersistencia);
//		EntityManager em = emf.createEntityManager();
//		
//		em.getTransaction().begin();
//		
//		for(String u : new String [] {"uNoAdmin", "uAdmin"})
//			em.remove(em.find(Usuario.class, u));
//		
//		em.getTransaction().commit();
//		
//		em.close();
//		emf.close();
//	}
}
