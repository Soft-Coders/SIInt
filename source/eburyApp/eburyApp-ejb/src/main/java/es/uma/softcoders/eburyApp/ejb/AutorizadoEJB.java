package es.uma.softcoders.eburyApp.ejb;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.uma.softcoders.eburyApp.Cliente;
import es.uma.softcoders.eburyApp.Empresa;
import es.uma.softcoders.eburyApp.Individual;
import es.uma.softcoders.eburyApp.PersonaAutorizada;
import es.uma.softcoders.eburyApp.Usuario;
import es.uma.softcoders.eburyApp.exceptions.ClienteExistenteException;
import es.uma.softcoders.eburyApp.exceptions.ClienteNoEncontradoException;
import es.uma.softcoders.eburyApp.exceptions.ContrasenaIncorrectaException;
import es.uma.softcoders.eburyApp.exceptions.CuentaNoCoincidenteException;
import es.uma.softcoders.eburyApp.exceptions.DatosIncorrectosException;
import es.uma.softcoders.eburyApp.exceptions.EburyAppException;
import es.uma.softcoders.eburyApp.exceptions.EmpresaExistenteException;
import es.uma.softcoders.eburyApp.exceptions.EmpresaNoEncontradaException;
import es.uma.softcoders.eburyApp.exceptions.ObligatorioNuloException;
import es.uma.softcoders.eburyApp.exceptions.PersonaAutorizadaExistenteException;
import es.uma.softcoders.eburyApp.exceptions.PersonaAutorizadaNoEncontradaException;
import es.uma.softcoders.eburyApp.exceptions.UsuarioNoVinculadoException;

@Stateless
public class AutorizadoEJB implements GestionAutorizado {
	
	@PersistenceContext(unitName="eburyAppEjb")
	private EntityManager em;
	
	public void agregarAutorizado() {
		
	}
	
	@Override
    public List<PersonaAutorizada> autorizadosInactivos() throws EburyAppException{
        Query q = em.createQuery("SELECT p FROM PersonaAutorizada p WHERE p.estado = 'INACTIVO'");
        List<PersonaAutorizada> cli = q.getResultList();
        if (cli.isEmpty())
            throw new ClienteNoEncontradoException("No hay cuentas inactivas");
        return cli;

    }

    @Override
    public List<PersonaAutorizada> autorizadosActivos() throws EburyAppException{
        Query q = em.createQuery("SELECT p FROM PersonaAutorizada p WHERE p.estado = 'ACTIVO'");
        List<PersonaAutorizada> cli = q.getResultList();
        if (cli.isEmpty())
            throw new ClienteNoEncontradoException("No hay cuentas activas");
        return cli;

    }
	
	@Override
	public void altaAutorizado(Long autorizado) throws EburyAppException{
	    	
		PersonaAutorizada p = em.find(PersonaAutorizada.class, autorizado);
		if(p == null)
			throw new EburyAppException("El autorizado no existe");
	 	
		p.setEstado("ACTIVO");
    	em.merge(p);
	    	
	}
	
	@Override
	public void registrarAutorizado(PersonaAutorizada p, Long usuario, String password)throws EburyAppException{
	    
    	if(p.getId()!= null) {
            PersonaAutorizada autorizado = em.find(PersonaAutorizada.class, p.getId());
            if(autorizado != null)
                throw new ClienteExistenteException("El cliente ya existe");
    	}

        if(p.getIdentificacion() == null)
            throw new ObligatorioNuloException("Identificacion nula");
        
        if(p.getNombre()==null)
            throw new ObligatorioNuloException("Nombre nulo");
        
        if(p.getEstado() == null)
            throw new ObligatorioNuloException("Estado del autorizado nulo");
        
        if(p.getApellidos()== null)
            throw new ObligatorioNuloException("Apellidos nulos");
        
        if(p.getDireccion() == null)
            throw new ObligatorioNuloException("Direccion nula");
        
        if(p.getUsuario()==null)
            throw new ObligatorioNuloException("Usuario nulo");
        
        if(usuario == null) {
    		throw new DatosIncorrectosException("Usuario nulo");
    	}
    	
    	//Comprobamos que la clave es correcta
    	Usuario user = em.find(Usuario.class, usuario);
    	if(password != user.getClave()) {
    		throw new ContrasenaIncorrectaException("Contraseña Incorrecta");
    	}
    	

    	
        p.setEstado("INACTIVO");
        p.setUsuario(user);
		em.persist(user);
        em.persist(p);
        

	}
	
	
	
	// Si la persona autorizada existe se usa su id
	// con los datos pasados por parámetro en p
	/*public void agregarEmpresa(Long idPersAut, Empresa empresa, Character cuenta) throws EmpresaNoEncontradaException, PersonaAutorizadaExistenteException, CuentaNoCoincidenteException, EmpresaExistenteException, UsuarioNoVinculadoException{
		
		
		
		
		if(empresaEntity == null) {
			throw new EmpresaNoEncontradaException("Empresa no encontrada");
		}
		
		PersonaAutorizada personaAutorizadaEntity = em.find(PersonaAutorizada.class, idPersAut);
		
		if(personaAutorizadaEntity.getUsuario() == null) {
			throw new UsuarioNoVinculadoException("Debe de haber un usuario vinculado");
		}
		
		if(cuenta != 'L' && cuenta != 'O') {
			throw new CuentaNoCoincidenteException("El caracter de cuenta no es válido, prueba con L (Lectura) o con O (Operativa)");
		}
		
		Map<PersonaAutorizada, Character> listaPersonasAutorizadas = empresaEntity.getAutorizacion();
		if(listaPersonasAutorizadas.get(personaAutorizadaEntity) == null) {
			listaPersonasAutorizadas.put(personaAutorizadaEntity, cuenta);
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
	}*/
	
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
		em.persist(personaAutorizadaEntity);
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
