package es.uma.softcoders.eburyApp.ejb;

import javax.ejb.Local;

import es.uma.softcoders.eburyApp.exceptions.InvalidJSONQueryException;

@Local
public interface Informes {
	public void informeHolanda(String json) throws InvalidJSONQueryException;
	public void informeAlemaniaInicio();
	public void informeAlemaniaPeriodico();
}