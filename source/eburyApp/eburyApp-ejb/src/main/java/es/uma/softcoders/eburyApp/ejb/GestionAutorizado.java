package es.uma.softcoders.eburyApp.ejb;

import java.util.List;

import javax.ejb.Local;

import es.uma.softcoders.eburyApp.Cliente;
import es.uma.softcoders.eburyApp.Empresa;
import es.uma.softcoders.eburyApp.PersonaAutorizada;
import es.uma.softcoders.eburyApp.exceptions.CuentaNoCoincidenteException;
import es.uma.softcoders.eburyApp.exceptions.EburyAppException;
import es.uma.softcoders.eburyApp.exceptions.EmpresaExistenteException;
import es.uma.softcoders.eburyApp.exceptions.EmpresaNoEncontradaException;
import es.uma.softcoders.eburyApp.exceptions.PersonaAutorizadaExistenteException;
import es.uma.softcoders.eburyApp.exceptions.PersonaAutorizadaNoEncontradaException;
import es.uma.softcoders.eburyApp.exceptions.UsuarioNoVinculadoException;



@Local
public interface GestionAutorizado {

	
	public void altaAutorizado(Long id) throws EburyAppException;
	
	public void registrarAutorizado(PersonaAutorizada p, Long usuario, String password) throws EburyAppException;
	/*
	 * La aplicación permitirá a un administrativo añadir personas autorizadas
	 * a las cuentas que pertenezcan a cliente que son personas jurídicas. 
	 * Las personas autorizadas serán las que podrán entrar en la aplicación 
	 * para realizar operaciones con la cuenta.
	 */
	//public void agregarEmpresa(Long id, Empresa empresa, Character cuenta)throws EmpresaNoEncontradaException, PersonaAutorizadaExistenteException, CuentaNoCoincidenteException, EmpresaExistenteException, UsuarioNoVinculadoException;
	
	public List<PersonaAutorizada> autorizadosInactivos() throws EburyAppException;
	
    public List<PersonaAutorizada> autorizadosActivos() throws EburyAppException;
	
	/*
	 * La aplicación permitirá a un administrativo modificar los datos de las 
	 * personas autorizadas a operar con cuentas de clientes que son personas jurídicas.
	 */
	public void modificarAutorizado(PersonaAutorizada p, Long autorizado) throws EburyAppException;
	
	/*
	 * La aplicación permitirá a un administrativo eliminar la relación que tenga una persona autorizada con
	 * las cuentas de una empresa.
	 */
	public void eliminarAutorizado(Long autorizado, Long empresa) throws PersonaAutorizadaNoEncontradaException, EmpresaNoEncontradaException;
	
	public void autorizar(PersonaAutorizada p, Long empresa, Character tipo) throws EburyAppException;
	
	/*
	 * La aplicación permitirá a un administrativo dar de baja a personas autorizadas a
	 * operar con cuentas cuyos clientes sean personas jurídicas. Estas personas no se 
	 * eliminan del sistema, ya que podría ser necesario que la información conste para
	 * alguna auditoría o informe. Una persona autorizada que esté de baja no puede acceder 
	 * a la cuenta en la que se encontraba autorizada.
	 */
	public void bajaAutorizado(Long autorizado) throws PersonaAutorizadaNoEncontradaException;

}
