package es.uma.softcoders.eburyApp.ejb;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import es.uma.softcoders.eburyApp.Cliente;
import es.uma.softcoders.eburyApp.Empresa;
import es.uma.softcoders.eburyApp.Individual;
import es.uma.softcoders.eburyApp.PersonaAutorizada;
import es.uma.softcoders.eburyApp.Usuario;
import es.uma.softcoders.eburyApp.exceptions.AutorizadoNoValidoException;
import es.uma.softcoders.eburyApp.exceptions.ClienteExistenteException;
import es.uma.softcoders.eburyApp.exceptions.ClienteNoEncontradoException;
import es.uma.softcoders.eburyApp.exceptions.ClienteNoValidoException;
import es.uma.softcoders.eburyApp.exceptions.ClienteNuloException;
import es.uma.softcoders.eburyApp.exceptions.ContrasenaIncorrectaException;
import es.uma.softcoders.eburyApp.exceptions.CuentaNoCoincidenteException;
import es.uma.softcoders.eburyApp.exceptions.DatosIncorrectosException;
import es.uma.softcoders.eburyApp.exceptions.EburyAppException;
import es.uma.softcoders.eburyApp.exceptions.EmpresaExistenteException;
import es.uma.softcoders.eburyApp.exceptions.EmpresaNoEncontradaException;
import es.uma.softcoders.eburyApp.exceptions.EmpresaSinUsuarioException;
import es.uma.softcoders.eburyApp.exceptions.ObligatorioNuloException;
import es.uma.softcoders.eburyApp.exceptions.PersonaAutorizadaExistenteException;
@Stateless
public class ClienteEJB implements GestionCliente {
	
    @PersistenceContext(unitName="eburyAppEjb")
	private EntityManager em;

    @Override
    public List<Cliente> clientesInactivos() throws EburyAppException{
        Query q = em.createQuery("SELECT c FROM Cliente c WHERE c.estado = 'INACTIVO'");
        List<Cliente> cli = q.getResultList();
        if (cli.isEmpty())
            throw new ClienteNoEncontradoException("No hay cuentas inactivas");
        return cli;

    }

    @Override
    public List<Cliente> clientesActivos() throws EburyAppException{
        Query q = em.createQuery("SELECT c FROM Cliente c WHERE c.estado = 'ACTIVO'");
        List<Cliente> cli = q.getResultList();
        if (cli.isEmpty())
            throw new ClienteNoEncontradoException("No hay cuentas activas");
        return cli;

    }

    @Override
    public void altaCliente(Long cliente, Long usuario, String password) throws EburyAppException{
    	
        Cliente c = em.find(Cliente.class, cliente);
        if(c == null)
            throw new ClienteExistenteException("El cliente no existe");
    	
    	if(c instanceof Individual) {
    		Usuario user = em.find(Usuario.class, usuario);
        	if(password != user.getClave()) {
        		throw new ContrasenaIncorrectaException("Contraseña Incorrecta");
        	}
        	
        	Individual i = (Individual)c;
        	i.setEstado("ACTIVO");
        	em.merge(i);
    	}else{
    		Empresa e = (Empresa)c;
    		e.setEstado("ACTIVO");
    		em.merge(e);
    	}
    	
    }
    
