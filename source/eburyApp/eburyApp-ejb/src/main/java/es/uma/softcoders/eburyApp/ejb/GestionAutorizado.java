package es.uma.softcoders.eburyApp.ejb;

import javax.ejb.Local;

import es.uma.softcoders.eburyApp.PersonaAutorizada;
import es.uma.softcoders.eburyApp.exceptions.CuentaNoCoincidenteException;
import es.uma.softcoders.eburyApp.exceptions.EmpresaExistenteException;
import es.uma.softcoders.eburyApp.exceptions.EmpresaNoEncontradaException;
import es.uma.softcoders.eburyApp.exceptions.PersonaAutorizadaExistenteException;
import es.uma.softcoders.eburyApp.exceptions.PersonaAutorizadaNoEncontradaException;
import es.uma.softcoders.eburyApp.exceptions.UsuarioNoVinculadoException;



@Local
public interface GestionAutorizado {

	/*
	 * La aplicación permitirá a un administrativo añadir personas autorizadas
	 * a las cuentas que pertenezcan a cliente que son personas jurídicas. 
	 * Las personas autorizadas serán las que podrán entrar en la aplicación 
	 * para realizar operaciones con la cuenta.
	 */
	public void agregarAutorizado(Long id, PersonaAutorizada p, Long empresa, Character cuenta) throws EmpresaNoEncontradaException, PersonaAutorizadaExistenteException, CuentaNoCoincidenteException, EmpresaExistenteException, UsuarioNoVinculadoException;
	
	/*
	 * La aplicación permitirá a un administrativo modificar los datos de las 
	 * personas autorizadas a operar con cuentas de clientes que son personas jurídicas.
	 */
	public void modificarAutorizado(PersonaAutorizada p, Long autorizado) throws PersonaAutorizadaNoEncontradaException;
	
	/*
	 * La aplicación permitirá a un administrativo eliminar la relación que tenga una persona autorizada con
	 * las cuentas de una empresa.
	 */
	public void eliminarAutorizado(Long autorizado, Long empresa) throws PersonaAutorizadaNoEncontradaException, EmpresaNoEncontradaException;
	
	/*
	 * La aplicación permitirá a un administrativo dar de baja a personas autorizadas a
	 * operar con cuentas cuyos clientes sean personas jurídicas. Estas personas no se 
	 * eliminan del sistema, ya que podría ser necesario que la información conste para
	 * alguna auditoría o informe. Una persona autorizada que esté de baja no puede acceder 
	 * a la cuenta en la que se encontraba autorizada.
	 */
	public void bajaAutorizado(Long autorizado) throws PersonaAutorizadaNoEncontradaException;

}
