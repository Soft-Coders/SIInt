package es.uma.softcoders.eburyApp.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
@Startup
public class InicializaBBDD {
	
	@PersistenceContext(unitName="eburyAppEjb")
	private EntityManager em;
	
	@PostConstruct
	public void inicializar() {
		
		if(em.find(null, em) != null)
			return;
		
	}
	
}
