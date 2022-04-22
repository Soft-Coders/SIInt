package es.uma.softcoders.eburyApp.ejb;

import es.uma.softcoders.eburyApp.CuentaFintech;

public interface GestionCuenta {
	
	/* Este método debe crear una cuenta fintech en la base de datos.
	 * @param cf Cuenta a crear, incluye todos los datos que deben almacenarse.
	 * Los parámetros no nulos deben incluirse obligatoriamente, los nullables
	 * pueden encontrarse o no.
	 * */
	public void crearCuentaFintech(CuentaFintech cf);
	
	/* Este método debe cambiar el estado de una cuenta fintech a fin
	 * de darla de baja. En caso de que estuviera dada de baja previamente,
	 * no se realizará ningún cambio.
	 * @param cuentafin IBAN de la Cuenta. */
	public void cerrarCuentaFintech(String cuentafin);
	
}
