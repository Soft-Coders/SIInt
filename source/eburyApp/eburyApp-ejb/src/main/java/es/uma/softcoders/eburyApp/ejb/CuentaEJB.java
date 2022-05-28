package es.uma.softcoders.eburyApp.ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.uma.softcoders.eburyApp.Cliente;
import es.uma.softcoders.eburyApp.Cuenta;
import es.uma.softcoders.eburyApp.CuentaFintech;
import es.uma.softcoders.eburyApp.Empresa;
import es.uma.softcoders.eburyApp.Individual;
import es.uma.softcoders.eburyApp.PersonaAutorizada;
import es.uma.softcoders.eburyApp.Usuario;
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
	
	private static Long auxIBAN = 10000000000L;
	private static Long swift = 1234567890L;

	@PersistenceContext(unitName="eburyAppEjb")
	private EntityManager em;

	@Override
	public void crearCuentaFintech(CuentaFintech cf) throws EburyAppException {
		if (cf.getIban() == null) {
			cf.setIban(auxIBAN.toString());
			auxIBAN++;
		} 
			if (em.find(CuentaFintech.class, cf.getIban()) != null) {
				throw new CuentaExistenteException("IBAN REGISTRADO, CUENTA FINTECH EXISTENTE");
			}
			if (cf.getCliente() == null) {
				throw new DatosIncorrectosException("CLIENTE NULO, INVÁLIDO");
			} 
			if (em.find(Cliente.class, cf.getCliente().getId()) == null) {
				System.out.println(em.createQuery("SELECT c FROM Cliente c").getResultList());
				throw new ClienteInexistenteException("CLIENTE INEXISTENTE");
			}
		if (cf.getEstado()==null) cf.setEstado("ACTIVO");
		if (cf.getFechaApertura()==null) cf.setFechaApertura(new Date());
		if (cf.getSwift()==null) {
			cf.setSwift(swift.toString());
			swift ++;
		}
		em.persist(cf);
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
	
	public boolean esIndividual(String usuario) {
		boolean a = false;
		if (usuario != null) {
			Usuario u = em.find(Usuario.class, usuario);
			Individual ind = u.getIndividual();
			if (ind != null) {
				a = true;
			}
		}
		return a;
	}

	public boolean esAutorizado(String usuario) {
		boolean a = false;
		if (usuario != null) {
			Usuario u = em.find(Usuario.class, usuario);
			PersonaAutorizada pau = u.getPersonaAutorizada();
			if (pau != null) {
				a = true;
			}
		}
		return a;
	}
	
	public String getIbanCuenta(String cuenta) {
		Cuenta c = em.find(Cuenta.class, cuenta);
		return c.getIban();
		
	}
	
	public CuentaFintech getCuentaFintech(String cuenta) {
 		return em.find(CuentaFintech.class, cuenta);
 	}
	
	/**
	 * Este método devuelve una lista con todas las cuentas fintech asociadas al
	 * usuario pasado por parámetro siempre que este tenga un Individual asociado
	 * @param usuario
	 * @return lista de cuentas fintech asociadas
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<CuentaFintech> getCuentasFintechPropias(String usuario){
		Query query = null;
		if (usuario != null) {
			Usuario u = em.find(Usuario.class, usuario);
			Individual ind = u.getIndividual();
			if (ind != null) {
				query = em.createQuery("SELECT a FROM CUENTA_FINTECH a WHERE a.cliente LIKE :idindividual")
						.setParameter("idindividual", ind);
			}
		}
		return (List<CuentaFintech>)query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Empresa> getEmpresasAutorizadas(String usuario){
		Query query = null;
		if (usuario != null) {
			Usuario u = em.find(Usuario.class, usuario);
			PersonaAutorizada pau = u.getPersonaAutorizada();
			if (pau != null) {
				query = em.createQuery("SELECT a FROM EMPRESA a WHERE a.autorizacion LIKE :idpau")
						.setParameter("idpau", pau);
			}
		}
		return (List<Empresa>)query.getResultList();
	}
	public List<CuentaFintech> getCuentasAutorizadas(String empresa){
		Query query = null;
		if (empresa != null) {
 			Empresa emp = em.find(Empresa.class, empresa);
 				query = em.createQuery("SELECT a FROM CUENTA_FINTECH a WHERE a.cliente LIKE :idemp")
 						.setParameter("idemp", emp);
 		}
 		return (List<CuentaFintech>)query.getResultList();
	}
}
