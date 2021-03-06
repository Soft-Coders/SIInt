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
    		throw new ContrasenaIncorrectaException("Contrase??a Incorrecta");
    	}
    	

    	
        p.setEstado("ACTIVO");
        p.setUsuario(user);
		em.persist(user);
        em.persist(p);
        

	}
	
	@Override
	public void autorizar(String paut, String empresa, char tipo) throws EburyAppException {
		if(tipo != 'O' && tipo != 'L')
			throw new EburyAppException("tipo no v??lido");
		if(empresa == null) 
			throw new EburyAppException("empresa nula");
		System.out.println("--------Antes del find-------");
		PersonaAutorizada p = em.find(PersonaAutorizada.class, Long.valueOf(paut));
		
		System.out.println("-------Antes de la query de empresa-------" + p.toString());
		Query q = em.createQuery("SELECT e FROM Empresa e WHERE e.identificacion = :fname ");
		q.setParameter("fname", empresa);	
		
		System.out.println("-----Antes de buscar las autorizaciones-------"+ q.toString());
		Empresa e = (Empresa)q.getSingleResult();
		Map<PersonaAutorizada, Character> empresaRelacion = e.getAutorizacion();
		
		System.out.println("-----Antes de a??adir la autorizacion-------"+ empresaRelacion.toString());
		empresaRelacion.put(p, tipo);
		//Map<Empresa, Character> autorizadoRelacion = p.getAutorizacion();
		//autorizadoRelacion.put(e, tipo);
		e.setAutorizacion(empresaRelacion);
		//p.setAutorizacion(autorizadoRelacion);
		
		System.out.println("------Antes del merge------");
		em.merge(e);
		System.out.println("------Antes del flush------");
		em.flush();
		System.out.println("----Despu??s del flush------");
		
		
	}
	
	
	
	// Si la persona autorizada existe se usa su id
	// con los datos pasados por par??metro en p
	/*public void agregarEmpresa(Long idPersAut, Empresa empresa, Character cuenta) throws EmpresaNoEncontradaException, PersonaAutorizadaExistenteException, CuentaNoCoincidenteException, EmpresaExistenteException, UsuarioNoVinculadoException{
		
		
		
		
		if(empresaEntity == null) {
			throw new EmpresaNoEncontradaException("Empresa no encontrada");
		}
		
		PersonaAutorizada personaAutorizadaEntity = em.find(PersonaAutorizada.class, idPersAut);
		
		if(personaAutorizadaEntity.getUsuario() == null) {
			throw new UsuarioNoVinculadoException("Debe de haber un usuario vinculado");
		}
		
		if(cuenta != 'L' && cuenta != 'O') {
			throw new CuentaNoCoincidenteException("El caracter de cuenta no es v??lido, prueba con L (Lectura) o con O (Operativa)");
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
	
	public void modificarAutorizado(PersonaAutorizada p, Long autorizado) throws EburyAppException{
		System.out.println("> HE LLEGADO AL METODO ");
		PersonaAutorizada personaAutorizadaEntity = em.find(PersonaAutorizada.class, autorizado);
		System.out.println("> HE HECHO FIND ");
		
		if(personaAutorizadaEntity == null) {
			throw new PersonaAutorizadaNoEncontradaException("La persona autorizada en cuesti??n no se encuentra en la base de datos");
		}
		System.out.println("> HAY PERSONA AUTORIZADA ");

		
		//Si alg??n atributo de la clase auxiliar no es nulo se da por hecho que tiene que ser cambiado
		if(p.getApellidos() != null) {
			personaAutorizadaEntity.setApellidos(p.getApellidos());
		}
		if(p.getDireccion() != null) {
			personaAutorizadaEntity.setDireccion(p.getDireccion());
		}

		System.out.println("> HE LLEGADO A LA 177");
		if(p.getIdentificacion() != null) {
			personaAutorizadaEntity.setIdentificacion(p.getIdentificacion());
		}
		if(p.getNombre() != null) {
			personaAutorizadaEntity.setNombre(p.getNombre());
		}
		if(p.getEstado() != null) {
			if(p.getEstado().equals("ACTIVO") || p.getEstado().equals("INACTIVO")) {
				personaAutorizadaEntity.setEstado(p.getEstado());
			}else{
				throw new EburyAppException("ESTADO NO VALIDO P-AUT");
			}
		}
		System.out.println("> ANTES DEL MERGE");

		em.merge(personaAutorizadaEntity);
		em.flush();
		System.out.println("> DESPUES DEL MERGE");

	}
	
	public void eliminarAutorizado(Long autorizado, Long empresa) throws PersonaAutorizadaNoEncontradaException, EmpresaNoEncontradaException{
		PersonaAutorizada personaAutorizadaEntity = em.find(PersonaAutorizada.class, autorizado);
		if(personaAutorizadaEntity == null) {
			throw new PersonaAutorizadaNoEncontradaException("La persona autorizada en cuesti??n no se encuentra en la base de datos");
		}
		
		Empresa empresaEntity = em.find(Empresa.class, empresa);
		if(empresaEntity == null) {
			throw new EmpresaNoEncontradaException("Empresa no encontrada");
		}
		
		//Cambia la relacion de la persona autorizada con la empresa
		Map<Empresa, Character> relacionAE = personaAutorizadaEntity.getAutorizacion();
		relacionAE.replace(empresaEntity, 'B');
		personaAutorizadaEntity.setAutorizacion(relacionAE);
		
		//Cambia la relaci??n de la empresa con la persona autorizada
		Map<PersonaAutorizada, Character> relacionEA = empresaEntity.getAutorizacion();
		relacionEA.replace(personaAutorizadaEntity, 'B');
		empresaEntity.setAutorizacion(relacionEA);
		
		//Si todas las relaciones con empresa las tiene bloqueadas se da de baja la persona autorizada autom??ticamente 
		boolean hayRelaciones = false;
		for(int i = 0; i<relacionAE.values().size();i++) {
			if(!relacionAE.values().iterator().next().equals('B')) {
				hayRelaciones = true;
			}
		}
		if(hayRelaciones == false) {
			bajaAutorizado(personaAutorizadaEntity.getId());
		}
		em.persist(personaAutorizadaEntity);
	}
	
	public void bajaAutorizado(Long autorizado) throws PersonaAutorizadaNoEncontradaException{
		PersonaAutorizada personaAutorizadaEntity = em.find(PersonaAutorizada.class, autorizado);
		if(personaAutorizadaEntity == null) {
			throw new PersonaAutorizadaNoEncontradaException("La persona autorizada en cuesti??n no se encuentra en la base de datos");
		}
		
		personaAutorizadaEntity.setEstado("INACTIVO");
		
		em.merge(personaAutorizadaEntity);
	}
	
}
