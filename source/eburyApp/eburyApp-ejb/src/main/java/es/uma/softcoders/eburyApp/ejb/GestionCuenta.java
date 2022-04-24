package es.uma.softcoders.eburyApp.ejb;

import javax.ejb.Local;
import es.uma.softcoders.eburyApp.CuentaFintech;

@Local
public interface GestionCuenta {
	
	/** Este método debe crear una cuenta fintech en la base de datos. 
	 * Los parámetros no nulos deben incluirse obligatoriamente, los nullables
	 * pueden encontrarse o no.
	 * 
	 * @param cf Cuenta a crear, incluye todos los datos que deben almacenarse.
	 * @author Marta Maleno Escudero
	 * */
	public void crearCuentaFintech(CuentaFintech cf);
	
	/** Este método debe cambiar el estado de una cuenta fintech a fin
	 * de darla de baja. En caso de que estuviera dada de baja previamente,
	 * no se realizará ningún cambio.
	 * 
	 * @param cuentafin IBAN de la Cuenta.
	 * @author Marta Maleno Escudero
	 *  */
	public void cerrarCuentaFintech(String cuentafin);
	
}
