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

<<<<<<< HEAD
	/* No sé para qué sirve el LOG, lo tiene el profe pero no se usa */	
	@PersistenceContext(name="eburyAppEjb")
=======
	@PersistenceContext(unitName="eburyAppEjb")
>>>>>>> 86d2706b3406e2bbd88b8d0ddb1d9d8108755bba

	private EntityManager em;

	@Override
	public void crearCuentaFintech(CuentaFintech cf) throws EburyAppException {
		if (cf.getIban() == null) {
			throw new DatosIncorrectosException("IBAN NULO, INVÁLIDO");
		} else {
			if (em.find(CuentaFintech.class, cf.getIban()) != null) {
				throw new CuentaExistenteException("IBAN REGISTRADO, CUENTA FINTECH EXISTENTE");
			}
			if (cf.getCliente() == null) {
				throw new DatosIncorrectosException("CLIENTE NULO, INVÁLIDO");
			} else if (em.find(Cliente.class, cf.getCliente()) == null) {
				throw new ClienteInexistenteException("CLIENTE INEXISTENTE");
			}
		}
		em.persist(cf);
	}

	@Override
	public void cerrarCuentaFintech(String cuentafin) throws EburyAppException {
		if (cuentafin == null) {
			throw new DatosIncorrectosException("IBAN NULO, INVÁLIDO");
		} else {
			CuentaFintech cf = em.find(CuentaFintech.class, cuentafin);
			if (em.find(CuentaFintech.class, cf.getIban()) != null) {
				throw new CuentaNoExistenteException("IBAN NO REGISTRADO, CUENTA FINTECH INEXISTENTE");
			}
			cf.setEstado("INACTIVA");
		}
	}

}
