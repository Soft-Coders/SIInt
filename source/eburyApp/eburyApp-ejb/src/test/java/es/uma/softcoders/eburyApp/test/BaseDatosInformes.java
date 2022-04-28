package es.uma.softcoders.eburyApp.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import es.uma.softcoders.eburyApp.CuentaFintech;
import es.uma.softcoders.eburyApp.CuentaReferencia;
import es.uma.softcoders.eburyApp.Divisa;
import es.uma.softcoders.eburyApp.Empresa;
import es.uma.softcoders.eburyApp.Individual;
import es.uma.softcoders.eburyApp.PersonaAutorizada;
import es.uma.softcoders.eburyApp.Segregada;

public class BaseDatosInformes {

    public static void inicializaBaseDatos(String nombreUnidadPersistencia) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(nombreUnidadPersistencia);
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		

            Date pDay = new Date(2017,06,23);
            Individual pInd = new Individual("Pep", "Doe");
            Divisa pDiv = new Divisa("GBP", "libras", '£', (long)0.94);
            CuentaReferencia pRef = new CuentaReferencia("CaixaBank", (long)40000, pDiv);
            Segregada pSeg = new Segregada("30",pRef);
            List<CuentaFintech> pCuentas = new ArrayList<CuentaFintech>();
            // Setters
            pInd.setIdentificacion("Ide");
            pInd.setTipo_cliente("INDIVIDUAL");
            pInd.setEstado("ACTIVO");
            pInd.setFecha_Alta(pDay);
            pInd.setDireccion("54");
            pInd.setCiudad("New York");
            pInd.setCodigoPostal("7207KE");
            pInd.setPais("NL");
            pCuentas.add(pSeg);
            pInd.setCuentas(pCuentas);
            // Persist
            em.persist(pInd);
            
            //EMPRESA
            Empresa pEmp = new Empresa("RazSocial");
            List<CuentaFintech> pCuentasEmp = new ArrayList<CuentaFintech>();
            PersonaAutorizada pPAut = new PersonaAutorizada("Ident","Nacho", "Lopezosa", "54");
            Map<PersonaAutorizada,Character> pMapEMP = new HashMap<>();
            Map<Empresa,Character> pMapPAUT = new HashMap<>();
            pEmp.setIdentificacion("Ide");
            pEmp.setTipo_cliente("EMPRESA");
            pEmp.setEstado("ACTIVO");
            pEmp.setFecha_Alta(pDay);
            pEmp.setDireccion("54");
            pEmp.setCiudad("Amsterdam");
            pEmp.setCodigoPostal("7207KE");
            pEmp.setPais("NL");
            pCuentasEmp.add(pSeg);
            pEmp.setCuentas(pCuentasEmp);
            pMapEMP.put(pPAut, 'A');
            pMapPAUT.put(pEmp, 'A');
            pEmp.setAutorizacion(pMapEMP);
            pPAut.setAutorizacion(pMapPAUT);

            em.persist(pEmp);
            em.persist(pPAut);

		
		
		em.getTransaction().commit();
		
		em.close();
		emf.close();
	}
    
}
