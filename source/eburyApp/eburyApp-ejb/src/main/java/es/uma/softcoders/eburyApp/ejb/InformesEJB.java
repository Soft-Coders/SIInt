package es.uma.softcoders.eburyApp.ejb;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import es.uma.softcoders.eburyApp.Cliente;
//import es.uma.softcoders.eburyApp.CuentaReferencia;
import es.uma.softcoders.eburyApp.Empresa;
import es.uma.softcoders.eburyApp.Individual;
import es.uma.softcoders.eburyApp.PersonaAutorizada;
import es.uma.softcoders.eburyApp.Segregada;
import es.uma.softcoders.eburyApp.exceptions.FailedInitialCSVException;
import es.uma.softcoders.eburyApp.exceptions.FailedPeriodicCSVException;
import es.uma.softcoders.eburyApp.exceptions.InvalidJSONQueryException;

@Stateless
public class InformesEJB implements GestionInformes{
	
	private static final String N_E = "noexistente";
	
	@PersistenceContext(unitName = "eburyAppEjb")
	private EntityManager em;
	
	/**
	 * Método encargado de responder a la consulta de la API REST para los informes de Holanda.
	 * 
	 * @throws InvalidJSONQueryException
	 * @param json String que contiene la consulta de la API en formato JSON
	 * @author Ignacio Lopezosa
	 */
	@Override
	public List<Object> informeHolanda(String json) throws InvalidJSONQueryException {
		
		List<Object> results;
		try {
			Object jsonFile    = JSONValue.parseWithException(json);
			JSONObject jsonObj = (JSONObject) jsonFile;
			if (jsonObj == null)
				throw new InvalidJSONQueryException("JSON Query NOT FOUND");
			System.out.println("jsonObj:\n=======\n" + jsonObj.toString() + "\n=======\n");
			
			// Buscar "searchParameters"
			Object searchParameters = jsonObj.get("searchParameters");
			if(searchParameters == null)
				throw new InvalidJSONQueryException("searchParameters NOT FOUND");			
			JSONObject spObj = (JSONObject) searchParameters;					// Cast into JSONObject
			System.out.println("spObj:\n=======\n" + spObj.toString() + "\n=======\n");
			// Buscar "questionType"
			String questionType = (String) spObj.get("questionType");
			System.out.println("questionType:\n=======\n" + questionType + "\n=======\n");
			if(questionType == null)
				throw new InvalidJSONQueryException("questionType NOT FOUND");
			
			// Elegir funcionamiento según "questionType"
			switch(questionType) {
			case "Product":case "product":case "PRODUCT":
				results = product(spObj);
				break;
			case "Customer":case "customer":case "CUSTOMER":
				results = customer(spObj);
				break;
			default:
				throw new InvalidJSONQueryException("questionType NOT VALID");	
			}
		}catch(ClassCastException e) {
			throw new InvalidJSONQueryException("json COULD NOT BE CAST PROPERLY");
		}catch(ParseException e) {
			throw new InvalidJSONQueryException("json COULD NOT BE PARSED PROPERLY"); // Properly
		}catch(Exception e) {
			throw new InvalidJSONQueryException("\n@@@@@\n" + json + "\n@@@@@\njson ERROR " + e.getMessage());
		}
		
		return results;
	}

