package es.uma.softcoders.eburyApp.ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uma.softcoders.eburyApp.Cliente;
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
    public void loginAdmin(String cuenta, String clave) throws CuentaNoCoincidenteException{
        Usuario u = em.find(Usuario.class, cuenta);
        if (u == null)
            throw new ClienteNoEncontradoException("Cuenta no existente");
        if (!u.isEsAdministrativo())
            throw new UsuarioNoAdministrativoException("El usuario no es administrativo");
        if (u.getClave() != clave)
            throw new CuentaNoCoincidenteException("Clave no coincidente");
    }

    public void loginUsuario(String cuenta, String clave) throws CuentaNoCoincidenteException{
        Usuario u = em.find(Usuario.class, cuenta);
        if (u == null)
            throw new ClienteNoEncontradoException("Cuenta no existente");
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