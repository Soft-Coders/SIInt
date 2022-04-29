package es.uma.softcoders.eburyApp.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uma.softcoders.eburyApp.Cliente;
import es.uma.softcoders.eburyApp.CuentaFintech;
import es.uma.softcoders.eburyApp.exceptions.ClienteInexistenteException;
import es.uma.softcoders.eburyApp.exceptions.CuentaExistenteException;
import es.uma.softcoders.eburyApp.exceptions.CuentaNoExistenteException;
import es.uma.softcoders.eburyApp.exceptions.DatosIncorrectosException;
import es.uma.softcoders.eburyApp.exceptions.EburyAppException;


/**
 * Session Bean implementation class CuentaEJB
 */
@Stateless
public class CuentaEJB implements GestionCuenta{

	/* No sé para qué sirve el LOG, lo tiene el profe pero no se usa */	
<<<<<<< HEAD
	@PersistenceContext(name="eburyAppEjb")
=======
	@PersistenceContext(unitName="eburyAppEjb")

>>>>>>> 877ee0a5f16d8d18b6508e1848ff86347a28057d
	private EntityManager em;

	@Override
	public void crearCuentaFintech(CuentaFintech cf) throws EburyAppException {
		if (em.find(CuentaFintech.class, cf.getIban()) != null) {
			throw new CuentaExistenteException("IBAN REGISTRADO, CUENTA FINTECH EXISTENTE");
		}
		if (cf.getIban() == null) {
			throw new DatosIncorrectosException("IBAN NULO, INVÁLIDO");
		}
		if (cf.getCliente() == null) {
			throw new NullPointerException("CLIENTE NULO, INVÁLIDO");
		}
		if (em.find(Cliente.class, cf.getCliente()) == null) {
			throw new ClienteInexistenteException("CLIENTE INEXISTENTE");
		}
		em.persist(cf);
	}

	@Override
	public void cerrarCuentaFintech(String cuentafin) throws CuentaNoExistenteException {
		CuentaFintech cf = em.find(CuentaFintech.class, cuentafin);
		if (em.find(CuentaFintech.class, cf.getIban()) != null) {
			throw new CuentaNoExistenteException("IBAN NO REGISTRADO, CUENTA FINTECH INEXISTENTE");
		}
		em.remove(cf);
	}

}
