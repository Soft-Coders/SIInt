package es.uma.softcoders.eburyApp.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import es.uma.softcoders.eburyApp.exceptions.InvalidJSONQueryException;

@Stateless
public class InformesEJB implements Informes{
	
	@PersistenceContext
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
		Object file = JSONValue.parse(json);
		JSONObject jsonObj = (JSONObject) file;
		
		// Buscar "searchParameters"
		Object searchParameters = jsonObj.get("searchParameters");
		if(searchParameters == null)
			throw new InvalidJSONQueryException("searchParameters NOT FOUND");
		JSONObject spObj = (JSONObject) searchParameters;
		
		// Buscar "questionType"
		String questionType = (String) spObj.get("questionType");
		if(questionType == null)
			throw new InvalidJSONQueryException("questionType NOT FOUND");
		
		List<Object> results;
		// Elegir funcionamiento según "questionType"
		switch(questionType) {
		case "Product":
			results = product(spObj);
			break;
		case "Customer":
			results = customer(spObj);
			break;
		default:
			throw new InvalidJSONQueryException("questionType NOT VALID");	
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
		
		String hql = "FROM CuentaFintech CF";
		String status = (String) spObj.get("status");
		String productNumber = (String) spObj.get("productNumber");
		
		if(status != null || productNumber != null) {
			hql.concat(" WHERE ");	
			int queryLength = hql.length();			// Longitud de query antes de WHERE
			
			if(status.equalsIgnoreCase("active"))
				hql.concat("CF.estado = 'ACTIVO'");	//TODO Determinar nomenclatura de CuentaFintech.estado
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
		
		Query query = em.createQuery(hql);
		List<Object> results = query.getResultList();
		return results;
	}
	
	/**
	 * Método privado que contiene la lógica para gestión de consultas de <b>Clientes</b> a través de API REST. Usado en informes de Holanda.
	 * 
	 * @param searchParameters Objeto JSON que contiene los datos de la consulta
	 * @author Ignacio Lopezosa
	 * @return Lista de las <b>Clientes</b>que cumplen las condiciones de la consulta
	 */
	private List<Object> customer(JSONObject spObj) {
		return null;
		// TODO Auto-generated method stub
		
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
