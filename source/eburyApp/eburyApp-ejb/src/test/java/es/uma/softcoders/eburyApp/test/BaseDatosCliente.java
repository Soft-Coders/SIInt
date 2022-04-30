package es.uma.softcoders.eburyApp.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import es.uma.softcoders.eburyApp.Cliente;
import es.uma.softcoders.eburyApp.Cuenta;
import es.uma.softcoders.eburyApp.CuentaFintech;
import es.uma.softcoders.eburyApp.CuentaReferencia;
import es.uma.softcoders.eburyApp.Divisa;
import es.uma.softcoders.eburyApp.Empresa;
import es.uma.softcoders.eburyApp.Individual;
import es.uma.softcoders.eburyApp.Pooled;
import es.uma.softcoders.eburyApp.Segregada;
import es.uma.softcoders.eburyApp.Usuario;

public class BaseDatosCliente {
	public static void inicializaBaseDatos(String nombreUnidadPersistencia) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(nombreUnidadPersistencia);
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		

			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			
			Cliente c1 = new Cliente("1111", "tipo", "ACTIVO", new Date(), "Calle calle, 1", "ciudad", "29620", "pais");
			c1.setID(Long.valueOf(1111));
			Cliente c2 = new Cliente("2222", "tipo", "ACTIVO", new Date(), "Calle calle, 1", "ciudad", "29620", "pais");
			c1.setID(Long.valueOf(2222));
			Cliente c3 = new Cliente("3333", "tipo", "ACTIVO", new Date(), "Calle calle, 1", "ciudad", "29620", "pais");
			c1.setID(Long.valueOf(3333));
			Cliente c4 = new Cliente("4444", "tipo", "ACTIVO", new Date(), "Calle calle, 1", "ciudad", "29620", "pais");
			c1.setID(Long.valueOf(4444));
			
			em.persist(c1);
			em.persist(c2);
			em.persist(c3);
			em.persist(c4);
			
			
			Empresa e1 = new Empresa();
			e1.setTipo_cliente("tipo");
			e1.setPais("España");
			e1.setIdentificacion("5555");
			e1.setFecha_Alta(new Date());
			e1.setDireccion("Calle calle, 1");
			e1.setEstado("ACTIVO");
			e1.setCodigoPostal("29010");
			e1.setCiudad("Ciudad");
			e1.setRazonSocial("razon");
			
			Usuario u1 = new Usuario();
			u1.setId(Long.valueOf(37));
			u1.setClave("1234");
			u1.setUsuario("admin37");
			u1.setEsAdministrativo(true);
			
			Individual i1 = new Individual();
			i1.setTipo_cliente("tipo");
			i1.setPais("España");
			i1.setIdentificacion("6666");
			i1.setFecha_Alta(new Date());
			i1.setDireccion("Calle calle, 1");
			i1.setEstado("ACTIVO");
			i1.setCodigoPostal("29010");
			i1.setCiudad("Ciudad");
			i1.setNombre("Johny");
			i1.setApellido("Chad");
			i1.setUsuario(u1);
			
			Usuario u2 = new Usuario();
			u2.setId(Long.valueOf(34));
			u2.setClave("1234");
			u2.setUsuario("pepesado34");
			u2.setEsAdministrativo(false);
			
			
			Individual i2 = new Individual();
			i2.setTipo_cliente("tipo");
			i2.setPais("España");
			i2.setIdentificacion("6666");
			i2.setFecha_Alta(new Date());
			i2.setDireccion("Calle calle, 1");
			i2.setEstado("ACTIVO");
			i2.setCodigoPostal("29010");
			i2.setCiudad("Ciudad");
			i2.setNombre("Pepe");
			i2.setApellido("Sado");
			i2.setUsuario(u2);
			
			em.persist(e1);
			em.persist(i1);
			
		em.getTransaction().commit();
		
		em.close();
		emf.close();
		
	}
}