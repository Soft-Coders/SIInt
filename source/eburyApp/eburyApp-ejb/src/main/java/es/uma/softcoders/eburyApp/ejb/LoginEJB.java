package es.uma.softcoders.eburyApp.ejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.uma.softcoders.eburyApp.Individual;
import es.uma.softcoders.eburyApp.PersonaAutorizada;
import es.uma.softcoders.eburyApp.Usuario;
import es.uma.softcoders.eburyApp.exceptions.ClienteNoEncontradoException;
import es.uma.softcoders.eburyApp.exceptions.CuentaNoCoincidenteException;
import es.uma.softcoders.eburyApp.exceptions.EburyAppException;
import es.uma.softcoders.eburyApp.exceptions.UsuarioNoAdministrativoException;

@Stateless
public class LoginEJB implements GestionLogin {

    @PersistenceContext(unitName="eburyAppEjb")
	private EntityManager em;
    
    @EJB 
    private GestionCliente gestionCliente;
    
    @Override
    public void loginAdmin(String cuenta, String clave) throws EburyAppException{
    	if(em == null)
          	throw new CuentaNoCoincidenteException(" @@@ EntityManager is NULL @@@ ");
    	Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.usuario = :cuenta", Usuario.class);
    	q.setParameter("cuenta", cuenta);
    	List us = q.getResultList();
    	System.out.println("-- uA --\n" + us);
    	System.out.println(us.isEmpty());
        if (us.isEmpty())
            throw new ClienteNoEncontradoException("Cuenta no existente");
    	System.out.println("-- HAY USUARIOS --");
        Usuario u = (Usuario) us.get(0);
        System.out.println("u > " + u);
        if (u == null)
            throw new ClienteNoEncontradoException("Cuenta no existente");
        if (!u.isEsAdministrativo())
            throw new UsuarioNoAdministrativoException("El usuario no es administrativo");
        if (u.getClave() != clave)
            throw new CuentaNoCoincidenteException("Clave no coincidente");
    }
    @Override
    public void loginUsuario(String cuenta, String clave) throws EburyAppException{
    	if(em == null)
    		throw new CuentaNoCoincidenteException(" @@@ EntityManager is NULL @@@ ");
    	
    	Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.usuario = :cuenta", Usuario.class);
    	q.setParameter("cuenta", cuenta);
    	List us = q.getResultList();
    	System.out.println(us.isEmpty());
    	
        if (us.isEmpty()) {
            throw new ClienteNoEncontradoException("Cuenta no existente");
        }
        
        Usuario u = (Usuario) us.get(0);
        if (u.getClave() != clave)
            throw new CuentaNoCoincidenteException("Clave no coincidente");
        PersonaAutorizada pA = u.getPersonaAutorizada();
        Individual ind = u.getIndividual();
        
        if(pA != null){
            gestionCliente.comprobarAutorizado(pA.getId());
        }
        
        if(ind != null){
            gestionCliente.comprobarCliente(ind.getID());
        }
    }

}