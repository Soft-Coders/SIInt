package es.uma.softcoders.eburyApp.ejb;

import javax.ejb.Local;

import es.uma.softcoders.eburyApp.Cliente;
import es.uma.softcoders.eburyApp.PersonaAutorizada;
import es.uma.softcoders.eburyApp.exceptions.CuentaNoCoincidenteException;
import es.uma.softcoders.eburyApp.exceptions.EmpresaExistenteException;
import es.uma.softcoders.eburyApp.exceptions.EmpresaNoEncontradaException;
import es.uma.softcoders.eburyApp.exceptions.PersonaAutorizadaExistenteException;
import es.uma.softcoders.eburyApp.exceptions.PersonaAutorizadaNoEncontradaException;



@Local
public interface GestionLogin {

	/*
	 * La aplicacion permitira acceder al sistema a un usuario administrativo de una forma diferente 
	 * a los usuarios naturales.
	 */
	public void loginAdmin(String cuenta, String clave);
}