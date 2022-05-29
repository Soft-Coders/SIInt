package es.uma.softcoders.eburyApp.ejb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.uma.softcoders.eburyApp.Cliente;
import es.uma.softcoders.eburyApp.Empresa;
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
        if (!u.getClave().equals(clave))
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
        if (!u.getClave().equals(clave))
            throw new CuentaNoCoincidenteException("Clave no coincidente");
        PersonaAutorizada pA = u.getPersonaAutorizada();
        Individual ind = u.getIndividual();
        
        /*if(pA != null){
            gestionCliente.comprobarAutorizado(pA.getId());
        }
        
        if(ind != null){
            gestionCliente.comprobarCliente(ind.getId());
        }*/
    }

    public Cliente esIndividual(String cuenta) throws EburyAppException{
        Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.usuario = :cuenta", Usuario.class);
    	q.setParameter("cuenta", cuenta);
    	List us = q.getResultList();
        if (us.isEmpty()) {
            throw new ClienteNoEncontradoException("Cuenta no existente");
        }        
        Usuario u = (Usuario) us.get(0);

        if(u.getIndividual()!=null){
            return u.getIndividual();
        }else{
            return null;
        }
    }

    public List<Empresa> empresasAutorizadas(String cuenta) throws EburyAppException{
        List<Empresa> empresas = new ArrayList<>();
        Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.usuario = :cuenta", Usuario.class);
    	q.setParameter("cuenta", cuenta);
    	List us = q.getResultList();
        if (us.isEmpty()) {
            throw new ClienteNoEncontradoException("Cuenta no existente");
        }    
        Usuario u = (Usuario) us.get(0);
        if(u.getPersonaAutorizada()!=null){
            Iterator i = u.getPersonaAutorizada().getAutorizacion().keySet().iterator();
            int cont = 0;
            while(i.hasNext()){
                empresas.add((Empresa) i.next());
            }
            return empresas;
        }else{
            return null;
        }
    }

    public boolean comprobacionAdministrativo(String id) throws EburyAppException{
        Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.usuario = :id", Usuario.class);
    	q.setParameter("id", id);
    	List us = q.getResultList();
        if (us.isEmpty()) {
            throw new ClienteNoEncontradoException("Cuenta no existente");
        }    
        Usuario u = (Usuario) us.get(0);
        return u.isEsAdministrativo();
    }

}