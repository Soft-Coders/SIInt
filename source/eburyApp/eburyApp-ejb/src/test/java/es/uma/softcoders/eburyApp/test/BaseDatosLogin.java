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
<<<<<<< HEAD
		//Usuario uNoAdmin = new Usuario("noadmin", "noadmin", false);
		//Usuario uAdmin = new Usuario("admin", "admin", true);
=======
		Usuario uNoAdmin = new Usuario("noadmin-0022", "noadmin", false);
		Usuario uAdmin = new Usuario("admin-0122", "admin", true);
>>>>>>> 86d2706b3406e2bbd88b8d0ddb1d9d8108755bba
		
		
		/*for(Usuario u : new Usuario [] {uNoAdmin, uAdmin})
			em.persist(u);
		
		em.getTransaction().commit();
		
		em.close();
		emf.close();
		*/
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
