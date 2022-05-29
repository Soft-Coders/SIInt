package es.uma.softcoders.eburyApp.ejb;

import java.util.List;

import javax.ejb.Local;

import es.uma.softcoders.eburyApp.CuentaFintech;
import es.uma.softcoders.eburyApp.Empresa;
import es.uma.softcoders.eburyApp.exceptions.CuentaExistenteException;
import es.uma.softcoders.eburyApp.exceptions.CuentaNoExistenteException;
import es.uma.softcoders.eburyApp.exceptions.DatosIncorrectosException;
import es.uma.softcoders.eburyApp.exceptions.EburyAppException;

@Local
public interface GestionCuenta {
	
	/** Este método debe crear una cuenta fintech en la base de datos. 
	 * Los parámetros no nulos deben incluirse obligatoriamente, los nullables
	 * pueden encontrarse o no.
	 * 
	 * @param cf Cuenta a crear, incluye todos los datos que deben almacenarse.
	 * @throws EburyAppException 
	 * @author Marta Maleno Escudero
	 * */
	public void crearCuentaFintech(CuentaFintech cf, String tipo, Long usuario) throws CuentaExistenteException, DatosIncorrectosException, EburyAppException;
	
	/** Este método debe crear una cuenta fintech en la base de datos. 
	 * Los parámetros no nulos deben incluirse obligatoriamente, los nullables
	 * pueden encontrarse o no.
	 * 
	 * @param cf Cuenta a crear, incluye todos los datos que deben almacenarse.
	 * @throws EburyAppException 
	 * @author Marta Maleno Escudero
	 * */
	public void crearCuentaFintech(CuentaFintech cf, String tipo, Long usuario, String emp) throws EburyAppException;
	
	/** Este método debe cambiar el estado de una cuenta fintech a fin
	 * de darla de baja. En caso de que estuviera dada de baja previamente,
	 * no se realizará ningún cambio.
	 * 
	 * @param cuentafin IBAN de la Cuenta
	 * @throws EburyAppException  
	 * @author Marta Maleno Escudero
	 *  */
	public void cerrarCuentaFintech(String cuentafin) throws CuentaNoExistenteException, EburyAppException;
	
	/**
	 * Este método devuelve una lista con todas las cuentas fintech asociadas al
	 * usuario pasado por parámetro siempre que este tenga un Individual asociado
	 * @param usuario
	 * @return lista de cuentas fintech asociadas
	 */
	public List<CuentaFintech> getCuentasFintechPropias(Long usuario);
	
	/**
	 * Este método devuelve si el usuario con identificación pasada por parámetro es individual o no
	 * @param usuario
	 * @return
	 */
	public boolean esIndividual(Long usuario);
	
	/**
	 * Este método devuelve si el usuario con identificación pasada por parámetro es autorizado o no
	 * @param usuario
	 * @return
	 */
	public boolean esAutorizado(Long usuario);
	
	public String getIbanCuenta(String cuenta);
 	public CuentaFintech getCuentaFintech(String cuenta);
 	public List<Empresa> getEmpresasAutorizadas(Long usuario);
 	public List<CuentaFintech> getCuentasAutorizadas(String empresa);

	/**
	 * Este método devuelve una lista con todas las cuentas fintech asociadas al
	 * usuario pasado por parámetro siempre que este tenga un Individual asociado
	 * @param usuario
	 * @return lista de cuentas fintech asociadas
	 */

}