	/** Método privado que contiene la lógica para gestión de consultas de <b>Cuentas</b> a través de API REST. Usado en informes de Holanda.
	 * 
	 * @param searchParameters Objeto JSON que contiene los datos de la consulta
	 * @author Ignacio Lopezosa
	 * @return Lista de las <b>Cuentas</b> que cumplen las condiciones de la consulta
	 * @throws InvalidJSONQueryException 
	 * */
	private List<Object> product(JSONObject spObj) throws InvalidJSONQueryException {
		
		List<Object> results = new ArrayList<>();
		Query querySegregadas;
//		Query queryReferencia;
		try {
			String predicate     = "";
			String status        = (String) spObj.get("status");
			String productNumber = (String) spObj.get("productNumber");
			
			if(status != null || productNumber != null) {
				predicate.concat(" WHERE ");	
				int queryLength = predicate.length();
				
				if(status.equalsIgnoreCase("active"))
					predicate.concat("C.estado = 'ACTIV[OAE]'");		//TODO Determinar nomenclatura de CuentaFintech.estado
				else if(status.equalsIgnoreCase("inactive"))
					predicate.concat("C.estado = 'INACTIV[OAE]'");	//TODO Determinar nomenclatura de CuentaFintech.estado
				else
					throw new InvalidJSONQueryException("status NOT VALID");
				
				if(productNumber != null) {
					if(predicate.length() > queryLength)			// Se ha modificado query?
						predicate.concat(" AND ");
					predicate.concat("C.iban = '" + productNumber + "'");
				}
			}
			
			querySegregadas = em.createQuery("FROM Segregada C" + predicate);
//			queryReferencia = em.createQuery("FROM CuentaReferencia C" + predicate + " AND C.depositadaEn IS NOT NULL");
			
			List<Object> resultsSegregadas = querySegregadas.getResultList();
//			List<Object> resultsReferencia = querySegregadas.getResultList();
			results.addAll(resultsSegregadas);
//			results.addAll(resultsReferencia);
		}catch(ClassCastException e) {
			throw new InvalidJSONQueryException("product COULD NOT BE CAST PROPERLY");
		}catch(IllegalArgumentException e) {
			throw new InvalidJSONQueryException("product final query NOT VALID FORMAT");
		}catch(Exception e) {
			throw new InvalidJSONQueryException("product ERROR " + e.getMessage());
		}
		
		return results;
	}
	
