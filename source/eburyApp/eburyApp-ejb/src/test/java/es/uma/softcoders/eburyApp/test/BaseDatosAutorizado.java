package es.uma.softcoders.eburyApp.test;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import es.uma.softcoders.eburyApp.Cliente;
import es.uma.softcoders.eburyApp.Empresa;
import es.uma.softcoders.eburyApp.Individual;
import es.uma.softcoders.eburyApp.PersonaAutorizada;
import es.uma.softcoders.eburyApp.Usuario;

public class BaseDatosAutorizado {

	public static void inicializaBaseDatos(String nombreUnidadPersistencia) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(nombreUnidadPersistencia);
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		//------------------------------------------------------
		
		// ------- Personas Autorizadas -------
		PersonaAutorizada pa1 = new PersonaAutorizada("ABC123", "Marta", "Maleno", "Calle Patata, 37");
		PersonaAutorizada pa2 = new PersonaAutorizada("ABC456", "Pablo", "Huertas", "Calle Boniato, 8");
		PersonaAutorizada pa3 = new PersonaAutorizada("ABC789", "Ignacio", "Lopezosa", "Calle Cebolla, 13");
		PersonaAutorizada pa4 = new PersonaAutorizada("DEF123", "Jesús", "Cestino", "Calle Puerro, 16");
		Boolean t = true; Boolean f = false;
		Usuario u1 = new Usuario("USUARIO1", "clave1", t);
		Usuario u2 = new Usuario("USUARIO2", "clave2", f);
		Usuario u3 = new Usuario("USUARIO3", "clave3", t);
		Usuario u4 = new Usuario("USUARIO4", "clave4", f);
		pa1.setUsuario(u1);
		pa2.setUsuario(u2);
		pa3.setUsuario(u3);
		pa4.setUsuario(u4);
		for (PersonaAutorizada personaAutorizada: new PersonaAutorizada [] {pa1, pa2, pa3, pa4}) {
			em.persist(personaAutorizada);
		}
		
		// ------- Empresas -------
		
		Empresa em1 = new Empresa("Aldi");
		Empresa em2 = new Empresa("EmpresaA");
		Empresa em3 = new Empresa("IKEA");
		Empresa em4 = new Empresa("EmpresaB");
		em2.setID((long)9423903);
		em4.setID((long)1467853);
		em1.setID((long)48712390);
		em3.setID((long)2168393);
		em1.setIdentificacion("Aldi");
		em2.setIdentificacion("EmpresaA");
		em3.setIdentificacion("IKEA");
		em4.setIdentificacion("EmpresaB");
		em1.setTipo_cliente("Empresa");
		em2.setTipo_cliente("Empresa");
		em3.setTipo_cliente("Empresa");
		em4.setTipo_cliente("Empresa");
		em1.setEstado("Activo");
		em1.setFecha_Alta(new Date());
		em1.setDireccion("Calle calle, 0");
		em1.setCiudad("Málaga");
		em1.setCodigoPostal("29000");
		em1.setPais("Esp");
		em2.setEstado("Activo");
		em2.setFecha_Alta(new Date());
		em2.setDireccion("Calle calle, 0");
		em2.setCiudad("Málaga");
		em2.setCodigoPostal("29000");
		em2.setPais("Esp");
		em3.setEstado("ACTIVO");
		em3.setFecha_Alta(new Date());
		em3.setDireccion("Calle calle, 0");
		em3.setCiudad("Málaga");
		em3.setCodigoPostal("29000");
		em3.setPais("Esp");
		em4.setEstado("Activo");
		em4.setFecha_Alta(new Date());
		em4.setDireccion("Calle calle, 0");
		em4.setCiudad("Málaga");
		em4.setCodigoPostal("29000");
		em4.setPais("Esp");
		
		for (Empresa empresa: new Empresa [] {em1, em2, em3, em4}) {
			em.persist(empresa);
		}
		
		//------------------------------------------------------
		em.getTransaction().commit();
		
		em.close();
		emf.close();
	}
}
