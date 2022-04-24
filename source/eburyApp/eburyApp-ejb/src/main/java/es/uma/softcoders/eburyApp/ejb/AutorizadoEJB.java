package es.uma.softcoders.eburyApp.ejb;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uma.softcoders.eburyApp.Empresa;
import es.uma.softcoders.eburyApp.PersonaAutorizada;
import es.uma.softcoders.eburyApp.exceptions.CuentaNoCoincidenteException;
import es.uma.softcoders.eburyApp.exceptions.EmpresaExistenteException;
import es.uma.softcoders.eburyApp.exceptions.EmpresaNoEncontradaException;
import es.uma.softcoders.eburyApp.exceptions.PersonaAutorizadaExistenteException;
import es.uma.softcoders.eburyApp.exceptions.PersonaAutorizadaNoEncontradaException;

@Stateless
public class AutorizadoEJB implements GestionAutorizado {
	
	@PersistenceContext(name="eburyAppEjb")
	private EntityManager em;
	
	@Override
	public void agregarAutorizado(PersonaAutorizada p, String empresa, Character cuenta) throws EmpresaNoEncontradaException, PersonaAutorizadaExistenteException, CuentaNoCoincidenteException, EmpresaExistenteException{
		Empresa empresaEntity = em.find(Empresa.class, empresa);
		if(empresaEntity == null) {
			throw new EmpresaNoEncontradaException("Empresa no encontrada");
		}
		
		PersonaAutorizada personaAutorizadaEntity = em.find(PersonaAutorizada.class, p.getId());
		if(personaAutorizadaEntity == null) {
			em.persist(p);
		} 
		
		if(cuenta != 'L' || cuenta != 'O') {
			throw new CuentaNoCoincidenteException("El caracter de cuenta no es válido, prueba con L (Lectura) o con O (Operativa)");
		}
		
		Map<PersonaAutorizada, Character> listaPersonasAutorizadas = empresaEntity.getAutorizacion();
		if(listaPersonasAutorizadas.get(p) == null) {
			listaPersonasAutorizadas.put(p, cuenta);
			empresaEntity.setAutorizacion(listaPersonasAutorizadas);
		} else {
			throw new PersonaAutorizadaExistenteException("Persona autorizada ya registrada en la empresa");
		}
		
		Map<Empresa, Character> listaEmpresas = personaAutorizadaEntity.getAutorizacion();
		if(listaEmpresas.get(empresaEntity) == null) {
			listaEmpresas.put(empresaEntity, cuenta);
			personaAutorizadaEntity.setAutorizacion(listaEmpresas);
		} else {
			throw new EmpresaExistenteException("Empresa ya registrada en la persona autorizada");
		}	
	}
	
	@Override
	public void modificarAutorizado(PersonaAutorizada p, String autorizado) throws PersonaAutorizadaNoEncontradaException{
		PersonaAutorizada personaAutorizadaEntity = em.find(PersonaAutorizada.class, autorizado);
		if(personaAutorizadaEntity == null) {
			throw new PersonaAutorizadaNoEncontradaException("La persona autorizada en cuestión no se encuentra en la base de datos");
		}
		
		//Si algún atributo de la clase auxiliar no es nulo se da por hecho que tiene que ser cambiado
		if(p.getApellidos() != null) {
			personaAutorizadaEntity.setApellidos(p.getApellidos());
		}
		if(p.getDireccion() != null) {
			personaAutorizadaEntity.setDireccion(p.getDireccion());
		}
		if(p.getFechaNacimiento() != null) {
			personaAutorizadaEntity.setFechaNacimiento(p.getFechaNacimiento());
		}
		if(p.getIdentificacion() != null) {
			personaAutorizadaEntity.setApellidos(p.getApellidos());
		}
		if(p.getUsuario() != null) {
			personaAutorizadaEntity.setUsuario(p.getUsuario());
		}
		if(p.getNombre() != null) {
			personaAutorizadaEntity.setNombre(p.getNombre());
		}
		if(p.getEstado() != null) {
			personaAutorizadaEntity.setEstado(p.getEstado());
		}
	}
	
	@Override
	public void eliminarAutorizado(String autorizado, String empresa) throws PersonaAutorizadaNoEncontradaException, EmpresaNoEncontradaException{
		PersonaAutorizada personaAutorizadaEntity = em.find(PersonaAutorizada.class, autorizado);
		if(personaAutorizadaEntity == null) {
			throw new PersonaAutorizadaNoEncontradaException("La persona autorizada en cuestión no se encuentra en la base de datos");
		}
		
		Empresa empresaEntity = em.find(Empresa.class, empresa);
		if(empresaEntity == null) {
			throw new EmpresaNoEncontradaException("Empresa no encontrada");
		}
		
		//Cambia la relacion de la persona autorizada con la empresa
		Map<Empresa, Character> relacionAE = personaAutorizadaEntity.getAutorizacion();
		relacionAE.replace(empresaEntity, 'B');
		personaAutorizadaEntity.setAutorizacion(relacionAE);
		
		//Cambia la relación de la empresa con la persona autorizada
		Map<PersonaAutorizada, Character> relacionEA = empresaEntity.getAutorizacion();
		relacionEA.replace(personaAutorizadaEntity, 'B');
		empresaEntity.setAutorizacion(relacionEA);
		
		//Si todas las relaciones con empresa las tiene bloqueadas se da de baja la persona autorizada automáticamente 
		boolean hayRelaciones = false;
		for(int i = 0; i<relacionAE.values().size();i++) {
			if(!relacionAE.values().iterator().next().equals('B')) {
				hayRelaciones = true;
			}
		}
		if(hayRelaciones == false) {
			bajaAutorizado(personaAutorizadaEntity.getId().toString());
		}
	}
	
	@Override
	public void bajaAutorizado(String autorizado) throws PersonaAutorizadaNoEncontradaException{
		PersonaAutorizada personaAutorizadaEntity = em.find(PersonaAutorizada.class, autorizado);
		if(personaAutorizadaEntity == null) {
			throw new PersonaAutorizadaNoEncontradaException("La persona autorizada en cuestión no se encuentra en la base de datos");
		}
		personaAutorizadaEntity.setEstado("BLOQUEADO");
	}
	
}