	/**
	 * Método privado que contiene la lógica para gestión de consultas de <b>Clientes</b> a través de API REST. Usado en informes de Holanda.
	 * 
	 * @param searchParameters Objeto JSON que contiene los datos de la consulta
	 * @author Ignacio Lopezosa
	 * @return Lista de las <b>Clientes</b>que cumplen las condiciones de la consulta
	 * @throws InvalidJSONQueryException 
	 */
	@SuppressWarnings("deprecation")
	private List<Object> customer(JSONObject spObj) throws InvalidJSONQueryException {
		
		Query query;
		try {
			String predicate   = "";
			int predicateLength= predicate.length();
			String startPeriod = (String) spObj.get("startPeriod");
			String endPeriod   = (String) spObj.get("endPeriod");
			JSONObject name    = (JSONObject) spObj.get("name");					// Cast into JSONObject				
			String firstName   = null;
			String lastName    = null;
			if(name != null) {
				firstName   = (String) name.get("firstName");	
				lastName    = (String) name.get("lastName");
			}
			JSONObject address = (JSONObject) spObj.get("address");				// Get String
			String street      = null;	
			String city        = null;
			String postalCode  = null;
			String country     = null;
			if(address != null) {
				street      = (String) address.get("street");	/* incluye calle y número */
				city        = (String) address.get("city");
				postalCode  = (String) address.get("postalCode");
				country     = (String) address.get("country");				
			}
			
			System.out.println("Customer:\n=======> sp, ep, na, fn, ln, ad, st, ct, pc, cn");			
			for(Object param : new Object [] {startPeriod, endPeriod, name, firstName, lastName, address, street, city, postalCode, country})
				if(param != null)
					System.out.println("> " + param.toString());
			System.out.println("=======");
			
			if(startPeriod != null)
				predicate += "i.fechaAlta = :startPeriod";
			if(endPeriod != null) {
				if(predicate.length() > predicateLength)
					predicate += " AND ";
				predicate += "i.fechaBaja = :endPeriod";
			}
			if(name != null) {
				if(firstName != null) {
					if(predicate.length() > predicateLength)
						predicate += " AND ";
					predicate += "i.nombre = '" + firstName + "'";
				}
				if(lastName != null) {
					if(predicate.length() > predicateLength)
						predicate += " AND ";
					predicate += "i.apellido = '" + lastName + "'";
				}
			}
			if(address != null) {
				if(street != null) {
					if(predicate.length() > predicateLength)
						predicate += " AND ";
					predicate += "i.direccion = '" + street + "'";
				}
				if(city != null) {
					if(predicate.length() > predicateLength)
						predicate += " AND ";
					predicate += "i.ciudad = '" + city + "'";
				}
				if(postalCode != null) {
					if(predicate.length() > predicateLength)
						predicate += " AND ";
					predicate += "i.codigoPostal = '" + postalCode + "'";
				}
				if(country != null) {
					if(!country.equalsIgnoreCase("netherlands") && !country.equalsIgnoreCase("NL") && !country.equalsIgnoreCase("holanda"))
						throw new InvalidJSONQueryException("customer.country NOT VALID");
					if(predicate.length() > predicateLength)
						predicate += " AND ";
					predicate += "i.pais = '" + country + "'";
				}
			}
			
			System.out.println("predicate:\n=======\n" + predicate + "\n=======");
			
			if(em == null)
				throw new NullPointerException("---El EntityManager es NULL---");
			try {
				String hql;
				if(predicate.equals(""))
					hql = "SELECT i FROM Individual i";
				else
					hql = "SELECT i FROM Individual i WHERE " + predicate;
				System.out.println("hql:\n=======\n" + hql + "\n=======");
				query = em.createQuery(hql);
			}catch(IllegalArgumentException e) {
				throw new InvalidJSONQueryException("-@@ 0 @@- => " + e.getMessage());
			}
			if(startPeriod != null) {
				try {
					
					String[] dateArr = startPeriod.split("-");
					int year = Integer.parseInt(dateArr[0]);
					if(year < 1900 || year > (new Date().getYear() + 1900))	//new Date().getYear() + 1900 = current year
						throw new InvalidJSONQueryException("startPeriod.year NOT VALID");
					int month = Integer.parseInt(dateArr[1]);
					if(month < 1 || month > 12)
						throw new InvalidJSONQueryException("startPeriod.month NOT VALID");
					int day = Integer.parseInt(dateArr[2]);
					if(day < 1 || day > 31)
						throw new InvalidJSONQueryException("startPeriod.day NOT VALID");
					Date spDate = new Date(year-1900, month, day);			// Deprecated -> Cambiar tipo a Calendar? TODO
					query.setParameter("startPeriod", spDate);
					
				}catch(NullPointerException e) {
					throw new InvalidJSONQueryException("startPeriod NOT VALID");
				}catch(IllegalArgumentException e) {
					throw new InvalidJSONQueryException("-@@ 1 @@- -> " + predicate + " <-");
				}
			}
			if(endPeriod != null) {
				try {
					
					String[] dateArr = endPeriod.split("-");
					int year = Integer.parseInt(dateArr[0]);
					if(year < 1900 || year > (new Date().getYear() + 1900))	//new Date().getYear() + 1900 = current year
						throw new InvalidJSONQueryException("endPeriod.year NOT VALID");
					int month = Integer.parseInt(dateArr[1]);
					if(month < 1 || month > 12)
						throw new InvalidJSONQueryException("endPeriod.month NOT VALID");
					int day = Integer.parseInt(dateArr[2]);
					if(day < 1 || day > 31)
						throw new InvalidJSONQueryException("endPeriod.day NOT VALID");
					Date epDate = new Date(year-1900, month, day);
					query.setParameter("endPeriod", epDate);
					
				}catch(NullPointerException e) {
					throw new InvalidJSONQueryException("endPeriod NOT VALID");
				}catch(IllegalArgumentException e) {
					throw new InvalidJSONQueryException("-@@ 2 @@- -> " + predicate + " <-");
				}
			}
		}catch(ClassCastException e) {
			throw new InvalidJSONQueryException("customer COULD NOT BE CAST PROPERLY " + e.getMessage());
		}catch(IllegalArgumentException e) {
			throw new InvalidJSONQueryException("customer final query NOT VALID FORMAT " + e.getMessage() + "-" + e.getClass());
		}catch(Exception e) {
			throw new InvalidJSONQueryException("customer ERROR " + e.getMessage() + "-" + e.getClass() + " ->\n" + e.getStackTrace().toString());
		}
		@SuppressWarnings("unchecked")
		List<Object> results = query.getResultList();
		
        System.out.println("R>>>>>>>\n" + results);
        System.out.println("A>>>>>>>");
        try {
        System.out.println(("-> " + em.createQuery("SELECT i FROM Individual i", Individual.class).getResultList().get(0)));
        }catch(Exception e) {
        	System.out.println("#> " + e.getMessage() + " - " + e.getClass() + " - " + e.getCause() + " - " + e.getStackTrace());
        }
		return results;
	}
	
