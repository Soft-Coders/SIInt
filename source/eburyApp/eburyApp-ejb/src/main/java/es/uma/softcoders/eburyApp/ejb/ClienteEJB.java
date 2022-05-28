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
import es.uma.softcoders.eburyApp.exceptions.DatosIncorrectosException;
import es.uma.softcoders.eburyApp.exceptions.EburyAppException;
import es.uma.softcoders.eburyApp.exceptions.EmpresaSinUsuarioException;
import es.uma.softcoders.eburyApp.exceptions.ObligatorioNuloException;
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
    public void altaCliente(Cliente c) throws EburyAppException {
    	 
        Cliente clienteEntity = em.find(Cliente.class, c.getId());


        if(c.getIdentificacion() == null)
            throw new ObligatorioNuloException("Identificacion nula");
        
        if(c.getTipoCliente()==null)
            throw new ObligatorioNuloException("Tipo del cliente nulo");
        
        if(c.getEstado() == null)
            throw new ObligatorioNuloException("Estado del cliente nulo");
        
        if(c.getFechaAlta() == null)
            throw new ObligatorioNuloException("Fecha de alta nula");
        
        if(c.getDireccion() == null)
            throw new ObligatorioNuloException("Direccion nula");
        
        if(c.getCiudad() == null)
            throw new ObligatorioNuloException("Ciudad nula");
        
        if(c.getCodigoPostal()==null)
            throw new ObligatorioNuloException("Codigo postal nulo");
        
        if(c.getPais()==null)
            throw new ObligatorioNuloException("Pais nulo");
        

        if(clienteEntity instanceof Empresa){
            //Comprobamos que los campos obligatorios de empresa han sido rellenados

            clienteEntity.setEstado("ACTIVO");
            em.persist(clienteEntity);
        
//            Map<PersonaAutorizada, Character> m = e.getAutorizacion();
//            Set<PersonaAutorizada> pAs= m.keySet();
//	         if (pAs == null){
//	            throw new EmpresaSinUsuarioException("La empresa no tiene ninguna persona autorizada");
//	         }

            
        }else if(clienteEntity instanceof Individual){
        	
        	
        	//Comprobamos que los campos obligatorios de individual han sido rellenados
        	
        	
            clienteEntity.setEstado("ACTIVO");
            
            em.persist(clienteEntity);
        }
    }

    @Override
    public void modificarCliente(Cliente c, Long cliente) throws EburyAppException{
        if(cliente == null){
            throw new EburyAppException("NULL ID");
        }
        Cliente clienteEntity = em.find(Cliente.class, cliente);
        
        if(clienteEntity == null){
            throw new ClienteNoEncontradoException("Cliente no encotrado");
        }
        
        if(c == null){
            throw new ClienteNuloException("Cliente nulo");
        }

        if(c.getIdentificacion() == null)
            throw new ObligatorioNuloException("Identificacion nula");
        
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
                       
            em.persist(indOut);

        }else{
            em.persist(clienteEntity);
        }
                
    }

    @Override
    public void comprobarCliente(Long cliente) throws EburyAppException{
        Individual clienteEntity = em.find(Individual.class, cliente);

        if (clienteEntity.getTipoCliente()=="EMPRESA")
            throw new ClienteNuloException("El cliente es una empresa");

        if(clienteEntity.getTipoCliente()=="INDIVIDUAL"){
            
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
