package es.uma.softcoders.eburyApp.ejb;

import javax.ejb.Local;

import es.uma.softcoders.eburyApp.exceptions.CuentaNoExistenteException;
import es.uma.softcoders.eburyApp.exceptions.DivisaInexistenteException;

@Local
public interface GestionTransaccion {
	
	/** Este método deberá realizar un cambio de divisas. 
	 * Un cambio de divisas es una transacción que relaciona dos CuentaReferencia 
	 * asociadas a la misma Pooled que poseen las divisas indicadas. Desde cuentaPool
	 * se buscará una cuentaReferencia relacionada con divOrigen y otra relacionada 
	 * con divDestino. Luego, se calculará el cambio de moneda mediante los atributos 
	 * cambioEur de cada divisa operando con el parámetro cantidad.
	 * En caso de que la cuenta Pooled no se relacione con una cuentaReferencia basada 
	 * en la divisa divDestino, se creará una nueva para poder realizar el cambio.
	 * 
	 * @param cuentaPool IBAN de la cuenta donde el cliente ha seleccionado realizar
	 * 		  el cambio de la divisa 
	 * @param divOrigen divisa desde la que se realiza el cambio
	 * @param divDestino divisa objetivo del cambio
	 * @param cantidad cuantía a cambiar, introducida en la divisa de origen
	 * @throws CuentaNoExistenteException 
	 * @throws DivisaInexistenteException 
	 * @author Marta Maleno Escudero
	 *  */
	public void cambioDivisa(String cuentaPool, String divOrigen, String divDestino, Double cantidad) throws CuentaNoExistenteException, DivisaInexistenteException;
}
