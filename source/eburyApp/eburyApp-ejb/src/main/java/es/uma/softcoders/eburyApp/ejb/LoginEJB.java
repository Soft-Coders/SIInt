package es.uma.softcoders.eburyApp.ejb;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.uma.softcoders.eburyApp.Individual;
import es.uma.softcoders.eburyApp.PersonaAutorizada;
import es.uma.softcoders.eburyApp.Usuario;
import es.uma.softcoders.eburyApp.exceptions.ClienteNoEncontradoException;
import es.uma.softcoders.eburyApp.exceptions.CuentaNoCoincidenteException;
import es.uma.softcoders.eburyApp.exceptions.UsuarioNoAdministrativoException;

@Stateless
public class LoginEJB implements GestionLogin {

    @PersistenceContext(unitName="eburyAppEjb")
	private EntityManager em;

    @Override
    public void loginAdmin(String cuenta, String clave) throws CuentaNoCoincidenteException, ClienteNoEncontradoException{
    	if(em == null)
          	throw new CuentaNoCoincidenteException(" @@@ EntityManager is NULL @@@ ");
    	Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.usuario = :cuenta AND u.clave = :clave", Usuario.class);
    	q.setParameter("cuenta", cuenta);
    	q.setParameter("clave", clave);
    	ArrayList<Usuario> us = (ArrayList<Usuario>) q.getResultList();
    	System.out.println("-- a --\n" + us);
        if (us == null || us.isEmpty())
            throw new ClienteNoEncontradoException("Cuenta no existente");
        Usuario u = (Usuario) us.get(0);
        System.out.println("u > " + u);
        if (u == null)
            throw new ClienteNoEncontradoException("Cuenta no existente");
        if (!u.isEsAdministrativo())
            throw new UsuarioNoAdministrativoException("El usuario no es administrativo");
        if (u.getClave() != clave)
            throw new CuentaNoCoincidenteException("Clave no coincidente");
    }

    public void loginUsuario(String cuenta, String clave) throws CuentaNoCoincidenteException, ClienteNoEncontradoException{
    	if(em == null)
    		throw new CuentaNoCoincidenteException(" @@@ EntityManager is NULL @@@ ");
    	Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.usuario = :cuenta AND u.clave = :clave", Usuario.class);
    	q.setParameter("cuenta", cuenta);
    	q.setParameter("clave", clave);
    	ArrayList<Usuario> us = (ArrayList<Usuario>) q.getResultList();
    	System.out.println("-- u --\n" + us);
        if (us == null || us.isEmpty())
            throw new ClienteNoEncontradoException("Cuenta no existente");
        Usuario u = (Usuario) us.get(0);
        System.out.println("u > " + u);
        if (u.getClave() != clave)
            throw new CuentaNoCoincidenteException("Clave no coincidente");
        
        PersonaAutorizada pA = u.getPersonaAutorizada();
        Individual ind = u.getIndividual();
        GestionCliente gestionCliente = new ClienteEJB();
        if(pA != null){
            gestionCliente.comprobarAutorizado(pA.getId());
        }
        
        if(ind != null){
            gestionCliente.comprobarCliente(ind.getID());
        }
    }

}