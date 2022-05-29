package es.uma.softcoders.eburyApp.ejb;

import java.util.List;

import javax.ejb.Local;

import es.uma.softcoders.eburyApp.Cliente;
import es.uma.softcoders.eburyApp.Segregada;
import es.uma.softcoders.eburyApp.exceptions.FailedInitialCSVException;
import es.uma.softcoders.eburyApp.exceptions.FailedPeriodicCSVException;
import es.uma.softcoders.eburyApp.exceptions.InvalidJSONQueryException;

@Local
public interface GestionInformes {
	public List<Segregada> product(String json) throws InvalidJSONQueryException;
	public List<Object> customer(String json) throws InvalidJSONQueryException;
	public void informeAlemaniaPeriodico(String path) throws FailedPeriodicCSVException;
	public void informeAlemaniaInicio(String path) throws FailedInitialCSVException;
}