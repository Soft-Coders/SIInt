package es.uma.softcoders.eburyApp.ejb;

import java.util.List;

import javax.ejb.Stateless;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import es.uma.softcoders.eburyApp.exceptions.InvalidJSONQueryException;

@Stateless
public class InformesEJB implements Informes{
	
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
		
		List<Object> query;
		// Elegir funcionamiento según "questionType"
		switch(questionType) {
		case "Product":
			query = product(spObj);
			break;
		case "Customer":
			query = customer(spObj);
			break;
		default:
			throw new InvalidJSONQueryException("questionType NOT VALID");	
		}
		return query;
	}

	/** Método privado que contiene la lógica para gestión de consultas de <b>Cuentas</b> a través de API REST. Usado en informes de Holanda.
	 * 
	 * @param searchParameters Objeto JSON que contiene los datos de la consulta
	 * @author Ignacio Lopezosa
	 * @return Lista de las <b>Cuentas</b> que cumplen las condiciones de la consulta
	 * */
	private List<Object> product(JSONObject searchParameters) {
		
		
		
		return null;
	}
	
	/**
	 * Método privado que contiene la lógica para gestión de consultas de <b>Clientes</b> a través de API REST. Usado en informes de Holanda.
	 * 
	 * @param searchParameters Objeto JSON que contiene los datos de la consulta
	 * @author Ignacio Lopezosa
	 * @return Lista de las <b>Clientes</b>que cumplen las condiciones de la consulta
	 */
	private List<Object> customer(JSONObject searchParameters) {
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
