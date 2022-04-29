package es.uma.softcoders.eburyApp.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
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
		/*
            //Individual
            Date pDay = new Date(117,6,23);
            Individual pInd = new Individual("Pep", "Doe");
            //CUENTA
            Divisa pDiv = new Divisa("GBP", "libras", '£', (long)0.94);
            CuentaReferencia pRef = new CuentaReferencia("CaixaBank", (long)40000, pDiv);
            Segregada pSeg = new Segregada("30",pRef);
            List<CuentaFintech> pCuentas = new ArrayList<CuentaFintech>();

            pSeg.setIban("NL66XYZW1291965209");
            
            em.persist(pInd);
            em.persist(pDiv);
            em.persist(pRef);
            em.persist(pSeg);
            
            // Setters
            pRef.setSegregada(pSeg);
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
            pInd.setID(Long.valueOf(1000002));
            // Persist
            em.persist(pInd);
            
            //EMPRESA
            Empresa pEmp = new Empresa("RazSocial");
            List<CuentaFintech> pCuentasEmp = new ArrayList<CuentaFintech>();
            PersonaAutorizada pPAut = new PersonaAutorizada("Ident","Nacho", "Lopezosa", "54");
            //CUENTA INACTIVA
            Divisa pDiv2 = new Divisa("EUR", "euro", '€', (long)1);
            CuentaReferencia pRef2 = new CuentaReferencia("Imagin", (long)40000, pDiv2);
            //Segregada pSeg2 = new Segregada("30",pRef2);
            //RELACION CON PERSONA AUTORIZADA
            Map<PersonaAutorizada,Character> pMapEMP = new HashMap<>();
            Map<Empresa,Character> pMapPAUT = new HashMap<>();

            pSeg2.setIban("NL66XYZW1291965208");

            //Persist
            em.persist(pDiv2);
            em.persist(pRef2);
            em.persist(pSeg2);
            em.persist(pEmp);
            em.persist(pPAut);
            //Setters & adds
            pEmp.setIdentificacion("Ide");
            pEmp.setTipo_cliente("EMPRESA");
            pEmp.setEstado("ACTIVO");
            pEmp.setFecha_Alta(pDay);
            pEmp.setDireccion("54");
            pEmp.setCiudad("Amsterdam");
            pEmp.setCodigoPostal("7207KE");
            pEmp.setPais("NL");
            pCuentasEmp.add(pSeg);
            pCuentasEmp.add(pSeg2);
            pEmp.setCuentas(pCuentasEmp);
            pEmp.setID(Long.valueOf(100001));
            pMapEMP.put(pPAut, 'A');
            pMapPAUT.put(pEmp, 'A');
            pEmp.setAutorizacion(pMapEMP);
            pPAut.setAutorizacion(pMapPAUT);
            //Persist
            em.persist(pEmp);
            em.persist(pPAut);

            Segregada s = new Segregada();
		
             */
		em.getTransaction().commit();
		
		em.close();
		emf.close();
	}
    
}