    @Override
    public void registrarCliente(Cliente c, Long usuario, String password) throws EburyAppException {
    
    	if(c.getID()!= null) {
            Cliente clienteEntity = em.find(Cliente.class, c.getID());
            if(clienteEntity != null)
                throw new ClienteExistenteException("El cliente ya existe");
    	}

        if(c.getIdentificacion() == null)
            throw new ObligatorioNuloException("Identificacion nula");
        
        if(c.getTipo_cliente()==null)
            throw new ObligatorioNuloException("Tipo del cliente nulo");
        
        if(c.getEstado() == null)
            throw new ObligatorioNuloException("Estado del cliente nulo");
        
        if(c.getFecha_Alta() == null)
            throw new ObligatorioNuloException("Fecha de alta nula");
        
        if(c.getDireccion() == null)
            throw new ObligatorioNuloException("Direccion nula");
        
        if(c.getCiudad() == null)
            throw new ObligatorioNuloException("Ciudad nula");
        
        if(c.getCodigoPostal()==null)
            throw new ObligatorioNuloException("Codigo postal nulo");
        
        if(c.getPais()==null)
            throw new ObligatorioNuloException("Pais nulo");
         
        else if(c instanceof Individual){
        	
        	if(usuario == null) {
        		throw new DatosIncorrectosException("Usuario nulo");
        	}
        	
        	//Comprobamos que la clave es correcta
        	Usuario user = em.find(Usuario.class, usuario);
        	if(password != user.getClave()) {
        		throw new ContrasenaIncorrectaException("Contraseña Incorrecta");
        	}
        	
        	
        	//Comprobamos que los campos obligatorios de individual han sido rellenados
        	Individual i = (Individual) c;
        	if(i.getNombre()==null) {
        		throw new ObligatorioNuloException("Nombre de individual nulo");
        	}
        	if(i.getApellido()==null) {
        		throw new ObligatorioNuloException("Apellido de individual nulo");
        	}
        	
            i.setEstado("INACTIVO");
            i.setUsuario(user);
            
            em.persist(i);
            user.setIndividual(i);
        }
    }
    
    @Override
    public void registrarEmpresa(Cliente c, Long idPersAut, Character cuenta)throws EburyAppException{

    	if(c.getID()!= null) {
            Cliente clienteEntity = em.find(Cliente.class, c.getID());
            if(clienteEntity != null)
                throw new ClienteExistenteException("El cliente ya existe");
    	}

        if(c.getIdentificacion() == null)
            throw new ObligatorioNuloException("Identificacion nula");
        
        if(c.getTipo_cliente()==null)
            throw new ObligatorioNuloException("Tipo del cliente nulo");
        
        if(c.getEstado() == null)
            throw new ObligatorioNuloException("Estado del cliente nulo");
        
        if(c.getFecha_Alta() == null)
            throw new ObligatorioNuloException("Fecha de alta nula");
        
        if(c.getDireccion() == null)
            throw new ObligatorioNuloException("Direccion nula");
        
        if(c.getCiudad() == null)
            throw new ObligatorioNuloException("Ciudad nula");
        
        if(c.getCodigoPostal()==null)
            throw new ObligatorioNuloException("Codigo postal nulo");
        
        if(c.getPais()==null)
            throw new ObligatorioNuloException("Pais nulo");
        

        if(c instanceof Empresa){
            //Comprobamos que los campos obligatorios de empresa han sido rellenados
        	Empresa e = (Empresa) c;
            if(e.getRazonSocial()==null) {
            	throw new DatosIncorrectosException("Razon social de empresa nula");
            }
            
            e.setEstado("INACTIVO");
            em.persist(e);
    		
    		PersonaAutorizada personaAutorizadaEntity = em.find(PersonaAutorizada.class, idPersAut);
        
            if(cuenta != 'L' && cuenta != 'O') {
    			throw new CuentaNoCoincidenteException("El caracter de cuenta no es válido, prueba con L (Lectura) o con O (Operativa)");
    		}
    		
    		Map<PersonaAutorizada, Character> listaPersonasAutorizadas = e.getAutorizacion();
    		if(listaPersonasAutorizadas.get(personaAutorizadaEntity) == null) {
    			listaPersonasAutorizadas.put(personaAutorizadaEntity, cuenta);
    			e.setAutorizacion(listaPersonasAutorizadas);
    		} else {
    			throw new PersonaAutorizadaExistenteException("Persona autorizada ya registrada en la empresa");
    		}
    		
    		Map<Empresa, Character> listaEmpresas = personaAutorizadaEntity.getAutorizacion();
    		if(listaEmpresas.get(e) == null) {
    			listaEmpresas.put(e, cuenta);
    			personaAutorizadaEntity.setAutorizacion(listaEmpresas);
    		} else {
    			throw new EmpresaExistenteException("Empresa ya registrada en la persona autorizada");
    		}
    	}	
    }
    
