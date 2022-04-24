package es.uma.softcoders.eburyApp.ejb;

import java.util.List;

import javax.ejb.Local;

import es.uma.softcoders.eburyApp.exceptions.FailedCSVException;
import es.uma.softcoders.eburyApp.exceptions.InvalidJSONQueryException;

@Local
public interface Informes {
	public List<Object> informeHolanda(String json) throws InvalidJSONQueryException;
	public void informeAlemaniaPeriodico(String path);
	public void informeAlemaniaInicio(String path) throws FailedCSVException;
}