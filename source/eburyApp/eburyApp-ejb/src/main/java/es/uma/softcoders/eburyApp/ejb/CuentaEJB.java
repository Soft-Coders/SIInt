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


	@PersistenceContext(unitName="eburyAppEjb")
	private EntityManager em;

	@Override
	public void crearCuentaFintech(CuentaFintech cf) throws EburyAppException {
		if (cf.getIban() == null) {
			System.out.println("IBAN NULL ->");
			throw new DatosIncorrectosException("IBAN NULO, INVÁLIDO");
		} else {
			System.out.println("IBAN NOT NULL");
			if (em.find(CuentaFintech.class, cf.getIban()) != null) {
				System.out.println("IBAN REGISTRADO ->");
				throw new CuentaExistenteException("IBAN REGISTRADO, CUENTA FINTECH EXISTENTE");
			}
			System.out.println("IBAN NO REGISTRADO");
			if (cf.getCliente() == null) {
				System.out.println("CLIENTE NULL ->");
				throw new DatosIncorrectosException("CLIENTE NULO, INVÁLIDO");
			} 
			System.out.println("CLIENTE NOT NULL");
			if (em.find(Cliente.class, cf.getCliente().getID()) == null) {
				System.out.println("CLIENTE INEXISTENTE ->");
				System.out.println(em.createQuery("SELECT c FROM Cliente c").getResultList());
				throw new ClienteInexistenteException("CLIENTE INEXISTENTE");
			}
			System.out.println("CLIENTE EXISTENTE");
		}
		System.out.println("PERSIST");
		em.persist(cf);
		System.out.println(" O-O ");
	}

	@Override
	public void cerrarCuentaFintech(String cuentafin) throws EburyAppException {
		if (cuentafin == null) {
			throw new DatosIncorrectosException("IBAN NULO, INVÁLIDO");
		} else {
			CuentaFintech cf = em.find(CuentaFintech.class, cuentafin);
			if (cf == null) {
				throw new CuentaNoExistenteException("IBAN NO REGISTRADO, CUENTA FINTECH INEXISTENTE");
			}
			cf.setEstado("INACTIVA");
		}
	}

}
