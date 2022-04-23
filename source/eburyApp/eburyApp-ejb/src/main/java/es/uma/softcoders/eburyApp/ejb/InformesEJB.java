package es.uma.softcoders.eburyApp.ejb;

import javax.ejb.Stateless;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import es.uma.softcoders.eburyApp.exceptions.InvalidJSONQueryException;

@Stateless
public class InformesEJB implements Informes{
	
	@Override
	public void informeHolanda(String json) throws InvalidJSONQueryException {
		Object file = JSONValue.parse(json);
		JSONObject jsonObj = (JSONObject) file;
		
		Object searchParameters = jsonObj.get("searchParameters");
		if(searchParameters == null)
			throw new InvalidJSONQueryException("searchParameters NOT FOUND");
		
		JSONObject spObj = (JSONObject) searchParameters;
		
		String questionType = (String) spObj.get("questionType");
		if(questionType == null)
			throw new InvalidJSONQueryException("questionType NOT FOUND");
		
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