	/**
	 * Método encargado de crear el fichero CSV con los datos pertinentes al <b>primer</b> informe de Alemania.
	 * 
	 * @param path Ubicación donde guardar el fichero CSV
	 * @author Ignacio Lopezosa
	 * @return void
	 * @throws FailedInitialCSVException
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void informeAlemaniaInicio(String path) throws FailedInitialCSVException {
		
		String predicate = " WHERE C.fechaApertura > :fiveYearsAgo";
		Date fiveYearsAgo = new Date();
		fiveYearsAgo.setYear(fiveYearsAgo.getYear()-5);	// Today 5 years ago
		if(em == null)
          	throw new FailedInitialCSVException(" @@@ EntityManager is NULL @@@ ");
		Query querySegregada = em.createQuery("FROM Segregada C" + predicate);
//		Query queryReferencia= em.createQuery("FROM CuentaReferencia C" + predicate);
		querySegregada.setParameter("fiveYearsAgo", fiveYearsAgo, TemporalType.DATE);
//		queryReferencia.setParameter("fiveYearsAgo", fiveYearsAgo, TemporalType.DATE);
		List<Segregada> cuentasSegregadas  = querySegregada.getResultList();
//		List<CuentaReferencia> cuentasReferencia = queryReferencia.getResultList();
		
		try(CSVPrinter p = new CSVPrinter(new FileWriter(path), CSVFormat.DEFAULT)){
			
			p.printRecord("IBAN", "Last_Name", "First_Name", "Street", "City", "Post_Code", "Country", "Identification_Number", "Date_Of_Birth");	// HEADER
			
			for(Segregada s : cuentasSegregadas){
				
				String iban      = s.getIban();
				if(iban.length() < 5 || iban.length() > 34)
					throw new FailedInitialCSVException("iban NOT VALID");
				Cliente c = s.getCliente();
				if(c instanceof Individual) {
					String apellido  = ((Individual) c).getApellido();
					String nombre    = ((Individual) c).getNombre();
					String direccion = c.getDireccion();
					String ciudad    = c.getCiudad();
					String cp        = c.getCodigoPostal();
					String pais      = c.getPais();
					String identity  = c.getIdentificacion();
					Date nacimiento  = ((Individual) c).getFechaNacimiento();
					
					// CSV construction
					// Solo se comprueba que no sean null los atributos en los que `nullable = true`
					String birth;
					if(nacimiento == null)
						birth = N_E;
					else {
						birth = (nacimiento.getYear() + 1900) + "-" + nacimiento.getMonth() + "-" + nacimiento.getDay();
						if(nacimiento.getYear() < 0 || nacimiento.getYear() > new Date().getYear())
							throw new FailedInitialCSVException("nacimiento NOT VALID");
					}
					// Checks:
					if(!pais.equalsIgnoreCase("germany") && !pais.equalsIgnoreCase("DE") && !pais.equalsIgnoreCase("alemania"))
						throw new FailedInitialCSVException("pais NOT VALID");
					
					p.printRecord(iban, apellido, nombre, direccion, ciudad, cp, pais, identity, birth);
					p.println();
				}
				else if(c instanceof Empresa){
					
					Set<PersonaAutorizada> persAuts = ((Empresa) c).getAutorizacion().keySet();
					for(PersonaAutorizada pa : persAuts) {
						String apellidos = pa.getApellidos();
						String nombre    = pa.getNombre();
						String direccion = pa.getDireccion();
						String ciudad    = N_E;
						String cp        = N_E;
						String pais      = N_E;
						String identity  = pa.getIdentificacion();
						Date nacimiento  = pa.getFechaNacimiento();
						
						// CSV construction
						// Solo se comprueba que no sean null los atributos en los que `nullable = true`
						String birth;
						if(nacimiento == null)
							birth = N_E;
						else {
							birth = (nacimiento.getYear() + 1900) + "-" + nacimiento.getMonth() + "-" + nacimiento.getDay();
							// Checks:
							if(nacimiento.getYear() < 0 || nacimiento.getYear() > new Date().getYear())
							throw new FailedInitialCSVException("nacimiento NOT VALID");
						}
						
						p.printRecord(iban, apellidos, nombre, direccion, ciudad, cp, pais, identity, birth);
						p.println();
					}
				}
				else {
					String apellidos = N_E;
					String nombre    = N_E;
					String direccion = c.getDireccion();
					String ciudad    = c.getCiudad();
					String cp        = c.getCodigoPostal();
					String pais      = c.getPais();
					String identity  = c.getIdentificacion();
					String birth     = N_E;
					
					// Checks:
					if(!pais.equalsIgnoreCase("germany") && !pais.equalsIgnoreCase("DE") && !pais.equalsIgnoreCase("alemania"))
						throw new FailedInitialCSVException("pais NOT VALID");
					
					// CSV construction
					p.printRecord(iban, apellidos, nombre, direccion, ciudad, cp, pais, identity, birth);
					p.println();
				}
			}
			
//			for(CuentaReferencia cr : cuentasReferencia) {
//				
//				// Toda la información de Ebury ha sido obtenida del servicio de información de empresas del gobierno de Reino Unido
//				// https://find-and-update.company-information.service.gov.uk/company/07088713
//				String iban      = cr.getIban();
//				if(iban.length() < 5 || iban.length() > 34)
//					throw new FailedInitialCSVException("iban NOT VALID");
//				String apellido  = "noexistente";
//				String nombre    = "Ebury Partners UK Ltd";
//				String direccion = "Third Floor 80-100 Victoria Street, Cardinal Place";
//				String ciudad    = "London";
//				String cp        = "SW1E 5JL";
//				String pais      = "United Kingdom";
//				String identity  = "07088713";
//				String birth     = "2009-11-27";
//				
//				// CSV construction
//				p.printRecord(iban, apellido, nombre, direccion, ciudad, cp, pais, identity, birth);
//				p.println();
//			}
			
		}catch(IOException e) {
			throw new FailedInitialCSVException("IOException INTERRUPTED INITIAL CSV");
		}catch(ClassCastException e) {
			throw new FailedInitialCSVException("INITIAL CSV parameter COULD NOT BE CAST PROPERLY");
		}catch(Exception e) {
			throw new FailedInitialCSVException("INITIAL CSV ERROR");
		}
	}
	
	/**
	 * Método encargado de crear el fichero CSV con los datos pertinentes al informe <b>semanal</b> de Alemania.
	 * 
	 * @param path Ubicación donde guardar el fichero CSV
	 * @author Ignacio Lopezosa
	 * @return void
	 * @throws FailedPeriodicCSVException
	 */
	@Override
	public void informeAlemaniaPeriodico(String path) throws FailedPeriodicCSVException {
		
		String predicate = " WHERE c.fechaApertura > :fiveYearsAgo AND c.estado LIKE 'ACTIV_'";
		Date fiveYearsAgo = new Date();
		fiveYearsAgo.setYear(fiveYearsAgo.getYear()-5);	// Today 5 years ago
		if(em == null)
          	throw new FailedPeriodicCSVException(" @@@ EntityManager is NULL @@@ ");
		Query querySegregada = em.createQuery("SELECT c FROM Segregada c" + predicate);
//		Query queryReferencia= em.createQuery("FROM CuentaReferencia C" + predicate);
		querySegregada.setParameter("fiveYearsAgo", fiveYearsAgo, TemporalType.DATE);
//		queryReferencia.setParameter("fiveYearsAgo", fiveYearsAgo, TemporalType.DATE);
		List<Segregada> cuentasSegregadas  = querySegregada.getResultList();
//		List<CuentaReferencia> cuentasReferencia = queryReferencia.getResultList();
		System.out.println(querySegregada.toString() + "\ncs > " + cuentasSegregadas);
		System.out.println(querySegregada.toString() + "\nall > " + em.createQuery("SELECT c FROM Segregada c").getResultList());
		
		try(CSVPrinter p = new CSVPrinter(new FileWriter(path), CSVFormat.DEFAULT)) {
			
			p.printRecord("IBAN", "Last_Name", "First_Name", "Street", "City", "Post_Code", "Country", "identification_Number", "Date_Of_Birth");	// HEADER
			
			for(Segregada s : cuentasSegregadas){
				
				String iban      = s.getIban();
				if(iban.length() < 5 || iban.length() > 34)
					throw new FailedPeriodicCSVException("iban NOT VALID");
				Cliente c = s.getCliente();
				// Si Cliente NO ACTIVO pasa a siguiente cliente
				System.out.println("IPA > " + c);
				if(!c.getEstado().equalsIgnoreCase("ACTIVO") && !c.getEstado().equalsIgnoreCase("ACTIVA") && !c.getEstado().equalsIgnoreCase("ACTIVE"))
					continue;
				System.out.println("-- ESTOY ACTIVO --");
				if(c instanceof Individual) {
					System.out.println("-- SOY INDIVIDUAL --");
					String apellido  = ((Individual) c).getApellido();
					String nombre    = ((Individual) c).getNombre();
					String direccion = c.getDireccion();
					String ciudad    = c.getCiudad();
					String cp        = c.getCodigoPostal();
					String pais      = c.getPais();
					String identity  = c.getIdentificacion();
					Date nacimiento  = ((Individual) c).getFechaNacimiento();
					// CSV construction
					// Solo se comprueba que no sean null los atributos en los que `nullable = true`
					String birth;
					if(nacimiento == null)
						birth = N_E;
					else {
						birth = (nacimiento.getYear() + 1900) + "-" + nacimiento.getMonth() + "-" + nacimiento.getDay();
						if(nacimiento.getYear() < 0 || nacimiento.getYear() > new Date().getYear())
							throw new FailedPeriodicCSVException("nacimiento NOT VALID");
					}
					// Checks:
					if(!pais.equalsIgnoreCase("germany") && !pais.equalsIgnoreCase("DE") && !pais.equalsIgnoreCase("alemania"))
						continue;
					
					p.printRecord(iban, apellido, nombre, direccion, ciudad, cp, pais, identity, birth);
					p.println();
					System.out.println("-- IMPRESO --");
				}
				else if(c instanceof Empresa){
					System.out.println("-- SOY EMPRESA --");
					Set<PersonaAutorizada> persAuts = ((Empresa) c).getAutorizacion().keySet();
					if(persAuts == null) {
						System.out.println("-- NO TENGO PERSONAS AUTORIZADAS --");
						p.printRecord(iban, N_E, N_E, N_E, N_E, N_E, N_E, N_E, N_E);
						p.println();
						continue;
					}
					System.out.println("-- SÍ TENGO PERSONAS AUTORIZADAS --");
					for(PersonaAutorizada pa : persAuts) {
						System.out.println("\tPA > " + pa);
						// Si PersonaAutorizada relacionada con empresa NO ACTIVA pasa a siguiente PerosonaAutorizada
						if(pa.getEstado() == null || 
								(!pa.getEstado().equalsIgnoreCase("ACTIVO") && !pa.getEstado().equalsIgnoreCase("ACTIVA") && !pa.getEstado().equalsIgnoreCase("ACTIVE")))
							continue;
						String apellidos = pa.getApellidos();
						String nombre    = pa.getNombre();
						String direccion = pa.getDireccion();
						String ciudad    = N_E;
						String cp        = N_E;
						String pais      = N_E;
						String identity  = pa.getIdentificacion();
						Date nacimiento  = pa.getFechaNacimiento();
						
						// CSV construction
						// Solo se comprueba que no sean null los atributos en los que `nullable = true`
						String birth;
						if(nacimiento == null)
							birth = N_E;
						else {
							birth = (nacimiento.getYear() + 1900) + "-" + nacimiento.getMonth() + "-" + nacimiento.getDay();
							// Checks:
							if(nacimiento.getYear() < 0 || nacimiento.getYear() > new Date().getYear())
								throw new FailedPeriodicCSVException("nacimiento NOT VALID");
						}
						p.printRecord(iban, apellidos, nombre, direccion, ciudad, cp, pais, identity, birth);
						p.println();
					}
				}
				else {
					String apellidos = N_E;
					String nombre    = N_E;
					String direccion = c.getDireccion();
					String ciudad    = c.getCiudad();
					String cp        = c.getCodigoPostal();
					String pais      = c.getPais();
					String identity  = c.getIdentificacion();
					String birth     = N_E;
					
					// Checks:
					if(!pais.equalsIgnoreCase("germany") && !pais.equalsIgnoreCase("DE") && !pais.equalsIgnoreCase("alemania"))
						throw new FailedPeriodicCSVException("pais NOT VALID");
					
					// CSV construction
					p.printRecord(iban, apellidos, nombre, direccion, ciudad, cp, pais, identity, birth);
					p.println();
				}
			}
			
//			for(CuentaReferencia cr : cuentasReferencia) {
//				
//				// Toda la información de Ebury ha sido obtenida del servicio de información de empresas del gobierno de Reino Unido
//				// https://find-and-update.company-information.service.gov.uk/company/07088713
//				String iban      = cr.getIban();
//				if(iban.length() < 5 || iban.length() > 34)
//					throw new FailedInitialCSVException("iban NOT VALID");
//				String apellido  = "noexistente";
//				String nombre    = "Ebury Partners UK Ltd";
//				String direccion = "Third Floor 80-100 Victoria Street, Cardinal Place";
//				String ciudad    = "London";
//				String cp        = "SW1E 5JL";
//				String pais      = "United Kingdom";
//				String identity  = "07088713";
//				String birth     = "2009-11-27";
//				
//				// CSV construction
//				p.printRecord(iban, apellido, nombre, direccion, ciudad, cp, pais, identity, birth);
//				p.println();
//			}

		} catch(IOException e) {
			throw new FailedPeriodicCSVException("IOException INTERRUPTED PERIODIC CSV");
		} catch(ClassCastException e) {
			throw new FailedPeriodicCSVException("PERIODIC CSV parameter COULD NOT BE CAST PROPERLY");
		} catch(Exception e) {
			throw new FailedPeriodicCSVException("PERIODIC CSV ERROR" + e.getMessage() + e.getCause() + e.getClass() + e.getStackTrace());
		}
	}
}
