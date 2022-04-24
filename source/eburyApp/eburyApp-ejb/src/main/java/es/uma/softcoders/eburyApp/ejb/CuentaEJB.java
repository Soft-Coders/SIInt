package es.uma.softcoders.eburyApp.ejb;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uma.softcoders.eburyApp.CuentaFintech;


/**
 * Session Bean implementation class CuentaEJB
 */
@Stateless
public class CuentaEJB implements GestionCuenta{

	/* No sé para qué sirve el LOG, lo tiene el profe pero no se usa */
	private static final Logger LOG = Logger.getLogger(CuentaEJB.class.getCanonicalName());
	
	@PersistenceContext(name="eburyApp")
	private EntityManager em;

	@Override
	public void crearCuentaFintech(CuentaFintech cf) {
		/*try {
			if (em.find(CuentaFintech.class, cf.getIban()) != null) {
				throw new CuentaExistenteException;
			}
		} catch(CuentaExistenteException e){
			
		}*/
		
		// TODO EXCEPCIONES
		em.persist(cf);
	}

	@Override
	public void cerrarCuentaFintech(String cuentafin) {
		CuentaFintech cf = em.find(CuentaFintech.class, cuentafin);
		// TODO EXCEPCIONES
		em.remove(cf);
	}

}
