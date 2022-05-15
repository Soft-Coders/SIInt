package es.uma.softcoders.eburyApp.test;

import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import es.uma.softcoders.eburyApp.Usuario;

public class BaseDatosLogin {
	protected static final String admin = String.valueOf(new Random().nextInt(99999));
	protected static final String noAdmin = String.valueOf(new Random().nextInt(99999));

	public static void inicializaBaseDatos(String nombreUnidadPersistencia) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(nombreUnidadPersistencia);
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		//----LoginEJB
		Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.usuario = :usuario");
		q.setParameter("usuario", BaseDatosLogin.noAdmin);
		if(q.getResultList().isEmpty()) {
			Usuario uNoAdmin = new Usuario(noAdmin, "noadmin", false);
			em.persist(uNoAdmin);
		}
		q = em.createQuery("SELECT u FROM Usuario u WHERE u.usuario = :usuario");
		q.setParameter("usuario", BaseDatosLogin.admin);
		if(q.getResultList().isEmpty()) {
			Usuario uAdmin = new Usuario(admin, "admin", true);
			em.persist(uAdmin);
		}
		
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
