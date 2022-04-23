package es.uma.softcoders.eburyApp.ejb;

import java.util.List;

import javax.ejb.Local;

import es.uma.softcoders.eburyApp.exceptions.InvalidJSONQueryException;

@Local
public interface Informes {
	public List<Object> informeHolanda(String json) throws InvalidJSONQueryException;
	public void informeAlemaniaInicio();
	public void informeAlemaniaPeriodico();
}