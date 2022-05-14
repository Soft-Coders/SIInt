package es.uma.softcoders.eburyApp.ejb;

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
import es.uma.softcoders.eburyApp.exceptions.UsuarioNoVinculadoException;

@Stateless
public class AutorizadoEJB implements GestionAutorizado {
	
	@PersistenceContext(unitName="eburyAppEjb")
	private EntityManager em;
	
	// Si la persona autorizada existe se usa su id, si no se crea en la base de datos
	// con los datos pasados por parámetro en p
	public void agregarAutorizado(Long idPersAut, PersonaAutorizada p, Long empresa, Character cuenta) throws EmpresaNoEncontradaException, PersonaAutorizadaExistenteException, CuentaNoCoincidenteException, EmpresaExistenteException, UsuarioNoVinculadoException{
		Empresa empresaEntity = em.find(Empresa.class, empresa);
		if(empresaEntity == null) {
			throw new EmpresaNoEncontradaException("Empresa no encontrada");
		}
		
		PersonaAutorizada personaAutorizadaEntity = em.find(PersonaAutorizada.class, idPersAut);
		if(personaAutorizadaEntity == null && p.getId()!= null && p.getIdentificacion()!= null && p.getNombre()!=null &&p.getApellidos()!=null && p.getDireccion()!=null) {
			p.setId(idPersAut);
			em.persist(p);
		}
		
		if(p.getUsuario() == null) {
			throw new UsuarioNoVinculadoException("Debe de haber un usuario vinculado");
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
	
	public void modificarAutorizado(PersonaAutorizada p, Long autorizado) throws PersonaAutorizadaNoEncontradaException{
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
			try {
				personaAutorizadaEntity.setEstado(p.getEstado());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void eliminarAutorizado(Long autorizado, Long empresa) throws PersonaAutorizadaNoEncontradaException, EmpresaNoEncontradaException{
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
			bajaAutorizado(personaAutorizadaEntity.getId());
		}
	}
	
	public void bajaAutorizado(Long autorizado) throws PersonaAutorizadaNoEncontradaException{
		PersonaAutorizada personaAutorizadaEntity = em.find(PersonaAutorizada.class, autorizado);
		if(personaAutorizadaEntity == null) {
			throw new PersonaAutorizadaNoEncontradaException("La persona autorizada en cuestión no se encuentra en la base de datos");
		}
		try {
			personaAutorizadaEntity.setEstado("INACTIVO");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