    @Override
    public void modificarCliente(Cliente c, Long cliente) throws EburyAppException{
        Cliente clienteEntity = em.find(Cliente.class, cliente);
        if(clienteEntity == null){
            throw new ClienteNoEncontradoException("Cliente no encotrado");
        }
        
        if(c == null){
            throw new ClienteNuloException("Cliente nulo");
        }

        if(c.getIdentificacion() == null)
            throw new ObligatorioNuloException("Identificacion nula");
        
        if(c.getTipo_cliente()==null)
            throw new ObligatorioNuloException("Tipo del cliente nulo");
        
        if(c.getEstado() == null)
            throw new ObligatorioNuloException("Estado del cliente nulo");
        
        if(c.getDireccion() == null)
            throw new ObligatorioNuloException("Direccion nula");
        
        if(c.getCiudad() == null)
            throw new ObligatorioNuloException("Ciduad nula");
        
        if(c.getCodigoPostal()==null)
            throw new ObligatorioNuloException("Codigo postal nulo");
        
        if(c.getPais()==null)
            throw new ObligatorioNuloException("Pais nulo");

  
        clienteEntity.setIdentificacion(c.getIdentificacion());
        clienteEntity.setTipo_cliente(c.getTipo_cliente());
        clienteEntity.setEstado(c.getEstado());
        clienteEntity.setDireccion(c.getDireccion());
        clienteEntity.setCiudad(c.getCiudad());
        clienteEntity.setCodigoPostal(c.getCodigoPostal());
        clienteEntity.setPais(c.getPais());

        if(clienteEntity instanceof Empresa && c instanceof Empresa){
            Empresa empIn = (Empresa) c;
            Empresa empOut = (Empresa) clienteEntity;

            if(empIn.getRazonSocial() != null)            
                empOut.setRazonSocial(empIn.getRazonSocial());

            em.persist(empOut);

        }else if(clienteEntity instanceof Individual && c instanceof Individual){
            Individual indIn = (Individual) c;
            Individual indOut = (Individual) clienteEntity;

            if(indIn.getNombre() != null)
                indOut.setNombre(indIn.getNombre()); 

            if(indIn.getApellido() != null)
                indOut.setApellido(indIn.getApellido());
                
            if(indIn.getFechaNacimiento() != null)
                indOut.setFechaNacimiento(indIn.getFechaNacimiento());
            
            if(indIn.getUsuario() != null) 
            	indOut.setUsuario(indIn.getUsuario());
           
            em.persist(indOut);

        }else{
            em.persist(clienteEntity);
        }
                
    }

    @Override
    public void comprobarCliente(Long cliente) throws EburyAppException{
        Individual clienteEntity = em.find(Individual.class, cliente);

        if (clienteEntity.getTipo_cliente()=="EMPRESA")
            throw new ClienteNuloException("El cliente es una empresa");

        if(clienteEntity.getTipo_cliente()=="INDIVIDUAL"){
            
            if(clienteEntity.getUsuario()==null){
                throw new ClienteNoValidoException("El cliente no posee usuario");
            }
            if(clienteEntity.getEstado() != "ACTIVO"){
                throw new ClienteNoValidoException("El cliente no está activo");
            }
        }

    }

    @Override
    public void comprobarAutorizado(Long aut) throws EburyAppException{

        PersonaAutorizada pAut = em.find(PersonaAutorizada.class, aut);
        if (pAut.getEstado() != "ACTIVO")
            throw new AutorizadoNoValidoException("El autorizado NO está activo");

        Map<Empresa, Character> m = pAut.getAutorizacion();    
        if (m.isEmpty())
            throw new AutorizadoNoValidoException("La persona NO está autorizada");

        if (pAut.getUsuario()==null)
            throw new AutorizadoNoValidoException("La persona autorizada NO tiene usuario");
    }

    @Override
    public void bajaCliente(Long cliente) throws EburyAppException{
        Cliente clienteEntity = em.find(Cliente.class, cliente);

        if(clienteEntity == null){
            throw new ClienteNuloException("Cliente nulo");
        }

        if(clienteEntity.getEstado() == "INACTIVO"){
            throw new ClienteNuloException("El cliente ya está inactivo");
        }

        clienteEntity.setEstado("INACTIVO");
        em.persist(clienteEntity);
    }
    
}
