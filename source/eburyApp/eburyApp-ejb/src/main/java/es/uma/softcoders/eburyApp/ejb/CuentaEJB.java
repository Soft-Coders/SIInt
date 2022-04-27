package es.uma.softcoders.eburyApp.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import es.uma.softcoders.eburyApp.CuentaFintech;
import es.uma.softcoders.eburyApp.exceptions.CuentaExistenteException;
import es.uma.softcoders.eburyApp.exceptions.CuentaNoExistenteException;
import es.uma.softcoders.eburyApp.exceptions.DatosIncorrectosException;


/**
 * Session Bean implementation class CuentaEJB
 */
@Stateless
public class CuentaEJB implements GestionCuenta{

	@PersistenceContext(name="eburyAppEjb")
	private EntityManager em;

	@Override
	public void crearCuentaFintech(CuentaFintech cf) throws CuentaExistenteException, DatosIncorrectosException {
		if (em.find(CuentaFintech.class, cf.getIban()) != null) {
			throw new CuentaExistenteException("IBAN REGISTRADO, CUENTA FINTECH EXISTENTE");
		}
		if (cf.getIban() == null) {
			throw new DatosIncorrectosException("IBAN NULO, INVÁLIDO");
		}
		// TODO EXCEPCIONES
		em.persist(cf);
	}

	@Override
	public void cerrarCuentaFintech(String cuentafin) throws CuentaNoExistenteException {
		CuentaFintech cf = em.find(CuentaFintech.class, cuentafin);
		if (em.find(CuentaFintech.class, cf.getIban()) != null) {
			throw new CuentaNoExistenteException("IBAN NO REGISTRADO, CUENTA FINTECH INEXISTENTEx");
		}
		// TODO EXCEPCIONES
		em.remove(cf);
	}

}
