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
import javax.persistence.Query;

import es.uma.softcoders.eburyApp.CuentaFintech;
import es.uma.softcoders.eburyApp.CuentaReferencia;
import es.uma.softcoders.eburyApp.Divisa;
import es.uma.softcoders.eburyApp.Empresa;
import es.uma.softcoders.eburyApp.Individual;
import es.uma.softcoders.eburyApp.PersonaAutorizada;
import es.uma.softcoders.eburyApp.Segregada;

public class BaseDatosInformes {
	  private static String nombreUnidadPersistencia;
      public static void setCuentas1(){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(nombreUnidadPersistencia);
		EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            Query queryDelete = em.createQuery("DELETE FROM SEGREGADA s WHERE s.iban LIKE 'NL%'");
            int deletedAccounts = queryDelete.executeUpdate();
            Divisa d1 = new Divisa("RAV", "raviolis", '%', (long)0.76);
            Divisa d2 = new Divisa("MAC", "macarrones", '#', (long)1.7);
            Divisa d3 = new Divisa("PES", "pesos", '$', (long)4.2);

            CuentaReferencia cR1 = new CuentaReferencia("BBVA-55", (long)40000, d1);
            CuentaReferencia cR2 = new CuentaReferencia("SANTANDER-55", (long)40000, d2);
            CuentaReferencia cR3 = new CuentaReferencia("CAJAMAR-55", (long)40000, d3);

            Segregada cS1 = new Segregada(cR1);
            Segregada cS2 = new Segregada(cR2);
            Segregada cS3 = new Segregada(cR3);

            cR1.setSegregada(cS1);
            cR2.setSegregada(cS2);
            cR3.setSegregada(cS3);

            Individual c1 = new Individual("Jesús","Cestino");
            
            List<CuentaFintech> list1 = new ArrayList<>();

            list1.add(cS1);
            list1.add(cS2);
            list1.add(cS3);

            Date pDate = new Date(117,6,23);

            c1.setIdentificacion("CLIENTE1-55");
            c1.setTipo_cliente("INDIVIDUAL");
            c1.setEstado("ACTIVO");
            c1.setFecha_Alta(pDate);
            c1.setDireccion("53");
            c1.setCiudad("Sofía");
            c1.setCodigoPostal("29620");
            c1.setPais("germany");
            c1.setFechaNacimiento(pDate);
            c1.setID((long)555555);
            c1.setCuentas(list1);
            
            

            //Setters segregadas
            cS1.setEstado("ACTIVA");
            cS2.setEstado("ACTIVA");
            cS3.setEstado("ACTIVA");

            cS1.setIban("ES1602091417-55");
            cS2.setIban("ES2602091417-55");
            cS3.setIban("ES3602091417-55");

            cS1.setFechaApertura(pDate);
            cS2.setFechaApertura(pDate);
            cS3.setFechaApertura(pDate);

            cS1.setCliente(c1);
            cS2.setCliente(c1);
            cS3.setCliente(c1);
            //Setters segregadas


            //Persist
            em.persist(d1);
            em.persist(d2);
            em.persist(d3);

            em.persist(cR1);
            em.persist(cR2);
            em.persist(cR3);

            em.persist(cS2);
            em.persist(cS2);
            em.persist(cS3);
            //Persist

            em.getTransaction().commit();
            em.close();
            emf.close();

      }

      public static void setCuentas2(){
            EntityManagerFactory emf = Persistence.createEntityManagerFactory(nombreUnidadPersistencia);
		EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            Segregada segregadaEntity = em.find(Segregada.class,"ES1602091417-55");
            segregadaEntity.setIban("45");
            em.persist(segregadaEntity);

            em.getTransaction().commit();
            em.close();
            emf.close();
      }
      public static void setCuentas3(){
            EntityManagerFactory emf = Persistence.createEntityManagerFactory(nombreUnidadPersistencia);
		EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            Segregada segregadaEntity = em.find(Segregada.class,"45");
            Individual individualEntity = em.find(Individual.class,(long)555555);
            individualEntity.setFechaNacimiento(null);
            segregadaEntity.setIban("ES1602091417-55");
            em.persist(individualEntity);
            em.persist(segregadaEntity);

            em.getTransaction().commit();
            em.close();
            emf.close();
      }

      
      public static void inicializaBaseDatos(String nombreUnidadPersistencia) {
        BaseDatosInformes.nombreUnidadPersistencia = nombreUnidadPersistencia;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(nombreUnidadPersistencia);
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
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
            Divisa pDiv2 = new Divisa("RUB", "rublo", 'R', (long)1);
            CuentaReferencia pRef2 = new CuentaReferencia("Imagin", (long)40000, pDiv2);
            Segregada pSeg2 = new Segregada("30",pRef2);
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
		
		
		em.getTransaction().commit();
		
		em.close();
		emf.close();
	}
    
}
