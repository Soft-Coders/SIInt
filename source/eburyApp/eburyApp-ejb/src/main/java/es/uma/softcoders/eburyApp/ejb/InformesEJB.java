package es.uma.softcoders.eburyApp.ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import es.uma.softcoders.eburyApp.exceptions.InvalidJSONQueryException;

@Stateless
public class InformesEJB implements Informes{
	
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
			
			// Buscar "searchParameters"
			Object searchParameters = jsonObj.get("searchParameters");
			if(searchParameters == null)
				throw new InvalidJSONQueryException("searchParameters NOT FOUND");
			String spStr     = (String) searchParameters;			// Get String
			Object spFile    = JSONValue.parseWithException(spStr);	// Parse to JSONObject
			JSONObject spObj = (JSONObject) spFile;					// Cast into JSONObject
			
			// Buscar "questionType"
			String questionType = (String) spObj.get("questionType");
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
			throw new InvalidJSONQueryException("json ERROR");
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
		
		Query query;
		try {
			String hql           = "FROM CuentaFintech CF";
			String status        = (String) spObj.get("status");
			String productNumber = (String) spObj.get("productNumber");
			
			if(status != null || productNumber != null) {
				hql.concat(" WHERE ");	
				int queryLength = hql.length();
				
				if(status.equalsIgnoreCase("active"))
					hql.concat("CF.estado = 'ACTIVO'");		//TODO Determinar nomenclatura de CuentaFintech.estado
				else if(status.equalsIgnoreCase("inactive"))
					hql.concat("CF.estado = 'INACTIVO'");	//TODO Determinar nomenclatura de CuentaFintech.estado
				else
					throw new InvalidJSONQueryException("status NOT VALID");
				
				if(productNumber != null) {
					if(hql.length() > queryLength)			// Se ha modificado query?
						hql.concat(" AND ");
					hql.concat("CF.iban = '" + productNumber + "'");
				}
			}
			query = em.createQuery(hql);
			
		}catch(ClassCastException e) {
			throw new InvalidJSONQueryException("product COULD NOT BE CAST PROPERLY");
		}catch(IllegalArgumentException e) {
			throw new InvalidJSONQueryException("product final query NOT VALID FORMAT");
		}catch(Exception e) {
			throw new InvalidJSONQueryException("product ERROR");
		}
		
		@SuppressWarnings("unchecked")
		List<Object> results = query.getResultList();
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
			String hql         = "FROM Cliente C WHERE ";
			int queryLength    = hql.length();
			String startPeriod = (String) spObj.get("startPeriod");
			String endPeriod   = (String) spObj.get("endPeriod");
			String nameStr     = (String) spObj.get("name");				// Get String
			String firstName   = null;
			String lastName    = null;
			if(nameStr != null) {
				Object nameFile    = JSONValue.parseWithException(nameStr);	// Parse to JSONObject
				JSONObject name    = (JSONObject) nameFile;					// Cast into JSONObject				
				firstName   = (String) name.get("fisrtName");	
				lastName    = (String) name.get("lastName");
			}
			String addressStr  = (String) spObj.get("address");					// Get String
			String street      = null;	
			String city        = null;
			String postalCode  = null;
			String country     = null;
			if(addressStr != null) {
				Object addressFile = JSONValue.parseWithException(addressStr);	// Parse to JSONObject
				JSONObject address = (JSONObject) addressFile;					// Cast into JSONObject
				street      = (String) address.get("street");	/* incluye calle y número */
				city        = (String) address.get("city");
				postalCode  = (String) address.get("postalCode");
				country     = (String) address.get("country");				
			}
			
			if(startPeriod != null) 
				hql.concat("C.fechaAlta = :startPeriod");
			if(endPeriod != null) {
				if(hql.length() > queryLength)
					hql.concat(" AND ");
				hql.concat("C.fechaBaja = :endPeriod");
			}
			if(nameStr != null) {
				if(firstName != null) {
					if(hql.length() > queryLength)
						hql.concat(" AND ");
					hql.concat("C.nombre = '" + firstName + "'");
				}
				if(lastName != null) {
					if(hql.length() > queryLength)
						hql.concat(" AND ");
					hql.concat("C.apellido = '" + lastName + "'");
				}
			}
			if(addressStr != null) {
				if(street != null) {
					if(hql.length() > queryLength)
						hql.concat(" AND ");
					hql.concat("C.direccion = '" + street + "'");
				}
				if(city != null) {
					if(hql.length() > queryLength)
						hql.concat(" AND ");
					hql.concat("C.ciudad = '" + city + "'");
				}
				if(postalCode != null) {
					try {
						Integer.parseInt(postalCode);	// Comprobando formato de número
					}catch(NumberFormatException e) {
						throw new InvalidJSONQueryException("address.postalCode NOT VALID");
					}
					if(hql.length() > queryLength)
						hql.concat(" AND ");
					hql.concat("C.codigoPostal = " + postalCode);
				}
				if(country != null) {
					if(hql.length() > queryLength)
						hql.concat(" AND ");
					hql.concat("C.pais = '" + country + "'");
				}
			}
			
			query = em.createQuery(hql);
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
					Date spDate = new Date(year, month, day);			// Deprecated -> Cambiar tipo a Calendar? TODO
					query.setParameter("startPeriod", spDate);
					
				}catch(NullPointerException e) {
					throw new InvalidJSONQueryException("startPeriod NOT VALID");
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
					Date epDate = new Date(year, month, day);	// Deprecated -> Cambiar tipo a Calendar? TODO
					query.setParameter("endPeriod", epDate);
					
				}catch(NullPointerException e) {
					throw new InvalidJSONQueryException("endPeriod NOT VALID");
				}
			}
		}catch(ParseException e) {
			throw new InvalidJSONQueryException("customer COULD NOT BE PARSED PROPERLY");
		}catch(ClassCastException e) {
			throw new InvalidJSONQueryException("customer COULD NOT BE CAST PROPERLY");
		}catch(IllegalArgumentException e) {
			throw new InvalidJSONQueryException("customer final query NOT VALID FORMAT");
		}catch(Exception e) {
			throw new InvalidJSONQueryException("customer ERROR");
		}
		@SuppressWarnings("unchecked")
		List<Object> results = query.getResultList();
		return results;
	}

	@Override
	public void informeAlemaniaInicio() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void informeAlemaniaPeriodico() {
		// TODO Auto-generated method stub
		
	}

}
