package es.uma.softcoders.eburyApp.ejb;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uma.softcoders.eburyApp.Cliente;
import es.uma.softcoders.eburyApp.Empresa;
import es.uma.softcoders.eburyApp.PersonaAutorizada;
import es.uma.softcoders.eburyApp.exceptions.EmpresaNoEncontradaException;
import es.uma.softcoders.eburyApp.exceptions.PersonaAutorizadaExistenteException;

@Stateless
public class AutorizadoEJB implements GestionAutorizado {
	
	@PersistenceContext(name="eburyApp")
	private EntityManager em;
	
	@Override
	public void agregarAutorizado(PersonaAutorizada p, String empresa, Character cuenta) throws EmpresaNoEncontradaException, PersonaAutorizadaExistenteException {
		Empresa empresaEntity = em.find(Empresa.class, empresa);
		if(empresaEntity == null) {
			throw new EmpresaNoEncontradaException("Empresa no encontrada");
		}
		
		PersonaAutorizada personaAutorizadaEntity = em.find(PersonaAutorizada.class, p.getId());
		if(personaAutorizadaEntity == null) {
			em.persist(personaAutorizadaEntity);
		} 
		
		Map<PersonaAutorizada, Character> listaPersonasAutorizadas = empresaEntity.getAutorizacion();
		
		if(listaPersonasAutorizadas.get(p) == null) {
			listaPersonasAutorizadas.put(p, cuenta);
			empresaEntity.setAutorizacion(listaPersonasAutorizadas);
		} else {
			throw new PersonaAutorizadaExistenteException("Persona autorizada ya registrada en la empresa");
		}
	}
	
	public void modificarAutorizado(PersonaAutorizada p, String autorizado) {
	
	}
	
	public void eliminarAutorizado(String personaAut) {
		
	}
	
	public void bajaAutorizado(String personaAut) {
		
	}
	
}
